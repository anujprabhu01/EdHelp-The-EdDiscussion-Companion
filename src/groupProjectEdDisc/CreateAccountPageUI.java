package groupProjectEdDisc;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Button;

import java.sql.SQLException;



public class CreateAccountPageUI { 
	private Label label_ApplicationTitle = new Label("Create EdHelp Account");
    private Label label_AccountCode = new Label("Account Code (Not needed for first Admin):");
    private TextField text_AccountCode = new TextField();
    
    private Label label_Username = new Label("Username:");
    private TextField text_Username = new TextField();
 
    private Label label_Password = new Label("Password:");
    private PasswordField text_Password = new PasswordField();

    private Label label_ReenterPassword = new Label("Re-enter Password:");
    private PasswordField text_ReenterPassword = new PasswordField();

    private Label label_AccountStatus = new Label("");
    private Button btn_CreateAccount = new Button("Create Account");
    
    private Button btn_backToLogin = new Button("Back to Login");
    
    private boolean admin = false;
	private boolean instructor = false;
	private boolean student = false;
    
    private Label label_textFieldEmpty = new Label("please fill all required text fields");
    
    // Sam's code
    private Label label_passwordNotSame = new Label("passwords do not match");
    
    private Label label_usernameExistsInDB = new Label("username is taken");
	
	
	public CreateAccountPageUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {
        // Setup for the application title at the top, centered
        setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH, Pos.CENTER, 0, 10);
        
        setupLabelUI(label_AccountCode, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 50);
        setupTextUI(text_AccountCode, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
                Pos.BASELINE_LEFT, 10, 70, true);
            
	    // Setup for the Username field
	    setupLabelUI(label_Username, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
	            Pos.BASELINE_LEFT, 10, 120);
	    setupTextUI(text_Username, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
	            Pos.BASELINE_LEFT, 10, 140, true);
	    
        // Setup for the Password field
        setupLabelUI(label_Password, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 190);
        setupTextUI(text_Password, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
        		Pos.BASELINE_LEFT, 10, 210, true);
            
        // Setup for the Re-enter Password field
        setupLabelUI(label_ReenterPassword, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 260);
        setupTextUI(text_ReenterPassword, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
                Pos.BASELINE_LEFT, 10, 280, true);
            
        // Status Label
        setupLabelUI(label_AccountStatus, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 340);
        
     // FieldEmpty label
        setupLabelUI(label_textFieldEmpty, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 360, "red");
        label_textFieldEmpty.setVisible(false);
        label_textFieldEmpty.setManaged(false);
        
        // Passwords do not match label
        setupLabelUI(label_passwordNotSame, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 360, "red");
        label_passwordNotSame.setVisible(false);
        label_passwordNotSame.setManaged(false);
        
        // username already exists in DB label
        setupLabelUI(label_usernameExistsInDB, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 360, "red");
        label_usernameExistsInDB.setVisible(false);
        label_usernameExistsInDB.setManaged(false);
            
        // Add create account button
        btn_CreateAccount.setText("Create Account");
        btn_CreateAccount.setLayoutX(375);
        btn_CreateAccount.setLayoutY(450);
        theRoot.getChildren().add(btn_CreateAccount);
        
        // Add back to login button
        btn_backToLogin.setText("Back to Login");
        btn_backToLogin.setLayoutX(25);
        btn_backToLogin.setLayoutY(450);
        theRoot.getChildren().add(btn_backToLogin);
            
        // Handle account creation attempt
        /*
        btn_CreateAccount.setOnAction(e -> {
        	if (isTextFieldEmpty(text_Username, text_Password, text_ReenterPassword)) {
                label_textFieldEmpty.setVisible(true);
                label_textFieldEmpty.setManaged(true);
            } else {
                try {
                    handleCreateAccount(driver); // Handle the account creation here
                } catch (SQLException ex) {
                    System.err.println("Error during account creation: " + ex.getMessage());
                    ex.printStackTrace(); // Optionally, you can log or handle this further
                }
            }
            
        });
        */
        
        btn_CreateAccount.setOnAction(e -> {
                	try {
                        handleCreateAccount(driver); // Handle the account creation here
                    } catch (SQLException ex) {
                        System.err.println("Error during account creation: " + ex.getMessage());
                        ex.printStackTrace(); // Optionally, you can log or handle this further
                    }
        });
        
        /*
         * if user wants to go back to login page from create account page because they misclicked, this button will reroute them to login page.
         * */
        btn_backToLogin.setOnAction(e -> {
        	driver.loadloginPage();
        });

        
        theRoot.getChildren().addAll(label_ApplicationTitle, label_AccountCode, text_AccountCode,
                label_Username, text_Username, 
                label_Password, text_Password, 
                label_ReenterPassword, text_ReenterPassword,
                label_AccountStatus, label_textFieldEmpty,  label_passwordNotSame, label_usernameExistsInDB);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleCreateAccount(gp360EdDisc_GUIdriver driver) throws SQLException {
		
		if (isTextFieldEmpty(text_Username, text_Password, text_ReenterPassword)) {
            label_textFieldEmpty.setVisible(true);
            label_textFieldEmpty.setManaged(true);
            label_passwordNotSame.setVisible(false);
        	label_passwordNotSame.setManaged(false);
        	label_usernameExistsInDB.setVisible(false);
            label_usernameExistsInDB.setManaged(false);
        }
		else { //text fields are not empty
			boolean passwordsEqual;
        	passwordsEqual = arePasswordsEqual(text_Password, text_ReenterPassword);
            
            if(!passwordsEqual) {
            	label_passwordNotSame.setVisible(true);
            	label_passwordNotSame.setManaged(true);
            	label_textFieldEmpty.setVisible(false);
                label_textFieldEmpty.setManaged(false);
                label_usernameExistsInDB.setVisible(false);
                label_usernameExistsInDB.setManaged(false);
            }
            else { //if passwords entered by user match
            	
            	//FIXME check here to see if account creating code exists in acc_create_codes table
            	/*
            	 * if account creation code exists, get roles associated with code and once user creates account, delete the code
            	 * */
            	if(gp360EdDisc_GUIdriver.getDBHelper().usernameExistsInDB(text_Username.getText())) { // check if username already exists in database
            		//label
            		label_usernameExistsInDB.setVisible(true);
                    label_usernameExistsInDB.setManaged(true);
                    label_textFieldEmpty.setVisible(false);
                    label_textFieldEmpty.setManaged(false);
                    label_passwordNotSame.setVisible(false);
                	label_passwordNotSame.setManaged(false);
            	}
            	else {
            		if(!gp360EdDisc_GUIdriver.getDBHelper().isInvCodeValid(text_AccountCode.getText())) {
            			System.out.println("invite code is invalid");
            		}
            		else { //invite code is valid and all other validity checks are met; no flags raised
            			admin = gp360EdDisc_GUIdriver.getDBHelper().isAdminForInvCode(text_AccountCode.getText());
            			instructor =  gp360EdDisc_GUIdriver.getDBHelper().isInstructorForInvCode(text_AccountCode.getText());
            			student =  gp360EdDisc_GUIdriver.getDBHelper().isStudentForInvCode(text_AccountCode.getText());
            			
            			if (gp360EdDisc_GUIdriver.getDBHelper().isDatabaseEmpty()) {
            				admin = true;
            				instructor = false;
            				student = false;
            			}
            			/*
            			 * if a user is creating an account with a code (new account), then:
            			 * 		they have not finished setting up their account
            			 * 		they do not need password reset (as first time setting up account)
            			 * 			a user only needs pass reset if password while logging in match one of the one time reset passwords in pass_reset_table
            			 * */
            			gp360EdDisc_GUIdriver.getDBHelper().register(text_Username.getText(), text_Password.getText(), admin, instructor, student, false, false);
            			gp360EdDisc_GUIdriver.getDBHelper().markInvCodeAsUsed(text_AccountCode.getText());
            			driver.loadloginPage();
            		}
            	}
            }
		}
		
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
    
    // function to check if a textField on a page is empty; accepts indefinite number of arguments
    private boolean isTextFieldEmpty(TextField...fields) {
    	boolean flag_userPassEmpty = false;
    	for(TextField field : fields) {
    		if(field.getText().isEmpty()) {
    			flag_userPassEmpty = true;
    		}
    	}
    	return flag_userPassEmpty;
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
        	label_passwordNotSame.setVisible(true);
        	label_passwordNotSame.setManaged(true);
        	label_textFieldEmpty.setVisible(false);
            label_textFieldEmpty.setManaged(false);
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
    
}