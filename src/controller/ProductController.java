package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Product;
import singleton.Database;
import state.AvailableState;
import state.ReservedState;
import state.SoldState;

public class ProductController {
	private Database con;
	public PreparedStatement st;
	public ResultSet rs;
	
	public ProductController() {
		con = Database.getInstance();
	}
	
	public void addListProduct(Product product) {
		try {
			st = con.getConn().prepareStatement("INSERT INTO products (name, type, colour, price, carat, weight, state) VALUES (?, ?, ?, ?, ?, ?, ?);");
			st.setString(1, product.getName());
			st.setString(2, product.getType());
			st.setString(3, product.getColour());
			st.setDouble(4, product.getPrice());
			st.setInt(5, product.getCarat());
			st.setDouble(6, product.getWeight());
			st.setString(7, product.getState().stateString());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Product> getListProducts() {
		try {
			st = con.getConn().prepareStatement("SELECT * FROM products");
			rs = st.executeQuery();
			if(rs.next()) {
				ArrayList<Product> products = new ArrayList<>();
				String name = rs.getString("name");
				String type = rs.getString("type");
				String colour = rs.getString("colour");
				double price = rs.getDouble("price");
				int carat = rs.getInt("carat");
				double weight = rs.getDouble("weight");
				String stateString = rs.getString("state");
				
				Product product = new Product(name, type, colour, price, carat, weight);
				
				if(stateString.equals("Available")) {
					product.setState(new AvailableState(product));
				} else if (stateString.equals("Reserved")) {
					product.setState(new ReservedState(product));
				} else if(stateString.equals("Sold")) {
					product.setState(new SoldState(product));
				}
				
				products.add(product);
				return products;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateStateinDB(Product product) {
		try {
			st = con.getConn().prepareStatement("UPDATE products SET state = ? WHERE name = ?");
			st.setString(1, product.getState().stateString());
			st.setString(2, product.getName());
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
