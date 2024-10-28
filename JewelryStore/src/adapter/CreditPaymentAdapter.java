package adapter;

public class CreditPaymentAdapter implements PaymentAdapter{
    @Override
    public double getPrice(double price) {
        return price * 0.90;
    }
}