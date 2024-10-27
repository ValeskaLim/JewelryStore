package singleton;

import java.util.ArrayList;

import model.Product;

public class Database {
	private static Database Instance;
	private ArrayList<Product> listProduct;

	public static Database getInstance() {
		if(Instance == null) {
			Instance = new Database();
		}
		return Instance;
	}

	public static void setInstance(Database instance) {
		Instance = instance;
	}

	public ArrayList<Product> getListProduct() {
		return listProduct;
	}

	public void addListProduct(Product product) {
		this.listProduct.add(product);
	}

	private Database() {
		listProduct = new ArrayList<>();
	}

}
