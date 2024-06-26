import java.io.*;
import java.util.*;
import java.time.LocalTime;

public class Order {
    public LocalTime time;
    public String id;
    public Instrument instrument;
    public int quantity;
    public Client client;
    public double price;
    public boolean side;   // True: Buyer, False: Seller
    public static HashSet<Order> orderHashSet = new HashSet<>();

    public static void readcsv(String csvFilename) {
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFilename));
            int rowCount = 0;
            while ((line = br.readLine()) != null) {
                if (rowCount == 0) {
                    rowCount++;
                    continue;
                }

                String[] l = line.split(splitBy);
                LocalTime time = LocalTime.parse("10:15:45");
                // LocalTime time = LocalTime.parse(l[0;
                String id = l[1];
                Instrument instrument = Instrument.instrumentHashMap.get(l[2]);
                int quantity = Integer.parseInt(l[3]);
                Client client = Client.clientHashMap.get(l[4]);
                double price = 0;
                boolean side = l[6] == "Buy";

                if (Objects.equals(l[5], "Market") && side) {
                    price = Double.MAX_VALUE;
                } else if (Objects.equals(l[5], "Market")) {
                    price = Double.MIN_VALUE;
                } else {
                    price = Double.parseDouble(l[5]);
                }

                Order order = new Order(time, id, instrument, quantity, client, price, side);
                orderHashSet.add(order);
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Order(LocalTime time, String id, Instrument instrument, int quantity, Client client, double price, boolean side) {
        this.time = time;
        this.id = id;
        this.instrument = instrument;
        this.quantity = quantity;
        this.client = client;
        this.price = price;
        this.side = side;
        this.client = client;
    }
}
