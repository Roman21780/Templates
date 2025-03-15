package Shop.order;

import Shop.cart.ShoppingCart;
import Shop.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String id;
    private final List<Product> items;
    private final ShoppingCart cart;
    private OrderStatus status;

    public Order(ShoppingCart cart) {
        this.id = java.util.UUID.randomUUID().toString();
        this.cart = cart;
        this.items = new ArrayList<>(cart.getItems());
        this.status = OrderStatus.PROCESSING;
    }

    // Геттеры и сеттеры
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public List<Product> getItems() {
        return items;
    }

    public ShoppingCart getCart() {
        return cart;
    }
}
