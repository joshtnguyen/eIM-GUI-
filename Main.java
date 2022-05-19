import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author Josh Nguyen, Hardeep Kainth
 *
 */

public class Main {
	/**
	 * Main method which pulls the inventory and users then initializes the login
	 * window
   *
   * @throws IOException in case the input and output failes
   * @throws FileNotFoundException in case file doesn't exist
	 */
	public static void main(String[] args) throws IOException, FileNotFoundException {

		FileManager.pullInventory();
		FileManager.pullUsers();
		// adminLogin();
		GUIManager loginWindow = new GUIManager();
		loginWindow.openWindow("Login");
		loginWindow.showLoginWindow();

	}

	/**
	 * checkLogin() method to return all files in backup @ param username - the
	 * username entered @ param password - the password entered
	 * 
	 * @return login - the user login
   *
   * @throws IOException in case the input and output failes
   * @throws FileNotFoundException in case file doesn't exist
	 */
	public static String checkLogin(String username, String password) throws IOException, FileNotFoundException {
		String login = "N/A";

		// Admin Logins
		File file = new File(FileManager.USER_DIRECTORY + "admin.txt");
		Scanner reader = new Scanner(file);
		while (reader.hasNextLine()) {
			if (reader.nextLine().indexOf("<U: " + username + ", P: " + password + ">") != -1) {
				login = "ADMIN";
			}
		}
		reader.close();

		// User Logins
		File f = new File(FileManager.USER_DIRECTORY + "user.txt");
		Scanner r = new Scanner(f);
		r.nextLine();
		r.nextLine();
		while (r.hasNextLine()) {
			String l1 = r.nextLine();
			String l2 = r.nextLine();
			String l3 = r.nextLine();
			String l4 = r.nextLine();
			if ((l2.equals(username)) && (l3.equals(password))) {
				login = "USER";
			}
		}
		return login;
	}

}