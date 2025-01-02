package controller;

import model.Admin;
import model.Customer;
import model.User;
import singleton.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private User loggedInUser;

    public User login(String username, String password) {
        Database db = Database.getInstance();
        String query = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = db.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {
             
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                String role = rs.getString("role");
                String name = rs.getString("username");

                if (password.equals(dbPassword)) {
                    if (role.equalsIgnoreCase("admin")) {
                        loggedInUser = new Admin(name, dbPassword, role);
                    } else if (role.equalsIgnoreCase("customer")) {
                        loggedInUser = new Customer(name, dbPassword, role);
                    }
                    return loggedInUser;
                } else {
                    System.out.println("Incorrect username/password. Please try again!");
                }
            } else {
            	System.out.println("Incorrect username/password. Please try again!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logout() {
        if (loggedInUser != null) {
            System.out.println("Logged out successfully.");
            loggedInUser = null;
            try {
            	System.out.println("Redirecting you in 3 seconds...");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
    
    public User getLoggedInUser() {
        return loggedInUser;
    }
}

