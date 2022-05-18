import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 
 * @author Josh Nguyen
 * 
 */
public class BackupManager {
  /**
  * getAllFiles() method to return all files in backup
  * @return fileList - list of all backup files
  */
	public static String[] getAllFiles() {
		File file = new File(FileManager.BACKUP_DIRECTORY);
		String[] fileList = file.list();
	    return fileList;
	}
  
	/**
  * printViewableFiles() method to return all files in backup
  * @return fileList - list of all backup files
  */
	public static void printViewableFiles() {
		String[] fileList = getAllFiles();
		if (fileList.length > 0) {
			System.out.println("List of Backups (" + fileList.length + ")\n");
			for (int i = 0; i < fileList.length; i++) {
				System.out.println("  " + (i + 1) + ") " + fileList[i]); 
			}
		} else {
			System.out.println("List of Backups (0)\n\n  No backup files here!");
		}
	}
	
	public static String saveBackup() throws IOException {
		try {
			String time = "" + LocalDateTime.now();
			time = time.replace(":", "--");
			
			FileManager.pushInventory(FileManager.BACKUP_DIRECTORY + time);
			System.out.println("Backup <<" + FileManager.BACKUP_DIRECTORY + time + ".txt>> has been created.");
      return time;
		} catch (Exception e) {
			System.out.println("Error on Backup");
      return null;
		}
	}

  public static String deleteBackup(String fileName) throws IOException {
    try {
      File file = new File(FileManager.BACKUP_DIRECTORY + fileName);
      if (file.delete()) {
        return fileName;
      } else {
        return null;
      }
    } catch (Exception e) {
      System.out.println("Error on Backup Removal");
      return null;
    }
  }
  
/**
  *getFileName for the Item class
  *@param name - name of the item 
  *@param type - type of the item
  *@param description - description of the condition of the item
  */	
	public static String getFileName(int backupFile) {
		String[] fileList = getAllFiles();
		if (backupFile <= 0) {
			return null;
		} else if (backupFile > fileList.length) {
			return null;
		}
		return fileList[backupFile - 1];
	}


/**
  *getFileLength() to return the length of fileList
  *@return int - length of fileList or 0 if null
  */
	public static int getFileLength() {
		String[] fileList = getAllFiles();
		if (fileList == null) {
			return 0;
		} else {
			return fileList.length;
		}
	}

}