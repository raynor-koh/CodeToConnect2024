import java.time.LocalTime;
public class Order {
    private LocalTime time;
    private String orderID;
    private Client client;
    private Instrument instrument;
    private boolean side;   // True: Buyer, False: Seller
    private int price;
    private int quantity;

    public Order(LocalTime time, String orderID, Instrument instrument, boolean side, int price, int quantity) {
        this.time = time;
        this.orderID = orderID;
        this.instrument = instrument;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }
    

}
