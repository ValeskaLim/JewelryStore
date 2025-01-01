package factory;

import model.Bracelet;
import model.Product;

public class BraceletFactory extends ProductFactory{
	@Override
	public Product createProduct(String name, String type, String colour, double price, int carat, double weight) {
		return new Bracelet(name, type, colour, price, carat, weight);
	}
}
