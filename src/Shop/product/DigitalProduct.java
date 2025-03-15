// 3. LSP (Liskov Substitution Principle)
//Класс DigitalProduct наследуется от Product и расширяет его поведение, оставаясь взаимозаменяемым.

package Shop.product;

public class DigitalProduct extends Product {
    public DigitalProduct(String name, double price) {
        super(name, price);
    }
}
