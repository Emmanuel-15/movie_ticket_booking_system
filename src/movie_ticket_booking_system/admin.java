package movie_ticket_booking_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class admin {

	public static void main(String[] args) {

		// Env variables
		String url = "jdbc:mysql://localhost:3306/temp";
		String user = "root";
		String password = "";

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n***Admin Menu***");
			System.out.println("1. View all movies");
			System.out.println("2. Add movie");
			System.out.println("3. Update movie");
			System.out.println("4. Delete movie");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");
			int ch = scanner.nextInt();

			switch (ch) {
			case 1:
				// viewing all movies
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection(url, user, password);

					String sql = "SELECT * FROM movies";
					PreparedStatement ps = conn.prepareStatement(sql);

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						System.out.println("\n******************");
						System.out.println("Movie Id :" + rs.getString("movie_id"));
						System.out.println("Movie Title :" + rs.getString("title"));
						System.out.println("Movie Language :" + rs.getString("language"));
					}

					rs.close();
					ps.close();
					conn.close();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 2:
				// adding a movie
				try {

					scanner.nextLine(); // Consume the newline character

					// Prompting user for movie details
					System.out.print("Enter Movie Title: ");
					String title = scanner.nextLine();

					System.out.print("Enter Movie Language: ");
					String language = scanner.nextLine();

					System.out.print("Enter Movie Duration: ");
					String duration = scanner.nextLine();

					System.out.print("Enter Movie Release Date: ");
					String releaseDate = scanner.nextLine();

					// SQL insert statement
					String sql = "INSERT INTO movies (title, language, duration, release_date) VALUES (?, ?, ?, ?)";

					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection(url, user, password);
					PreparedStatement ps = conn.prepareStatement(sql);

					// Setting the values for the prepared statement
					ps.setString(1, title);
					ps.setString(2, language);
					ps.setString(3, duration);
					ps.setString(4, releaseDate);

					// Executing the update
					int rowsInserted = ps.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("Movie inserted!");
					}

					// Closing the prepared statement and connection
					ps.close();
					conn.close();

				} catch (Exception e) {
					System.out.println(e);
				}

				break;

			case 4:
				// Deleting a movie by ID
				try {
					scanner.nextLine(); // Consume the newline character

					System.out.print("Enter Movie ID to delete: ");
					int movieId = scanner.nextInt();

					// SQL delete statement
					String sql = "DELETE FROM movies WHERE movie_id = ?";

					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection(url, user, password);
					PreparedStatement ps = conn.prepareStatement(sql);

					// Setting the value for the prepared statement
					ps.setInt(1, movieId);

					// Executing the update
					int rowsDeleted = ps.executeUpdate();
					if (rowsDeleted > 0)
						System.out.println("Movie deleted successfully!");
					else
						System.out.println("No movie found.");

					// Closing the prepared statement and connection
					ps.close();
					conn.close();

				} catch (Exception e) {
					System.out.println(e);
				}
				break;

			case 5:
				// Exit the program
				System.out.println("Exiting...");
				scanner.close();
				return; // Exit the main method

			default:
				System.out.println("\nInvalid choice");
				break;
			}
		}

	}

}