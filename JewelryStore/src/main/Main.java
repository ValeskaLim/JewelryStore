package main;

import java.util.ArrayList;
import java.util.Scanner;
import product.Product;
import singleton.Database;

public class Main {
	Scanner scan = new Scanner(System.in);
	Database db = Database.getInstance();
	
	public Main() {
		// TODO Auto-generated constructor stub
		Boolean menu = true;
		while(menu) {
			System.out.println("1. Add a Accessories");
			System.out.println("2. View all Accessories");
			System.out.println("3. Exit the program");
			
			int choose= scan.nextInt();scan.nextLine();
			switch(choose) {
			case 1:
				insert();
				break;
			case 2:
				view();
				break;
			case 3:
				menu = false;
				break;
			}
		}
	}
	
	public void insert() {
		String name;
		String type;
		String colour;
		double price;
		int carat;
		int weight;
		
		
	
	}
	
	public void view() {
		for(Product p : db.getListProduct()) {
			System.out.println(p.getName());
			System.out.println(p.getType());
			System.out.println(p.getColour());
			System.out.println(p.getPrice());
			System.out.println(p.getCarat());
			System.out.println(p.getCarat());
		}
	}
	
	
}
