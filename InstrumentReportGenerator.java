import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*; 

public class InstrumentReportGenerator {
  public static HashMap<String, ArrayList<String>> rows = new HashMap<String, ArrayList<String>>();

  public static void addFailedPolicy(String instrumentID, String openPrice, String closePrice, String totalVolume, String VWAP, String dayHigh, String dayLow) {
    ArrayList<String> value = new ArrayList<String>();
    value.add(openPrice);
    value.add(closePrice);
    value.add(totalVolume);
    value.add(VWAP);
    value.add(dayHigh);
    value.add(dayLow);
    InstrumentReportGenerator.rows.put(instrumentID, value);

  }

  public static void generateCSVFile() {
    try (FileWriter csvWriter = new FileWriter("C:\\Users\\USER\\Documents\\BOFA\\CodeToConnect2024\\output_exchange_report.csv")) {
    // Write header row
    csvWriter.append("Instrument ID,Open Price,Close Price,Total Volume,VWAP,Day High,Day Low\n");

    // Write data row
    // Iterating HashMap through forEach and
    // Printing all. elements in a Map

    for (HashMap.Entry<String, ArrayList<String>> set : rows.entrySet()) {
      csvWriter.append(set.getKey()).append(",");
      for (String item : set.getValue()) {
        csvWriter.append(item).append(",");
      }
      csvWriter.append("\n");
    }
  
    // Close the csvWriter to save the data
    csvWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    //
  }
}
