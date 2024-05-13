import java.io.*;
import java.util.*;

public class Client implements Comparable<Client> {
    public String id;
    public HashSet<String> currencies;
    public boolean positionCheck;
    public int rating;
    public HashMap<Instrument, Integer> position;
    public static HashMap<String, Client> clientHashMap = new HashMap<>();

    public Client(String csvFilename) {
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
                String id = l[0];
                HashSet<String> currencies = new HashSet<>();
                for (int i = 1; i <= l.length - 3; i++) {
                    currencies.add(l[i].replace("\"", ""));
                }
                boolean positionCheck = Objects.equals(l[l.length - 2], "Y");
                int rating = Integer.parseInt(l[l.length - 1]);

                Client client = new Client(id, currencies, positionCheck, rating);
                clientHashMap.put(id, client);
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(String id, HashSet<String> currencies, boolean positionCheck, int rating) {
        this.id = id;
        this.currencies = currencies;
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
