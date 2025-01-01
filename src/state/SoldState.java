package state;

import model.Product;

public class SoldState extends State {

	public SoldState(Product product) {
		super(product);

	}

	@Override
	public void changeState() {
		System.out.println("Product is already sold. No further state changes.");
	}

	@Override
	public String stateString() {
		// TODO Auto-generated method stub
		return "Sold";
	}
	
	@Override
	public String toString() {
	    return "Product " + product.getName() + " is sold!";
	}

}
