import java.util.Comparator;

public class SellOrderComparator implements Comparator<Order> {
    public int compare(Order order1, Order order2) {
        if (Double.compare(order2.price, order1.price) != 0) {
            return Double.compare(order1.price, order2.price);
        }

        if (Integer.compare(order1.client.rating, order2.client.rating) != 0) {
            return Integer.compare(order1.client.rating, order2.client.rating);
        }

        return order1.time.compareTo(order2.time);

    }
}
