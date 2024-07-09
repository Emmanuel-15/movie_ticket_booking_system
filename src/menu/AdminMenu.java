package menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import connection.DBConnection;

public class AdminMenu {

	public static void adminMenu(Scanner scanner) {

		while (true) {
			System.out.println("\n***Admin Menu***");
			System.out.println("1. View all movies");
			System.out.println("2. Add movie");
			System.out.println("3. Update movie");
			System.out.println("4. Delete movie");
			System.out.println("5. View all show");
			System.out.println("6. Add show");
			System.out.println("7. Delete show");
			System.out.println("8. Exit");
			System.out.print("Enter your choice: ");
			int ch = scanner.nextInt();

			switch (ch) {
			case 1:
				// viewing all movies
				try (Connection conn = DBConnection.createC()) {

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
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 2:
				// adding a movie
				try (Connection conn = DBConnection.createC()) {

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

			case 3:
				System.out.println("Feature still in progress");

				break;

			case 4:
				// Deleting a movie by ID
				try (Connection conn = DBConnection.createC()) {
					scanner.nextLine(); // Consume the newline character

					System.out.print("Enter Movie ID to delete: ");
					int movieId = scanner.nextInt();

					// SQL delete statement
					String sql = "DELETE FROM movies WHERE movie_id = ?";

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
				try (Connection conn = DBConnection.createC()) {

					String sql = "SELECT * FROM `show`";
					PreparedStatement ps = conn.prepareStatement(sql);

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						System.out.println("\n******************");
						System.out.println("Show Id :" + rs.getString("show_id"));
						System.out.println("Movie Id :" + rs.getString("movie_id"));
						System.out.println("Start time :" + rs.getString("start_time"));
						System.out.println("End time :" + rs.getString("end_time"));
						System.out.println("Screen :" + rs.getString("screen"));
					}

					rs.close();
					ps.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				// adding a show
				try (Connection conn = DBConnection.createC()) {

					scanner.nextLine(); // Consume the newline character

					// Prompting user for show details
					System.out.print("Enter Movie Id: ");
					int movie_id = scanner.nextInt();

					scanner.nextLine(); // consume the newline character

					System.out.print("Enter Start time: ");
					String start_time = scanner.nextLine();

					System.out.print("Enter End time: ");
					String end_time = scanner.nextLine();

					System.out.print("Enter Screen number: ");
					String screen = scanner.nextLine();

					// SQL insert statement
					String sql = "INSERT INTO `show` (movie_id, start_time, end_time, screen) VALUES (?, ?, ?, ?)";

					PreparedStatement ps = conn.prepareStatement(sql);

					// Setting the values for the prepared statement
					ps.setInt(1, movie_id);
					ps.setString(2, start_time);
					ps.setString(3, end_time);
					ps.setString(4, screen);

					// Executing the update
					int rowsInserted = ps.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("Show inserted!");
					} else
						System.out.println("Error while inserting show");

					// Closing the prepared statement and connection
					ps.close();
					conn.close();

				} catch (Exception e) {
					System.out.println(e);
				}

				break;
			case 7:
				// Deleting a movie by ID
				try (Connection conn = DBConnection.createC()) {
					scanner.nextLine(); // Consume the newline character

					System.out.print("Enter Show ID to delete: ");
					int show_id = scanner.nextInt();

					// SQL delete statement
					String sql = "DELETE FROM `show` WHERE show_id = ?";

					PreparedStatement ps = conn.prepareStatement(sql);

					// Setting the value for the prepared statement
					ps.setInt(1, show_id);

					// Executing the update
					int rowsDeleted = ps.executeUpdate();
					if (rowsDeleted > 0)
						System.out.println("Show deleted successfully!");
					else
						System.out.println("No Show found.");

					// Closing the prepared statement and connection
					ps.close();
					conn.close();

				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			case 8:
				// Exit the program
				System.out.println("logged out...");
				// scanner.close();
				return; // Exit the main method

			default:
				System.out.println("\nInvalid choice");
				break;
			}
		}
	}
}
