import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Josh Nguyen, Adrian Rozario, Hardeep Kainth
 * 
 **/
public class FileManager {

	public final static String DATA_DIRECTORY = "data/";
	public final static String INVENTORY_DIRECTORY = DATA_DIRECTORY + "";
	public final static String USER_DIRECTORY = DATA_DIRECTORY + "userData/";
	public final static String BACKUP_DIRECTORY = INVENTORY_DIRECTORY + "backups/";
	private static ArrayList<Item> inventory = new ArrayList<Item>();
	private static ArrayList<User> users = new ArrayList<User>();
	private static boolean sortedByID = true;

	/**
	 * getList method to return the current inventory
	 * 
	 * @return ret - the current inventory
	 */
	public static String getList() {
		if (inventory.isEmpty()) {
			return "Sorry. The inventory is empty...";
		}
		String ret = "";
		for (Item i : inventory) {
			ret += "\n";
			ret += i;
		}
		return ret;
	}

	/**
	 * getInventorySize method to return the size of the inventory
	 * 
	 * @return ret - the size of the current inventory
	 */
	public static int getInventorySize() {
		return inventory.size();
	}

	/**
	 * addItem method to add an item to the inventory with user input
	 * 
	 * @param i - the item to add to the inventory
	 */
	public static void addItem(Item i) throws IOException {
		inventory.add(i);
		pushInventory();
	}

	/**
	 * getInventory method to get the inventory data
	 * 
	 * @return inventory - the current inventory
	 */
	public static ArrayList<Item> getInventory() {
		return inventory;
	}

	/**
	 * removeItem method to remove and return an item from the inventory
	 * 
	 * @return item - item that was to be removed
	 */

	public static Item removeItem(int index) throws IOException {
		Item item = inventory.remove(index);
		pushInventory();
		for (User u : users) {
			ArrayList<Item> rentals = u.getRentals();
			for (int i = 0; i < rentals.size(); i++) {
				if (item.getID().equals(rentals.get(i).getID())) {
					rentals.remove(i);
					i--;
				}
			}
		}
		return item;
	}

	/**
	 * pushInventory method to update the inventory text file with the current
	 * inventory
	 */
	public static void pushInventory() throws IOException {
		pushInventory(INVENTORY_DIRECTORY + "list");
	}

	/**
	 * pushInventory method to update the inventory text file with the current
	 * inventory
	 * 
	 * @param source - String of the old inventory to be updated
	 */
	public static void pushInventory(String source) throws IOException {
		try {
			FileWriter w = new FileWriter(source + ".txt");
			String s = "=====> INVENTORY MANAGER\n<IDS>: " + Item.getLastID() + "\n";

			if (inventory != null) {
				for (Item i : inventory)
					s += i.getID() + "\n" + i.getName() + "\n" + i.getType() + "\n" + i.getDescription() + "\n"
							+ i.getIsClaimedBy() + "\n";
			}

			w.write(s);
			w.close();

		} catch (Exception e) {
			return;
		}
	}

	/**
	 * pullInventory method to get the inventory data from the text file
	 */
	public static void pullInventory() throws FileNotFoundException, IOException {
		try {
			pullInventory(INVENTORY_DIRECTORY + "list.txt");
		} catch (Exception ignore) {

		}
	}

	/**
	 * pullInventory method to get the inventory data from the text file
	 * 
	 * @param source - String of the inventory
	 */
	public static void pullInventory(String source) throws FileNotFoundException, IOException {
		try {
			sortedByID = true;
			File file = new File(source);
			Scanner sc = new Scanner(file);
			boolean flag = true;
			if (!sc.nextLine().equals("=====> INVENTORY MANAGER")) {
				return;
			}
			String id = sc.nextLine();
			if (id.indexOf("<IDS>: ") != -1) {
				id = id.substring(id.indexOf("<IDS>: "));
				id = id.substring(id.indexOf(" ") + 1);
				Item.setLastID(Integer.parseInt(id));
			}

			String l1 = "NOTATHING";
			do {
				l1 = "NOTATHING";
				String l2 = "NOTATHING";
				String l3 = "NOTATHING";
				String l4 = "NOTATHING";
				String l5 = "NOTATHING";
				l1 = sc.nextLine();
				if (!l1.equals("=====> USER MANAGER")) {
					l2 = sc.nextLine();
					l3 = sc.nextLine();
					l4 = sc.nextLine();
					l5 = sc.nextLine();
					if (flag) {
						flag = false;
						inventory.clear();
					}
					inventory.add(new Item(l1, l2, l3, l4, l5));

				}

			} while (!l1.equals("=====> USER MANAGER"));

		} catch (Exception e) {
			pushInventory();
			if (!source.equals(INVENTORY_DIRECTORY + "list.txt")) {
				int lastID = Item.getLastID();
				for (User u : users) {
					ArrayList<Item> currentRentals = u.getRentals();
					for (int i = 0; i < currentRentals.size(); i++) {
						if (Integer.parseInt(currentRentals.get(i).getID().substring(1)) > lastID) {
							currentRentals.remove(i);
							i--;
						}
					}
				}
				for (Item i : inventory) {
					i.setIsClaimedBy("null");
				}
				for (User u : users) {
					for (Item i : u.getRentals()) {
						findItem(i.getID()).setIsClaimedBy(u.getID());
					}
				}
				pushUsers();
				pushInventory();
			}

			return;
		}
	}

	/**
	 * pullUsers method to get the user data from the text file
	 */
	public static void pullUsers() throws FileNotFoundException {
		try {
			File file = new File(USER_DIRECTORY + "user.txt");
			Scanner sc = new Scanner(file);
			boolean flag = true;
			String id = sc.nextLine();
			id = sc.nextLine();
			if (id.indexOf("<IDS>: ") != -1) {
				id = id.substring(id.indexOf("<IDS>: "));
				id = id.substring(id.indexOf(" ") + 1);
				User.setUserID(Integer.parseInt(id));
			}
			while (true) {
				String l1 = "NOTATHING";
				String l2 = "NOTATHING";
				String l3 = "NOTATHING";
				String l4 = "NOTATHING";
				if (sc.hasNextLine()) {
					l1 = sc.nextLine();
					l2 = sc.nextLine();
					l3 = sc.nextLine();
					l4 = sc.nextLine();
					if (flag) {
						flag = false;
						users.clear();
					}
					users.add(new User(l2, l3, l1));
					if (!l4.equals("null")) {
						int firstIndex = l4.indexOf("[");
						int lastIndex = l4.indexOf("]");
						while (firstIndex != -1) {
							String tempID = l4.substring(firstIndex + 1, lastIndex);
							for (Item item : inventory) {
								if (item.getID().equals(tempID)) {
									users.get(users.size() - 1).addRental(item);

								}
							}
							l4 = l4.substring(lastIndex + 1);
							firstIndex = l4.indexOf("[");
							lastIndex = l4.indexOf("]");
						}
					}
				} else {
					return;
				}
			}
		} catch (Exception e) {
			return;
		}
	}

	/**
	 * pushUsers method to push data into text file when the program is closed
	 * 
	 * @param source - the String which is the name of the file
	 */

	public static void pushUsers() throws IOException {
		try {
			pushUsers(FileManager.USER_DIRECTORY + "user");
		} catch (Exception e) {
			return;
		}
	}

	/**
	 * pushUsers method to push data into text file when the program is closed
	 * 
	 * @param source - the String which is the name of the file
	 */

	public static void pushUsers(String source) throws IOException {
		try {
			FileWriter w = new FileWriter(source + ".txt");
			String s = "=====> USER MANAGER\n<IDS>: " + User.getUserIDs() + "\n";
			if (users != null) {
				for (User u : users) {
					s += u.getID() + "\n" + u.getUsername() + "\n" + u.getPassword() + "\n";
					if (u.getRentals() != null && u.getRentals().size() != 0) {
						ArrayList<Item> userRentals = u.getRentals();
						for (int i = 0; i < userRentals.size() - 1; i++) {
							s += "[" + userRentals.get(i).getID() + "], ";
						}
						s += "[" + userRentals.get(userRentals.size() - 1).getID() + "]\n";
					} else {
						s += "\n";
					}
				}
			}
			w.write(s);
			w.close();

		} catch (Exception e) {
			return;
		}
	}

	public static void sortInventory(ArrayList<Item> a, int first, int last) throws IOException {
		if (a == null || a.size() == 0) {
			return;
		}
		if (sortedByID) {
			int g = first;
			int h = last;
			int mid = (first + last) / 2;
			int dividingValue = Integer.parseInt(a.get(mid).getID().substring(1));
			do {
				while (Integer.parseInt(a.get(g).getID().substring(1)) < dividingValue) {
					g++;
				}
				while (Integer.parseInt(a.get(h).getID().substring(1)) > dividingValue) {
					h--;
				}
				if (g <= h) {
					Item temp = a.get(g);
					a.set(g, a.get(h));
					a.set(h, temp);
					g++;
					h--;
				}
			} while (g < h);
			if (h > first) {
				sortInventory(a, first, h);
			}
			if (g < last) {
				sortInventory(a, g, last);
			}
		} else {
			int g = first;
			int h = last;
			int mid = (first + last) / 2;
			String dividingValue = a.get(mid).getName();
			do {
				while (a.get(g).getName().compareTo(dividingValue) < 0) {
					g++;
				}
				while (a.get(h).getName().compareTo(dividingValue) > 0) {
					h--;
				}
				if (g <= h) {
					Item temp = a.get(g);
					a.set(g, a.get(h));
					a.set(h, temp);
					g++;
					h--;
				}
			} while (g < h);
			if (h > first) {
				sortInventory(a, first, h);
			}
			if (g < last) {
				sortInventory(a, g, last);
			}
		}
	}

	public static boolean addUser(String username, String password) throws FileNotFoundException, IOException {
		for (User u : users) {
			if (u.getUsername().equals(username)) {
				return false;
			}
		}
		File file = new File(USER_DIRECTORY + "admin.txt");
		Scanner sc = new Scanner(file);
		sc.nextLine();
		sc.nextLine();
		while (sc.hasNextLine()) {
			String string = sc.nextLine();
			int firstIndex = string.indexOf(" ");
			int lastIndex = string.indexOf(",");
			if (username.equals(string.substring(firstIndex + 1, lastIndex))) {
				return false;
			}
		}
		users.add(new User(username, password));
		FileManager.pushUsers(FileManager.USER_DIRECTORY + "user");
		return true;
	}

	public static Item findItem(String id) {
		for (Item i : inventory) {
			if (id.equals(i.getID())) {
				return i;
			}
		}
		return null;
	}

	public static User findUser(String username) {
		for (User u : users) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}

	public static User findUserID(String id) {
		for (User u : users) {
			if (u.getID().equals(id)) {
				return u;
			}
		}
		return null;
	}

	public static ArrayList<User> getUsers() {
		return users;
	}

	public static void toggleSort() {
		sortedByID = !sortedByID;
	}

}