// 4. ISP (Interface Segregation Principle)
//Интерфейс PaymentMethod разделен на CreditCardPayment и PayPalPayment,
// чтобы классы не реализовывали ненужные методы.

package Shop.PaymentMethod;

public interface CreditCardPayment {
    void payByCreditCard(double amount);
}
