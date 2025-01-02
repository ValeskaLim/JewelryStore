package model;

public class Customer extends User{
	private String customerId;
	
	public Customer(String username, String password, String role) {
		this.setUsername(username);
		this.setPassword(password);
		this.setRole(role);
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
