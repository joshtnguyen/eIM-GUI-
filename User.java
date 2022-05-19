import java.util.ArrayList;

/**
 * @author Hardeep Kainth
 */
public class User {
	private String username;
	private String password;
	private String id;
	private ArrayList<Item> rentals = new ArrayList<Item>();

	private static int userIDs = 0;
	private static ArrayList<User> users = new ArrayList<User>();

	/**
	 * User Constructor with two String parameters
   *
   * @param username - the username of the new user
   * @param password - the password of the user
	 */
	public User(String username, String password) {
		userIDs++;
		this.username = username;
		this.password = password;
		id = "U" + userIDs;
	}

	/**
	 * User Constructor with three String parameters
   *
   * @param username - the username of the new user
   * @param password - the password of the user
   * @param id - id of the user
	 */
	public User(String username, String password, String id) {
		this.username = username;
		this.password = password;
		this.id = id;
	}

	/**
	 * getId() method to return the id
	 * 
	 * @return id - the id of the user
	 */

	public String getID() {
		return id;
	}

	/**
	 * findUser() method to take in a String and return which user object has the id
	 * 
   * @param userID
	 * @return user object - null if doesn't exist and User object if it does
	 */

	public static User findUser(String userID) {
		for (User u : users) {
			if (u.getID().equals(userID)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * getUsername() method to return the username of the User
	 * 
	 * @return username - name of the User
	 */

	public String getUsername() {
		return username;
	}

	/**
	 * getPassword() method to return the password of the User
	 * 
	 * @return password - password of the user
	 */

	public String getPassword() {
		return password;
	}

	/**
	 * getUsers() method to return the list of users;
	 * 
	 * @return users - list of all the users objects
	 */

	public static ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * setUserID() method to set a user's ID;
	 * 
	 * @param id - int of the student ID
	 */
	public static void setUserID(int id) {
		userIDs = id;
	}

	/**
	 * addRental() method to add a rental;
	 * 
	 * @param item - Item to add as a rental
	 */
	public void addRental(Item item) {
		if (item != null) {
			rentals.add(item);
		}
	}

	/**
	 * removeRental() method to remove a rental;
	 * 
	 * @param id - Item ID to remove as a rental
	 */
	public void removeRental(String id) {
		for (int i = 0; i < rentals.size(); i++) {
			if (id.equals(rentals.get(i).getID())) {
				rentals.remove(i);
				i--;
			}
		}
	}

	/**
	 * getUserIDs() method to set a user's ID;
	 * 
	 * @return id - int of the student ID
	 */
	public static int getUserIDs() {
		return userIDs;
	}

	/**
	 * getRentals() method to return the rentals
	 * 
	 * @return rentals - an arrayList of items containing what can be rented out
	 */
	public ArrayList<Item> getRentals() {
		return rentals;
	}

}