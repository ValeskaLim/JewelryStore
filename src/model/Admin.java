package model;

public class Admin extends User{

	public Admin(String username, String password, String role) {
		this.setUsername(username);
		this.setPassword(password);
		this.setRole(role);
	}
}
