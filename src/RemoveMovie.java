import java.sql.*;
import java.io.*;
import java.util.*;

public class RemoveMovie {
	
	public static void remove_movie(Connection conn, Scanner input) throws SQLException {
		
		String title = "";
		int year = 0;
		
		String remove = "DELETE FROM Movie WHERE title = ? AND year = ?";
		String check = "SELECT * from Movie WHERE title = ? AND year = ?";
		
		String new_line = System.getProperty("line.separator");
		
		boolean running = true;
		
		while(running) {
		
			System.out.println("What is the title of the movie you would like to remove from the database?");
			input.nextLine();
			title = input.nextLine().trim();
		
			System.out.println("What is the year " + title + " was released?");
			year = input.nextInt();
		
			PreparedStatement check_statement = conn.prepareStatement(check);
			check_statement.setString(1, title);
			check_statement.setInt(2, year);
			ResultSet check_result = check_statement.executeQuery();
		
			if(check_result.next()) { // iterate through rows
			
				System.out.println();
				System.out.println(title + " has been found in the database. Would you like to delete the following movie?");
					
				String data = "";
				for(int i = 0; i < 4; i++) { // iterates through columns					
				data += check_result.getString(i+1) + " : ";
				}
				System.out.println(data);
				System.out.println("This action cannot be undone. Type 'delete' to confirm or any other key to cancel.");
			
				input.nextLine();
				String ans = input.nextLine();
			
				if(ans.equals("delete")) {
				
					PreparedStatement remove_statement = conn.prepareStatement(remove);
					remove_statement.setString(1, title);
					remove_statement.setInt(2, year);
				
					remove_statement.executeUpdate();
					
					System.out.println();
					System.out.println(title + " has been successfully deleted from the database.");
					System.out.println();
				}	
			}
			else {
				System.out.println();
				System.out.println(title + " has not been found in the database.");
				System.out.println();
			}
			
			System.out.println("Would you like to: " + new_line + "1) Delete another movie " + new_line + "2) Return to the menu");
			System.out.println();
			int ans2 = input.nextInt();
		
			if(ans2 == 2) {
				running = false;
			}
					
		}
		
	}

}
