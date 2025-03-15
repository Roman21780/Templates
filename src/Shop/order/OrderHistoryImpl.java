package Shop.order;

import Shop.User;

import java.util.List;

public class OrderHistoryImpl implements OrderHistory {
    @Override
    public List<Order> getOrderHistory(User user) {
        return user.getOrderHistory();
    }
}
