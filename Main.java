import java.util.*;
import java.io.*;


/**
 * 
 * @author Josh Nguyen
 *
 */

public class Main {

	public static void main(String[] args) throws IOException, FileNotFoundException {

    FileManager.pullInventory();
    FileManager.pullUsers();
    System.out.println(FileManager.getUsers());
    //adminLogin();
    GUIManager loginWindow = new GUIManager();
    loginWindow.openWindow("Login");
    loginWindow.showLoginWindow();
    
  }

  public static String checkLogin(String username, String password) throws IOException, FileNotFoundException {
    String login = "N/A";

    // Admin Logins
    File file = new File(FileManager.USER_DIRECTORY + "admin.txt");
    Scanner reader = new Scanner(file);
    while(reader.hasNextLine()) {
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

  /**
  * Developer Testing System for Admin Panel
  */
  public static void adminLogin() throws IOException {
		
    Scanner sc = new Scanner(System.in);
		
		FileManager.pullInventory();
		
		System.out.println("\nWelcome to Rental Inventory Manager! (ADMIN PANEL)\n");
		printCommands();
		
		String input = "";
		do {
			System.out.print("\nCommand >> ");
			input = feedback(sc).toLowerCase();
			
			boolean failCommand = true;
			if (input.equals("/list")) {
				System.out.println("List of Items (" + FileManager.getInventorySize() + ") \n" + FileManager.getList());
				failCommand = false;
			}
			if (input.equals("/additem")) {
				failCommand = false;
				FileManager.addItem();
      }
      if (input.equals("/removeitem")) {
        failCommand = false;
        FileManager.removeItem();
			}
			if (input.equals("/savebackup")) {
				BackupManager.saveBackup();
				failCommand = false;
			}
			if (input.equals("/loadbackup")) {
				int numOfBackups = BackupManager.getFileLength();
				if (numOfBackups > 0) {
					System.out.print("Choose a backup file (1-" + numOfBackups + ") >> ");
					try {
						int backupNumber = Integer.parseInt(sc.nextLine());
						String fileName = BackupManager.getFileName(backupNumber);
						if (fileName.equals(null)) {
							System.out.println("Invalid File Number");
						} else {
							FileManager.pullInventory(FileManager.BACKUP_DIRECTORY + fileName);
						}
					} catch (Exception e) {
						System.out.println("An error occured in loading the backup.");
					}
				} else {
					System.out.println("There are no backups to load!");
				}
				failCommand = false;
			}
			if (input.equals("/listbackups")) {
				BackupManager.printViewableFiles();
				failCommand = false;
			}
			if (input.equals("/quit")) {
				failCommand = false;
			}
			if (failCommand) {
				System.out.println("Invalid Command! Try one of the few below: \n");
				printCommands();
			}
			
		} while (!input.equals("/quit"));
		
		FileManager.pushInventory();
		System.out.println("Have a great day!");
	}
	
	public static void printCommands() {
		System.out.println("Commands:");
		System.out.println(" /list");
		System.out.println(" /addItem");
    System.out.println(" /removeItem");
		System.out.println(" /saveBackup");
		System.out.println(" /loadBackup");
		System.out.println(" /listBackups");
		System.out.println(" /quit");
	}
	
	public static String feedback(Scanner sc) {
		String input = sc.nextLine();
		return input;
	}
	
}