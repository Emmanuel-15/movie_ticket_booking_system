package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		MainMenu menu = new MainMenu();

		while (true) {
			System.out.println("\n**********Welcome to Movie Ticket Booking System***********");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print("\nEnter your choice: ");
			int ch = scanner.nextInt();

			switch (ch) {
			case 1:
				menu.login();
				menu.mainMenu();
				break;

			case 2:
				menu.register();
				menu.login();
				menu.mainMenu();
				break;

			case 3:
				// Exit the program
				System.out.println("Exiting...");
				scanner.close();
				return; // Exit the main method

			default:
				System.out.println("\nInvalid choice");
				break;
			}
		}

//		menu.login();
//		menu.mainMenu();
	}
}