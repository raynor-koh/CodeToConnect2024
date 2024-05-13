import java.util.*; 

public class MatchingAlgorithm {
  public String type; //Auction or Continuous
  public ArrayList<Order> orders;
  public HashSet<Instrument> allInstruments;
  public HashMap<String, Client> allClients;
  public Orders allOrders;

  public PriorityQueue<Order> buyOrders;
  public PriorityQueue<Order> sellOrders;

  public MatchingAlgorithm(String instrumentsCSV, String clientsCSV, String ordersCSV) {
    Instrument(instrumentsCSV);
    Client(clientsCSV);
    Order(ordersCSV);
    
    this.allClients = Client.clients;
    this.allInstruments = Instrument.instrumentHashSet;
    this.allOrders = 
    this.buyOrders = new PriorityQueue<>(new BuyOrderComparator());
    this.sellOrders = new PriorityQueue<>(new SellOrderComparator());
  }

  public void addOrder(Order order) {
    if (order.side) {
      this.buyOrders.add(order);
    } else {
      this.sellOrders.add(order);
    }
  }

  public void addAllOrders(Orders allOrders) {
    for (Order order : allOrders.orders.values()) {
      addOrder(order);
    }
  }

  public void createTX(Order bid, Order ask, double price, int quantity){}

  public void match() {
    Order bid = buyOrders.poll();
    Order ask = sellOrders.poll();

    double txPrice = ask.price;
    int txQuantity;

    if (bid.quantity < ask.quantity) {
      txQuantity = bid.quantity;
    } else {
      txQuantity = ask.quantity;
    }

    if (bid.price < ask.price) {
      // TODO: IDK WHY PASS
    } else if (bid.price == ask.price) {
      createTx();
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
