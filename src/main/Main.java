package main;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import adapter.CashPaymentAdapter;
import adapter.CreditPaymentAdapter;
import adapter.QRISPaymentAdapter;
import controller.ProductController;
import controller.UserController;
import factory.BraceletFactory;
import factory.NecklaceFactory;
import factory.ProductFactory;
import factory.RingFactory;
import model.Admin;
import model.Customer;
import model.Product;
import model.User;
import state.AvailableState;
import state.ReservedState;
import state.SoldState;

public class Main {
	Scanner scan = new Scanner(System.in);
	ProductController controller = new ProductController();
	UserController userController = new UserController();
	
	public void login() {
		
		System.out.print("Enter your username: ");
		String username = scan.nextLine();
		System.out.print("Enter your password: ");
		String password = scan.nextLine();
		
		User user = userController.login(username, password);
		
		if(user != null) {
			if(user instanceof Admin)
				adminMenu();
			else if(user instanceof Customer)
				customerMenu();
		}
		else {
			login();
		}
	}
	
	public void customerMenu() {
		boolean menu = true;
		while(menu) {
			System.out.println("==========================");
			System.out.println("=      JEWELRY STORE     =");
			System.out.println("==========================");
			System.out.println("1. View all Accessories");
			System.out.println("2. Buy Accessories");
			System.out.println("3. Logout");
			System.out.println("4. Exit the program");
			System.out.print(">> ");
			
			int choose = Integer.parseInt(scan.nextLine());
			
			switch(choose) {
			case 1:
				viewAllItems();
				break;
			case 2:
				buyItem(scan);
				break;
			case 3:
				userController.logout();
				clearScreen();
				menu = false;
				login();
				break;
			case 4:
				System.out.println("Exiting program...");
				menu = false;
				break;
			}
		}
	}
	
	public void adminMenu() {
		boolean menu = true;
		while(menu) {
			System.out.println("==========================");
			System.out.println("=  JEWELRY STORE (ADMIN) =");
			System.out.println("==========================");
			System.out.println("1. Add an accessories");
			System.out.println("2. View all Accessories");
			System.out.println("3. Buy Accessories");
			System.out.println("4. Logout");
			System.out.println("5. Exit the program");
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
				userController.logout();
				clearScreen();
				menu = false;
				login();
				break;
			case 5:
				System.out.println("Exiting program...");
				menu = false;
				break;
			}
		}
	}
	
	
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

	    while (!productExists) {
	        if (accName.isBlank()) {
	            System.out.println("Please enter a valid accessory name!");
	            accName = scan.nextLine();
	            continue;
	        }

	        for (Product p : list) {
	            if (p.getName().equals(accName)) {
	                if (p.getState() instanceof AvailableState) {
	                    p.getState().changeState();
	                    controller.updateStateinDB(p);
	                    product = p;
	                    productExists = true;
	                    break;
	                } else if (p.getState() instanceof ReservedState) {
	                    System.out.println("This product is currently reserved");
	                    return;
	                } else if (p.getState() instanceof SoldState) {
	                    System.out.println("This product is already sold!");
	                    return;
	                }
	            }
	        }

	        if (!productExists) {
	            System.out.println("Product not found! Please enter a valid accessory name!");
	            accName = scan.nextLine();
	        }
	    }

	    System.out.print("Enter the payment method (Cash / QRIS / Credit) : ");
	    String paymentMethod = scan.nextLine().toUpperCase();
	    while (!paymentMethod.equals("CASH") && !paymentMethod.equals("QRIS") && !paymentMethod.equals("CREDIT")) {
	        System.out.print("Payment method invalid! (Cash / QRIS / Credit) : ");
	        paymentMethod = scan.nextLine().toUpperCase();
	    }

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
	    
	    while (!confirm.equals("Y") && !confirm.equals("N")) {
	        System.out.println("Choose a valid confirmation! >> ");
	        confirm = scan.next().toUpperCase();
	    }
	    
	    if (confirm.equals("Y")) {
	        try {
	            for (int i = 0; i < 5; i++) {
	                System.out.print("*");
	                Thread.sleep(1000);;
	            }
	            System.out.println();
	            product.getState().changeState();
	            controller.updateStateinDB(product);
	            System.out.println("Transaction success");
	            System.out.print("Enter any key to continue...");
	            scan.nextLine();
	            scan.nextLine();
	            System.out.println();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    } else {
	        if (product.getState() instanceof ReservedState) {
	            ReservedState reservedState = (ReservedState) product.getState();
	            reservedState.cancelReservation();
	            controller.updateStateinDB(product);
	        }
	        System.out.println("Transaction cancelled");
	        System.out.print("Enter any key to continue...");
	        scan.nextLine();
	        scan.nextLine();
	    }
	}
	
	public void clearScreen() {
		for(int i = 0; i < 30; i++) {
			System.out.println();
		}
	}
	
	public Main() {
		login();
	}
	
	public static void main(String args[]) {
		new Main();
	}
	
	
}
