// 1. SRP (Single Responsibility Principle)
//Класс ShoppingCart отвечает только за управление корзиной (добавление/удаление товаров).

package Shop.cart;

import Shop.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Product> items = new ArrayList<>();

    public void addItem(Product product) {
        items.add(product);
    }

    public void removeItem(Product product) {
        items.remove(product);
    }

    public List<Product> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotal() {
        return items.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear() {
        items.clear();
    }
}
