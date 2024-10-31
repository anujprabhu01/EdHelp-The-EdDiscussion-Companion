package groupProjectEdDisc;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

public class InstructorAccountPageUI {
private Label label_ApplicationTitle = new Label("Instructor Account");
    
    private Button btn_Logout = new Button("Log Out");
    
    private Button btn_menu = new Button("Menu");
    
	
	
	public InstructorAccountPageUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {
        // Setup for the application title at the top, centered
        setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 200, Pos.CENTER, 70, 10);
        
        
        // Add login button
        btn_Logout.setText("Log Out");
        btn_Logout.setLayoutX(215);
        btn_Logout.setLayoutY(200);
        theRoot.getChildren().add(btn_Logout);
        
        // Add menu button
        btn_menu.setLayoutX(10);
        btn_menu.setLayoutY(10);
        btn_menu.setMaxWidth(100);
        theRoot.getChildren().add(btn_menu);
        
        // Handle login attempt
        btn_Logout.setOnAction(e -> {
        	handleLogOut(driver);
        });
        
        // Handle menu operations
        btn_menu.setOnAction(e -> {
        	try {
        	handleMenu(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
 
        // Adding the elements to the root pane
        theRoot.getChildren().addAll(label_ApplicationTitle);
    }
	
	private void handleMenu(gp360EdDisc_GUIdriver driver) throws SQLException { 
		driver.showMenuPopUp(driver.CURRENT_SESSION);
	}
	
	private void handleLogOut(gp360EdDisc_GUIdriver driver) {
		//Log Out
		gp360EdDisc_GUIdriver.USERNAME = "";
		driver.loadloginPage();
	}
	
	/**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
    private void setupLabelUI(Label l, String font, double fontSize, double width, Pos alignment, double x, double y) {
        l.setFont(Font.font(font, fontSize));
        l.setMinWidth(width);
        l.setAlignment(alignment);
        l.setLayoutX(x);
        l.setLayoutY(y);
    }
}
