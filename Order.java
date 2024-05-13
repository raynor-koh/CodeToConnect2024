import java.time.LocalTime;
public class Order {
    private LocalTime time;
    private String orderID;
    private Client client;
    private Instrument instrument;
    private boolean side;   // True: Buyer, False: Seller
    private int price;
    private int quantity;
}
