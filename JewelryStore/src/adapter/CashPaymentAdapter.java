package adapter;

public class CashPaymentAdapter implements PaymentAdapter {
    @Override
    public double getPrice(double price) {
        return price;
    }
}
