import java.util.HashMap;
public class Client {

    private String[] currency;
    private boolean positionCheck;
    private int rating;
    private HashMap<Instrument, Integer> position;

    public Client(String[] currency, boolean positionCheck, int rating) {
        this.currency = currency;
        this.positionCheck = positionCheck;
        this.rating = rating;
        this.position = new HashMap<>();
    }
}
