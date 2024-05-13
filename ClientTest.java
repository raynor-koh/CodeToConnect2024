import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {

    @Test
    public void readcsvExample() {
        Client n = new Client("./example-set/input_clients.csv");
        assertTrue(!Client.clientHashMap.isEmpty());
        assertTrue(Client.clientHashMap.size() == 5);
    }

    @Test
    public void readcsvTest() {
        Client n = new Client("./test-set/input_clients.csv");
        assertTrue(!Client.clientHashMap.isEmpty());
        assertTrue(Client.clientHashMap.size() == 15);
    }


}