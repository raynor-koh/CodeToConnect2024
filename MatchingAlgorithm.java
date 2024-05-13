import java.util.*;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchingAlgorithm {
  public String type; //Auction or Continuous
  public ArrayList<Order> orders = new ArrayList<>();
  public HashMap<Instrument, PriorityQueue<Order>> buyPQMap = new HashMap<>();
  public HashMap<Instrument, PriorityQueue<Order>> sellPQMap = new HashMap<>();
  public int startContinuousIndex = 0;
  public HashMap<Instrument, Double> instrumentPriceTimesVolume = new HashMap<>();
  public HashMap<Instrument, Double> instrumentVolume = new HashMap<>();

  public MatchingAlgorithm() {

  }

  public MatchingAlgorithm(String instrumentsCSV, String clientsCSV, String ordersCSV) {
    Instrument.readcsv(instrumentsCSV);
    new Client(clientsCSV);
    Order.readcsv(ordersCSV);
    for (Instrument x : Instrument.instrumentHashMap.values()) {
      PriorityQueue<Order> buyTemp = new PriorityQueue<>(new BuyOrderComparator());
      PriorityQueue<Order> sellTemp = new PriorityQueue<>(new SellOrderComparator());
      buyPQMap.put(x, buyTemp);
      sellPQMap.put(x, sellTemp);
    }
  }

  public void continuous() {
    for (int i=startContinuousIndex; i < orders.size(); i++) {
      Order order = orders.get(i);
      if (order == null) {
        break;
      }
      //add into PQ of either buy or sell
      //get the instrument id and run the match on instrument ID
      if (order.side) {
        buyPQMap.get(order.instrument).add(order);
      } else {
        sellPQMap.get(order.instrument).add(order);
      }
      Instrument instrument = order.instrument;
      this.match(instrument);
    }
  }

  public double findOpenPrice(Instrument instrument) {
    Object[] array = Order.orderHashSet.toArray();
    Order[] xs = (Order[]) array;
    Arrays.sort(xs, Comparator.comparing(x -> x.time));
    List<Order> ys = Stream.of(xs)
                                .filter(x -> x.instrument.equals(instrument))
                                .filter(x -> x.time.compareTo(LocalTime.of(9,30,0)) < 0)
            .collect(Collectors.toList());
    // key: price | val: quantity
    HashMap<Double, Integer> map = new HashMap<>();
    for (Order order : ys) {
      double currPrice = order.price;
      if (currPrice == Double.MAX_VALUE || currPrice == Double.MIN_VALUE) {
        continue;
      }
      ArrayList<Order> buy = new ArrayList<>();
      ArrayList<Order> sell = new ArrayList<>();
      for (Order t : ys) {
        if (t.side) {
          buy.add(t);
        } else {
          sell.add(t);
        }
      }
      buy.sort((x, y) -> (int) (y.price - x.price));
      sell.sort((x, y) -> (int) (x.price - y.price));

      // Calculate Max Orders Fulfilled

      // Find Buy Floor
      int buyFloorIndex = Integer.MIN_VALUE;
      for (int i = 0; i < buy.size(); i++) {
        if (currPrice > buy.get(i).price) {
          buyFloorIndex = i;
          break;
        }
      }

      // Find Sell Floor
      // Find Buy Floor
      int sellFloorIndex = Integer.MIN_VALUE;
      for (int i = 0; i < sell.size(); i++) {
        if (currPrice > sell.get(i).price) {
          sellFloorIndex = i;
          break;
        }
      }
      if (sellFloorIndex == Integer.MIN_VALUE || buyFloorIndex == Integer.MIN_VALUE) {
        map.put(currPrice, 0);
        continue;
      }
      int buyQty = 0;
      for (int i = 0; i < buyFloorIndex; i++ ) {
        buyQty += buy.get(i).quantity;
      }
      int sellQty = 0;
      for (int i = 0; i < sellFloorIndex; i++ ) {
        sellQty += sell.get(i).quantity;
      }
      map.put(currPrice, Math.min(buyQty, sellQty));
    }

    double openPrice = 0;
    int maxQty = Integer.MIN_VALUE;
    for (double price : map.keySet()) {
        if (map.get(price) > maxQty) {
          maxQty = map.get(price);
          openPrice = price;
        }
    }

    return openPrice;
  }

  public void match(Instrument instrument) {
    if (!buyPQMap.containsKey(instrument) || !sellPQMap.containsKey(instrument) || buyPQMap.get(instrument).isEmpty() || sellPQMap.get(instrument).isEmpty()) {
      return;
    }
    Order buyOrder = buyPQMap.get(instrument).poll();
    Order sellOrder = sellPQMap.get(instrument).poll();

    ArrayList<Order> tempPoppedOrders = new ArrayList<>();

    while (!buyPQMap.get(instrument).isEmpty() && !sellPQMap.get(instrument).isEmpty()) {
      if (buyOrder.price != Double.MAX_VALUE && sellOrder.price != Double.MIN_VALUE && buyOrder.price < sellOrder.price) {
        break;
      }
      while (buyOrder.price == Double.MAX_VALUE && sellOrder.price == Double.MIN_VALUE) {
        //compare time values
        if (buyOrder.time.compareTo(sellOrder.time) > 0) {
          while (!sellPQMap.get(instrument).isEmpty() && sellOrder.price == Double.MIN_VALUE) {
            tempPoppedOrders.add(sellOrder);
            sellOrder = sellPQMap.get(instrument).poll();          
          }
        } else {
          while (!buyPQMap.get(instrument).isEmpty() && buyOrder.price == Double.MAX_VALUE) {
            tempPoppedOrders.add(buyOrder);
            buyOrder = buyPQMap.get(instrument).poll();          
          }
        }
      }
      double dealingPrice = 0.0;
      double dealingQuantity = 0.0;
      if (buyOrder.price >= sellOrder.price) {
        //compare time
        
        if (buyOrder.time.compareTo(sellOrder.time) > 0) {
          dealingPrice = sellOrder.price;
        } else {
          dealingPrice = buyOrder.price;
        }
        dealingQuantity = Math.min(buyOrder.quantity, sellOrder.quantity);
        buyOrder.quantity -= dealingQuantity;
        sellOrder.quantity -= dealingQuantity;
      }
      if (buyOrder.quantity == 0) {
        buyOrder = !buyPQMap.get(instrument).isEmpty() ? buyPQMap.get(instrument).poll() : null;
      }
      if (sellOrder.quantity == 0) {
        sellOrder = !sellPQMap.get(instrument).isEmpty() ? sellPQMap.get(instrument).poll() : null;
      }

      this.instrumentVolume.put(instrument, this.instrumentVolume.getOrDefault(instrument, 0.0) + dealingQuantity);
      this.instrumentPriceTimesVolume.put(instrument, this.instrumentPriceTimesVolume.getOrDefault(instrument, 0.0) + dealingQuantity * dealingPrice);
    }
    
    if (buyOrder != null && buyOrder.quantity != 0.0) {
      buyPQMap.get(instrument).add(buyOrder);
    } 
    if (sellOrder != null && sellOrder.quantity != 0.0) {
      sellPQMap.get(instrument).add(sellOrder);
    }
    for (int i = 0; i < tempPoppedOrders.size(); i++) {
      Order order = tempPoppedOrders.get(i);
      if (order.side) {
        buyPQMap.get(instrument).add(order);
      } else {
        sellPQMap.get(instrument).add(order);
      }
    }
  }

  public boolean checkInstrumentExists(Order order) {
    return Instrument.instrumentHashMap.containsKey(order.instrument.id);
  }

  public boolean checkCurrency(Order order) {
    return order.client.currencies.contains(order.instrument.currency);
  }

  public boolean checkLotSize(Order order) {
    return order.quantity % order.instrument.lotSize == 0 ? true : false;
  }

  public boolean checkPosition(Order order) {
    //no position check is needed for client, so pass this check
    if (!order.client.positionCheck) {
      return true;
    }
    //if the client doesn't own the instrument at all or if the quantity they own is less than the required quantity of the order
    if (!order.client.position.containsKey(order.instrument) || (order.client.position.get(order.instrument) < order.quantity)) {
      return false;
    }
    return true;
  }

  public void openAuctionMatch(Instrument instrument) {
    double openPrice = findOpenPrice(instrument);
    List<Order> auction = Stream.of((Order[]) Order.orderHashSet.toArray())
            .filter(x -> x.instrument.equals(instrument))
            .filter(x -> x.time.compareTo(LocalTime.of(9,30,0)) < 0)
            .collect(Collectors.toList());
  }

  public static void main(String[] args) {
    MatchingAlgorithm x = new MatchingAlgorithm("C:\\Users\\USER\\Documents\\BOFA\\CodeToConnect2024\\example-set\\input_instruments.csv", "C:\\Users\\USER\\Documents\\BOFA\\CodeToConnect2024\\example-set\\input_clients.csv", "C:\\Users\\USER\\Documents\\BOFA\\CodeToConnect2024\\example-set\\input_orders.csv");
    for (Order order : Order.orderHashSet) {
      if (!x.checkInstrumentExists(order)) {
        ExchangeReportGenerator.addFailedPolicy(order.id, "REJECTED - INSTRUMENT NOT FOUND");
      } else if (!x.checkCurrency(order)) {
        ExchangeReportGenerator.addFailedPolicy(order.id, "REJECTED - MISMATCH CURRENCY");
      } else if (!x.checkLotSize(order)) {
        ExchangeReportGenerator.addFailedPolicy(order.id, "REJECTED - INVALID LOT SIZE");
      } else if (!x.checkPosition(order)) {
        ExchangeReportGenerator.addFailedPolicy(order.id, "REJECTED - POSITION CHECK FAILED");
      }
    }
    ExchangeReportGenerator.generateCSVFile();
  }
}
