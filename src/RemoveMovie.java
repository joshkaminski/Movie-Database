import java.sql.*;
import java.io.*;
import java.util.*;

public class RemoveMovie {
	
	// variable that allows for user input to be received 
	Scanner input;
	
	// strings that hold the commands to be passed into the prepared statements
	String remove = "DELETE FROM Movie WHERE title = ? AND year = ?";
	String check = "SELECT * from Movie WHERE title = ? AND year = ?";
	
	// variables that hold the name and year of the movie to be deleted
	String title = "";
	int year = 0;
	
	// strings used to organize output
	String new_line = System.getProperty("line.separator");
	String border = "*****************************************************************"
			+ "***********************************";
	
	// variable that keeps track of the running state of program
	boolean running = true;
	
	// constructor takes scanner as input
	public RemoveMovie(Scanner in) {
		
		input = in;
	}
	
	// method that allows a movie to be removed from the database
	public void remove_movie(Connection conn) throws SQLException {
		
		// ensures that running variable is set to true 
		running = true;
		
		// runs while user wants to remove a movie
		while(running) {
			
			System.out.println(border);
			System.out.println("You have selected to remove a movie from the database" + 
					new_line + "Enter \"CANCEL\" at any time to return to the main menu");
			System.out.println(border);
			
			// calls method that gets movie info from user 
			int run = get_input();
			
			// if user cancels removing a movie, breaks the loop and returns to main menu
			if(run == 0) break;
		
			// creates a prepared statement to check for a movie in the database
			PreparedStatement check_statement = conn.prepareStatement(check);
			check_statement.setString(1, title);
			check_statement.setInt(2, year);
			
			// executes query and stores results in a ResultSet
			ResultSet res = check_statement.executeQuery();
			
			// string to format output
			String break_line = "";
			
			// sets the size of the break_line if the movie is found in the database
			if(res.next()) {
				for(int i = 0; i < res.getString(1).length() + res.getString(4).length() + 20; i++) {
					break_line += "-";
				}
			}
			
			// executes query and stores results in a ResultSet
			ResultSet check_result = check_statement.executeQuery();
		
			// runs if movie is already in the database
			if(check_result.next()) { 
			
				System.out.println(border);
				System.out.println("\"" + title + "\"" + " (" + year + ")" +  " has been "
						+ "found in the database. Are you sure you would like to delete the "
						+ "following movie?:" + new_line);
				System.out.println(break_line);
				
				// outputs the movie that was found in the database
				String data = "| ";
				for(int i = 0; i < 4; i++) { // iterates through columns					
					data += check_result.getString(i+1) + " | ";
				}
				System.out.println(data);
				
				// warns user that removing a movie cannot be undone 
				System.out.println(break_line + new_line);
				System.out.println("Deleting " + "\"" + title + "\"" + " (" + year + ")" + 
						" cannot be undone. Type 'DELETE' to confirm, or any other key to cancel.");
				System.out.println(border);
				System.out.print("Enter selection: ");
				
				// gets input to confirm removal
				String ans = input.nextLine();
			
				// runs if user wants to delete movie
				if(ans.equals("DELETE")) {
				
					// prepares statement that will remove movie 
					PreparedStatement remove_statement = conn.prepareStatement(remove);
					remove_statement.setString(1, title);
					remove_statement.setInt(2, year);
				
					// removes movie from database
					remove_statement.executeUpdate();
					
					// tells user that movie has been removed from the database
					System.out.println(border);
					System.out.println("\"" + title + "\"" + " (" + year + ")" + 
							" has been successfully deleted from the database.");
					System.out.println(border);
					
					// closes remove statement
					remove_statement.close();
				}	
				
				// tells user that movie was not removed if the don't enter DELETE
				else {
					System.out.println(border);
					System.out.println("\"" + title + "\"" + " (" + year + ")" + 
							" has not been deleted from the database.");
					System.out.println(border);
				}
			}
			
			// tells user movie cannot be removed if the movie isn't in the database
			else {
				System.out.println(border);
				System.out.println("\"" + title + "\"" + " (" + year + ")" + 
						" has not been found in the database. Cannot delete movie.");
				System.out.println(border);
			}
			
			// asks user if they would like to remove another movie
			System.out.println("Would you like to: " + new_line + new_line + 
						"1) Delete another movie " + new_line + "2) Return to the menu");
			System.out.println(border);
			System.out.print("Enter selection: ");
			
			// loop won't run again if they don't want to remove another movie
			int ans2 = input.nextInt();
			if(ans2 == 2) {
				running = false;
			}
			
			// closes check statement
			check_statement.close();
		}
	}
	
	// method that gets movie info from the user 
	public int get_input() {
		
		// variables that keep track of which info has been recorded in the case of invalid input
		int step = 0;
		int counter_year = 0;
		
		// runs until user enters valid input
		while(true) {
		
			// runs if waiting for title input
			if(step == 0) {
				System.out.println("What is the title of the movie you would like to "
						+ "remove from the database?" + new_line);
				System.out.print("Enter title: ");
				
				// gets user input and checks to see if user wants to cancel
				input.nextLine();
				title = input.nextLine().trim();
				if(title.equals("CANCEL")) return 0;
				
				// increments step counter
				step++;
			}
			
			// runs if waiting for year input
			if(step == 1) {
				try {
					// runs if this is the first time asking for the year
					if(counter_year == 0) {
						System.out.println(border);
						System.out.println("What is the year " + "\"" + title + "\"" + " was released?");
					}
					System.out.print(new_line + "Enter year: ");
					
					// gets user input and checks to see if user wants to cancel
					String temp_year = input.nextLine().trim();
					if(temp_year.equals("CANCEL")) return 0;
					
					// checks if user input is valid
					if(temp_year.length() != 4 || Integer.parseInt(temp_year) < 1895 
							|| Integer.parseInt(temp_year) > Calendar.getInstance().get(Calendar.YEAR)) 
						throw new IllegalStateException();
					
					// casts user input to integer
					year = Integer.parseInt(temp_year);				
				}
				// runs if user does not enter an integer
				catch(Exception e){
					System.out.println(border);
					System.out.println("Invalid input. Please enter an integer representing the year "
							+ "that " + title + " was released.");
					
					// increments counter of how many times the user has been asked for the year
					counter_year++;
					continue;
				}
			}
			
			// returns nonzero value if all user input has been correctly received
			return 1;
		}
	}

}
