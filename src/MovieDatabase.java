import java.sql.*;
import java.io.*;
import java.util.*;

public class MovieDatabase {
	
	// variable that allows for user input to be received 
	Scanner input = new Scanner(System.in);
	
	// variables that allow for connection to database
	String url = "jdbc:mysql://localhost:3306/My_Movie_Database";
	String username = "root";
	String password = "jkamMySQL24";
	Connection conn;
	
	// objects used to perform actions on the database
	AddMovie add = new AddMovie(input);
	BrowseMovie browse = new BrowseMovie(input);
	RemoveMovie remove = new RemoveMovie(input);
	// UpdateMovie update = new UpdateMovie();
	
	// strings used to organize output
	String border = "*****************************************************************"
			+ "************************************************************";	
	String new_line = System.getProperty("line.separator");
	
	// variables that keep track of current state of program
	boolean running = true;
	int selection = 0;
		
	public static void main(String[] args) throws SQLException{
		
		// main method instantiates instance of a MovieDatabase
		new MovieDatabase();
	}
	
	public MovieDatabase() throws SQLException{
		
		// establish connection to database
		conn = DriverManager.getConnection(url, username, password);
		
		// output message welcomes user to database
		System.out.println(border + new_line + "Hello! Welcome to the Movie Database!");
		
		// runs as long as the user wants to continue performing actions on the database
		while(running) {
			
			// calls main_menu function to get user selection
			selection = main_menu();
			
			// runs if user selects to browse movies
			if(selection == 1) {
				System.out.println(border);
				browse.browse_movie(conn);
			}
			// runs if user selects to add a movie
			else if(selection == 2) {
				add.add_movie(conn);
			}
			
			// runs if user selects to remove a movie
			else if(selection == 3){
				remove.remove_movie(conn);
				//System.out.println(border);
			}
			
//			else if(selection == 4){
//				//update.update_movie(conn, input);
//			}
			
			// runs if user selects to exit program
			else {
				System.out.println(border + new_line + "Thank you for using the Movie "
						+ "Database. Goodbye.");
				break;
				
			}
		}
		
		// closes connection to database and scanner object
		conn.close();
		input.close();
		
	}
	
	// method that gets menu selection from user
	public int main_menu() {
		
		// counts how many times this method has run (if there is invalid user input)
		int counter = 0;
		
		System.out.println(border);
		System.out.println("Would you like to: " + new_line);
		
		// runs until valid user input is received 
		while(true){
			
			// asks user for input
			System.out.println("1) Browse the current movies in the database" + 
					new_line + "2) Add a movie to the database" + new_line + "3) "
					+ "Delete a movie from the database" + new_line + "4) Exit the database"); 
			System.out.println(border);
			System.out.print("Enter selection: ");
			
			// attempts to parse user input as an integer
			try{
				// discards next line of input if loop has ran more than once
				if(counter > 0) input.nextLine();
			
				// gets input and returns user input if it is valid
				int ans = input.nextInt();
				if(ans > 0 && ans < 5) return ans;
				
				// if input is not valid, tells user and loop will run again 
				else {
					System.out.println(border);
					System.out.println("Invalid input. Please enter an integer 1-5, corresponding "
							+ "to one of the following menu options:" + new_line);
				}
			}
			// if input is not valid, tells user and loop will run again 
			catch(Exception e) {
				System.out.println(border);
				System.out.println("Invalid input. Please enter an integer 1-5, corresponding "
						+ "to one of the following menu options:" + new_line);
				counter++;
			}
		}	
	}
}
