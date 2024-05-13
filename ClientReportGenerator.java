import java.io.FileWriter;
import java.io.IOException;
import java.util.*; 

public class ClientReportGenerator {
  public static HashMap<String, HashMap<String, Double>> rows = new HashMap<String, HashMap<String, Double>>();

  public static void addClientPosition(String clientID, String instrumentId, double netPosition) {
    HashMap<String, Double> inner;
    if (!ClientReportGenerator.rows.containsKey(clientID)) {
      inner = new HashMap<String, Double> ();
      ClientReportGenerator.rows.put(clientID, inner);
    } else {
      inner = ClientReportGenerator.rows.get(clientID);
    }
    inner.put(instrumentId, netPosition);
  }

  public static void generateCSVFile() {
    try (FileWriter csvWriter = new FileWriter("C:\\Users\\USER\\Documents\\BOFA\\CodeToConnect2024\\output_client_report.csv")) {
    // Write header row
    csvWriter.append("Client Id,Instrument ID,Net Position\n");

    // Write data row

    for (HashMap.Entry<String, HashMap<String, Double>> outer : rows.entrySet()) {
      for (HashMap.Entry<String, Double> inner : outer.getValue().entrySet()) {
        csvWriter.append(outer.getKey()).append(",").append(inner.getKey()).append(",").append(String.valueOf(inner.getValue())).append("\n");
      }
    }
    // Close the csvWriter to save the data
    csvWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    // ExchangeReportGenerator.addFailedPolicy("D1" ,"REJECTED – MISMATCH CURRENCY");
    // ExchangeReportGenerator.addFailedPolicy("B2" ,"REJECTED – INVALID LOT SIZE");
    // ExchangeReportGenerator.addFailedPolicy("C2" ,"REJECTED – POSITION CHECK FAILED");
    // ExchangeReportGenerator.generateCSVFile();
  }
}
