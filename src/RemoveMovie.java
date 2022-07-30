// FIX SPACING FOR CONFIRMATION OUTPUT
// ADD ENTER SELECTION: FOR DELETE

import java.sql.*;
import java.io.*;
import java.util.*;

public class RemoveMovie {
	
	String new_line = System.getProperty("line.separator");
	String border = "***************************************************************************";
	
	Scanner input;
	
	// variables that hold the name and year of the movie to be deleted
	String title = "";
	int year = 0;
	
	// strings that hold the commands to be passed into the prepared statements
	String remove = "DELETE FROM Movie WHERE title = ? AND year = ?";
	String check = "SELECT * from Movie WHERE title = ? AND year = ?";
	
	// variable holds true if the user wants to remove another movie
		boolean running = true;
	
	public RemoveMovie(Scanner in) {
		
		input = in;
	}
	
	public void remove_movie(Connection conn) throws SQLException {
		
		running = true;
		
		// runs while user wants to remove a movie
		while(running) {
			
			System.out.println(border);
			System.out.println("You have selected to remove a movie from the database" + new_line + "Enter \"CANCEL\" at any time to return to the main menu");
			System.out.println(border);
			
			int run = get_input();
			
			if(run == 0) break;
			// get_input();
		
//			System.out.println("What is the title of the movie you would like to remove from the database?");
//			input.nextLine();
//			title = input.nextLine().trim();
//		
//			System.out.println("What is the year " + title + " was released?");
//			year = input.nextInt();
		
			PreparedStatement check_statement = conn.prepareStatement(check);
			check_statement.setString(1, title);
			check_statement.setInt(2, year);
			
			ResultSet check_result = check_statement.executeQuery();
		
			if(check_result.next()) { // iterate through rows
			
				System.out.println(border);
				System.out.println("\"" + title + "\"" + " (" + year + ")" +  " has been found in the database. Are you sure you would like to delete the following movie?:" + new_line);
					
				String data = "";
				for(int i = 0; i < 4; i++) { // iterates through columns					
				data += check_result.getString(i+1) + " : ";
				}
				System.out.println(data);
				System.out.println("Deleting " + "\"" + title + "\"" + " (" + year + ")" + " cannot be undone. Type 'DELETE' to confirm,  or any other key to cancel.");
			
				input.nextLine();
				String ans = input.nextLine();
			
				if(ans.equals("DELETE")) {
				
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
	
	public int get_input() {
		
		int step = 0;
		int counter_year = 0;
		int counter_rating = 0;
		
		// System.out.println("What is the title of the movie you would like to add to the database?");
		
		while(true) {
		
			if(step == 0) {
				System.out.println("What is the title of the movie you would like to remove from the database?");
				System.out.println();
				//System.out.println(border);
				System.out.print("Enter title: ");
				input.nextLine();
				title = input.nextLine().trim();
				if(title.equals("CANCEL")) return 0;
				step++;
			}
			
			if(step == 1) {
				try {
					// if(counter_year > 0) input.nextLine();
					
					if(counter_year == 0) {
						System.out.println(border);
						System.out.println("What is the year " + title + " was released?");
					}
					System.out.println();
					System.out.print("Enter year: ");
					String temp_year = input.nextLine().trim();
					if(temp_year.equals("CANCEL")) return 0;
					if(temp_year.length() != 4 || Integer.parseInt(temp_year) < 1895 || Integer.parseInt(temp_year) > Calendar.getInstance().get(Calendar.YEAR)) throw new IllegalStateException();
					
					year = Integer.parseInt(temp_year);
					return 1;
				
				}
				catch(Exception e){
					System.out.println(border);
					System.out.println("Invalid input. Please enter an integer representing the year that " + title + " was released.");
					// System.out.println();
					// System.out.print("Enter year: ");
					counter_year++;
					continue;
				}
			}
		}
	}

}
