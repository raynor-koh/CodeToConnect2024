import java.time.LocalTime;
public class Order {
<<<<<<< Updated upstream
    LocalTime time;
    String orderID;
    Client client;
    Instrument instrument;
    boolean side;   // True: Buyer, False: Seller
    double price;
    int quantity;
=======
    public LocalTime time;
    public String orderID;
    public Client client;
    public Instrument instrument;
    public boolean side;   // True: Buyer, False: Seller
    public int price;
    public int quantity;

    public Order() {

    }
>>>>>>> Stashed changes

    public Order(LocalTime time, String orderID, Instrument instrument, boolean side, int price, int quantity) {
        this.time = time;
        this.orderID = orderID;
        this.instrument = instrument;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }
}
