import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class GUIManager extends JFrame implements ActionListener {

  private String windowType;
  private Container container = getContentPane();
  private JLabel userLabel = new JLabel("USERNAME");
  private JLabel passwordLabel = new JLabel("PASSWORD");
  private JTextField userField = new JTextField();
  private JPasswordField passwordField = new JPasswordField();
  private JButton loginButton = new JButton("LOGIN");
  private JButton resetButton = new JButton("RESET");
  private JCheckBox showPassword = new JCheckBox("Show Password");
  private JCheckBox createAccount = new JCheckBox("Create Account");
  private ArrayList<JLabel> labels = new ArrayList<JLabel>();
  private ArrayList<JButton> buttons = new ArrayList<JButton>();
  private ArrayList<JButton> actionButtons = new ArrayList<JButton>();
  private ArrayList<JTextField> textFields = new ArrayList<JTextField>();
  private static int pageNumber = 1;
  private static int backupPageNumber = 1;
  private static int otherPageNumber = 1;
  private static User currentUser;
  /**
  *openWindow method to create the GUI
  *@param subTitle - the title of the frame
  */
  public void openWindow(String subTitle) throws FileNotFoundException {

    windowType = subTitle;
    
    // THE TITLE OF THE FRAME
    if (subTitle.equals(null)) {
      subTitle = "";
    } else {
      subTitle = " - " + subTitle;
    }
    File settings = new File("data/settings.txt");
    Scanner sc = new Scanner(settings);
    String title = "";
    String version = "";
    while (sc.hasNextLine()) {
      String setting = sc.nextLine();
      if (setting.indexOf("<WindowTitle> = ") != -1) {
        title = setting.substring(setting.indexOf("=") + 2);
      } else if (setting.indexOf("<Version> = ") != -1) {
        version = " " + setting.substring(setting.indexOf("=") + 2);
      }
    }

    // GENERATING THE FRAME
    setTitle(title + subTitle + version);
    setSize(400, 400);
    setVisible(true);
    if (windowType.equals("Login") || windowType.equals("Admin Panel") || windowType.equals("User Panel")) {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } else {
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    sc.close();
  }
  /**
  *showLoginWindow method to display the GUI
  */
  public void showLoginWindow() {
    if (windowType.equals("Login")) {
      container.setLayout(null);
      userLabel.setBounds(50, 50, 100, 30);
      passwordLabel.setBounds(50, 120, 100, 30);
      userField.setBounds(150, 50, 150, 30);
      passwordField.setBounds(150, 120, 150, 30);
      showPassword.setBounds(150, 150, 150, 30);
      createAccount.setBounds(150, 170, 150, 30);
      loginButton.setBounds(50, 220, 100, 30);
      resetButton.setBounds(200, 220, 100, 30);
      container.add(userLabel);
      container.add(passwordLabel);
      container.add(userField);
      container.add(passwordField);
      container.add(showPassword);
      container.add(createAccount);
      container.add(loginButton);
      container.add(resetButton);
      loginButton.addActionListener(this);
      resetButton.addActionListener(this);
      showPassword.addActionListener(this);
      createAccount.addActionListener(this);
    }
  }
  
  /**
  *adminLogin method to display the GUI
  */
  public void adminLogin() {
    if (windowType.equals("Admin Panel")) {
      container.setLayout(null);
      int nButtons = 0;
      int xVal = 50;
      int yVal = 20;
      int wVal = 300;
      int yDer = 40;
      buttons.add(new JButton("List Inventory")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Add Item")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Remove Item")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Save Backup")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Load Backup")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Delete Backup")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Quit")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      for (JButton b : buttons) {
        container.add(b);
        b.addActionListener(this);
      }
    }
  }

  /**itemList() method to create the button objects onto the GUI
  */
  public void itemList(int page) {
    if (windowType.equals("Item List")) {
      pageNumber = page;
      container.setLayout(null);
      int nButtons = -1;
      int xVal = 50;
      int yVal = 20;
      int wVal = 300;
      int yDer = 40;
      int start = 7 * (page - 1);
      int end = start + 6;
      for (Item i : FileManager.getInventory()) {
        nButtons++; 
        buttons.add(new JButton("" + i.getID() + " - " + i.getName())); 
        if (nButtons >= start && nButtons <= end) {
          buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); 
          yVal += yDer;
          container.add(buttons.get(nButtons));
          buttons.get(nButtons).addActionListener(this);
        }
      }
      actionButtons.add(new JButton("<"));
      actionButtons.get(0).setBounds(50, yVal, 50, 30);
      actionButtons.add(new JButton(">"));
      actionButtons.get(1).setBounds(300, yVal, 50, 30);
      actionButtons.add(new JButton("HOME"));
      actionButtons.get(2).setBounds(125, yVal, 150, 30);
      container.add(actionButtons.get(0));
      container.add(actionButtons.get(1));
      container.add(actionButtons.get(2));
      actionButtons.get(0).addActionListener(this);
      actionButtons.get(1).addActionListener(this);
      actionButtons.get(2).addActionListener(this);
    }
  }

  public void addItem() {
    
    if (windowType.equals("Add Item")) {
      container.setLayout(null);
      labels.add(new JLabel("Name of Item: "));
      labels.add(new JLabel("Type of Item: "));
      labels.add(new JLabel("Description: "));
      textFields.add(new JTextField());
      textFields.add(new JTextField());
      textFields.add(new JTextField());
      labels.get(0).setBounds(50, 50, 150, 30);
      labels.get(1).setBounds(50, 100, 150, 30);
      labels.get(2).setBounds(50, 150, 150, 30);
      textFields.get(0).setBounds(175, 50, 150, 30);
      textFields.get(1).setBounds(175, 100, 150, 30);
      textFields.get(2).setBounds(175, 150, 150, 30);
      buttons.add(new JButton("Add Item"));
      buttons.get(0).setBounds(150, 220, 100, 30);
      buttons.add(new JButton("HOME"));
      buttons.get(1).setBounds(150, 270, 100, 30);
      for (JLabel l : labels) {
        container.add(l);
      }
      for (JTextField t : textFields) {
        container.add(t);
      }
      for (JButton b : buttons) {
        container.add(b);
        b.addActionListener(this);
      }
      
    }
  }

  public void removeItem(int page) {
    if (windowType.equals("Remove Item")) {
      pageNumber = page;
      container.setLayout(null);
      int nButtons = -1;
      int xVal = 50;
      int yVal = 20;
      int wVal = 300;
      int yDer = 40;
      int start = 7 * (page - 1);
      int end = start + 6;
      for (Item i : FileManager.getInventory()) {
        nButtons++; 
        buttons.add(new JButton("" + i.getID() + " - " + i.getName())); 
        if (nButtons >= start && nButtons <= end) {
          buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); 
          yVal += yDer;
          container.add(buttons.get(nButtons));
          buttons.get(nButtons).addActionListener(this);
        }
      }
      actionButtons.add(new JButton("<"));
      actionButtons.get(0).setBounds(50, yVal, 50, 30);
      actionButtons.add(new JButton(">"));
      actionButtons.get(1).setBounds(300, yVal, 50, 30);
      actionButtons.add(new JButton("HOME"));
      actionButtons.get(2).setBounds(125, yVal, 150, 30);
      container.add(actionButtons.get(0));
      container.add(actionButtons.get(1));
      container.add(actionButtons.get(2));
      actionButtons.get(0).addActionListener(this);
      actionButtons.get(1).addActionListener(this);
      actionButtons.get(2).addActionListener(this);
    }
  }

  /**
  * userLogin method to display the GUI
  */
  public void userLogin() {
    if (windowType.equals("User Panel")) {
      container.setLayout(null);
      int nButtons = 0;
      int xVal = 50;
      int yVal = 20;
      int wVal = 300;
      int yDer = 40;
      buttons.add(new JButton("Profile")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Rentals")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("List Inventory")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Rent")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Return")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      buttons.add(new JButton("Quit")); buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); nButtons++; yVal += yDer;
      for (JButton b : buttons) {
        container.add(b);
        b.addActionListener(this);
      }
    }
  }

  
  public void loadBackup(int page) {
    if (windowType.equals("Load Backup")) {
      backupPageNumber = page;
      container.setLayout(null);
      int nButtons = -1;
      int xVal = 50;
      int yVal = 20;
      int wVal = 300;
      int yDer = 40;
      int start = 7 * (page - 1);
      int end = start + 6;
      for (String i : BackupManager.getAllFiles()) {
        nButtons++; 
        i = i.substring(5, 7) + "/" + i.substring(8, 10) + "/" + i.substring(0, 4) + " - " + i.substring(11,21).replace("--", ":");
        buttons.add(new JButton(i)); 
        if (nButtons >= start && nButtons <= end) {
          buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); 
          yVal += yDer;
          container.add(buttons.get(nButtons));
          buttons.get(nButtons).addActionListener(this);
        }
      }
      actionButtons.add(new JButton("<"));
      actionButtons.get(0).setBounds(50, yVal, 50, 30);
      actionButtons.add(new JButton(">"));
      actionButtons.get(1).setBounds(300, yVal, 50, 30);
      actionButtons.add(new JButton("HOME"));
      actionButtons.get(2).setBounds(125, yVal, 150, 30);
      container.add(actionButtons.get(0));
      container.add(actionButtons.get(1));
      container.add(actionButtons.get(2));
      actionButtons.get(0).addActionListener(this);
      actionButtons.get(1).addActionListener(this);
      actionButtons.get(2).addActionListener(this);
    }
  }

  public void deleteBackup(int page) {
    if (windowType.equals("Delete Backup")) {
      backupPageNumber = page;
      container.setLayout(null);
      int nButtons = -1;
      int xVal = 50;
      int yVal = 20;
      int wVal = 300;
      int yDer = 40;
      int start = 7 * (page - 1);
      int end = start + 6;
      for (String i : BackupManager.getAllFiles()) {
        nButtons++; 
        i = i.substring(5, 7) + "/" + i.substring(8, 10) + "/" + i.substring(0, 4) + " - " + i.substring(11,21).replace("--", ":");
        buttons.add(new JButton(i)); 
        if (nButtons >= start && nButtons <= end) {
          buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); 
          yVal += yDer;
          container.add(buttons.get(nButtons));
          buttons.get(nButtons).addActionListener(this);
        }
      }
      actionButtons.add(new JButton("<"));
      actionButtons.get(0).setBounds(50, yVal, 50, 30);
      actionButtons.add(new JButton(">"));
      actionButtons.get(1).setBounds(300, yVal, 50, 30);
      actionButtons.add(new JButton("HOME"));
      actionButtons.get(2).setBounds(125, yVal, 150, 30);
      container.add(actionButtons.get(0));
      container.add(actionButtons.get(1));
      container.add(actionButtons.get(2));
      actionButtons.get(0).addActionListener(this);
      actionButtons.get(1).addActionListener(this);
      actionButtons.get(2).addActionListener(this);
    }
  }

  /** yourRentals() method to create the button objects onto the GUI
  */
  public void yourRentals(int page) {
    if (windowType.equals("Your Rentals")) {
      otherPageNumber = page;
      container.setLayout(null);
      int nButtons = -1;
      int xVal = 50;
      int yVal = 20;
      int wVal = 300;
      int yDer = 40;
      int start = 7 * (page - 1);
      int end = start + 6;
      ArrayList<Item> temp = new ArrayList<Item>();
      for (Item i : FileManager.getInventory()) {
        if (i.getID().equals(currentUser.getID())) {
          temp.add(i);
        }
      }
      for (Item i : temp) {
        nButtons++; 
        buttons.add(new JButton("" + i.getID() + " - " + i.getName())); 
        if (nButtons >= start && nButtons <= end) {
          buttons.get(nButtons).setBounds(xVal, yVal, wVal, 30); 
          yVal += yDer;
          container.add(buttons.get(nButtons));
          buttons.get(nButtons).addActionListener(this);
        }
      }
      actionButtons.add(new JButton("<"));
      actionButtons.get(0).setBounds(50, yVal, 50, 30);
      actionButtons.add(new JButton(">"));
      actionButtons.get(1).setBounds(300, yVal, 50, 30);
      actionButtons.add(new JButton("HOME"));
      actionButtons.get(2).setBounds(125, yVal, 150, 30);
      container.add(actionButtons.get(0));
      container.add(actionButtons.get(1));
      container.add(actionButtons.get(2));
      actionButtons.get(0).addActionListener(this);
      actionButtons.get(1).addActionListener(this);
      actionButtons.get(2).addActionListener(this);
    }
  }
  
  /**
  *actionPerformed method to handle events
  *@param e - An action performed by the user
  */
  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      
      // LOGIN WINDOW
      if (windowType.equals("Login")) {
        if (e.getSource() == loginButton) {
          String pass = "";
          for (char c : passwordField.getPassword()) {
            pass += c;
          }
          if (userField.getText().equals("") || pass.equals("")) {
            JOptionPane.showMessageDialog(this, "The USERNAME and PASSWORD field are required.");
          } else if (createAccount.isSelected()) {
            boolean successful = FileManager.addUser(userField.getText(), pass);
            if (!successful) {
              JOptionPane.showMessageDialog(this, "Username already taken. Please try again.");
            } else {
              JOptionPane.showMessageDialog(this, "User sucessfully created! Welcome " + userField.getText() + "!");
              currentUser = FileManager.findUser(userField.getText());
              setVisible(false);
              GUIManager loginWindow = new GUIManager();
              loginWindow.openWindow("User Panel");
              loginWindow.userLogin();
            }
          } else {
            String access = Main.checkLogin(userField.getText(), pass);
            if (access.equals("ADMIN")) {
              JOptionPane.showMessageDialog(this, "Successfully logged in to the ADMIN PANEL. Welcome " + userField.getText() + "!");
              setVisible(false);
              GUIManager loginWindow = new GUIManager();
              loginWindow.openWindow("Admin Panel");
              loginWindow.adminLogin();
            } else if (access.equals("USER")) {
              JOptionPane.showMessageDialog(this, "Successfully logged in to the USER PANEL. Welcome " + userField.getText() + "!");
              currentUser = FileManager.findUser(userField.getText());
              setVisible(false);
              GUIManager loginWindow = new GUIManager();
              loginWindow.openWindow("User Panel");
              loginWindow.userLogin();
            } else {
              JOptionPane.showMessageDialog(this, "Invalid Username or Password");
              passwordField.setText("");
            }
          }
        }
    
        if (e.getSource() == resetButton) {
          userField.setText("");
          passwordField.setText("");
        }
    
        if (e.getSource() == showPassword) {
          if (showPassword.isSelected()) {
            passwordField.setEchoChar((char) 0);
          } else {
            passwordField.setEchoChar('*');
          }
        }
  
        if (e.getSource() == createAccount) {
          if (createAccount.isSelected()) {
            loginButton.setText("Sign Up");
          } else {
            loginButton.setText("Login");
          }
        }

      // ADMIN WINDOW
      } else if (windowType.equals("Admin Panel")) {
        
        if (e.getSource() == buttons.get(0)) { // List Items
          GUIManager itemList = new GUIManager();
          itemList.openWindow("Item List");
          itemList.itemList(pageNumber);
        }

        if (e.getSource() == buttons.get(1)) { // Add Items
          GUIManager addItem = new GUIManager();
          addItem.openWindow("Add Item");
          addItem.addItem();
        }

        if (e.getSource() == buttons.get(2)) { // Remove Items
          GUIManager removeItem = new GUIManager();
          removeItem.openWindow("Remove Item");
          removeItem.removeItem(pageNumber);
        }

        if (e.getSource() == buttons.get(3)) { // Save Backup
          String fileName = BackupManager.saveBackup();
          JOptionPane.showMessageDialog(this, "BACKUP HAS BEEN CREATED!\n\nFileName:\n" + fileName + ".txt");
          
        }
        
        if (e.getSource() == buttons.get(4)) { // Load Backup
          GUIManager loadBackup = new GUIManager();
          loadBackup.openWindow("Load Backup");
          loadBackup.loadBackup(backupPageNumber);
        }

        if (e.getSource() == buttons.get(5)) { // Delete Backup
          GUIManager deleteBackup = new GUIManager();
          deleteBackup.openWindow("Delete Backup");
          deleteBackup.deleteBackup(backupPageNumber);
        }

        if (e.getSource() == buttons.get(6)) { // Quit
          System.exit(0);
        }

      // USER WINDOW
      } else if (windowType.equals("User Panel")) {
        if (e.getSource() == buttons.get(0)) { // Profile
          String pass = "";
          for (int i = 0; i < currentUser.getPassword().length(); i++) {
            if (i == 0 || i == currentUser.getPassword().length() - 1) {
              pass += currentUser.getPassword().charAt(i);
            } else {
              pass += "*";
            }
          }
          int sizeOfRentals = 0;
          if (currentUser.getRentals() != null) {
            sizeOfRentals = currentUser.getRentals().size();
          }
          JOptionPane.showMessageDialog(this, "ID: " + currentUser.getID() + "\nUsername: " + currentUser.getUsername() + "\nPassword: " + pass + "\nCurrently Rented Items: " + sizeOfRentals);
        }

        if (e.getSource() == buttons.get(1)) { // Rentals
          /*String s = "Currently Rented Items:";
          if (currentUser.getRentals() == null) {
            JOptionPane.showMessageDialog(this, s);
          } else {
            ArrayList<Item> currentRentals = currentUser.getRentals();
            for (Item i : currentRentals) {
              s += "\n" + i.getID() + " - " + i.getName();
            }
            JOptionPane.showMessageDialog(this, s);
          }*/

          GUIManager yourRentals = new GUIManager();
          yourRentals.openWindow("Your Rentals");
          yourRentals.yourRentals(otherPageNumber);
          
        }

        if (e.getSource() == buttons.get(2)) { // List Items
          GUIManager itemList = new GUIManager();
          itemList.openWindow("Item List");
          itemList.itemList(pageNumber);
        }

        if (e.getSource() == buttons.get(3)) { // Rent
          JOptionPane.showMessageDialog(this, "Rent Button Not Implemented Yet!");
        }

        if (e.getSource() == buttons.get(4)) { // Return
          JOptionPane.showMessageDialog(this, "Return Button Not Implemented Yet!");
        }

        if (e.getSource() == buttons.get(5)) { // Quit
          System.exit(0);
        }
        
      // ITEM LIST WINDOW
      } else if (windowType.equals("Item List")) {
        for (int i = 0; i < FileManager.getInventorySize(); i++) {
          if (e.getSource() == buttons.get(i)) {
            Item item = FileManager.getInventory().get(i);
            JOptionPane.showMessageDialog(this, "ID: " + item.getID() + "\nName of Item: " + item.getName() + "\nType of Item: " + item.getType() + "\nDescription: " + item.getDescription() + "\nIs Claimed By: " + item.getIsClaimedBy());
          }
        }
        if (e.getSource() == actionButtons.get(0) && pageNumber >= 2) {
          GUIManager itemList = new GUIManager();
          itemList.openWindow("Item List");
          pageNumber--;
          itemList.itemList(pageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(1) && pageNumber - 1 < (FileManager.getInventorySize() - 1) / 7) {
          GUIManager itemList = new GUIManager();
          itemList.openWindow("Item List");
          pageNumber++;
          itemList.itemList(pageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(2)) {
          setVisible(false);
        }
        
      // ADD ITEM WINDOW
      } else if (windowType.equals("Add Item")) {
        if (e.getSource() == buttons.get(0)) {
          if (!textFields.get(0).getText().equals("") && !textFields.get(1).getText().equals("")) {
            FileManager.addItem(new Item(textFields.get(0).getText(), textFields.get(1).getText(), textFields.get(2).getText()));
            JOptionPane.showMessageDialog(this, textFields.get(0).getText() + " has been added to the Inventory.");
            setVisible(false);
          } else {
            JOptionPane.showMessageDialog(this, "NAME and TYPE fields are required.");
          }
        } else if (e.getSource() == buttons.get(1)) {
          setVisible(false);
        }

      // REMOVE ITEM WINDOW
      } else if (windowType.equals("Remove Item")) {
        for (int i = 0; i < FileManager.getInventorySize(); i++) {
          if (e.getSource() == buttons.get(i)) {
            Item item = FileManager.removeItem(i);
            setVisible(false);
            JOptionPane.showMessageDialog(this, "THE FOLLOWING ITEM WAS REMOVED: \n\nID: " + item.getID() + "\nName of Item: " + item.getName() + "\nType of Item: " + item.getType() + "\nDescription: " + item.getDescription() + "\nIs Claimed By: " + item.getIsClaimedBy());
            pageNumber = 1;
            setVisible(false);
          }
        }
        if (e.getSource() == actionButtons.get(0) && pageNumber >= 2) {
          GUIManager removeItem = new GUIManager();
          removeItem.openWindow("Remove Item");
          pageNumber--;
          removeItem.removeItem(pageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(1) && pageNumber - 1 < (FileManager.getInventorySize() - 1) / 7) {
          GUIManager removeItem = new GUIManager();
          removeItem.openWindow("Remove Item");
          pageNumber++;
          removeItem.removeItem(pageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(2)) {
          setVisible(false);
        }

      // BACKUP LIST WINDOW
      } else if (windowType.equals("Load Backup")) {
        for (int i = 0; i < BackupManager.getAllFiles().length; i++) {
          if (e.getSource() == buttons.get(i)) {
            String fileName = BackupManager.getAllFiles()[i];
            JOptionPane.showMessageDialog(this, "BACKUP HAS BEEN LOADED:\n" + fileName);
            FileManager.pullInventory(FileManager.BACKUP_DIRECTORY + fileName);
            setVisible(false);
          }
        }
        if (e.getSource() == actionButtons.get(0) && backupPageNumber >= 2) {
          GUIManager loadBackup = new GUIManager();
          loadBackup.openWindow("Load Backup");
          backupPageNumber--;
          loadBackup.loadBackup(backupPageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(1) && backupPageNumber - 1 < (FileManager.getInventorySize() - 1) / 7) {
          GUIManager loadBackup = new GUIManager();
          loadBackup.openWindow("Load Backup");
          backupPageNumber++;
          loadBackup.loadBackup(backupPageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(2)) {
          setVisible(false);
        }
      

      // DELETE BACKUP WINDOW
      } else if (windowType.equals("Delete Backup")) {
        for (int i = 0; i < BackupManager.getAllFiles().length; i++) {
          if (e.getSource() == buttons.get(i)) {
            String fileName = BackupManager.getAllFiles()[i];
            if (BackupManager.deleteBackup(fileName) == null) {
              JOptionPane.showMessageDialog(this, "UNABLE TO DELETE BACKUP:\n" + fileName);
              setVisible(false);
            } else {
              JOptionPane.showMessageDialog(this, "BACKUP SUCCESSFULLY DELETED:\n" + fileName);
              backupPageNumber = 1;
              setVisible(false);
            }
          }
        }
        if (e.getSource() == actionButtons.get(0) && backupPageNumber >= 2) {
          GUIManager loadBackup = new GUIManager();
          loadBackup.openWindow("Load Backup");
          backupPageNumber--;
          loadBackup.loadBackup(backupPageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(1) && backupPageNumber - 1 < (FileManager.getInventorySize() - 1) / 7) {
          GUIManager loadBackup = new GUIManager();
          loadBackup.openWindow("Load Backup");
          backupPageNumber++;
          loadBackup.loadBackup(backupPageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(2)) {
          setVisible(false);
        }
        
      // RENTALS LIST WINDOW
      } else if (windowType.equals("Your Rentals")) {

        ArrayList<Item> temp = new ArrayList<Item>();
        for (Item i : FileManager.getInventory()) {
          if (i.getID().equals(currentUser.getID())) {
            temp.add(i);
          }
        }
        
        for (int i = 0; i < temp.size(); i++) {
          if (e.getSource() == buttons.get(i)) {
            Item item = temp.get(i);
            JOptionPane.showMessageDialog(this, "ID: " + item.getID() + "\nName of Item: " + item.getName() + "\nType of Item: " + item.getType() + "\nDescription: " + item.getDescription() + "\nIs Claimed By: " + item.getIsClaimedBy());
          }
        }
        if (e.getSource() == actionButtons.get(0) && pageNumber >= 2) {
          GUIManager itemList = new GUIManager();
          itemList.openWindow("Your Rentals");
          otherPageNumber--;
          itemList.itemList(otherPageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(1) && otherPageNumber - 1 < (FileManager.getInventorySize() - 1) / 7) {
          GUIManager itemList = new GUIManager();
          itemList.openWindow("Your Rentals");
          otherPageNumber++;
          itemList.itemList(otherPageNumber);
          setVisible(false);
        } else if (e.getSource() == actionButtons.get(2)) {
          setVisible(false);
        }
      }
      
    } catch (Exception ignore) {
      System.out.println("Exception Occurred.");
    }
  }
  
}