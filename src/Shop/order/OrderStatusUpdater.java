package Shop.order;

public interface OrderStatusUpdater {
    void updateStatus(Order order, OrderStatus status);
}
