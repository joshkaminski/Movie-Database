import java.sql.*;
import java.io.*;
import java.util.*;

public class BrowseMovie{
	
	String new_line = System.getProperty("line.separator");
	String break_line = "";
	String border = "****************************************************************************************************";

	
	Scanner input;
	
	String select = "select * from Movie";
	
	public BrowseMovie(Scanner in) {
		
		input = in;
	}
	
	public void browse_movie(Connection conn) throws SQLException {
		
		int selection = sort_menu();
		
		if(selection == 1) {
			select = "select * from Movie";
		}
		else if(selection == 2) {
			select = "select * from Movie order by Title, Year desc, Rating desc, Genre";
		}
		else if(selection == 3){
			select = "select * from Movie order by Year desc, Title, Rating desc, Genre";
		}
		else if(selection == 4){
			select = "select * from Movie order by Rating desc, Title, Year desc, Genre";
		}
		else {
			select = "select * from Movie order by Genre, Title, Year desc, Rating desc";
			
		}
		
		break_line = "   ";
		
		Statement state = conn.createStatement();
		
		// sta.executeUpdate("INSERT INTO Movie VALUES (9.5, 'Thriller', 'Shutter Island')");

		ResultSet res2 = state.executeQuery(select);
		
		int max_title = 0;
		int max_genre = 0;
		
		while(res2.next()) {
			max_title = Math.max(max_title, res2.getString(1).length());
			max_genre = Math.max(max_genre, res2.getString(4).length());
		}
		
		for(int i = 0; i < max_title + max_genre + 21; i++) {
			break_line += "-";
		}
		
		ResultSet res = state.executeQuery(select);
		
		System.out.println(border);
		System.out.println("The current movies in the database are:" + new_line);
		System.out.println(break_line);
		int counter = 1;
		
						
		while(res.next()) { // iterate through rows
					
			String data = Integer.toString(counter) + ") |";
			counter++;
			
			for(int i = 0; i < (max_title - res.getString(1).length() + 1)/2 + 1; i++) {

				data += " ";
			}
			
			data += res.getString(1);
			
			for(int i = 0; i < (max_title - res.getString(1).length())/2 + 1; i++) {
				data += " ";
			}
			
			//if(res.getString(1).length()%2 == 1) data += " ";
		
			
			
			
			
			data += "| " + res.getString(2) + " | " + res.getString(3) + " | ";
			
			for(int i = 0; i < (max_genre - res.getString(4).length())/2 + 1; i++) {
				data += " ";
			}
			
			data += res.getString(4);
			
			for(int i = 0; i < (max_genre - res.getString(4).length() + 1)/2 + 1; i++) {
				data += " ";
			}
			//if(res.getString(4).length()%2 == 1) data += " ";
			
			
			data += "|";
							
//			for(int i = 0; i < 4; i++) { // iterates through columns					
//				data += res.getString(i+1) + " | ";
//			}
			System.out.println(data);
			System.out.println(break_line);
		}
		
		System.out.println();
		
		state.close();
	}
	
	public int sort_menu() {
		
		int counter = 0;
	
		System.out.println("Would you like to sort the movies by: " + new_line);
		
		while(true){
			
			// if(counter > 0) System.out.println(border);
			
			System.out.println("1) Date added" + 
					new_line + "2) Title (alphabetically)" + new_line + "3) Year released" + 
					new_line + "4) Rating" + new_line + "5) Genre (alphabetically)"); // or browse
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
