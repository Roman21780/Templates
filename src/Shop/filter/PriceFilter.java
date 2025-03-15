package Shop.filter;

import Shop.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class PriceFilter implements ProductFilter {
    private final double minPrice;
    private final double maxPrice;

    public PriceFilter(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
