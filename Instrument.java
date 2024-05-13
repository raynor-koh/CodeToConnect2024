public class Instrument {

    public String id;
    public String currency;
    public int lotSize;

    public Instrument(String id, String currency, int lotSize) {
        this.id = id;
        this.currency = currency;
        this.lotSize = lotSize;
    }
}
