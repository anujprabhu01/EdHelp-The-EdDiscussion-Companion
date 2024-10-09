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

//H2 imports
import java.sql.SQLException;

//Scanner if needed
import java.util.Scanner;

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
	public final static double WINDOW_HEIGHT = 500;
	public static String USERNAME = "";
	
	private Scene scene;
	private Pane theRoot;
	private boolean firstLogin = false;
	
	
	
	
	@Override
	public void start(Stage theStage) throws Exception {
		theStage.setTitle("Ed Discussion Group Project");			// Label the stage (a window)
		
		theRoot = new Pane();							// Create a pane within the window
		
		scene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene
		
		theStage.setScene(scene);						// Set the scene on the stage
		
		theStage.show();									// Show the stage to the user
		if (databaseHelper.isDatabaseEmpty()) { //FIXME Should check if database is empty
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
	
	public void loadUserAccount() {
		theRoot.getChildren().clear();
		UserAccountPageUI userAccountPage = new UserAccountPageUI(theRoot, this);
	}
	
	public void loadAdminAccount() {
		theRoot.getChildren().clear();
		AdminAccountPageUI adminAccountPage = new AdminAccountPageUI(theRoot, this);
	}
	
	public void showPopupWindow() {
	    Stage popupStage = new Stage();
	    popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main window
	    popupStage.setTitle("");
	    Label label_roleSelect = new Label("Select your role for this session");
	    Button btn_Admin = new Button("Admin");
	    Button btn_Instructor = new Button("Instructor");
	    Button btn_Student = new Button("Student");
	    popupStage.setOnCloseRequest(WindowEvent::consume);

	    btn_Admin.setOnAction(e -> {
	    	loadAdminAccount();
	        popupStage.close(); // Close the pop-up
	    });

	    btn_Instructor.setOnAction(e -> {
	    	loadUserAccount();
	    	popupStage.close(); // Close the pop-up
	    });
	    
	    btn_Student.setOnAction(e -> {
	    	loadUserAccount();
	        popupStage.close(); // Close the pop-up
	    });
	    
	    HBox Buttonlayout = new HBox(10, btn_Student, btn_Admin, btn_Instructor);
	    Buttonlayout.setAlignment(Pos.CENTER);

	    VBox layout = new VBox(20, label_roleSelect, Buttonlayout); // 20 is the spacing between the label and buttons
	    layout.setAlignment(Pos.CENTER);
	    
	    Scene popupScene = new Scene(layout, 350, 200);
	    popupStage.setScene(popupScene);
	    popupStage.showAndWait(); // Wait until the pop-up is closed
	   }
	
	
	public static DatabaseHelper getDBHelper() {
		return databaseHelper;
	}
	
	public static void main(String[] args) {	
		try { 
			databaseHelper.startH2Console();
			databaseHelper.connectToDatabase(); // Connect to the database
			
			launch(args);		
		} catch (SQLException e) {
			System.err.println("Database error: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			System.out.println("Good Bye!!");
			databaseHelper.closeConnection();
			databaseHelper.stopH2Console();
		}
		
	}		
}