// Main класс зависит от абстракции ProductRepository

package Shop;

import Shop.PaymentMethod.CCPayment;
import Shop.PaymentMethod.PPPayment;
import Shop.cart.ShoppingCart;
import Shop.delivery.DeliveryTracker;
import Shop.filter.KeywordFilter;
import Shop.filter.ManufacturerFilter;
import Shop.filter.PriceFilter;
import Shop.order.*;
import Shop.product.Product;
import Shop.repository.InMemoryProductRepository;
import Shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ShoppingCart cart = new ShoppingCart();
    private static final List<Order> orders = new ArrayList<>();
    private static final DeliveryTracker deliveryTracker = new DeliveryTracker();
    static ProductRepository productRepository = new InMemoryProductRepository();
    private static final User currentUser = new User("Гость"); // Добавляем текущего пользователя

    public static void main(String[] args) {

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showProducts();
                    break;
                case 2:
                    filterProducts();
                    break;
                case 3:
                    manageCart();
                    break;
                case 4:
                    checkout();
                    break;
                case 5:
                    trackOrder();
                    break;
                case 6:
                    returnOrder();
                    break;
                case 7:
                    showRecommendations();
                    break;
                case 8:
                    updateOrderStatus();
                    break;
                case 9:
                    rateProduct();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Все товары");
        System.out.println("2. Фильтр товаров");
        System.out.println("3. Корзина");
        System.out.println("4. Оформить заказ");
        System.out.println("5. Статус заказа");
        System.out.println("6. Возврат товара");
        System.out.println("7. Рекомендации");
        System.out.println("8. Обновить статус заказа");
        System.out.println("9. Оценить товар");
        System.out.println("0. Выход");
        System.out.print("Выберите: ");
    }

    private static void showProducts() {
        System.out.println("\nДоступные товары:");
        productRepository.getAllProducts().forEach(p ->
                System.out.printf("%s - $%.2f (%s) ★%.1f%n",
                        p.getName(), p.getPrice(), p.getManufacturer(), p.getRating()));
    }

    private static void filterProducts() {
        System.out.println("\nФильтровать по:");
        System.out.println("1. Цене");
        System.out.println("2. Производителю");
        System.out.println("3. Ключевому слову");
        int filterChoice = scanner.nextInt();
        scanner.nextLine();

        List<Product> filtered = productRepository.getAllProducts();

        switch (filterChoice) {
            case 1:
                System.out.print("Минимальная цена: ");
                double min = scanner.nextDouble();
                System.out.print("Максимальная цена: ");
                double max = scanner.nextDouble();
                filtered = new PriceFilter(min, max).filter(filtered);

                // Сортировка по цене (от минимума к максимуму)
                filtered = filtered.stream()
                        .sorted(Comparator.comparingDouble(Product::getPrice))
                        .collect(Collectors.toList());
                break;
            case 2:
                System.out.print("Производитель: ");
                filtered = new ManufacturerFilter(scanner.nextLine()).filter(filtered);
                break;
            case 3:
                System.out.print("Ключевое слово: ");
                filtered = new KeywordFilter(scanner.nextLine()).filter(filtered);
                break;
            default:
                System.out.println("Неверный выбор");
                return;
        }

        System.out.println("\nНайдено товаров: " + filtered.size());
        filtered.forEach(p ->
                System.out.printf("%s - $%.2f (%s) ★%.1f%n",
                        p.getName(), p.getPrice(), p.getManufacturer(), p.getRating()));
    }

    private static void manageCart() {
        while (true) {
            System.out.println("\n1. Добавить товар");
            System.out.println("2. Удалить товар");
            System.out.println("3. Просмотреть корзину");
            System.out.println("4. Назад");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addToCart();
                    break;
                case 2:
                    removeFromCart();
                    break;
                case 3:
                    showCart();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private static void addToCart() {
        System.out.println("Введите название товара: ");
        String name = scanner.nextLine();
        productRepository.getAllProducts().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(
                        product -> {
                            cart.addItem(product); // Добавляем товар в корзину
                            System.out.println("Товар успешно добавлен: " + product.getName());
                        },
                        () -> System.out.println("Товар не найден")
                );
    }

    private static void removeFromCart() {
        System.out.print("Введите название товара для удаления: ");
        String productName = scanner.nextLine();

        cart.getItems().stream()
                .filter(p -> p.getName().equalsIgnoreCase(productName))
                .findFirst()
                .ifPresentOrElse(
                        product -> {
                            cart.removeItem(product); // Удаляем товар из корзины
                            System.out.println("Товар успешно удален: " + product.getName());
                        },
                        () -> System.out.println("Товар не найден в корзине")
                );
    }

    private static void showCart() {
        System.out.println("\nВаша корзина:");
        cart.getItems().forEach(p ->
                System.out.printf("- %s: $%.2f%n", p.getName(), p.getPrice()));
        System.out.printf("Итого: $%.2f%n", cart.getTotal());
    }


    private static void returnOrder() {
        System.out.print("Введите номер заказа для возврата: ");
        String orderId = scanner.nextLine();

        orders.stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .ifPresentOrElse(
                        order -> {
                            deliveryTracker.updateStatus(order, OrderStatus.RETURNED);
                            System.out.println("Заказ успешно возвращен!");
                            cart.clear();
                        },
                        () -> System.out.println("Заказ не найден!")
                );
    }

    private static void showRecommendations() {
        RecommendationSystem recommendationSystem = new RecommendationSystem();

        // Используем OrderHistoryImpl для получения истории заказов
        OrderHistory orderHistory = new OrderHistoryImpl();
        List<Product> purchaseHistory = orderHistory.getOrderHistory(currentUser).stream()
                .flatMap(order -> order.getCart().getItems().stream())
                .collect(Collectors.toList());

        // Получаем рекомендации
        List<Product> recommendations = recommendationSystem.getRecommendations(purchaseHistory);

        System.out.println("\nРекомендуем к покупке:");
        recommendations.forEach(p ->
                System.out.printf("• %s (рейтинг: ★%.1f)%n", p.getName(), p.getRating()));
    }

    private static void processPayment(int choice, double amount) {
        switch (choice) {
            case 1:
                new CCPayment().payByCreditCard(amount);
                break;
            case 2:
                new PPPayment().payByPayPal(amount);
                break;
            default:
                System.out.println("Неверный выбор оплаты");
        }
    }

    private static void checkout() {
        if (cart.getItems().isEmpty()) {
            System.out.println("Корзина пуста!");
            return;
        }

        System.out.println("Выберите способ оплаты:");
        System.out.println("1. Кредитная карта");
        System.out.println("2. PayPal");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        processPayment(paymentChoice, cart.getTotal());

        Order order = new Order(cart);
        orders.add(order);
        currentUser.addToOrderHistory(order);
        deliveryTracker.updateStatus(order, OrderStatus.PROCESSING);

        System.out.println("Заказ оформлен! Номер: " + order.getId());
        cart.clear();
    }

    private static void trackOrder() {
        System.out.print("Введите номер заказа: ");
        String id = scanner.nextLine();
        orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                        o -> System.out.println("Статус: " + deliveryTracker.getStatus(o)),
                        () -> System.out.println("Заказ не найден")
                );

    }

    private static void updateOrderStatus() {
        System.out.print("Введите номер заказа: ");
        String orderId = scanner.nextLine();

        // Находим заказ по ID
        Order order = orders.stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElse(null);

        if (order == null) {
            System.out.println("Заказ не найден!");
            return;
        }

        // Выводим доступные статусы
        System.out.println("Выберите новый статус:");
        System.out.println("1. PROCESSING");
        System.out.println("2. SHIPPED");
        System.out.println("3. DELIVERED");
        System.out.println("4. RETURNED");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();

        // Определяем новый статус
        OrderStatus newStatus = switch (statusChoice) {
            case 1 -> OrderStatus.PROCESSING;
            case 2 -> OrderStatus.SHIPPED;
            case 3 -> OrderStatus.DELIVERED;
            case 4 -> OrderStatus.RETURNED;
            default -> {
                System.out.println("Неверный выбор статуса");
                yield null;
            }
        };

        // Обновляем статус заказа
        if (newStatus != null) {
            OrderStatusUpdater statusUpdater = deliveryTracker; // Используем DeliveryTracker как реализацию
            statusUpdater.updateStatus(order, newStatus);
            System.out.println("Статус заказа обновлен: " + newStatus);
        }
    }

    private static void rateProduct() {
        System.out.print("Введите название товара: ");
        String productName = scanner.nextLine();
        System.out.print("Введите оценку (от 1 до 5): ");
        double rating = scanner.nextDouble();
        scanner.nextLine();

        productRepository.getAllProducts().stream()
                .filter(p -> p.getName().equalsIgnoreCase(productName))
                .findFirst()
                .ifPresentOrElse(
                        product -> {
                            product.updateRating(rating);
                            System.out.println("Рейтинг товара обновлен: " + product.getName());
                        },
                        () -> System.out.println("Товар не найден")
                );
    }
}











