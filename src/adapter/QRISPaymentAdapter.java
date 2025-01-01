package adapter;

public class QRISPaymentAdapter implements PaymentAdapter{
    @Override
    public double getPrice(double price) {
    	// PPN 12%
        return price * 1.12;
    }
}
