import java.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * 
 * @author Ivan Ivanov
 *
 */

class MysqlCon{  

	//Conect variables for the DB
	private Connection connection = null;
	private String connectionUrl = "jdbc:mysql://lp2.cd3s6sbksbtz.us-east-2.rds.amazonaws.com/lp2";
	private String connectionUser = "admin";
	private String connectionPassword = "!qwerty123";
	//here lp2 is database name, admin is username and !qwerty123 is password 

	public static ArrayList <AirbnbListing> data = null;

	/**
	 * Try to connect to the DB instance
	 * @return the connection
	 */
	public Connection getConnection(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			return connection; 
		}
		catch(Exception e){
			System.out.println(e); 
			System.out.println("No connection!");
		}
		return null;
	}

	/**
	 * Create a new user and add him to the DB instance
	 * @param user the username of the new user
	 * @param pass the password choosen by the user
	 * @param name the full name of the user
	 * @return
	 */
	public void createUser(String user, String pass, String name) {
		//Coonect to the DB
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		//Create hashed password from normal password String
		String hashed = BCrypt.hashpw(pass, BCrypt.gensalt());

		//Create query to check if user exists
		String userCheck = "SELECT * FROM users WHERE user_login='"+user+"'";

		// the mysql insert statement
		String insertUser = "INSERT INTO users (user_login, user_password, user_name) "
				+ "VALUES ( '" + user + "' , '" + hashed + "' , '" + name + "' );";

		try{ 
			stmt=con.createStatement();  
			rs=stmt.executeQuery(userCheck);  
			try {
				if(!rs.isBeforeFirst()) {
					//User does not exists

					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = con.prepareStatement(insertUser);

					// execute the preparedstatement
					preparedStmt.execute();

					//the sql should be successful otherwise throw an error
				}
			}catch(Exception e) {
				System.out.println("This user already exists!");
			}

		}catch(Exception e){ 
			System.out.println(e); 
			System.out.println("No connection!");
		}finally{
			try { rs.close(); } catch (Exception e) { /* ignored */ }
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}

	}

	/**
	 * Chage the password of the user
	 * @param user the username of the new user
	 * @param pass the new password of the user
	 * @return
	 */
	public void changePass(String user, String pass) {
		//Coonect to the DB
		Connection con = getConnection();
		PreparedStatement stmt = null;

		//Create hashed password from normal password String
		String hashed = BCrypt.hashpw(pass, BCrypt.gensalt());

		// the mysql update statement
		String updateUser = "UPDATE users SET user_password = '"+hashed+"' WHERE user_login='"+user+"'";

		try{    
			// create the mysql insert preparedstatement
			stmt = con.prepareStatement(updateUser);

			// execute the preparedstatement
			stmt.execute();

			//the preparedstatement should be successful otherwise throw an error
		}catch(Exception e){ 
			System.out.println(e); 
			System.out.println("No connection!");
		}finally{
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}

	}

	/**
	 * Check if the typed password of the user is correct.
	 * Use to check at log in to the system
	 * @param user username of the user
	 * @param normalPass password typed by user
	 * @return true if the password is correct
	 */
	public boolean checkPass(String user, String normalPass) {
		//Coonect to the DB
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		//sql to get the password of the specified user from DB
		String sql = "SELECT user_password FROM users WHERE user_login='"+user+"'";
		try{ 
			stmt=con.createStatement();  
			rs=stmt.executeQuery(sql);
			if(rs.isBeforeFirst()) {
				//user exists
				rs.next();
				//hashed version of the password returned from the query
				String hashedPass = rs.getString(1);
				if(BCrypt.checkpw(normalPass, hashedPass)) {
					//password check is successful
					return true;
				}
				//User or password are wrong
				return false;
			}
		}catch(Exception e){ 
			System.out.println(e);
			System.out.println("No connection!");
			return false;
		}finally{
			try { rs.close(); } catch (Exception e) { /* ignored */ }
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}
		return false;
	}

	public void addFavourite(String userName, String propertyId) {
		//Coonect to the DB
		Connection con = getConnection();
		Statement stmt = null;

		//sql to set new address of user
		String updateAddress = "INSERT INTO favourites (user_id, property_id) values ((SELECT id_ FROM users WHERE user_login = '"+userName+"'),"+propertyId+");";

		try{ 
			stmt=con.createStatement(); 

			// create the mysql update preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(updateAddress);

			// execute the preparedstatement
			preparedStmt.execute();	
		}catch(Exception e){ 
			System.out.println(e);
		} finally{
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}
	}

	public void removeFavourite(String userName, String propertyId) {
		//Coonect to the DB
		Connection con = getConnection();
		Statement stmt = null;

		//sql to set new address of user
		String updateAddress = "DELETE FROM favourites WHERE user_id = (SELECT id_ FROM users WHERE user_login = '"+userName+"') AND property_id = '"+propertyId+"';";

		try{ 
			stmt=con.createStatement(); 

			// create the mysql update preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(updateAddress);

			// execute the preparedstatement
			preparedStmt.execute();	
		}catch(Exception e){ 
			System.out.println(e);
		} finally{
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}
	}

	/**
	 * Update the address of the user
	 * @param user username of the user
	 * @param address the new address of user with username 'user'
	 */
	public void changeAddress(String user, String address) {
		//Coonect to the DB
		Connection con = getConnection();
		PreparedStatement stmt = null;


		//sql to set new address of user
		String updateAddress = "UPDATE users SET user_address='"+address+"' WHERE user_login='"+user+"'";

		try{
			// create the mysql update preparedstatement
			stmt = con.prepareStatement(updateAddress);

			// execute the preparedstatement
			stmt.execute();	
		}catch(Exception e){ 
			System.out.println(e);
		} finally{
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}
	}

	/**
	 * Update the profile photo of user
	 * @param user username of the user
	 * @param photoURL the name of the photo that the user choose located in ( imgs/photoName.*
	 */
	public void changePhoto(String user, String photoURL) {
		//Coonect to the DB
		Connection con = getConnection();
		PreparedStatement stmt = null;
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(new File(photoURL));

			stmt = con.prepareStatement("UPDATE users SET user_photo = ? WHERE user_login = '" + user+"'");
			stmt.setBinaryStream(1, (InputStream) inputStream);

			stmt.execute();
		}catch(Exception e){ 
			System.out.println(e);
		} finally{
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		} 
	}

	/*
	 * Return all the properties from the DB
	 */
	public ArrayList<AirbnbListing> loadAll() {
		return load("SELECT * FROM properties");
	}

	/**
	 * Load properties from the database with specified price range
	 * @param minPrice The min price of a property from the database
	 * @param maxPrice The max price of a property from the database
	 * @return ArrayList of properties filtered by price set by user
	 */
	public ArrayList<AirbnbListing> loadFiltered(int minPrice, int maxPrice,int nights) {
		String sql = "SELECT * FROM ((SELECT * FROM properties WHERE price BETWEEN "+minPrice+" AND " + 
				maxPrice +" AND minimum_nights = "+nights+")t1 left JOIN (SELECT property_id,'true' as fav FROM favourites "
				+ "where user_id = (SELECT id_ from users where user_login = '"+ScreensFramework.username+"'))t2 ON t1.id = t2.property_id )";
		return load(sql);
	}

	/**
	 * Load properties from specific borough with specified price range
	 * @param borough The name of the borough we want properties from
	 * @param minPrice The min price of a property from the database
	 * @param maxPrice The max price of a property from the database
	 * @param nights Number of nights the user will stay
	 * @return ArrayList of properties from a borough and filtered by price and nights set by user
	 */
	public ArrayList<AirbnbListing> loadFromBorough(String borough, int minPrice, int maxPrice, int nights){
		String sql = "SELECT * FROM ((SELECT * FROM properties WHERE price BETWEEN " + minPrice + " AND " 
				+ maxPrice + " AND neighbourhood = '" + borough+"' AND minimum_nights <= "+nights+")t1 left JOIN (SELECT property_id,'true' as fav "
				+ "FROM favourites where user_id = (SELECT id_ from users where user_login = '"+ScreensFramework.username+"'))t2 "
				+ "ON t1.id = t2.property_id )";
		System.out.println(sql);
		return load(sql);
	}

	/**
	 * Load properties from specific borough with specified price range and specified room type
	 * @param borough The name of the borough we want properties from
	 * @param minPrice The min price of a property from the database
	 * @param maxPrice The max price of a property from the database
	 * @param nights Number of nights the user will stay
	 * @param room The type of room the user wants
	 * @return ArrayList of properties from a borough and filtered by price, nights, and room type set by user
	 */
	public ArrayList<AirbnbListing> loadFromBorough(String borough, int minPrice, int maxPrice, int nights, String room){
		String sql = "SELECT * FROM ((SELECT * FROM properties WHERE price BETWEEN " + minPrice + " AND "
				+ maxPrice + " AND neighbourhood = '" + borough+"' AND minimum_nights <= "+nights+" AND room_type = '"+room+"')t1 left JOIN (SELECT property_id,'true' as fav "
				+ "FROM favourites where user_id = (SELECT id_ from users where user_login = '"+ScreensFramework.username+"'))t2 "
				+ "ON t1.id = t2.property_id )";
		System.out.println(sql);
		return load(sql);
	}

	/**
	 * Load properties with specific id
	 * @param propertyId The id of the property
	 * @return ArrayList of the property with the given id
	 */
	public ArrayList<AirbnbListing> loadById(String propertyId){
		String sql = "SELECT * FROM ((SELECT * FROM properties WHERE id = "+propertyId +")t1  "
				+ "left JOIN (SELECT property_id,'true' as fav FROM favourites where user_id = (SELECT id_ "
				+ "from users where user_login = '"+ScreensFramework.username+"'))t2 ON t1.id = t2.property_id )";
		return load(sql);
	}

	/**
	 * Get the number of properties in each neighbourhood 
	 * used to determine colour
	 * @param Min price range
	 * @param Max price range
	 * @return map of key value pairs
	 */
	public Map getNumberOfPropertiesInNeighbourhoods(int min, int max, String room, int minNights) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		//Coonect to the DB
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT neighbourhood,  count(neighbourhood) as amount FROM properties WHERE price BETWEEN "+min+" AND "+max+" GROUP BY neighbourhood;";
		if(room != null) {
			sql = "SELECT neighbourhood,  count(neighbourhood) as amount FROM properties WHERE room_type='"+room+"' AND minimum_nights <= "+minNights+" AND price BETWEEN "+min+" AND "+max+" GROUP BY neighbourhood;";
			
		}
		//sql to get the name of each neighbourhood and the amount of properties in that neighbourhood
		
		try{ 
			stmt=con.createStatement();  
			rs=stmt.executeQuery(sql);

			while (rs.next()) {
				//process data
				String neighbourhood = rs.getString("neighbourhood");
				String amount = rs.getString("amount");
				map.put(neighbourhood, Integer.parseInt(amount));
			}
			return map;

		}catch(Exception e){ 
			System.out.println(e);
			System.out.println("No connection!");
			return null;
		}finally{
			try { rs.close(); } catch (Exception e) { /* ignored */ }
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}

	}

	 ArrayList<AirbnbListing> maxProperty= null;
	/**
	 * Load properties with highest price
	 * @return ArrayList of 1 property
	 */
	public ArrayList<AirbnbListing> getMAXPrice(){
		if(maxProperty==null) {
		String sql = "SELECT * FROM properties ORDER BY price DESC LIMIT 1";
		maxProperty = load(sql);
		return maxProperty;
		}
		else {
			return maxProperty;
		}
	}

	/** 
	 * Return an ArrayList containing the rows in the London Properties online DataBase.
	 */
	private ArrayList<AirbnbListing> load(String query) {
		//Coonect to the DB
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		System.out.print("DATABASE: Begin loading Airbnb London dataset... ");
		ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();

		//sql to get all properties from the DB
		String sql = query;

		//Try to connect to the DB
		try{
			stmt=con.createStatement();  

			rs= stmt.executeQuery(sql); 
			while (rs.next()) {
				try {
					String id = rs.getString("id");
					String name = rs.getString("name");
					String host_id = rs.getString("host_id");
					String host_name = rs.getString("host_name");
					String neighbourhood = rs.getString("neighbourhood");
					double latitude = convertDouble(rs.getString("latitude"));
					double longitude = convertDouble(rs.getString("longitude"));
					String room_type = rs.getString("room_type");
					int price = convertInt(rs.getString("price"));
					int minimumNights = convertInt(rs.getString("minimum_nights"));
					int numberOfReviews = convertInt(rs.getString("number_of_reviews"));
					String lastReview = rs.getString("last_review");
					double reviewsPerMonth = convertDouble(rs.getString("reviews_per_month"));
					int calculatedHostListingsCount = convertInt(rs.getString("calculated_host_listings_count"));
					int availability365 = convertInt(rs.getString("availability_365"));
					Boolean fav = false;
					try {
						String favtemp = rs.getString("fav");
						fav = Boolean.valueOf(favtemp);
					}
					catch(Exception e) {
						fav = false;
					}

					AirbnbListing listing = new AirbnbListing(id, name, host_id,
							host_name, neighbourhood, latitude, longitude, room_type,
							price, minimumNights, numberOfReviews, lastReview,
							reviewsPerMonth, calculatedHostListingsCount, availability365, fav
							);
					listings.add(listing);
				}catch(Exception e) {System.out.println(e);}
			}
		}catch(Exception e){ 
			System.out.println(e);
		}finally{
			try { rs.close(); } catch (Exception e) { /* ignored */ }
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}
		System.out.println("Success! Number of loaded records: " + listings.size()+ " FROM DATABASE");
		return listings;
	}

	/**
	 * Get the name of the user 
	 * @param username the username of the user
	 * @return String value of the user's Full name
	 */
	public String getName(String username) {
		//Coonect to the DB
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		//sql to get the name of the specified user from DB
		String sql = "SELECT user_name FROM users WHERE user_login='"+username+"'";
		try{ 
			stmt=con.createStatement();  
			rs=stmt.executeQuery(sql);

			if(rs.isBeforeFirst()) {
				rs.next();
				//user exists return his full name
				return rs.getString(1).toString();
			}
		}catch(Exception e){ 
			System.out.println(e);
			System.out.println("No connection!");
			return null;
		}finally{
			try { rs.close(); } catch (Exception e) { /* ignored */ }
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}
		return null;
	}

	public ArrayList<AirbnbListing> loadFavProperties(){	
		return load("SELECT id, name, host_id, host_name, neighbourhood, latitude,longitude,room_type,price,minimum_nights,number_of_reviews, last_review, reviews_per_month, calculated_host_listings_count, availability_365, fav FROM ((SELECT * FROM properties)t1 RIGHT JOIN (SELECT property_id,'true' as fav FROM favourites where user_id = (SELECT id_ from users where user_login = '"+ScreensFramework.username+"'))t2 ON t1.id = t2.property_id );");
	}

	/**
	 * Get the address of the user 
	 * @param username the username of the user
	 * @return String value of the user's address
	 */
	public String getAddress(String username) {
		//Connect to the DB
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		//sql to get the address of the specified user from DB
		String sql = "SELECT user_address FROM users WHERE user_login='"+username+"'";
		try{ 
			stmt=con.createStatement();  
			rs=stmt.executeQuery(sql);

			if(rs.isBeforeFirst()) {
				rs.next();
				//user exists return his address
				return rs.getString(1).toString();
			}
		}catch(Exception e){ 
			System.out.println(e);
			System.out.println("No connection!");
			return null;
		}finally{
			try { rs.close(); } catch (Exception e) { /* ignored */ }
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}
		return null;
	}

	/**
	 * Get the photo of the user 
	 * @param username the username of the user
	 * @return Image object of the retrieved image from DB
	 */
	public Image getPhoto(String username) {
		//Coonect to the DB
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		//sql to get the address of the specified user from DB
		String sql = "SELECT user_photo FROM users WHERE user_login='"+username+"'";

		try{ 
			stmt=con.createStatement();  
			rs=stmt.executeQuery(sql);

			rs.next();

			InputStream input = rs.getBinaryStream(1);  
			InputStreamReader inputReader = new InputStreamReader(input);                        
			if(inputReader.ready())
			{
				File tempFile = new File("tempFile.jpg");

				FileOutputStream fos = new FileOutputStream(tempFile);
				byte[] buffer = new byte[16777215];
				while(input.read(buffer) > 0){
					fos.write(buffer);                        
				}
				Image image = new Image(tempFile.toURI().toURL().toString());
				return image;
			} 

		}catch(Exception e){ 
			System.out.println(e);
			System.out.println("No connection!");
			return null;
		}finally{
			try { rs.close(); } catch (Exception e) { /* ignored */ }
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { con.close(); } catch (Exception e) { /* ignored */ }
		}
		return null;
	}

	/**
	 *
	 * @param doubleString the string to be converted to Double type
	 * @return the Double value of the string, or -1.0 if the string is 
	 * either empty or just whitespace
	 */
	private Double convertDouble(String doubleString){
		if(doubleString != null && !doubleString.trim().equals("")){
			return Double.parseDouble(doubleString);
		}
		return -1.0;
	}

	/**
	 *
	 * @param intString the string to be converted to Integer type
	 * @return the Integer value of the string, or -1 if the string is 
	 * either empty or just whitespace
	 */
	private Integer convertInt(String intString){
		if(intString != null && !intString.trim().equals("")){
			return Integer.parseInt(intString);
		}
		return -1;
	}
}

