import java.sql.*;
import java.io.*;
import java.util.*;

public class AddMovie {
	
	public static void add_movie(Connection conn, Statement state) throws SQLException {
		
		String title = "";
		int year = 0;
		double rating = 0.0;
		String genre = "";
		
		Scanner t = new Scanner(System.in);
		System.out.println("What is the title of the movie you would like to add to the database?");
		title = t.nextLine();
		
		
		Scanner y = new Scanner(System.in);
		System.out.println("What is the year " + title + " was released?");
		year = y.nextInt();
		
		
		Scanner r = new Scanner(System.in);
		System.out.println("What is the rating (from 0-10) of " + title + "?");
		rating = r.nextDouble();
		
		
		Scanner g = new Scanner(System.in);
		System.out.println("What is the genre of " + title + "?");
		genre = g.nextLine();
		
		
		String add = "INSERT INTO Movie VALUES ('" + title + "', " + year + ", " + rating + ", '" + genre + "')";
		
		state.executeUpdate(add);
		
		System.out.println("Move successfully added. Would you like to add another movie or return to the menu?");
		
		/*
		t.close();
		y.close();
		r.close();
		g.close();
		*/
	}
	

}
