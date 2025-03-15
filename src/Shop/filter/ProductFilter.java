// 2. OCP (Open/Closed Principle)
//Интерфейс ProductFilter позволяет добавлять новые фильтры без изменения существующего кода.

package Shop.filter;

import Shop.product.Product;

import java.util.List;

public interface ProductFilter {
    List<Product> filter(List<Product> products);
}
