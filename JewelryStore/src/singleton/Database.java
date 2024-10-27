package singleton;

import java.util.ArrayList;

import product.Product;

public class Database {
	private static Database Instance;
	private ArrayList<Product> listProduct;

	public static Database getInstance() {
			// TODO Auto-generated method stub
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

	public void setListProduct(Product product) {
		this.listProduct.add(product);
	}

	private Database() {
		// TODO Auto-generated constructor stub
		listProduct = new ArrayList<>();
	}

}
