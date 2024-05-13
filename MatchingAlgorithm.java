import java.util.*; 

public class MatchingAlgorithm {
  public String type; //Auction or Continuous
  public ArrayList<Order> orders;
  public Instruments allInstruments;
  public Clients allClients;
  public Orders addOrders;

  public PriorityQueue<Order> buyOrders;
  public PriorityQueue<Order> sellOrders;

  public MatchingAlgorithm(String instrumentsCSV, String clientsCSV, String ordersCSV) {
    this.allInstruments = new Instruments(instrumentsCSV);
    this.allClients = new Clients(clientsCSV);
    this.addOrders = new Orders(ordersCSV);
    this.buyOrders = new PriorityQueue<>(new BuyOrderComparator());
    this.buyOrders = new PriorityQueue<>(new SellOrderComparator());
  }

  
  
}
