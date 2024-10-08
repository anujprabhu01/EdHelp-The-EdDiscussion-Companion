package groupProjectEdDisc;

//JavaFX imports needed to support the Graphical User Interface
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*******
 * <p>  Class </p>
 * 
 * <p> Description: A JavaFX demonstration application and baseline for a sequence of projects </p>
 * 
 * <p> Copyright:  </p>
 * 
 * @author Jake Mulera
 * 
 * 
 * 
 */

public class gp360EdDisc_GUIdriver extends Application {
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 500;
	
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
		if (firstLogin == true) { //FIXME Should check if database is empty
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
	
	public static void main(String[] args) {				// This method may not be required
		launch(args);										// for all JavaFX applications using
	}		
}