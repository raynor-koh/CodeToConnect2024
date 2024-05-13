import java.util.HashMap;
public class Client implements Comparable<Client> {

    String[] currency;
    boolean positionCheck;
    int rating;
    HashMap<Instrument, Integer> position;

    //this constructor is for taking in a CSV as file
    public Client (String csvFilename) {
        //initializes a HashMap of all the clients
    }


    public Client(String[] currency, boolean positionCheck, int rating) {
        this.currency = currency;
        this.positionCheck = positionCheck;
        this.rating = rating;
        this.position = new HashMap<>();
    }

    @Override
    public int compareTo(Client comparable) {
        return this.rating - comparable.rating;
    }

    public void addInstrument(Instrument instrument, int quantity) {
        if (!this.position.containsKey(instrument)) {
            this.position.put(instrument, quantity);
        } else {
            int currQuantity = this.position.get(instrument);
            this.position.put(instrument, currQuantity + quantity);
        }
    }
}
