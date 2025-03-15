package Shop.PaymentMethod;

public class PPPayment implements PayPalPayment {
    @Override
    public void payByPayPal(double amount) {
        System.out.printf("Оплачено %.2f через PayPal\n", amount);
    }
}
