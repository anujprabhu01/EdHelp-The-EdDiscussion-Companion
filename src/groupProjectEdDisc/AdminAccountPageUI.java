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
import javafx.scene.control.CheckBox;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Random;


public class AdminAccountPageUI { 
	
	// Sam's code
	private Stage deleteStage = new Stage();
	private Label deletePopupLabel = new Label("Are you sure?");
	private Button noConfirm = new Button("No");
	private Button yesConfirm = new Button("Yes");
	private VBox deletePopup = new VBox(10, deletePopupLabel, noConfirm, yesConfirm);
	private Scene popupDeleteScene = new Scene(deletePopup, 250, 150);
	
	// Jake's code
	private Label label_ApplicationTitle = new Label("Admin Account");
	private Button btn_logOut = new Button("Log Out");
	
    private Label label_InviteUser = new Label("Invite User");
    private Label label_InviteUserEmail = new Label("Email:");
    private TextField text_emailInvite = new TextField();
    private CheckBox check_Admin = new CheckBox("Admin");
    private CheckBox check_student = new CheckBox("Student");
    private CheckBox check_Instructor = new CheckBox("Admin");
    private Button btn_sendInvite = new Button("Invite send");
    
    private Label label_ResetAccount = new Label("Reset Account");
    private Label label_ResetUserEmail = new Label("Email:");
    private TextField text_emailReset = new TextField();
    private Button btn_sendReset = new Button("Reset send");
    
    private Label label_DeleteAccount = new Label("Delete Account");
    private Label label_DeleteUser = new Label("Username:");
    private TextField text_UserToDelete = new TextField();
    private Button btn_Delete = new Button("Delete");
    
    private Label label_AddOrRemoveRoles = new Label("Add or Remove Roles");
    private Label label_AddorUser = new Label("Username:");
    private TextField text_AddorUsername = new TextField();
    private CheckBox check_AddorAdmin = new CheckBox("Admin");
    private CheckBox check_Addorstudent = new CheckBox("Student");
    private CheckBox check_AddorInstructor = new CheckBox("Admin");
    private Button btn_ShowRoles = new Button("Check Roles");
    private Button btn_SetRoles = new Button("Set Roles");
    
    private Label label_list = new Label("List Users");
    private Button btn_ListUsers = new Button("List All Users");
    
	
	public AdminAccountPageUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {
        // Setup for the application title at the top, centered
        setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 120, 
        		Pos.CENTER, 40, 10);
        label_ApplicationTitle.setStyle("-fx-font-weight: bold");

        btn_logOut.setText("Log Out");
        btn_logOut.setLayoutX(430);
        btn_logOut.setLayoutY(10);
        btn_logOut.setMaxWidth(100);
        theRoot.getChildren().add(btn_logOut);
            
        // Handle send invite
        btn_logOut.setOnAction(e -> {
        	handleLogOut(driver);
        });
        
        
        //Setup for Invite user label, email label and email text field
        setupLabelUI(label_InviteUser, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 40);
        label_InviteUser.setStyle("-fx-font-weight: bold");

        setupLabelUI(label_InviteUserEmail, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, 87);
        setupTextUI(text_emailInvite, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 250,
                Pos.BASELINE_LEFT, 20, 107, true);
        
        //Setup for Student check box when selecting roles for an invite
        check_student.setText("Student");
        check_student.setLayoutX(20);
        check_student.setLayoutY(65);
        theRoot.getChildren().add(check_student);
        
        //Setup for Admin check box when selecting roles for an invite
        check_Admin.setText("Admin");
        check_Admin.setLayoutX(100);
        check_Admin.setLayoutY(65);
        theRoot.getChildren().add(check_Admin);
        
        //Setup for Instructor check box when selecting roles for an invite
        check_Instructor.setText("Instructor");
        check_Instructor.setLayoutX(170);
        check_Instructor.setLayoutY(65);
        theRoot.getChildren().add(check_Instructor);
        
        //Button to send an invite to the listed email
        btn_sendInvite.setText("Send Invite");
        btn_sendInvite.setLayoutX(300);
        btn_sendInvite.setLayoutY(110);
        theRoot.getChildren().add(btn_sendInvite);
            
        // Handle send invite
        btn_sendInvite.setOnAction(e -> {
        	try {
        	handleSendInvite(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        //Setup for reset account
        setupLabelUI(label_ResetAccount, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 150);
        label_ResetAccount.setStyle("-fx-font-weight: bold");

        setupLabelUI(label_ResetUserEmail, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, 175);
        setupTextUI(text_emailReset, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 250,
                Pos.BASELINE_LEFT, 20, 195, true);
        
        btn_sendReset.setText("Send One Time Password");
        btn_sendReset.setLayoutX(300);
        btn_sendReset.setLayoutY(198);
        theRoot.getChildren().add(btn_sendReset);
            
        // Handle account creation attempt
        btn_sendReset.setOnAction(e -> {
        	handleSendReset(driver);
        });
        
      //Setup for Delete account
        setupLabelUI(label_DeleteAccount, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 235);
        label_DeleteAccount.setStyle("-fx-font-weight: bold");

        setupLabelUI(label_DeleteUser, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, 260);
        setupTextUI(text_UserToDelete, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 250,
                Pos.BASELINE_LEFT, 20, 280, true);
        
        btn_Delete.setText("Delete Account");
        btn_Delete.setLayoutX(300);
        btn_Delete.setLayoutY(283);
        theRoot.getChildren().add(btn_Delete);
            
        // Handle account creation attempt
        btn_Delete.setOnAction(e -> {
        	try {
        	handleDeleteUser(driver);
        	} catch(SQLException ex) {
        		ex.printStackTrace();
        	}
        });
        
        
      //Setup for Invite user label, email label and email text field
        setupLabelUI(label_AddOrRemoveRoles, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 320);
        label_AddOrRemoveRoles.setStyle("-fx-font-weight: bold");

        setupLabelUI(label_AddorUser, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, 365);
        setupTextUI(text_AddorUsername, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 250,
                Pos.BASELINE_LEFT, 20, 385, true);
        
        //Setup for Student check box when selecting roles for an invite
        check_Addorstudent.setText("Student");
        check_Addorstudent.setLayoutX(20);
        check_Addorstudent.setLayoutY(345);
        theRoot.getChildren().add(check_Addorstudent);
        
        //Setup for Admin check box when selecting roles for an invite
        check_AddorAdmin.setText("Admin");
        check_AddorAdmin.setLayoutX(100);
        check_AddorAdmin.setLayoutY(345);
        theRoot.getChildren().add(check_AddorAdmin);
        
        //Setup for Instructor check box when selecting roles for an invite
        check_AddorInstructor.setText("Instructor");
        check_AddorInstructor.setLayoutX(170);
        check_AddorInstructor.setLayoutY(345);
        theRoot.getChildren().add(check_AddorInstructor);
        
        //Button to show roles of listed username
        btn_ShowRoles.setText("Show Roles");
        btn_ShowRoles.setLayoutX(300);
        btn_ShowRoles.setLayoutY(388);
        theRoot.getChildren().add(btn_ShowRoles);
            
        // Handle show roles
        btn_ShowRoles.setOnAction(e -> {
        	handleShowRoles(driver);
        });
        
        
      //Button to Set roles of listed username
        btn_SetRoles.setText("Set Roles");
        btn_SetRoles.setLayoutX(390);
        btn_SetRoles.setLayoutY(388);
        theRoot.getChildren().add(btn_SetRoles);
            
        // Handle show roles
        btn_SetRoles.setOnAction(e -> {
        	handleSetRoles(driver);
        });
        
        
        //Setup for label on List users
        setupLabelUI(label_list, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 425);
        label_list.setStyle("-fx-font-weight: bold");
        
        //Button for listing Users
        btn_ListUsers.setText("List All Users");
        btn_ListUsers.setLayoutX(20);
        btn_ListUsers.setLayoutY(450);
        theRoot.getChildren().add(btn_ListUsers);
            
        // Handle show roles
        btn_ListUsers.setOnAction(e -> {
        	handleListUsers(driver);
        });
        
        theRoot.getChildren().addAll(label_ApplicationTitle ,label_InviteUserEmail, label_InviteUser, text_emailInvite, 
        		label_ResetAccount, text_emailReset, label_ResetUserEmail,label_DeleteAccount, label_DeleteUser, text_UserToDelete,
        		label_AddOrRemoveRoles, label_AddorUser, text_AddorUsername, label_list);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleSendInvite(gp360EdDisc_GUIdriver driver) throws SQLException {
		//Send an invite to the user by making a pop up occur with random invite code
		boolean isAdmin = check_Admin.isSelected();
		boolean isStudent = check_student.isSelected();
		boolean isInstructor = check_Instructor.isSelected();
		
		if(!isAdmin && !isStudent && !isInstructor) { //FIXME generate error label for admin that tells admin to select atleast one role
			System.out.println("please select atleast one role");
		}
		else {
			String code = generateRandomString();
			
			boolean success = gp360EdDisc_GUIdriver.getDBHelper().inviteUser(code, isAdmin, isInstructor, isStudent);
			
			if(success) {
				check_Admin.setSelected(false);
		        check_student.setSelected(false);
		        check_Instructor.setSelected(false);
		        
		        text_emailInvite.setText("");
			}
			else { //unsuccessful invite query
				System.out.println("unsuccessful invite");
			}
		}
	}
	
	private void handleSendReset(gp360EdDisc_GUIdriver driver) {
		//Send an Reset to the user by making a pop up occur with random one-time password 
	}
	
	private void handleDeleteUser(gp360EdDisc_GUIdriver driver) throws SQLException {
		//Make a pop up occur that asks are you sure you want to delete, if yes delete the user from the database
		//query if data base contains the textfield
		//create pop up with yes/no buttons
		// change to query
		if(text_UserToDelete.getText().equals("")) { //FIXME implement label
			System.out.println("enter username of associated user to delete first");
		}
		else {
			//first, get username given by admin
			String username = text_UserToDelete.getText();
			
			
			deleteStage.setTitle("Are you sure?");
			deleteStage.initModality(Modality.APPLICATION_MODAL); //this is important because it prevents the user from doing anything other than in the pop-up scene
			
			noConfirm.setOnAction(e -> {
				deleteStage.close();
				driver.loadAdminAccount();
			});
			
			yesConfirm.setOnAction(e -> {
				boolean success = gp360EdDisc_GUIdriver.getDBHelper().deleteUser(username);
				if(success) {
					System.out.println("successful delete by admin");
					deleteStage.close();
					driver.loadAdminAccount();
				}
				else {
					System.out.println("unsuccessful delete by admin");
				}
			});
			deletePopup.setAlignment(Pos.CENTER);
			
			deleteStage.setScene(popupDeleteScene);
			deleteStage.showAndWait();
		}
}

	
	private void handleLogOut(gp360EdDisc_GUIdriver driver) {
		gp360EdDisc_GUIdriver.USERNAME = "";
		driver.loadloginPage();
	}
	
	private void handleShowRoles(gp360EdDisc_GUIdriver driver) {
		//Check the boxes for the roles of the username inputted to text_AddorUsername
	}
	
	private void handleSetRoles(gp360EdDisc_GUIdriver driver) {
		//Set the roles of the listed user in accordance with the checked boxes
	}
	
	private void handleListUsers(gp360EdDisc_GUIdriver driver) {
		//Make a new widow that contains a list of all users in the database
		gp360EdDisc_GUIdriver.getDBHelper().listUserAccounts();
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
    
    /**********************************************************************************************
     * Other Helper Methods
     **********************************************************************************************/
    public static String generateRandomString() {
    	String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@+!";
    	int LENGTH = 16; // Length of the random string
    	
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            // Generate a random index
            int index = random.nextInt(CHARACTERS.length());
            // Append the character at the random index to the StringBuilder
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
    
    
}