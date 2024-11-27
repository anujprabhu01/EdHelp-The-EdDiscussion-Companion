package groupProjectEdDisc;

//JavaFX imports needed to support the Graphical User Interface
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.sql.SQLException;
//ADDED THIS JAKE
import javafx.scene.control.TextField;


//H2 imports
// import java.sql.SQLException;

//Scanner
// import java.util.Scanner;

/*******
* <p>  Class </p>
* 
* <p> Description: A JavaFX demonstration application and baseline for a sequence of projects </p>
* 
* <p> Copyright:  </p>
* 
* @author Jake Mulera
* @author anujprabhu
* @author Samuel Strechay
* 
* 
*/

public class gp360EdDisc_GUIdriver extends Application {
	
	private static final DatabaseHelper databaseHelper = new DatabaseHelper();
	// private static final Scanner scanner = new Scanner(System.in);
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 550;
	public static String USERNAME = "";
	public static String CURRENT_SESSION = "";
	public static String CURRENT_SEARCH_GROUP = "";
	
	private Scene scene;
	private Pane theRoot;

	
	
	
	
	@Override
	public void start(Stage theStage) throws Exception {
		theStage.setTitle("Ed Discussion Group Project");			// Label the stage (a window)
		
		theRoot = new Pane();							// Create a pane within the window
		
		scene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene
		
		theStage.setScene(scene);						// Set the scene on the stage
		
		theStage.show();									// Show the stage to the user
		if (databaseHelper.isDatabaseEmpty()) { 
			loadCreateAccountPage();
		} else {
			loadloginPage();
		}
		
	}
	
	public void loadloginPage() {
		theRoot.getChildren().clear();
		LoginPageUI loginPage = new LoginPageUI(theRoot, this);
	}
	
	public void loadCreateAccountPage() {
		theRoot.getChildren().clear();
		CreateAccountPageUI createAccountPage = new CreateAccountPageUI(theRoot, this);
	}
	
	public void loadFinishAccountSetup() {
		theRoot.getChildren().clear();
		FinishAccountSetupUI finishAccountPage = new FinishAccountSetupUI(theRoot, this);
	}
	
	public void searchPage() {
		theRoot.getChildren().clear();
		SearchPageUI searchPage = new SearchPageUI(theRoot, this);
		
	}
	
	public void loadInstructorAccount() {
		theRoot.getChildren().clear();
		InstructorAccountPageUI instructorAccountPage = new InstructorAccountPageUI(theRoot, this);
		//CURRENT_SESSION = "INSTRUCTOR";
	}
	
	public void loadAdminAccount() {
		theRoot.getChildren().clear();
		AdminAccountPageUI adminAccountPage = new AdminAccountPageUI(theRoot, this);
		//CURRENT_SESSION = "ADMIN";
	}
	
	public void loadArticleAPage() {
		theRoot.getChildren().clear();
		ArticleAPageUI articleAPage = new ArticleAPageUI(theRoot, this);
	}
	
	public void loadGroupPage() {
		theRoot.getChildren().clear();
		GroupManagerPageUI groupPage = new GroupManagerPageUI(theRoot, this);
	}
	
	public void showPopupWindow() {
		try {
			Stage popupStage = new Stage();
	        popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main window
	        popupStage.setTitle("");
	        Label label_roleSelect = new Label("Select your role for this session");

	        Button btn_Admin = new Button("Admin");
	        Button btn_Instructor = new Button("Instructor");
	        Button btn_Student = new Button("Student");

	        // Event handling should be set before calling showAndWait
	        btn_Admin.setOnAction(e -> {
	            loadAdminAccount();
	            popupStage.close(); // Close the pop-up
	        });

	        btn_Instructor.setOnAction(e -> {
	            loadInstructorAccount();
	            popupStage.close(); // Close the pop-up
	        });

	        btn_Student.setOnAction(e -> {
	            searchPage();
	            popupStage.close(); // Close the pop-up
	        });

	        HBox buttonLayout = new HBox(10, btn_Student, btn_Admin, btn_Instructor);
	        buttonLayout.setAlignment(Pos.CENTER);

	        VBox layout = new VBox(20, label_roleSelect, buttonLayout); // 20 is the spacing between the label and buttons
	        layout.setAlignment(Pos.CENTER);

	        Scene popupScene = new Scene(layout, 350, 200);
	        popupStage.setScene(popupScene);

	        // Setup visibility based on roles (after creating the layout)
	        btn_Admin.setVisible(false);
	        btn_Admin.setManaged(false);

	        btn_Instructor.setVisible(false);
	        btn_Instructor.setManaged(false);

	        btn_Student.setVisible(false);
	        btn_Student.setManaged(false);

	        if (getDBHelper().isAdminForUsers(USERNAME)) {
	            btn_Admin.setVisible(true);
	            btn_Admin.setManaged(true);
	            CURRENT_SESSION = "ADMIN";
	        }
	        if (getDBHelper().isInstructorForUsers(USERNAME)) {
	            btn_Instructor.setVisible(true);
	            btn_Instructor.setManaged(true);
	            CURRENT_SESSION = "INSTRUCTOR";
	        }
	        if (getDBHelper().isStudentForUsers(USERNAME)) {
	            btn_Student.setVisible(true);
	            btn_Student.setManaged(true);
	            CURRENT_SESSION = "STUDENT";
	        }

	        // Show and wait should be called after everything is set
	        popupStage.showAndWait(); // Wait until the pop-up is closed
		} catch(SQLException ex) {
			ex.printStackTrace();
		}

	   }
	
	public void showPassResetPOP() {
	    Stage popupStage = new Stage();
	    popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main window
	    popupStage.setTitle("Reset your password");
	    Label label_enterNewPass = new Label("Enter new Password:");
	    TextField text_newPass = new TextField();
	    Label label_confirmNewPass = new Label("Re-enter new Password:");
	    TextField text_confirmednewPass = new TextField();
	    Button btn_confirm = new Button();
	    Label label_passwordNotSame = new Label("passwords do not match");
	    label_passwordNotSame.setStyle("-fx-text-fill: " + "red" + ";");
	    label_passwordNotSame.setVisible(false);
	    label_passwordNotSame.setManaged(false);
	    popupStage.setOnCloseRequest(WindowEvent::consume);

	    btn_confirm.setOnAction(e -> {
	    	Boolean equal = arePasswordsEqual(text_newPass, text_confirmednewPass);
	    	if (equal) {
	    		databaseHelper.setPasswordWithUSER(text_newPass.getText());
	    		popupStage.close(); // Close the pop-up
	    	}
	    	else {
	    		label_passwordNotSame.setVisible(true);
	    	    label_passwordNotSame.setManaged(true);
	    		
	    		
	    	}  
	    });
	    btn_confirm.setText("Login");
	    
	    
	    text_newPass.setMaxWidth(175);
	    text_confirmednewPass.setMaxWidth(175);
	    
	    VBox layout = new VBox(15, label_enterNewPass, text_newPass, label_confirmNewPass, text_confirmednewPass, label_passwordNotSame, btn_confirm); // 15 is the spacing between the label and buttons
	    layout.setAlignment(Pos.CENTER);
	    
	    Scene popupScene = new Scene(layout, 350, 200);
	    popupStage.setScene(popupScene);
	    popupStage.showAndWait(); // Wait until the pop-up is closed
	   }
	
		
	public void showMenuPopUp(String session) {
		try {
			Stage popupStage = new Stage();
	        popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main window
	        popupStage.setTitle("");
	        Label label_roleSelect = new Label("Select a page");

	        Button btn_Article = new Button("Article Manager");
	        Button btn_Account = new Button("Account Manager");
	        Button btn_Group = new Button("Group Manager");
	        Button btn_Search = new Button("Search Page");
	        

	        // Event handling should be set before calling showAndWait
	        btn_Account.setOnAction(e -> {
	            loadAdminAccount();
	            popupStage.close(); // Close the pop-up
	        });

	        btn_Article.setOnAction(e -> {
	            loadArticleAPage();
	            popupStage.close(); // Close the pop-up
	        });
	        
	        btn_Group.setOnAction(e -> {
	            loadGroupPage();
	            popupStage.close(); // Close the pop-up
	        });
	        
	        btn_Search.setOnAction(e -> {
	        	searchPage();
	            popupStage.close(); // Close the pop-up
	        });

	        HBox buttonLayout = new HBox(10, btn_Account, btn_Article, btn_Group, btn_Search);
	        buttonLayout.setAlignment(Pos.CENTER);

	        VBox layout = new VBox(20, label_roleSelect, buttonLayout); // 20 is the spacing between the label and buttons
	        layout.setAlignment(Pos.CENTER);

	        Scene popupScene = new Scene(layout, 350, 200);
	        popupStage.setScene(popupScene);
	        
	        // Setup visibility based on roles (after creating the layout)
	        btn_Account.setVisible(false);
	        btn_Account.setManaged(false);

	        btn_Article.setVisible(false);
	        btn_Article.setManaged(false);
	        
	        btn_Group.setVisible(false);
	        btn_Group.setManaged(false);
	        
	        btn_Search.setVisible(false);
	        btn_Search.setManaged(false);
	        
	        if (getDBHelper().isInstructorForUsers(USERNAME) && CURRENT_SESSION.equals("INSTRUCTOR")) { //
	        	btn_Article.setVisible(true);
	        	btn_Article.setManaged(true);
	        	btn_Group.setVisible(true);
		        btn_Group.setManaged(true);
		        btn_Search.setVisible(true);
		        btn_Search.setManaged(true);
	        }
	        else if (getDBHelper().isAdminForUsers(USERNAME) && CURRENT_SESSION.equals("ADMIN")) {
	        	btn_Article.setVisible(true);
	        	btn_Article.setManaged(true);
	        	btn_Account.setVisible(true);
	        	btn_Account.setManaged(true);
	        	btn_Group.setVisible(true);
		        btn_Group.setManaged(true);
	        }

	        // Show and wait should be called after everything is set
	        popupStage.showAndWait(); // Wait until the pop-up is closed
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static DatabaseHelper getDBHelper() {
		return databaseHelper;
	}
	
	private boolean arePasswordsEqual(TextField pass, TextField reenterPass) {
    	char[] charArray1;
        char[] charArray2;
        boolean passEqual;
        
    	charArray1 = pass.getText().toCharArray();
        charArray2 = reenterPass.getText().toCharArray();
        passEqual = true;
        // Compare the lengths first for quick check
        if (charArray1.length != charArray2.length) {
            passEqual = false;
        }
        else { //if lengths of passwords are equal
        	for(int i = 0; i < charArray1.length; i++) {
            	if(charArray1[i] != charArray2[i]) { // if characters do not match
            		passEqual = false;
            		break;
            	}
            }
        }
        return passEqual;
    }
	
	public static void main(String[] args) {	
		try { 
			databaseHelper.startH2Console();
			databaseHelper.encryptionHelper(); //connect to encryptionHelper
			databaseHelper.connectToDatabase(); // Connect to the database
			
			
			
			
			launch(args);		
		} catch (SQLException e) {
			System.err.println("Database error: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Encryption error: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			System.out.println("Good Bye!!");
			databaseHelper.closeConnection();
			databaseHelper.stopH2Console();
		}
		
	}		
}