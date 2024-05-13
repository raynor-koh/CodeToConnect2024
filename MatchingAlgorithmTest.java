import org.junit.Test;

import static org.junit.Assert.*;

public class MatchingAlgorithmTest {

    @Test
    public void testFindOpenPriceExample() {
        Instrument.readcsv("./example-set/input_instruments.csv");
        Order.readcsv("./example-set/input_orders.csv");
        MatchingAlgorithm x = new MatchingAlgorithm();
        double price = x.findOpenPrice(Instrument.instrumentHashMap.get("SIA"));
        assertEquals(32.1, price);
    }

    @Test
    public void testFindOpenPriceTest() {
        Instrument.readcsv("./test-set/input_instruments.csv");
        Order.readcsv("./test-set/input_orders.csv");
        MatchingAlgorithm x = new MatchingAlgorithm();
        double price = x.findOpenPrice(Instrument.instrumentHashMap.get("INST_003"));
        assertTrue(price > 0);
    }
}