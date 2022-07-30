import java.sql.*;
import java.io.*;
import java.util.*;

public class AddMovie{
	
	String new_line = System.getProperty("line.separator");
	String border = "***************************************************************************";
	
	Scanner input;
	
	String title = "";
	int year = 0;
	double rating = 0.0;
	String genre = "";
	
	String add = "INSERT INTO Movie VALUES (?, ?, ?, ?)";
	String check = "SELECT count(*) from Movie WHERE title = ? AND year = ?";
	
	boolean running = true;
	
	public AddMovie(Scanner in) {
	
		input = in;
	}
	
	public void add_movie(Connection conn) throws SQLException {
		
		running = true;
		
		while(running) {
			
			System.out.println(border);
			System.out.println("You have selected to add a movie to the database" + new_line + "Enter \"CANCEL\" at any time to return to the main menu");
			System.out.println(border);
		
			int run = get_input();
			
			if(run == 0) break;
			// get_input();
			
			PreparedStatement check_statement = conn.prepareStatement(check);
			check_statement.setString(1, title);
			check_statement.setInt(2, year);
			
			ResultSet check_result = check_statement.executeQuery();
			
			if(check_result.next()) {
				
				if(check_result.getInt(1) == 1) {
					System.out.println(border);
					System.out.println("\"" + title + "\"" + " (" + year + ")" + " already exists in the database.");
					System.out.println("\"" + title + "\"" + " (" + year + ")" + " was not added to the database a second time.");
					System.out.println(border);
				}
				else{
					PreparedStatement add_statement = conn.prepareStatement(add);
					add_statement.setString(1, title);
					add_statement.setInt(2, year);
					add_statement.setDouble(3, rating);
					add_statement.setString(4, genre);
				
					add_statement.executeUpdate();
					
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










































//JFrame f = new JFrame("Add a Movie");
//
//JLabel page_title = new JLabel("Add to the Movie Database", SwingConstants.CENTER);
//
//JLabel title_label = new JLabel("Title:");
//JLabel year_label = new JLabel("Year:");
//JLabel rating_label = new JLabel("Rating:");
//JLabel genre_label = new JLabel("Genre:");
//
//JTextField title_field = new JTextField();
//JTextField year_field = new JTextField();
//JTextField rating_field = new JTextField();
//JTextField genre_field = new JTextField();
//
//JPanel spacer1 = new JPanel();
//JPanel spacer2 = new JPanel();
//
//JButton back_button = new JButton("Back");
//JButton add_button = new JButton("Add");
//
//String default_result = "Enter movie details and click \"add\" to add a movie to the database";
//String added_result = "You've successfylly added a movie! Enter movie details to add another";
//JLabel result = new JLabel(default_result);
//JPanel button_panel = new JPanel();
//
//GridBagLayout grid_bag = new GridBagLayout();
//GridBagConstraints constraints = new GridBagConstraints();
//
//public AddMovieFrame(){
//	
//	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
//	f.setBackground(Color.white);
//	
//	f.setLayout(grid_bag);
//	constraints.fill = GridBagConstraints.HORIZONTAL;
//	
//	page_title.setFont(new Font("Verdana", Font.PLAIN, 18));
//	page_title.setBorder(new EmptyBorder(10,50,10,50));
//	
//	title_label.setFont(new Font("Verdana", Font.PLAIN, 15));
//	year_label.setFont(new Font("Verdana", Font.PLAIN, 15));
//	rating_label.setFont(new Font("Verdana", Font.PLAIN, 15));
//	genre_label.setFont(new Font("Verdana", Font.PLAIN, 15));
//	
//	title_field.setFont(new Font("Verdana", Font.PLAIN, 15));
//	year_field.setFont(new Font("Verdana", Font.PLAIN, 15));
//	rating_field.setFont(new Font("Verdana", Font.PLAIN, 15));
//	genre_field.setFont(new Font("Verdana", Font.PLAIN, 15));
//	
//	result.setBorder(new EmptyBorder(40,0,10,0));
//	
////	title_label.setBorder(new EmptyBorder(10,0,10,0));
////	year_label.setBorder(new EmptyBorder(10,0,10,0));
////	rating_label.setBorder(new EmptyBorder(10,0,10,0));
////	genre_label.setBorder(new EmptyBorder(10,0,10,0));
//	
//	//title_label.setFont(new Font("Verdana", Font.PLAIN, 12));
//	//year_label.setFont(new Font("Verdana", Font.PLAIN, 12));
//	
//	button_panel.setLayout(new GridLayout(1,2));
//	button_panel.add(back_button);
//	button_panel.add(add_button);
//	
//	
//	constraints.gridx = 0;
//	constraints.gridy = 0;
//	constraints.gridwidth = 10;
//	f.add(page_title, constraints);
//	constraints.gridwidth = 3;
//	
//	constraints.gridx = 0;
//	constraints.gridy = 1;
//	f.add(title_label, constraints);
//	
//	constraints.gridx = 0;
//	constraints.gridy = 2;
//	f.add(year_label, constraints);
//
//	constraints.gridx = 0;
//	constraints.gridy = 3;
//	f.add(rating_label, constraints);
//	
//	constraints.gridx = 0;
//	constraints.gridy = 4;
//	f.add(genre_label, constraints);
//	
//	constraints.gridwidth = 7;
//	
//	constraints.gridx = 3;
//	constraints.gridy = 1;
//	f.add(title_field, constraints);
//	
//	constraints.gridx = 3;
//	constraints.gridy = 2;
//	f.add(year_field, constraints);
//	
//	constraints.gridx = 3;
//	constraints.gridy = 3;
//	f.add(rating_field, constraints);
//	
//	constraints.gridx = 3;
//	constraints.gridy = 4;
//	f.add(genre_field, constraints);
//	
//	constraints.gridwidth = 10;
//	
//	constraints.gridx = 0;
//	constraints.gridy = 5;
//	f.add(spacer1, constraints);
//	
//	constraints.gridx = 0;
//	constraints.gridy = 6;
//	f.add(spacer2, constraints);
//	
//	constraints.gridx = 0;
//	constraints.gridy = 7;
//	f.add(button_panel, constraints);
//	
//	constraints.gridx = 0;
//	constraints.gridy = 8;
//	f.add(result, constraints);
//	
//	
//	f.setSize(550, 450); // sets up frame size
//	f.setVisible(true);
//	
//}
//
//public static void main(String[] args) {
//	
//	new AddMovieFrame();
//
//}
