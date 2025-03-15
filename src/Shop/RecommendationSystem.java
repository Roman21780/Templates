package Shop;

import Shop.product.Product;

import java.util.List;
import java.util.stream.Collectors;

import static Shop.Main.productRepository;

public class RecommendationSystem {
    public List<Product> getRecommendations(List<Product> purchaseHistory) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getRating() > 4.0)
                .collect(Collectors.toList());
    }
}
