package Shop.repository;

import Shop.product.DigitalProduct;
import Shop.product.Product;

import java.util.ArrayList;
import java.util.List;

public class InMemoryProductRepository implements ProductRepository {
    private static final List<Product> products = new ArrayList<>();

    // Статический блок для инициализации продуктов
    static {
        initProducts();
    }

    public static void initProducts() {
        products.add(new Product("Sony Headphones", 45.0, "Sony"));
        products.add(new Product("iPhone Case", 15.0, "Apple"));
        products.add(new DigitalProduct("E-book", 9.99));
        products.add(new Product("Keyboard", 25.0, "Logitech"));
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
}
