import java.io.FileWriter;
import java.io.IOException;
import java.util.*; 

public class ExchangeReportGenerator {
  public static Map<String, String> rows = new HashMap<String, String>();

  public static void addFailedPolicy(String orderID, String reason) {
    ExchangeReportGenerator.rows.put(orderID, reason);
  }

  public static void generateCSVFile() {
    try (FileWriter csvWriter = new FileWriter("C:\\Users\\USER\\Documents\\BOFA\\CodeToConnect2024\\output_exchange_report.csv")) {
    // Write header row
    csvWriter.append("Order Id,Rejection Reason\n");

    // Write data row
    // Iterating HashMap through forEach and
    // Printing all. elements in a Map

    for (Map.Entry<String, String> set : rows.entrySet()) {
      csvWriter.append(set.getKey()).append(",").append(set.getValue()).append("\n");
    }
    // Close the csvWriter to save the data
    csvWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ExchangeReportGenerator.addFailedPolicy("D1" ,"REJECTED – MISMATCH CURRENCY");
    ExchangeReportGenerator.addFailedPolicy("B2" ,"REJECTED – INVALID LOT SIZE");
    ExchangeReportGenerator.addFailedPolicy("C2" ,"REJECTED – POSITION CHECK FAILED");
    ExchangeReportGenerator.generateCSVFile();
  }
}
