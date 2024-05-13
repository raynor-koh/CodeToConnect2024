import java.time.LocalTime;
public class Order {
    LocalTime time;
    String orderID;
    Client client;
    Instrument instrument;
    boolean side;   // True: Buyer, False: Seller
    double price;
    int quantity;

    public Order(LocalTime time, String orderID, Instrument instrument, boolean side, int price, int quantity) {
        this.time = time;
        this.orderID = orderID;
        this.instrument = instrument;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }
    

}
