import java.util.*; 

public class MatchingAlgorithm {
  public String type; //Auction or Continuous
  public ArrayList<Order> orders;
  public Instruments allInstruments;
  public Clients allClients;
  public Orders addOrders;

  public MatchingAlgorithm(String instrumentsCSV, String clientsCSV, String ordersCSV) {
    this.allInstruments = Instruments(instrumentsCSV);
    this.allClients = Clients(clientsCSV);
    this.addOrders = Orders(ordersCSV);
  }



  
  
}
