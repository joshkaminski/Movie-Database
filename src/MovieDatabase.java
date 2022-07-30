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
	
	boolean running = true;
	int selection = 0;
	
	String border = "***************************************************************************";
	String new_line = System.getProperty("line.separator");
	
	AddMovie add = new AddMovie(input);
	BrowseMovie browse = new BrowseMovie(input);
	RemoveMovie remove = new RemoveMovie(input);
	// UpdateMovie update = new UpdateMovie();
		
	// main method instantiates instance of a MovieDatabase
	public static void main(String[] args) throws SQLException{
		
		new MovieDatabase();
		
	}
	
	
	
	public MovieDatabase() throws SQLException{
		
		// establish connection to database
		conn = DriverManager.getConnection(url, username, password);
		
		System.out.println(border + new_line + "Hello! Welcome to the Movie Database!");
		
		
		while(running) {
			
			selection = main_menu();
			
			if(selection == 0) {
				System.out.println(border);
				selection = main_menu();
			}
			else if(selection == 1) {
				System.out.println(border);
				browse.browse_movie(conn);
			}
			else if(selection == 2) {
				add.add_movie(conn);
			}
			else if(selection == 3){
				remove.remove_movie(conn);
				//System.out.println(border);
			}
			else if(selection == 4){
				//update.update_movie(conn, input);
			}
			else {
				
				System.out.println(border + new_line + "Thank you for using the Movie Database. Goodbye.");
				break;
				
			}
		}
		
		
				
		conn.close();
		input.close();
		
	}
	
	
	
	public int main_menu() {
		
		int counter = 0;
		
		System.out.println(border);
		System.out.println("Would you like to: " + new_line);
		
		while(true){
			
			// if(counter > 0) System.out.println(border);
			
			System.out.println("1) Browse the current movies in the database" + 
					new_line + "2) Add a movie to the database" + new_line + "3) Delete a movie from the database" + 
					new_line + "4) Update a movie in the database" + new_line + "5) Exit the database"); // or browse
			System.out.println(border);
			System.out.print("Enter selection: ");
			
			try{
				if(counter > 0) input.nextLine();
			
				int ans = input.nextInt();
				
				if(ans > 0 && ans < 6) return ans;
				
				else {
					System.out.println(border);
					System.out.println("Invalid input. Please enter an integer 1-5, corresponding to one of the following menu options:" + new_line);
				}
			}
			catch(Exception e) {
				System.out.println(border);
				System.out.println("Invalid input. Please enter an integer 1-5, corresponding to one of the following menu options:" + new_line);
				counter++;
			}
		}
		
	}

}
