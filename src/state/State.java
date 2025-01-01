package state;

import model.Product;

public abstract class State {
	protected Product product;
	
	public State(Product product) {
		super();
		this.product = product;
	}
	
	public abstract void changeState();
	public abstract String stateString();

}
