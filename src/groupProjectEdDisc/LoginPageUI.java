package groupProjectEdDisc;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
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
    private PasswordField text_Password = new PasswordField();
    
    // Label label_LoginStatus = new Label(""); // use if needed and add to theRoot; not in use right now
    private Button btn_Login = new Button("Login");
    private Button btn_haveCode = new Button("Have a Code\nClick Here");
    
    private Label label_userPassEmpty = new Label("username/passoword is missing");
    
    private Label label_incorrectCreds = new Label("incorrect credentials");
	
	
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
        /*
        // Status Label
        setupLabelUI(label_LoginStatus, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
            Pos.BASELINE_LEFT, 10, 210);
            */
        
        // FieldEmpty label
        setupLabelUI(label_userPassEmpty, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 210, "red");
        label_userPassEmpty.setVisible(false);
        label_userPassEmpty.setManaged(false);
        
        // incorrect credentials label
        setupLabelUI(label_incorrectCreds, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 210, "red");
        label_incorrectCreds.setVisible(false);
        label_incorrectCreds.setManaged(false);
        
     // Add login button
        btn_Login.setText("Login");
        btn_Login.setLayoutX(150);
        btn_Login.setLayoutY(250);
        theRoot.getChildren().add(btn_Login);
        
        // Handle login attempt
        btn_Login.setOnAction(e -> {
        		try {
                    handleLogin(driver); // Handle the account login here
                } catch (SQLException ex) {
                    System.err.println("Error during account creation: " + ex.getMessage());
                    ex.printStackTrace(); // Optionally, you can log or handle this further
                }
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
            label_Password, text_Password, label_userPassEmpty, label_incorrectCreds);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleLogin(gp360EdDisc_GUIdriver driver)  throws SQLException {
//		System.out.print("IN!!!!!");
//		System.out.print(text_Username.getText());
//		System.out.print("\n");
//		System.out.print(text_Password.getText());
		
		if (isTextFieldEmpty(text_Username, text_Password)) {
    		label_userPassEmpty.setVisible(true);
    		label_userPassEmpty.setManaged(true);
    		label_incorrectCreds.setVisible(false);
            label_incorrectCreds.setManaged(false);
    		
    	} else {

		if (gp360EdDisc_GUIdriver.getDBHelper().login(text_Username.getText(), text_Password.getText())) {
			System.out.println("Login Successful!");
			gp360EdDisc_GUIdriver.USERNAME = text_Username.getText();
			/////This is NEWW JAKE
			if (gp360EdDisc_GUIdriver.getDBHelper().getNeedPassReset()) {
				driver.showPassResetPOP();
			}
			
			if (!gp360EdDisc_GUIdriver.getDBHelper().getFinishSetup()) {
				driver.loadFinishAccountSetup();
				return;
			} 
			
			if (gp360EdDisc_GUIdriver.getDBHelper().hasTwoOrMoreRoles()) {
				driver.showPopupWindow();
			}
			else {
				if (gp360EdDisc_GUIdriver.getDBHelper().oneRoleReturn() == "admin") {
					driver.loadAdminAccount();
				}
				else if (gp360EdDisc_GUIdriver.getDBHelper().oneRoleReturn() == "instructor"){
					driver.loadArticleAPage();
				}
				else {
					driver.loadUserAccount();
				}
			}
		}
		else {
			//FIXME Login Credentials are incorrect
			label_userPassEmpty.setVisible(false);
    		label_userPassEmpty.setManaged(false);
			label_incorrectCreds.setVisible(true);
	        label_incorrectCreds.setManaged(true);
		}
    	}
		
		
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
    
    private void setupLabelUI(Label l, String font, double fontSize, double width, Pos alignment, double x, double y, String color) {
        l.setFont(Font.font(font, fontSize));
        l.setMinWidth(width);
        l.setAlignment(alignment);
        l.setLayoutX(x);
        l.setLayoutY(y);
        l.setStyle("-fx-text-fill: " + color + ";"); // set color of label
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
    
    /**********************************************************************************************
     * Other Helper Methods
     **********************************************************************************************/
    private boolean isTextFieldEmpty(TextField...fields) {
    	boolean flag_userPassEmpty = false;
    	for(TextField field : fields) {
    		if(field.getText().isEmpty()) {
    			flag_userPassEmpty = true;
    		}
    	}
    	return flag_userPassEmpty;
    }
}