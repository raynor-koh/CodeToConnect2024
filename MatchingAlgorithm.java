import java.util.*; 

public class MatchingAlgorithm {
  public String type; //Auction or Continuous
  public ArrayList<Order> orders;


  public MatchingAlgorithm(String instrumentsCSV, String clientsCSV, String ordersCSV) {
    Instrument.readcsv(instrumentsCSV);

    for (Instrument x : Instrument.instrumentHashMap.values()) {
      PriorityQueue<Order> buyTemp = new PriorityQueue<>(new BuyOrderComparator());
      PriorityQueue<Order> sellTemp = new PriorityQueue<>(new SellOrderComparator());
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
