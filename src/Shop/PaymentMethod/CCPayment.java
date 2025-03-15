package Shop.PaymentMethod;

public class CCPayment implements CreditCardPayment {
    @Override
    public void payByCreditCard(double amount) {
        System.out.printf("Оплачено %.2f с помощью кредитной карты\n", amount);
    }
}
