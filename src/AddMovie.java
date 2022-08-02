import java.sql.*;
import java.io.*;
import java.util.*;

public class AddMovie{
	
	// variable that allows for user input to be received 
	Scanner input;
	
	// strings that hold the queries and commands to be passed into the statements
	String add = "INSERT INTO Movie VALUES (?, ?, ?, ?)";
	String check = "SELECT count(*) from Movie WHERE title = ? AND year = ?";
		
	// variables that hold data about the movie
	String title = "";
	int year = 0;
	double rating = 0.0;
	String genre = "";
		
	// strings used to organize output
	String new_line = System.getProperty("line.separator");
	String border = "*****************************************************************"
			+ "************************************************************";
	
	// variable that keeps track of the running state of program
	boolean running = true;
	
	// constructor takes scanner as input
	public AddMovie(Scanner in) {
	
		input = in;
	}
	
	// method that allows a movie to be added to the database
	public void add_movie(Connection conn) throws SQLException {
		
		// ensures that running variable is set to true 
		running = true;
		
		// runs while user wants to add movies 
		while(running) {
			
			System.out.println(border);
			System.out.println("You have selected to add a movie to the database" + new_line + 
					"Enter \"CANCEL\" at any time to return to the main menu");
			System.out.println(border);
			
			// calls method that gets movie info from user 
			int run = get_input();
			
			// if user cancels adding a movie, breaks the loop and returns to main menu
			if(run == 0) break;
			
			// creates a prepared statement to check for a movie in the database
			PreparedStatement check_statement = conn.prepareStatement(check);
			check_statement.setString(1, title);
			check_statement.setInt(2, year);
			
			// executes query and stores results in a ResultSet
			ResultSet check_result = check_statement.executeQuery();
			
			if(check_result.next()) {
				
				// checks if the movie already exists in the database and outputs error message if it does
				if(check_result.getInt(1) == 1) {
					System.out.println(border);
					System.out.println("\"" + title + "\"" + " (" + year + ")" + 
							" already exists in the database.");
					System.out.println("\"" + title + "\"" + " (" + year + ")" + 
							" was not added to the database a second time.");
					System.out.println(border);
				}
				
				// runs if movie does not exist in the database
				else{
					// prepares statement that will add movie 
					PreparedStatement add_statement = conn.prepareStatement(add);
					add_statement.setString(1, title);
					add_statement.setInt(2, year);
					add_statement.setDouble(3, rating);
					add_statement.setString(4, genre);
				
					// adds movie to the database
					add_statement.executeUpdate();
					
					// tells user that movie has been added to the database
					System.out.println(border);
					System.out.println("\"" + title + "\"" + " (" + year + ")" + " has "
							+ "been successfully added to the database.");
					System.out.println(border);
					
					// closes add statement
					add_statement.close();
				}
			}
			// asks user if they would like to add another movie
			System.out.println("Would you like to: " + new_line + new_line + "1) "
					+ "Add another movie " + new_line + "2) Return to the menu");
			System.out.println(border);
			System.out.print("Enter selection: ");
			
			// loop won't run again if they don't want to add another movie
			int ans = input.nextInt();
			if(ans == 2) {
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
		int counter_rating = 0;
		
		// runs until user enters valid input
		while(true) {
		
			// runs if waiting for title input
			if(step == 0) {
				System.out.println("What is the title of the movie you would like "
						+ "to add to the database?" + new_line);
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
					
					// increments step counter
					step++;
				}
				
				// runs if user does not enter an integer
				catch(Exception e){
					System.out.println(border);
					System.out.println("Invalid input. Please enter an integer representing the "
							+ "year that " + "\"" + title + "\"" + " was released.");
					
					// increments counter of how many times the user has been asked for the year
					counter_year++;
					continue;
				}
			}
		
			// runs if waiting for rating input
			if(step == 2) {
				try {
					// runs if this is the first time asking for the rating
					if(counter_rating == 0) {
						System.out.println(border);
						System.out.println("What is the rating (from 0-10) of " + "\"" + 
						title + "\"" + "?");
					}
					System.out.print(new_line + "Enter rating: ");
					
					// gets user input and checks to see if user wants to cancel
					String temp_rating = input.nextLine().trim();
					if(temp_rating.equals("CANCEL")) return 0;
					
					// checks if user input is valid
					if(Double.parseDouble(temp_rating) < 0 || Double.parseDouble(temp_rating) > 10) 
						throw new IllegalStateException();
					
					// casts user input to double
					rating = Double.parseDouble(temp_rating);
					
					// increments step counter
					step++;
				}
				
				// runs if user does not enter a double
				catch(Exception e){
					System.out.println(border);
					System.out.println("Invalid input. Please enter an number representing the rating ("
							+ "from 0-10) of " + "\"" + title + "\"" + ".");
					
					// increments counter of how many times the user has been asked for the year
					counter_rating++;
					continue;
				}
			}
			
			// runs if waiting for genre input
			if(step == 3) {
				System.out.println(border);
				System.out.println("What is the genre of " + "\"" + title + "\"" +  "?" + new_line);
				System.out.print("Enter genre: ");
				
				// gets user input and checks to see if user wants to cancel
				genre = input.nextLine().trim();
				if(genre.equals("CANCEL")) return 0;
			}
			
			// returns nonzero value if all user input has been correctly received
			return 1;
		}
	}
}
