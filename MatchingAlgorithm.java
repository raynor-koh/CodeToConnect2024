import java.util.*; 

public class MatchingAlgorithm {
  public String type; //Auction or Continuous
  public ArrayList<Order> orders;
  public HashMap<Instrument, PriorityQueue<Order>> buyPQMap;
  public HashMap<Instrument, PriorityQueue<Order>> sellPQMap;
  public int startContinuousIndex;

  public MatchingAlgorithm(String instrumentsCSV, String clientsCSV, String ordersCSV) {
    Instrument.readcsv(instrumentsCSV);

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
      Instrument instrument = order.instrument;
      this.match(instrument);
    }
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
      if (buyOrder.price >= sellOrder.price) {
        //compare time
        double dealingPrice;
        int dealingQuantity;
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
      
    }
    
  }

  public boolean checkInstrumentExists(Order order) {
    return this.allInstruments.contains(order.instrument);
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

  
}
