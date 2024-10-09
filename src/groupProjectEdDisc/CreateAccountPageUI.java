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
    
    private boolean admin = false;
	private boolean instructor = false;
	private boolean student = false;
	
	
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
            
        // Add create account button
        btn_CreateAccount.setText("Create Account");
        btn_CreateAccount.setLayoutX(200);
        btn_CreateAccount.setLayoutY(350);
        theRoot.getChildren().add(btn_CreateAccount);
            
        // Handle account creation attempt
        btn_CreateAccount.setOnAction(e -> {
        	try {
        		handleCreateAccount(driver);
        	}
        	catch (SQLException s) {
        		System.out.print("ERRRRRORORORO");
        	}
        	finally {
        		
        	}
            
        });
        
        theRoot.getChildren().addAll(label_ApplicationTitle, label_AccountCode, text_AccountCode,
                label_Username, text_Username, 
                label_Password, text_Password, 
                label_ReenterPassword, text_ReenterPassword,
                label_AccountStatus);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleCreateAccount(gp360EdDisc_GUIdriver driver) throws SQLException {
		try {
			admin = false;
			instructor = false;
			student = false;
			if (text_AccountCode.getText().contains("1")) {//1 = Admin 
				admin = true;
			}
			if (text_AccountCode.getText().contains("2")) {//2 = instuctor 
				instructor = true;
			}
			if (text_AccountCode.getText().contains("1")) {//3 = student
				student = true;
			}
			if (gp360EdDisc_GUIdriver.getDBHelper().isDatabaseEmpty()) {
				admin = true;
				instructor = false;
				student = false;
			}
			gp360EdDisc_GUIdriver.getDBHelper().register(text_Username.getText(), text_Password.getText(), admin, instructor, student, false, false);
			driver.loadloginPage();
		}
		finally {
			
		}
		
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