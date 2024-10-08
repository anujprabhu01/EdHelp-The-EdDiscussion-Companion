package groupProjectEdDisc;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Button;




public class LoginPageUI { 
	private Label label_ApplicationTitle = new Label("Login to EdHelp Account");
    private Label label_Username = new Label("Username:");
    private TextField text_Username = new TextField();
    
    private Label label_Password = new Label("Password:");
    private TextField text_Password = new TextField();
    
    private Label label_LoginStatus = new Label("");
    private Button btn_Login = new Button("Login");
    private Button btn_haveCode = new Button("Have a Code\nClick Here");
	
	
	public LoginPageUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {
        // Setup for the application title at the top, centered
        setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH, Pos.CENTER, 0, 10);
        
        // Setup for the Username field
        setupLabelUI(label_Username, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
            Pos.BASELINE_LEFT, 10, 50);
        setupTextUI(text_Username, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
            Pos.BASELINE_LEFT, 10, 70, true);
        
        // Setup for the Password field
        setupLabelUI(label_Password, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
            Pos.BASELINE_LEFT, 10, 130);
        setupTextUI(text_Password, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
            Pos.BASELINE_LEFT, 10, 150, true);
        
        // Status Label
        setupLabelUI(label_LoginStatus, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
            Pos.BASELINE_LEFT, 10, 210);
        
     // Add login button
        btn_Login.setText("Login");
        btn_Login.setLayoutX(150);
        btn_Login.setLayoutY(250);
        theRoot.getChildren().add(btn_Login);
        
        // Handle login attempt
        btn_Login.setOnAction(e -> {
            handleLogin(driver);
        });
        
        // Add Code button
        btn_haveCode.setText("Have a Code?\n  Click Here");
        btn_haveCode.setLayoutX(250);
        btn_haveCode.setLayoutY(245);
        theRoot.getChildren().add(btn_haveCode);
        
     // Handle code button click
        btn_haveCode.setOnAction(e -> {
        	driver.loadCreateAccountPage();
        });
        
        
        // Adding the elements to the root pane
        theRoot.getChildren().addAll(label_ApplicationTitle, label_Username, text_Username, 
            label_Password, text_Password, label_LoginStatus);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleLogin(gp360EdDisc_GUIdriver driver) {
		driver.loadFinishAccountSetup(); //remove
		//If it is the users first time logging in call  *** driver.FinishAccountSetup() ***
		//ADD CODE HERE USE SQL DATABASE to ensure account credentials and 
		//Have a popup that asks the user which role they would like to login as before sending to account page; if only one role skip this and go straight to page
	}
	
    private void setupLabelUI(Label l, String font, double fontSize, double width, Pos alignment, double x, double y) {
        l.setFont(Font.font(font, fontSize));
        l.setMinWidth(width);
        l.setAlignment(alignment);
        l.setLayoutX(x);
        l.setLayoutY(y);
    }

    private void setupTextUI(TextField t, String font, double fontSize, double width, Pos alignment, double x, double y, boolean editable) {
        t.setFont(Font.font(font, fontSize));
        t.setMinWidth(width);
        t.setMaxWidth(width);
        t.setAlignment(alignment);
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setEditable(editable);
    }
}
	
	
	
