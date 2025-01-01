package main;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import adapter.CashPaymentAdapter;
import adapter.CreditPaymentAdapter;
import adapter.QRISPaymentAdapter;
import controller.ProductController;
import factory.BraceletFactory;
import factory.NecklaceFactory;
import factory.ProductFactory;
import factory.RingFactory;
import model.Product;
import state.ReservedState;
import state.SoldState;

public class Main {
	Scanner scan = new Scanner(System.in);
	ProductController controller = new ProductController();
	
	
	public void addItem(Scanner scan) {
		System.out.print("Enter jewelry name (5 - 20 character) : ");
		String name = scan.nextLine();
		while(name.length() < 5 || name.length() > 20) {			
			System.out.print("Name must between 5 - 20 character! : ");
            name = scan.nextLine();
		}
		
		System.out.print("Enter jewelry type (Bracelet / Necklace / Ring) : ");
		String type = scan.nextLine().toUpperCase();
		while(!type.equals("BRACELET") && !type.equals("NECKLACE") && !type.equals("RING")) {
			System.out.print("Invalid type. Choose between (Bracelet / Necklace / Ring) : ");
            type = scan.nextLine().toUpperCase();
		}
		
		System.out.print("Enter color : ");
		String color = scan.nextLine();
		while(color.isBlank()) {
			System.out.print("Color can't be empty! : ");
            color = scan.nextLine();
		}
		
		System.out.print("Enter jewelry price : ");
		double price = Double.parseDouble(scan.nextLine());
		while(price <= 0) {
			System.out.print("Price must more than 0! : ");
            price = Double.parseDouble(scan.nextLine());
		}
		
		System.out.print("Enter jewelry carat : ");
		int carat = Integer.parseInt(scan.nextLine());
		while(carat <= 0) {
			System.out.print("Carat must more than 0! : ");
            carat = Integer.parseInt(scan.nextLine());
		}
		
		System.out.print("Enter jewelry weight (in grams) : ");
		double weight = Double.parseDouble(scan.nextLine());
		while(weight <= 0) {
			System.out.print("Weight must more than 0! : ");
            weight = Double.parseDouble(scan.nextLine());
		}

		System.out.print("Enter the payment method (Cash / QRIS / Credit) : ");
		String paymentMethod = scan.nextLine().toUpperCase();
		while(paymentMethod.isEmpty()) {
			System.out.print("Payment method cannot be empty! (Cash / QRIS / Credit) : ");
			paymentMethod = scan.nextLine().toUpperCase();
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
		
		controller.addListProduct(product);
		System.out.println("ITEM SUCCESSFULLY ADDED!");
		System.out.print("Enter any key to continue...");
		scan.nextLine();
		clearScreen();
		
		// Timer wait 3 second
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void viewAllItems() {
		ArrayList<Product> list = controller.getListProducts();
				
		if(list == null || list.isEmpty()) {
			System.out.println("No item in the list!");
			System.out.print("Enter any key to continue...");
			scan.nextLine();
			System.out.println();

			return;
		}
		
		for(Product p : list) {
			System.out.println("========================");
			System.out.println("Name: " + p.getName());
			System.out.println("Type: " + p.getType());
			System.out.println("Color: " + p.getColour());
			System.out.println("Price: $" + p.getPrice());
			System.out.println(p.getCarat() + " Karat");
			System.out.println("Weight: " + p.getWeight() + " gram");
			System.out.println(p.getState().toString());
			System.out.println("========================");
		}

		System.out.print("Enter any key to continue...");
        scan.nextLine();
        clearScreen();
    }
	
	public void buyItem(Scanner scan) {
		ArrayList<Product> list = controller.getListProducts();
		
		System.out.println();
		System.out.println();
		viewAllItems();
		System.out.print("Enter the name of the available accessory to be bought (Case-sensitive) >> ");
		String accName = scan.nextLine();
		boolean productExists = false;
		Product product = null;
        for (Product p : list) {
            if (p.getName().equals(accName) && !(p.getState() instanceof SoldState)) {
                    p.getState().changeState();
                    controller.updateStateinDB(p);
                	product = p;
                    productExists = true;
                    break;
            } else if(p.getState() instanceof SoldState) {
            	System.out.println("This product is sold!");
            	System.out.print("Enter any key to continue...");
                scan.nextLine();
                clearScreen();
            	return;
            }
        }

		while (accName.isBlank() || !productExists) {
		    productExists = false;
		    System.out.println("Please enter a valid accessory name!");
		    accName = scan.next();
		        
		    for (Product p : list) {
		         if (p.getName().equals(accName) && !(p.getState() instanceof SoldState)) {
		                    p.getState().changeState();
		                    controller.updateStateinDB(p);
		                	product = p;
		                    productExists = true;
		                    break;
		         } else if(p.getState() instanceof SoldState) {
		            	System.out.println("This product is sold!");
		            	break;
		         }
		    }
		}
		
		
		System.out.print("Enter the payment method (Cash / QRIS / Credit) : ");
		String paymentMethod = scan.nextLine().toUpperCase();
		while(paymentMethod.isEmpty() || !paymentMethod.equals("CASH") || paymentMethod.equals("QRIS") || paymentMethod.equals("CREDIT")) {
			System.out.print("Payment method invalid! (Cash / QRIS / Credit) : ");
			paymentMethod = scan.nextLine().toUpperCase();
		}
		
//		PaymentAdapter payment = null;
		double price = product.getPrice();
        price = switch (paymentMethod) {
            case "CASH" -> new CashPaymentAdapter().getPrice(price);
            case "QRIS" -> new QRISPaymentAdapter().getPrice(price);
            case "CREDIT" -> new CreditPaymentAdapter().getPrice(price);
            default -> price;
        };
        
        System.out.println("Payment method: " + paymentMethod);
        System.out.println("Total: $" + price);
        System.out.println("Confirm buy [Y/N] >>");
        String confirm = scan.next().toUpperCase();
        
        while(confirm.isBlank() && (confirm.equals("Y") || confirm.equals("N"))) {
        	System.out.println("Choose a valid confirmation! >> ");
        	confirm = scan.next().toUpperCase();
        }
        
        if(confirm.equals("Y")) {
        	try {
        		System.out.print("*");
				TimeUnit.SECONDS.sleep(2);
        		System.out.print("*");
				TimeUnit.SECONDS.sleep(2);
        		System.out.print("*");
				TimeUnit.SECONDS.sleep(2);
        		System.out.print("*");
				TimeUnit.SECONDS.sleep(2);
        		System.out.println("*");
				TimeUnit.SECONDS.sleep(2);
				product.getState().changeState();
				controller.updateStateinDB(product);
				System.out.println("Transaction success");
				System.out.print("Enter any key to continue...");
		        scan.nextLine();
		        System.out.println();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        } else {
            if (product.getState() instanceof ReservedState) {
                ReservedState reservedState = (ReservedState) product.getState();
                reservedState.cancelReservation();
            }
        	System.out.println("Transaction cancelled");
        }
	}
	
	public Main() {
		boolean menu = true;
		while(menu) {
			System.out.println("========================");
			System.out.println("=     JEWELRY STORE    =");
			System.out.println("========================");
			System.out.println("1. Add an Accessory");
			System.out.println("2. View all Accessories");
			System.out.println("3. Buy Accessories");
			System.out.println("4. Exit the program");
			System.out.print(">> ");
			
			int choose = Integer.parseInt(scan.nextLine());
			
			switch(choose) {
			case 1:
				addItem(scan);
				break;
			case 2:
				viewAllItems();
				break;
			case 3:
				buyItem(scan);
				break;
			case 4:
				System.out.println("Exiting program...");
				menu = false;
				break;
			}
		}
	}
	
	public void clearScreen() {
		for(int i = 0; i < 30; i++) {
			System.out.println();
		}
	}
	
	public static void main(String args[]) {
		new Main();
	}
	
	
}
