package main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import factory.BraceletFactory;
import factory.NecklaceFactory;
import factory.ProductFactory;
import factory.RingFactory;
import model.Product;
import singleton.Database;

public class Main {
	Scanner scan = new Scanner(System.in);
	Database db = Database.getInstance();
	
	
	public void addItem(Scanner scan, Database db) {
		System.out.print("Enter jewelry name (5 - 20 character) : ");
		String name = scan.nextLine();
		while(name.length() < 5 || name.length() > 20) {			
			System.out.print("Name must between 5 - 20 character! : ");
			String newName = scan.nextLine();
			name = newName;
		}
		
		System.out.print("Enter jewelry type (Bracelet / Necklace / Ring) : ");
		String type = scan.nextLine().toUpperCase();
		while(!type.equals("BRACELET") && !type.equals("NECKLACE") && !type.equals("RING")) {
			System.out.print("Invalid type. Choose between (Bracelet / Necklace / Ring) : ");
			String newType = scan.nextLine().toUpperCase();
			type = newType;
		}
		
		System.out.print("Enter color : ");
		String color = scan.nextLine();
		while(color.isEmpty()) {
			System.out.print("Color can't be empty! : ");
			String newColor = scan.nextLine();
			color = newColor;
		}
		
		System.out.print("Enter jewelry price : ");
		double price = Double.parseDouble(scan.nextLine());
		while(price <= 0) {
			System.out.print("Price must more than 0! : ");
			double newPrice = Double.parseDouble(scan.nextLine());
			price = newPrice;
		}
		
		System.out.print("Enter jewelry carat : ");
		int carat = Integer.parseInt(scan.nextLine());
		while(carat <= 0) {
			System.out.print("Carat must more than 0! : ");
			int newCarat = Integer.parseInt(scan.nextLine());
			carat = newCarat;
		}
		
		System.out.print("Enter jewelry weight (in grams) : ");
		double weight = Double.parseDouble(scan.nextLine());
		while(weight <= 0) {
			System.out.print("Weight must more than 0! : ");
			double newWeight = Double.parseDouble(scan.nextLine());
			weight = newWeight;
		}
		
		// Determine type of product
		ProductFactory factory = switch(type) {
		case "BRACELET" ->
			new BraceletFactory();
		case "NECKLACE" ->
			new NecklaceFactory();
		case "RING" ->
			new RingFactory();
		default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
		
		Product product = factory.createProduct(name, type, color, price, carat, weight);
		
		db.addListProduct(product);
		System.out.println("ITEM SUCCESSFULLY ADDED!");
		System.out.println("");
		
		// Timer wait 3 second
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void viewAllItems(Database db) {
		ArrayList<Product> list = db.getListProduct();
				
		if(list.isEmpty()) {
			System.out.println("No item in the list!");
			System.out.println("");
			return;
		}
		
		for(Product p : list) {
			System.out.println("========================");
			System.out.println("Name: " + p.getName());
			System.out.println("Type: " + p.getType());
			System.out.println("Color: " + p.getColour());
			System.out.println("Price: IDR" + p.getPrice());
			System.out.println(p.getCarat() + " Karat");
			System.out.println("Weight: " + p.getWeight() + " gram");
			System.out.println("========================");
		}
		
		// Timer wait 3 second
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Main() {
		Boolean menu = true;
		while(menu) {
			System.out.println("1. Add a Accessories");
			System.out.println("2. View all Accessories");
			System.out.println("3. Exit the program");
			System.out.print(">> ");
			
			int choose = Integer.parseInt(scan.nextLine());
			
			switch(choose) {
			case 1:
				addItem(scan, db);
				break;
			case 2:
				viewAllItems(db);
				break;
			case 3:
				System.out.println("Exiting program...");
				menu = false;
				break;
			}
		}
	}
	
	public static void main(String args[]) {
		new Main();
	}
	
	
}
