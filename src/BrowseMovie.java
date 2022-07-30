import java.sql.*;
import java.io.*;
import java.util.*;

public class BrowseMovie{
	
	String new_line = System.getProperty("line.separator");
	String break_line = "";
	
	Scanner input;
	
	String select = "select * from Movie";
	
	public BrowseMovie(Scanner in) {
		
		input = in;
	}
	
	public void browse_movie(Connection conn) throws SQLException {
		
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

}

//JFrame f = new JFrame("Movie Database");
//
//JPanel top_panel = new JPanel();
//JPanel bottom_panel = new JPanel();
//
//JLabel page_title = new JLabel("Welcome to the Movie Database", SwingConstants.CENTER);
//
//JTextField search_bar = new JTextField();
//JButton search_button = new JButton("Search"); 
//JButton add_button = new JButton("Add New Movie"); 
//
//JLabel sort_label = new JLabel("Sort By:");
//JRadioButton recent_button = new JRadioButton("Recent", true);
//JRadioButton title_button = new JRadioButton("Title");
//JRadioButton year_button = new JRadioButton("Year");
//JRadioButton rating_button = new JRadioButton("Rating");
//JRadioButton genre_button = new JRadioButton("Genre");
//
//ButtonGroup sort = new ButtonGroup();
//
//GridBagLayout grid_bag = new GridBagLayout();
//GridBagConstraints constraints = new GridBagConstraints();
//
//public BrowseMovieFrame(){
//	
//	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
//	f.setBackground(Color.white); 
//	f.add(top_panel, BorderLayout.NORTH);
//	bottom_panel.setBackground(Color.BLUE);
//	f.add(bottom_panel, BorderLayout.CENTER);
//	
//	top_panel.setLayout(grid_bag);	
//	constraints.fill = GridBagConstraints.HORIZONTAL;
//	
//	page_title.setFont(new Font("Verdana", Font.PLAIN, 18));
//	page_title.setBorder(new EmptyBorder(12,0,12,0));
//	
//	search_bar.setFont(new Font("Verdana", Font.PLAIN, 16));
//	
//	sort_label.setBorder(new EmptyBorder(10,0,10,0));
//	// page_title.set
//	
//	constraints.gridx = 0;
//	constraints.gridy = 0;
//	constraints.gridwidth = 7;
//	top_panel.add(page_title, constraints);
//	constraints.gridwidth = 1;
//	
//	constraints.gridx = 0;
//	constraints.gridy = 1;
//	constraints.gridwidth = 6;
//	top_panel.add(search_bar, constraints);
//	constraints.gridwidth = 1;
//	
//	constraints.gridx = 6;
//	constraints.gridy = 1; // 
//	top_panel.add(search_button, constraints);
//	
//	constraints.gridx = 6; // 
//	constraints.gridy = 2;
//	top_panel.add(add_button, constraints);
//	
//	constraints.gridx = 0;
//	constraints.gridy = 2; // 
//	top_panel.add(sort_label, constraints);
//	
//	constraints.gridx = 1;
//	constraints.gridy = 2; // 
//	top_panel.add(recent_button, constraints);
//	
//	constraints.gridx = 2;
//	constraints.gridy = 2; // 
//	top_panel.add(title_button, constraints);
//	
//	constraints.gridx = 3;
//	constraints.gridy = 2; // 
//	top_panel.add(year_button, constraints);
//	
//	constraints.gridx = 4;
//	constraints.gridy = 2; // 
//	top_panel.add(rating_button, constraints);
//	
//	constraints.gridx = 5;
//	constraints.gridy = 2; // 
//	top_panel.add(genre_button, constraints);
//	
//	sort.add(recent_button);
//	sort.add(title_button);
//	sort.add(year_button);
//	sort.add(rating_button);
//	sort.add(genre_button);
//	
//	f.setSize(550, 450); // sets up frame size
//	f.setVisible(true);
//	
//}
//
//public static void main(String[] args) {
//	
////	try {
////        UIManager.setLookAndFeel(
////                      "javax.swing.plaf.metal.MetalLookAndFeel");
////                    //  "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
////                    //UIManager.getCrossPlatformLookAndFeelClassName());
////    } catch (Exception ex) {
////        ex.printStackTrace();
////    }
//	
//	new BrowseMovieFrame();
//
//}
