import java.sql.*;
import java.io.*;
import java.util.*;

public class AddMovie {
	
	int temp = 0;
	
	public AddMovie() throws SQLException{
		
	}
	
	public static void add_movie(Connection conn, Scanner input) throws SQLException {
		
		String title = "";
		int year = 0;
		double rating = 0.0;
		String genre = "";
		
		String add = "INSERT INTO Movie VALUES (?, ?, ?, ?)";
		String check = "SELECT count(*) from Movie WHERE title = ? AND year = ?";
		
		boolean running = true;
		
		while(running) {
		
			// get_input(title, year, rating, genre, input);
			
			System.out.println("What is the title of the movie you would like to add to the database?");
			input.nextLine();
			title = input.nextLine().trim();
			
			System.out.println("What is the year " + title + " was released?");
			year = input.nextInt();
			
			System.out.println("What is the rating (from 0-10) of " + title + "?");
			rating = input.nextDouble();
			
			System.out.println("What is the genre of " + title + "?");
			input.nextLine();
			genre = input.nextLine().trim();
		
			// String add = "INSERT INTO Movie VALUES ('" + title + "', " + year + ", " + rating + ", '" + genre + "')";
			
			PreparedStatement check_statement = conn.prepareStatement(check);
			check_statement.setString(1, title);
			check_statement.setInt(2, year);
			
			ResultSet check_result = check_statement.executeQuery();
			
			if(check_result.next()) {
				
				if(check_result.getInt(1) == 1) {
					System.out.println();
					System.out.println(title + " already exists in the database.");
					System.out.println();
				}
				else{
					PreparedStatement add_statement = conn.prepareStatement(add);
					add_statement.setString(1, title);
					add_statement.setInt(2, year);
					add_statement.setDouble(3, rating);
					add_statement.setString(4, genre);
				
					add_statement.executeUpdate();
					
					System.out.println();
					System.out.println(title + " has been successfully added to the database.");
					System.out.println();
					
				}
			}
			
			String new_line = System.getProperty("line.separator");
			System.out.println("Would you like to: " + new_line + "1) Add another movie " + new_line + "2) Return to the menu");
			System.out.println();
			int ans = input.nextInt();
			
			if(ans == 2) {
				running = false;
			}
		}
	}
	
	public static void get_input(String title, int year, double rating, String genre, Scanner input) {
		
	}
}
