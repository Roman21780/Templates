// 5. DIP (Dependency Inversion Principle)
//ProductRepository зависит от абстракции, а не от конкретной реализации базы данных.

package Shop.repository;

import Shop.product.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
}
