package factory;

import model.Necklace;
import model.Product;

public class NecklaceFactory extends ProductFactory {
	
	@Override
	public Product createProduct(String name, String type, String colour, double price, int carat, double weight) {
		return new Necklace(name, type, colour, price, carat, weight);
	}
}
