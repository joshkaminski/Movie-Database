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
	Statement state;
	
	boolean running = true;
	int selection = 0;
	
	// main method instantiates instance of a MovieDatabase
	public static void main(String[] args) throws SQLException{
		
		new MovieDatabase();
		
	}
	
	
	
	public MovieDatabase() throws SQLException{
		
		// establish connection to database
		conn = DriverManager.getConnection(url, username, password);
		state = conn.createStatement();
		
		while(running) {
			
			selection = main_menu();
			
			if(selection == 1) {
				AddMovie.add_movie(conn, input);
			}
			else if(selection == 2) {
				//access_movie();
			}
			else if(selection == 3){
				RemoveMovie.remove_movie(conn, input);
			}
			else {
				break;
				
			}
		}
		
		// sta.executeUpdate("INSERT INTO Movie VALUES (9.5, 'Thriller', 'Shutter Island')");
		ResultSet res = state.executeQuery("select * from Movie");
				
		while(res.next()) { // iterate through rows
			
			String data = "";
					
			for(int i = 0; i < 4; i++) { // iterates through columns					
				data += res.getString(i+1) + " : ";
			}
			System.out.println(data);
					
		}
				
		conn.close();
		state.close();
		input.close();
		
	}
	
	
	
	public int main_menu() {
		
		String new_line = System.getProperty("line.separator");
		System.out.println("Would you like to: " + new_line + "1) Add a movie " + new_line + "2) Access a movie " + new_line + "3) Delete a movie" + new_line + "4) Quit"); // or browse
		System.out.println();
		int ans = input.nextInt();
		
		return ans;
		
	}

}
