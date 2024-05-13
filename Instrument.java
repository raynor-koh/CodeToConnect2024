import java.util.*;

import java.io.*;
import java.util.*;

public class Instrument {
    public static HashMap<String, Instrument> instrumentHashMap;
    public String id;
    public String currency;
    public int lotSize;

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
                String id = l[0];
                String currency = l[1];
                int lotSize = Integer.parseInt(l[2]);

                Instrument instrument = new Instrument(id, currency, lotSize);
                instrumentHashMap.put(id, instrument);
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Instrument(String id, String currency, int lotSize) {
        this.id = id;
        this.currency = currency;
        this.lotSize = lotSize;
    }
}
