
/**
 *
 * @author Adrian Rozario, Josh Nguyen
 *
 */
public class Item {

	private String id;
	private String name;
	private String type;
	private String description;
	private String isClaimedBy;

	private static int lastID = 0;

	/**
	 * Constructor for the Item class
	 * 
	 * @param name - name of the item
	 * @param type - type of the item
	 */
	public Item(String name, String type, String description) {
		lastID++;
		id = "I" + lastID;
		this.name = name;
		this.type = type;
		this.description = description;
		isClaimedBy = null;
	}

	/**
	 * Constructor for the Item class
	 * 
	 * @param name        - name of the item
	 * @param type        - type of the item
	 * @param description - description of the condition of the item
	 */
	public Item(String id, String name, String type, String description, String isClaimedBy) {
    this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.isClaimedBy = isClaimedBy;
	}

	/**
	 * toString() method to return name, type, and description of an item
	 * 
	 * @return - name, type, description of the item
	 */
	public String toString() {
		return id + " >> " + name + " (" + type + ")\n - \"" + description + "\"\n - Claimed By ID: " + isClaimedBy;
	}

	/**
	 * getID() method to return the ID of the item
	 * 
	 * @return String - id of the item
	 */
	public String getID() {
		return id;
	}

	/**
	 * getName() method to return the name of the item
	 * 
	 * @return String - name of the item
	 */
	public String getName() {
		return name;
	}

	/**
	 * getType() method to return the type of the item
	 * 
	 * @return String - type of the item
	 */
	public String getType() {
		return type;
	}

	/**
	 * getDescription() method to return the description of the item
	 * 
	 * @return String - description of the item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * getIsClaimedBy() method to return the User object
	 * 
	 * @return String - user's id that is borrowing object currently
	 */
	public String getIsClaimedBy() {
		return isClaimedBy;
	}

	/**
	 * getLastID method to return the the ID
	 * 
	 * @return lastID - the user's ID
	 */

	public static int getLastID() {
		return lastID;
	}
	
	/**
	 * setIsClaimedBy method to change the claimed user ids
	 * 
	 * @param id - int of the user ID
	 */
	public void setIsClaimedBy(String id) {
		isClaimedBy = id;
	}

	/**
	 * setLastID method to change the ID numbers
	 * 
	 * @param id - int of the last user ID
	 */
	public static void setLastID(int id) {
		lastID = id;
	}

}