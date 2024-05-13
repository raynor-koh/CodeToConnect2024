import java.util.*; 

public class MatchingAlgorithm {
  public String type; //Auction or Continuous
  public ArrayList<Order> orders;
  public Instruments allInstruments;
  public HashMap<String, Client> allClients;
  public Orders allOrders;

  

  public PriorityQueue<Order> buyOrders;
  public PriorityQueue<Order> sellOrders;

  public MatchingAlgorithm(String instrumentsCSV, String clientsCSV, String ordersCSV) {
    Instrument(instrumentsCSV);
    Client(clientsCSV);
    Order(ordersCSV);
    
    this.allClients = Client.clients;
    this.allInstruments = new Instruments(instrumentsCSV);
    this.allClients = 
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
      createTX(bid, ask, txPrice, txQuantity);

      if (bid.quantity < ask.quantity) {
        ask.quantity -= bid.quantity;
        sellOrders.add(ask);
      } else if (bid.quantity == ask.quantity) {
        // Removed from PQ
      } else if (bid.quantity > ask.quantity) {
        bid.quantity -= ask.quantity;
        buyOrders.add(bid);
      }
    }

  }
  
}
