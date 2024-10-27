package factory;

import model.Product;
import model.Ring;

public class RingFactory extends ProductFactory {

	@Override
	public Product createProduct(String name, String type, String colour, double price, int carat, double weight) {
		return new Ring(name, type, colour, price, carat, weight);
	}
}
