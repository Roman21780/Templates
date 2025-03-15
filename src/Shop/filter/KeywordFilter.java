// Новые фильтры можно добавлять, не меняя существующий код

package Shop.filter;

import Shop.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class KeywordFilter implements ProductFilter {
    private final String keyword;

    public KeywordFilter(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
    }
}
