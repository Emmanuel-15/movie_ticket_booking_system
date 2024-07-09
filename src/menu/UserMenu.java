package menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import connection.DBConnection;
import main.MainMenu;

public class UserMenu {

	public static void userMenu(Scanner scanner) {

		MainMenu menu = new MainMenu();

		while (true) {
			System.out.println("\n*****User Menu*****");
			System.out.println("1. Browse movies");
			System.out.println("2. Book show");
			System.out.println("3. Cancel show");
			System.out.println("4. View all bookings");
			System.out.println("5. Exit");
			System.out.print("\nEnter your choice: ");
			int ch = scanner.nextInt();
			System.out.println("\n");

			switch (ch) {
			case 1:
				// viewing all movies
				try (Connection conn = DBConnection.createC()) {

					String sql = "SELECT * FROM movies";
					PreparedStatement ps = conn.prepareStatement(sql);

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						System.out.println("******************");
						System.out.println("Movie Id :" + rs.getString("movie_id"));
						System.out.println("Movie Title :" + rs.getString("title"));
						System.out.println("Language :" + rs.getString("language"));
						System.out.println("Duration :" + rs.getString("duration"));
						System.out.println("******************\n");
					}

					rs.close();
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 2:
				try (Connection conn = DBConnection.createC()) {
					scanner.nextLine(); // Consume the newline character

					System.out.println("Enter movie name you want to search: ");
					String movieName = scanner.nextLine();

					String sql = "SELECT * FROM movies WHERE title = ?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, movieName);

					ResultSet rs = ps.executeQuery();

					// Check if there are any results for the first query
					if (rs.next()) {
						int movie_id = rs.getInt("movie_id");

						String sql2 = "SELECT * FROM `show` WHERE movie_id = ?";
						PreparedStatement ps2 = conn.prepareStatement(sql2);
						ps2.setInt(1, movie_id);

						ResultSet rs2 = ps2.executeQuery();

						// Check if there are any results for the second query
						if (rs2.next()) {
							do {
								System.out.println("******************");
								System.out.println("Show Id :" + rs2.getInt("show_id"));
								System.out.println("Movie Id :" + rs2.getInt("movie_id"));
								System.out.println("Start Time :" + rs2.getString("start_time"));
								System.out.println("End Time :" + rs2.getString("end_time"));
								System.out.println("******************\n");
							} while (rs2.next());

							System.out.println("\nEnter show ID you wanna book: ");
							int show_id = scanner.nextInt();

							scanner.nextLine(); // Consume the newline character

							System.out.println("\nEnter the date you wanna book show: ");
							String date = scanner.nextLine();

							String sql3 = "INSERT INTO bookings (user_id, show_id, show_date) VALUES (?, ?, ?)";
							PreparedStatement ps3 = conn.prepareStatement(sql3);

							ps3.setInt(1, menu.user_id); // Set the user_id (from MainMenu)
							ps3.setInt(2, show_id); // Set the show_id
							ps3.setString(3, date); // Set the booking_date (ensure bookingDate is a valid LocalDate)

							int rowsInserted = ps3.executeUpdate();

							if (rowsInserted > 0)
								System.out.println("\nShow booked!\n");
							else
								System.out.println("Error while booking show.");

						} else {
							System.out.println("Currently no shows available for this movie");
						}

						// Close rs2 and ps2
						rs2.close();
						ps2.close();

					} else {
						System.out.println("No results found for movie: " + movieName);
					}

					// Close rs and ps
					rs.close();
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}

				break;

			case 3:
				try (Connection conn = DBConnection.createC()) {
					scanner.nextLine(); // Consume the newline character

					System.out.println("Enter booking id you wanna cancel: ");
					int show_id = scanner.nextInt();

					// SQL delete statement
					String sql = "DELETE FROM bookings WHERE booking_id = ?";

					PreparedStatement ps = conn.prepareStatement(sql);

					// Setting the value for the prepared statement
					ps.setInt(1, show_id);

					// Executing the update
					int rowsDeleted = ps.executeUpdate();
					if (rowsDeleted > 0)
						System.out.println("show cancelled successfully!");
					else
						System.out.println("No show found.");

					// Closing the prepared statement and connection
					ps.close();
					conn.close();

				} catch (Exception e) {
					System.out.println(e);
				}
				break;

			case 4:
				// viewing all show
				try (Connection conn = DBConnection.createC()) {

					String sql = "SELECT * FROM bookings where user_id = ?";
					PreparedStatement ps = conn.prepareStatement(sql);

					ps.setInt(1, menu.user_id);

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						System.out.println("******************");
						System.out.println("Booking Id :" + rs.getInt("booking_id"));
						System.out.println("Show Id: " + rs.getInt("show_id"));
						System.out.println("Show date :" + rs.getString("show_date"));
						System.out.println("******************\n");
					}

					rs.close();
					ps.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 5:
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
