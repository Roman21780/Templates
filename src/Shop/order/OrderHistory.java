package Shop.order;

import Shop.User;

import java.util.List;

public interface OrderHistory {
    List<Order> getOrderHistory(User user);
}
