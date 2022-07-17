import java.sql.*;
import java.io.*;
import java.util.*;

public class MovieDatabase {
	
	public static void main(String[] args) throws SQLException{
		
		// variables that allow for connection to database
		String url = "jdbc:mysql://localhost:3306/My_Movie_Database";
		String username = "root";
		String password = "jkamMySQL24";
		
		// establish connection to database
		Connection conn = DriverManager.getConnection(url, username, password);
		Statement state = conn.createStatement();
		
		boolean running = true;
		int selection = 0;
		
		while(running) {
			
			selection = main_menu();
			
			if(selection == 1) {
				AddMovie.add_movie(conn, state);
			}
			else if(selection == 2) {
				//access_movie();
			}
			else if(selection == 3){
				//remove_movie();
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
	}
	
	public static int main_menu() {
		
		String new_line = System.getProperty("line.separator");
		System.out.println("Would you like to: " + new_line + "1) Add a movie " + new_line + "2) Access a movie " + new_line + "3) Delete a movie" + new_line + "4) Quit"); // or browse
		
		Scanner input = new Scanner(System.in);
		int ans = input.nextInt();
		// input.close();
		return ans;
		
	}

}
