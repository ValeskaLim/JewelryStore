package factory;

import product.Product;

public abstract class ProductFactory {

	public static ProductFactory CreateFactory(String type) {
		// TODO Auto-generated constructor stub
		if(type.equals("Bracelet")) {
			return new BraceletFactory();
		}else if(type.equals("Necklace")) {
			return new NecklaceFactory();
		}else {
			return new RingFactory();
		}
	}
	
	public abstract Product CreateProduct(String name, String type, String colour, double price, int carat, int weight);

}
