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

import java.sql.SQLException;




public class CreateAccountPageUI { 
	private Label label_ApplicationTitle = new Label("Create EdHelp Account");
    private Label label_AccountCode = new Label("Account Code (Not needed for first Admin):");
    private TextField text_AccountCode = new TextField();
    
    private Label label_Username = new Label("Username:");
    private TextField text_Username = new TextField();
 
    private Label label_Password = new Label("Password:");
    private TextField text_Password = new TextField();

    private Label label_ReenterPassword = new Label("Re-enter Password:");
    private TextField text_ReenterPassword = new TextField();

    private Label label_AccountStatus = new Label("");
    private Button btn_CreateAccount = new Button("Create Account");
    
    private Label label_textFieldEmpty = new Label("please fill all required text fields");
	
	
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
        setupLabelUI(label_textFieldEmpty, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 350, "red");
        label_textFieldEmpty.setVisible(false);
        label_textFieldEmpty.setManaged(false);
            
        // Add create account button
        btn_CreateAccount.setText("Create Account");
        btn_CreateAccount.setLayoutX(200);
        btn_CreateAccount.setLayoutY(350);
        theRoot.getChildren().add(btn_CreateAccount);
            
        // Handle account creation attempt
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
        
        theRoot.getChildren().addAll(label_ApplicationTitle, label_AccountCode, text_AccountCode,
                label_Username, text_Username, 
                label_Password, text_Password, 
                label_ReenterPassword, text_ReenterPassword,
                label_AccountStatus, label_textFieldEmpty);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleCreateAccount(gp360EdDisc_GUIdriver driver) throws SQLException {
		try {
			//FIXME ADD CODE HERE to create the account in database
			gp360EdDisc_GUIdriver.getDBHelper().register(text_Username.getText(), text_Password.getText(), "Admin");
			driver.loadloginPage();
		} catch (SQLException e) {
		System.err.println("Database error: " + e.getMessage());
		e.printStackTrace();
	}
		finally {
			//
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