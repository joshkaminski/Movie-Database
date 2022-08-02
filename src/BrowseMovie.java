import java.sql.*;
import java.io.*;
import java.util.*;

public class BrowseMovie{
	
	// variable that allows for user input to be received 
	Scanner input;
	
	// strings to hold the query to be passed into the statement
	String select = "select * from Movie";
	
	// strings used to organize output
	String new_line = System.getProperty("line.separator");
	String break_line = "";
	String border = "*****************************************************************"
			+ "************************************************************";
	
	// constructor takes scanner as input
	public BrowseMovie(Scanner in) {
		
		input = in;
	}
	
	// method that allows user to browse movies in the database
	public void browse_movie(Connection conn) throws SQLException {
		
		// calls sort_menu method to get user input for how to sort movies
		int selection = sort_menu();
		
		// sorts by when movie was added
		if(selection == 1) {
			select = "select * from Movie";
		}
		
		// sorts by title
		else if(selection == 2) {
			select = "select * from Movie order by Title, Year desc, Rating desc, Genre";
		}
		
		// sorts by year
		else if(selection == 3){
			select = "select * from Movie order by Year desc, Title, Rating desc, Genre";
		}
		
		// sorts by rating
		else if(selection == 4){
			select = "select * from Movie order by Rating desc, Title, Year desc, Genre";
		}
		
		// sorts by genre
		else {
			select = "select * from Movie order by Genre, Title, Year desc, Rating desc";	
		}
		
		// resets break_line variable
		break_line = "    ";
		
		// creates statement to execute the query
		Statement state = conn.createStatement();
		
		// executes query and stores results 
		ResultSet res2 = state.executeQuery(select);
		
		// declares variables that will hold the length of the longest title and longest genre
		int max_title = 0;
		int max_genre = 0;
		
		// finds the longest title and longest genre in the database
		while(res2.next()) {
			max_title = Math.max(max_title, res2.getString(1).length());
			max_genre = Math.max(max_genre, res2.getString(4).length());
		}
		
		// sets the break_line length
		for(int i = 0; i < max_title + max_genre + 21; i++) {
			break_line += "-";
		}
		
		// executes query and stores results 
		ResultSet res = state.executeQuery(select);
		
		System.out.println(border);
		System.out.println("The current movies in the database are:" + new_line);
		System.out.println(break_line);
		
		// counter used to output number of movies in database
		int counter = 1;
					
		// iterates through each movie in the database
		while(res.next()) { 
					
			// outputs which number movie the loop is on and increments the counter
			String movie = Integer.toString(counter) + ")";
			if(counter < 10) {
				movie += " ";
			}
			movie += " |";
			counter++;
			
			// adds space padding in front of movie title to string
			for(int i = 0; i < (max_title - res.getString(1).length() + 1)/2 + 1; i++) {
				movie += " ";
			}
			
			// adds movie title to string
			movie += res.getString(1);
			
			// adds space padding after movie title to string
			for(int i = 0; i < (max_title - res.getString(1).length())/2 + 1; i++) {
				movie += " ";
			}
			
			// adds year and rating to string
			movie += "| " + res.getString(2) + " | " + res.getString(3) + " | ";
			
			// adds space padding in front of movie genre to string
			for(int i = 0; i < (max_genre - res.getString(4).length())/2 + 1; i++) {
				movie += " ";
			}
			
			// adds genre to string
			movie += res.getString(4);
			
			// adds space padding after movie genre to string
			for(int i = 0; i < (max_genre - res.getString(4).length() + 1)/2 + 1; i++) {
				movie += " ";
			}
			
			movie += "|";
				
			// outputs the data
			System.out.println(movie);
			System.out.println(break_line);
		}
		
		System.out.println();
		
		// closes the statement
		state.close();
	}
	
	// method that gets sort selection from user
	public int sort_menu() {
		
		// counts how many times this method has run (if there is invalid user input)
		int counter = 0;
	
		System.out.println("Would you like to sort the movies by: " + new_line);
		
		// runs until valid user input is received 
		while(true){
			
			// asks user for input
			System.out.println("1) Date added" + 
					new_line + "2) Title (alphabetically)" + new_line + "3) Year released" + 
					new_line + "4) Rating" + new_line + "5) Genre (alphabetically)"); // or browse
			System.out.println(border);
			System.out.print("Enter selection: ");
			
			// attempts to parse user input as an integer
			try{
				// discards next line of input if loop has ran more than once
				if(counter > 0) input.nextLine();
			
				// gets input and returns user input if it is valid
				int ans = input.nextInt();
				if(ans > 0 && ans < 6) return ans;
				
				// if input is not valid, tells user and loop will run again 
				else {
					System.out.println(border);
					System.out.println("Invalid input. Please enter an integer 1-5, "
							+ "corresponding to one of the following menu options:" + new_line);
				}
			}
			
			// if input is not valid, tells user and loop will run again 
			catch(Exception e) {
				System.out.println(border);
				System.out.println("Invalid input. Please enter an integer 1-5, "
						+ "corresponding to one of the following menu options:" + new_line);
				counter++;
			}
		}
	}
}
