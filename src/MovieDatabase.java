import java.sql.*;

public class MovieDatabase {

	public static void main(String[] args) throws SQLException{
		
		String url = "jdbc:mysql://localhost:3306/My_Movie_Database";
		String username = "root";
		String password = "jkamMySQL24";
		String query = "select * from Movie";
		
		Connection con = DriverManager.getConnection(url, username, password);
		Statement sta = con.createStatement();
		// sta.executeQuery("INSERT INTO Movie VALUES (9.5, 'Thriller', 'Shutter Island')");
		ResultSet res = sta.executeQuery(query);
		
		while(res.next()) { // iterate through rows
			String data = "";
			
			for(int i = 0; i < 3; i++) { // iterates through columns
				data += res.getString(i+1) + " : ";
			}
			System.out.println(data);
			
		}
		
		con.close();
		sta.close();
		

	}

}
