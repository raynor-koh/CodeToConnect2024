import java.util.HashMap;
public class Client implements Comparable<Client> {

    String[] currency;
    boolean positionCheck;
    int rating;
    HashMap<Instrument, Integer> position;

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

    public void addInstrument(Instrument instrument, int quantity) throws InvalidLotSizeException {
        if (quantity % instrument.lotSize != 0) {
            throw new InvalidLotSizeException();
        }
        if (!this.position.containsKey(instrument)) {
            this.position.put(instrument, quantity);
        } else {
            int currQuantity = this.position.get(instrument);
            this.position.put(instrument, currQuantity + quantity);
        }
    }
}
