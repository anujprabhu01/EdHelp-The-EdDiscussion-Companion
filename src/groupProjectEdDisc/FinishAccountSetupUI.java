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




public class FinishAccountSetupUI { 
	private Label label_ApplicationTitle = new Label("Finish Setting Up your Account");
    private Label label_email = new Label("Email:");
    private TextField text_email = new TextField();
    
    private Label label_Firstname = new Label("First Name:");
    private TextField text_Firstname = new TextField();
 
    private Label label_MiddleName = new Label("Middle Name:");
    private TextField text_MiddleName = new TextField();

    private Label label_LastName = new Label("Last Name:");
    private TextField text_LastName = new TextField();
    
    private Label label_preFirst = new Label("Prefered First Name:");
    private TextField text_preFirst = new TextField();

    private Button btn_ConfirmDetails = new Button("Confirm Account Details");
	
	
	public FinishAccountSetupUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {
        // Setup for the application title at the top, centered
        setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH, Pos.CENTER, 0, 10);
        
        setupLabelUI(label_email, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 50);
        setupTextUI(text_email, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
                Pos.BASELINE_LEFT, 10, 70, true);
            
	    // Setup for the First Name field
	    setupLabelUI(label_Firstname, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
	            Pos.BASELINE_LEFT, 10, 120);
	    setupTextUI(text_Firstname, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
	            Pos.BASELINE_LEFT, 10, 140, true);
	    
        // Setup for the Middle Name field
        setupLabelUI(label_MiddleName, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 190);
        setupTextUI(text_MiddleName, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
        		Pos.BASELINE_LEFT, 10, 210, true);
            
        // Setup for the Last Name Password field
        setupLabelUI(label_LastName, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 260);
        setupTextUI(text_LastName, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
                Pos.BASELINE_LEFT, 10, 280, true);
        
     // Setup for the Pref Name Password field
        setupLabelUI(label_preFirst, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 330);
        setupTextUI(text_preFirst, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 20,
                Pos.BASELINE_LEFT, 10, 350, true);
            
            
        // Add create account button
        btn_ConfirmDetails.setText("Create Account");
        btn_ConfirmDetails.setLayoutX(200);
        btn_ConfirmDetails.setLayoutY(400);
        theRoot.getChildren().add(btn_ConfirmDetails);
            
        // Handle account creation attempt
        btn_ConfirmDetails.setOnAction(e -> {
            handleConfirmDetails(driver);
        });
        
        theRoot.getChildren().addAll(label_ApplicationTitle, label_email, text_email,
        		label_Firstname, text_Firstname, 
        		label_MiddleName, text_MiddleName, 
        		label_LastName, text_LastName,
        		label_preFirst, text_preFirst);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleConfirmDetails(gp360EdDisc_GUIdriver driver) {
		driver.loadAdminAccount();
		//driver.loadUserAccount();
		//FIXME ADD CODE HERE to add details to the account in database
		//If role == Admin
		// driver.loadAdminAccount()
		//else 
		// driver.loadUserAccount()
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