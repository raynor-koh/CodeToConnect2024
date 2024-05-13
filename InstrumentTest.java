import org.junit.Test;

import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class InstrumentTest {

    @Test
    public void testReadCSVCanRead() {
        Instrument.readcsv("./test-set/input_instruments.csv");
        assertTrue(!Instrument.instrumentHashMap.isEmpty());
    }

    @Test
    public void testReadCSVSize() {
        Instrument.readcsv("./test-set/input_instruments.csv");
        assertTrue(Instrument.instrumentHashMap.size() == 10);
    }
}