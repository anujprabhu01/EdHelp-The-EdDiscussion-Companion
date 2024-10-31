package groupProjectEdDisc;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.control.Button;




public class UserAccountPageUI { 
	private Label label_ApplicationTitle = new Label("User Account");
    
    private Button btn_Logout = new Button("Log Out");
    
	
	
	public UserAccountPageUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {
        // Setup for the application title at the top, centered
        setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 200, Pos.CENTER, 70, 10);
        
        
     // Add login button
        btn_Logout.setText("Log Out");
        btn_Logout.setLayoutX(215);
        btn_Logout.setLayoutY(200);
        theRoot.getChildren().add(btn_Logout);
        
        // Handle login attempt
        btn_Logout.setOnAction(e -> {
        	handleLogOut(driver);
        });
        
 
        // Adding the elements to the root pane
        theRoot.getChildren().addAll(label_ApplicationTitle);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleLogOut(gp360EdDisc_GUIdriver driver) {
		//Log Out
		gp360EdDisc_GUIdriver.USERNAME = "";
		driver.loadloginPage();
	}
	
    private void setupLabelUI(Label l, String font, double fontSize, double width, Pos alignment, double x, double y) {
        l.setFont(Font.font(font, fontSize));
        l.setMinWidth(width);
        l.setAlignment(alignment);
        l.setLayoutX(x);
        l.setLayoutY(y);
    }

}