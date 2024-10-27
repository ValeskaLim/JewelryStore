package factory;

import model.Product;

public abstract class ProductFactory {
	public abstract Product createProduct(String name, String type, String colour, double price, int carat, double weight);

}
