package model;

public class Product {
	private String name;
	private String type;
	private String colour;
	private double price;
	private int carat;
	private double weight;
	
	public Product(String name, String type, String colour, double price, int carat, double weight) {
		this.name = name;
		this.type = type;
		this.colour = colour;
		this.price = price;
		this.carat = carat;
		this.weight = weight;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCarat() {
		return carat;
	}

	public void setCarat(int carat) {
		this.carat = carat;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}
