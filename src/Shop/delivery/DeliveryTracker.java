// DeliveryTracker отвечает только за отслеживание статусов

package Shop.delivery;

import Shop.order.Order;
import Shop.order.OrderStatus;
import Shop.order.OrderStatusUpdater;

public class DeliveryTracker implements OrderStatusUpdater {
    @Override
    public void updateStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        System.out.println("Статус заказа " + order.getId() + " изменен на: " + status);
    }

    // Добавляем метод getStatus
    public String getStatus(Order order) {
        return order.getStatus().toString();
    }
}
