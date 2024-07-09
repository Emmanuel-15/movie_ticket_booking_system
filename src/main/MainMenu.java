package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import connection.DBConnection;
import menu.AdminMenu;
import menu.UserMenu;

public class MainMenu {
	private String roleName;
	public static int user_id;
	private Scanner sc = new Scanner(System.in);

	public void login() {
		try (Connection con = DBConnection.createC()) {
			Statement st = con.createStatement();

			System.out.println("\nLogin to Movie Ticket Booking System by providing your credentials:");

			System.out.println("\nEnter username: ");
			String username = sc.nextLine();

			System.out.println("Enter password: ");
			String password = sc.nextLine();

			String fetchQuery = "SELECT * FROM Login WHERE username = '" + username + "' AND password = '" + password
					+ "'";

			ResultSet rs = st.executeQuery(fetchQuery);

			if (rs.next()) {
				roleName = rs.getString("userType");
				user_id = rs.getInt("loginId");
				System.out.println("Logged in.");

			} else {
				System.out.println("Wrong credentials. Please check the username and password.");
				login();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void register() {
		try (Connection con = DBConnection.createC()) {
			System.out.println("\nRegister for Movie Ticket Booking System by providing your details:");

			System.out.print("Enter username: ");
			String username = sc.nextLine();

			System.out.print("Enter password: ");
			String password = sc.nextLine();

			// SQL insert statement
			String insertQuery = "INSERT INTO Login (username, password) VALUES (?, ?)";

			try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
				// Setting the values for the prepared statement
				ps.setString(1, username);
				ps.setString(2, password);

				// Executing the update
				int rowsInserted = ps.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("\nRegistration successful!\n");
				} else {
					System.out.println("Registration failed. Please try again.");
					register(); // Recursive call to retry registration
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Main menu
	public void mainMenu() {
		try {
			if (roleName.equals("admin")) {
				AdminMenu.adminMenu(sc);
				return;
			} else {
				UserMenu.userMenu(sc);
				return;
			}

		} catch (NullPointerException e) {
			System.out.println("Not Found!!");
		}
		sc.next();
	}
}
