package movie_ticket_booking_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class user {

	public static void main(String[] args) {
		// Env variables
		String url = "jdbc:mysql://localhost:3306/temp";
		String user = "root";
		String password = "";

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n***User Menu***");
			System.out.println("1. Browse movies");
			System.out.println("2. Book show");
			System.out.println("3. Cancel show");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int ch = scanner.nextInt();

			switch (ch) {
			case 1:
				// viewing all movies
				break;

			case 2:

				break;

			case 3:

				break;

			case 4:
				// Exit the program
				System.out.println("Logged out...");
				return; // Exit the main method

			default:
				System.out.println("Invalid choice");
				break;
			}
		}

	}

}