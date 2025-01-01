package adapter;

public class QRISPaymentAdapter implements PaymentAdapter{
    @Override
    public double getPrice(double price) {
        return price;
    }
}
