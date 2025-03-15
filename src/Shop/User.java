package Shop;

import Shop.order.Order;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final List<Order> orderHistory = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addToOrderHistory(Order order) {
        orderHistory.add(order);
    }

    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }
}
