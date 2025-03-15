// DRY (Don't Repeat Yourself):
//Метод validatePrice в классе Product переиспользуется при создании товаров.

package Shop.product;

import static Shop.Constants.MIN_PRICE;

public class Product {
    private final String name;
    private final double price;
    private final String manufacturer;
    private double rating;

    public Product(String name, double price, String manufacturer) {
        validatePrice(price);
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.rating = 5.0; // Начальный рейтинг
    }

    public Product(String name, double price) {
        this(name, price, "Unknown");
    }

    private void validatePrice(double price) {
        if (price < MIN_PRICE) throw new IllegalArgumentException("Цена не может быть отрицательной");
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getRating() {
        return rating;
    }

    public void updateRating(double newRating) {
        this.rating = (this.rating + newRating) / 2;
    }
}
