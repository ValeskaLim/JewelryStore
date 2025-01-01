package state;

import model.Product;

public class AvailableState extends State {

	public AvailableState(Product product) {
		super(product);

	}
	@Override
	public void changeState() {
		product.setState(new ReservedState(product));
	}
	
	@Override
	public String stateString() {
		// TODO Auto-generated method stub
		return "Available";
	}
	
	@Override
	public String toString() {
	    return "Product " + product.getName() + " is available!";
	}
	
}
