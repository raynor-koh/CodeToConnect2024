import java.io.*;
import java.util.*;
import java.time.LocalTime;

public class Order {
    public LocalTime time;
    public String id;
    public Instrument instrument;
    public int quantity;
    public Client client;
    public int price;
    public boolean side;   // True: Buyer, False: Seller
    public static HashSet<Order> orderHashSet;

    public static void readcsv(String csvFilename) {
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\matth\\Downloads\\DataSets\\example-set\\input_orders.csv"));
            int rowCount = 0;
            while ((line = br.readLine()) != null) {
                if (rowCount == 0) {
                    rowCount++;
                    continue;
                }

                String[] l = line.split(splitBy);
                LocalTime time = LocalTime.parse(l[0]);
                String id = l[1];
                Instrument instrument = l[2];
                int quantity = ;
                Client client = ;
                int price = ;
                boolean side =
                int lotSize = Integer.parseInt(l[2]);

                Instrument instrument = new Instrument(id, currency, lotSize);
                instruments.put(id, instrument);
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Order(LocalTime time, String id, Instrument instrument, int quantity, Client client, int price, boolean side) {
        this.time = time;
        this.id = id;
        this.instrument = instrument;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }
}
