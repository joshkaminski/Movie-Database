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
	String border = "****************************************************************************************************";
	
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
			System.out.println("You have selected to add a movie to the database" + new_line + "Enter \"CANCEL\" at any time to return to the main menu");
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
					System.out.println("\"" + title + "\"" + " (" + year + ")" + " already exists in the database.");
					System.out.println("\"" + title + "\"" + " (" + year + ")" + " was not added to the database a second time.");
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
					System.out.println("\"" + title + "\"" + " (" + year + ")" + " has been successfully added to the database.");
					System.out.println(border);
					
				}
			}
			
			String new_line = System.getProperty("line.separator");
			System.out.println("Would you like to: " + new_line + new_line + "1) Add another movie " + new_line + "2) Return to the menu");
			System.out.println(border);
			//System.out.println();
			System.out.print("Enter selection: ");
			int ans = input.nextInt();
			
			if(ans == 2) {
				running = false;
			}
			
			check_statement.close();
			
		}
		
		
	}
	
	public int get_input() {
		
		int step = 0;
		int counter_year = 0;
		int counter_rating = 0;
		
		// System.out.println("What is the title of the movie you would like to add to the database?");
		
		while(true) {
		
			if(step == 0) {
				System.out.println("What is the title of the movie you would like to add to the database?");
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
						System.out.println("What is the year " + "\"" + title + "\"" + " was released?");
					}
					System.out.println();
					System.out.print("Enter year: ");
					String temp_year = input.nextLine().trim();
					if(temp_year.equals("CANCEL")) return 0;
					if(temp_year.length() != 4 || Integer.parseInt(temp_year) < 1895 || Integer.parseInt(temp_year) > Calendar.getInstance().get(Calendar.YEAR)) throw new IllegalStateException();
					
					year = Integer.parseInt(temp_year);
					step++;
				
				}
				catch(Exception e){
					System.out.println(border);
					System.out.println("Invalid input. Please enter an integer representing the year that " + "\"" + title + "\"" + " was released.");
					// System.out.println();
					// System.out.print("Enter year: ");
					counter_year++;
					continue;
				}
			}
		
			if(step == 2) {
				try {
					//if(counter_rating > 0) input.nextLine();
					if(counter_rating == 0) {
						System.out.println(border);
						System.out.println("What is the rating (from 0-10) of " + "\"" + title + "\"" + "?");
					}
					System.out.println();
					System.out.print("Enter rating: ");
					String temp_rating = input.nextLine().trim();
					if(temp_rating.equals("CANCEL")) return 0;
					
					rating = Double.parseDouble(temp_rating);
					step++;
				}
				catch(Exception e){
					//String in = input.nextLine().trim();
					//if(in.toLowerCase().equals("cancel")) return 0;
					
					System.out.println(border);
					System.out.println("Invalid input. Please enter an number representing the rating (from 0-10) of " + "\"" + title + "\"" +  "?");
					//System.out.println();
					//System.out.print("Enter rating: ");
					counter_rating++;
					continue;
				}
			}
			
			if(step == 3) {
				System.out.println(border);
				System.out.println("What is the genre of " + "\"" + title + "\"" +  "?");
				System.out.println();
				System.out.print("Enter genre: ");
				// input.nextLine();
				genre = input.nextLine().trim();
				if(genre.equals("CANCEL")) return 0;
				return 1;
			}
			
		}
			
		
		// return 0;
	}
}
