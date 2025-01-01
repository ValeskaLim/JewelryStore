package state;

import model.Product;

public class ReservedState extends State{

	public ReservedState(Product product) {
		super(product);

	}
 
	@Override
	public void changeState() {
		product.setState(new SoldState(product));
	}
	
    public void cancelReservation() {
        product.setState(new AvailableState(product));
    }

	@Override
	public String stateString() {
		// TODO Auto-generated method stub
		return "Reserved";
	}
	
	@Override
	public String toString() {
	    return "Product " + product.getName() + " is reserved!";
	}

}
