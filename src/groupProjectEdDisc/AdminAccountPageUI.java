package groupProjectEdDisc;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;


public class AdminAccountPageUI { 
	
	// Sam's code
	private Stage deleteStage = new Stage();
	private Label deletePopupLabel = new Label("Are you sure?");
	private Button noConfirm = new Button("No");
	private Button yesConfirm = new Button("Yes");
	private VBox deletePopup = new VBox(10, deletePopupLabel, noConfirm, yesConfirm);
	private Scene popupDeleteScene = new Scene(deletePopup, 250, 150);
	
	private Stage showStage = new Stage();
	private Label showPopupLabel = new Label("Shown User");
	private Button showBack = new Button("Back");
	private VBox showVBox = new VBox(20, showPopupLabel, showBack);
	private Scene showScene = new Scene(showVBox, 360, 100);
	private Label label_usernameDoesNotExist = new Label("username does not exist");
	
	private Label label_selfAdmin = new Label("please select Admin for yourself");
	private Label label_selectRoles = new Label("please select at least one checkbox");

	
	// Jake's code
	private Label label_ApplicationTitle = new Label("Admin User Control");
	private Button btn_logOut = new Button("Log Out");
	private Button btn_menu = new Button("Menu");
	
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
    
    private Label label_atleastOneRole = new Label("please select at least one role to assign");
    private Label label_usernameForDeletion = new Label("enter username of associated user to delete");
    
    private Label label_onlySystemAdmin = new Label("Cannot delete user: They are the only administrator in the system");
    private Label label_onlyGroupAdmin = new Label("Cannot delete user: They are the only admin for one or more groups");
    
	
	public AdminAccountPageUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {
        // Setup for the application title at the top, centered
        setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 200, 
        		Pos.CENTER, 70, 10);
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
        
        btn_menu.setText("Menu");
        btn_menu.setLayoutX(10);
        btn_menu.setLayoutY(10);
        btn_menu.setMaxWidth(100);
        theRoot.getChildren().add(btn_menu);
            
        // Handle menu operations
        btn_menu.setOnAction(e -> {
        	try {
        	handleMenu(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        // set up label to inform admin that atleast one role has to be selected before inviting another user
        setupLabelUI(label_atleastOneRole, "Arial", 12, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 250, 80);
        label_atleastOneRole.setStyle("-fx-text-fill: red;"); // set color of label
        label_atleastOneRole.setVisible(false);
        label_atleastOneRole.setManaged(false);

        
        
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
        
     // Setup label to delete user through username
        setupLabelUI(label_usernameForDeletion, "Arial", 12, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 150, 255);
        label_usernameForDeletion.setStyle("-fx-text-fill: red;"); // set color of label
        label_usernameForDeletion.setVisible(false);
        label_usernameForDeletion.setManaged(false);

        
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
        
        
        setupLabelUI(label_usernameDoesNotExist, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 130, 442, "red");
        label_usernameDoesNotExist.setVisible(false);
        label_usernameDoesNotExist.setManaged(false);
        //label_selfAdmin
        setupLabelUI(label_selfAdmin, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 130, 442, "red");
        label_selfAdmin.setVisible(false);
        label_selfAdmin.setManaged(false);
        //label_selectRoles
        setupLabelUI(label_selectRoles, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 130, 442, "red");
        label_selectRoles.setVisible(false);
        label_selectRoles.setManaged(false);
        
        setupLabelUI(label_onlySystemAdmin, "Arial", 12, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 150, 255, "red");
        label_onlySystemAdmin.setVisible(false);
        label_onlySystemAdmin.setManaged(false);
        
        setupLabelUI(label_onlyGroupAdmin, "Arial", 12, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 150, 255, "red");
        label_onlyGroupAdmin.setVisible(false);
        label_onlyGroupAdmin.setManaged(false);
        
        theRoot.getChildren().addAll(label_ApplicationTitle ,label_InviteUserEmail, label_InviteUser, text_emailInvite, 
        		label_ResetAccount, text_emailReset, label_ResetUserEmail,label_DeleteAccount, label_DeleteUser, text_UserToDelete,
        		label_AddOrRemoveRoles, label_AddorUser, text_AddorUsername, label_list, label_usernameDoesNotExist, label_selfAdmin,
        		label_selectRoles,label_usernameForDeletion, label_atleastOneRole, label_onlySystemAdmin, label_onlyGroupAdmin);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleSendInvite(gp360EdDisc_GUIdriver driver) throws SQLException {
		//Send an invite to the user by making a pop up occur with random invite code
		boolean isAdmin = check_Admin.isSelected();
		boolean isStudent = check_student.isSelected();
		boolean isInstructor = check_Instructor.isSelected();
		String email = text_emailInvite.getText();
		
		if(!isAdmin && !isStudent && !isInstructor) { //FIXME generate error label for admin that tells admin to select atleast one role
			System.out.println("please select atleast one role");
			label_atleastOneRole.setVisible(true);
			label_atleastOneRole.setManaged(true);
		}
		else {
			String code = generateRandomString();
			
			boolean success = gp360EdDisc_GUIdriver.getDBHelper().inviteUser(code, isAdmin, isInstructor, isStudent);
			
			if (success) {
				check_Admin.setSelected(false);
		        check_student.setSelected(false);
		        check_Instructor.setSelected(false);
		        
		        text_emailInvite.setText("");
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Email Invite");
		        alert.setHeaderText(null);
		        alert.setContentText("This Message has been sent to " + email + "\nThis is a one time invite code to sign up for ed help: " + code);
		        alert.showAndWait();
		    } else {
		        // Handle the case where the password update failed
		        Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("");
		        alert.setContentText("Failed to generate one time one code\nPlease check the email and try again");
		        alert.showAndWait();
		    }
			label_atleastOneRole.setVisible(false);
			label_atleastOneRole.setManaged(false);
		}
		
	}
////////////////////NEW FUNCTION JAKE
	private void handleSendReset(gp360EdDisc_GUIdriver driver) {
	    String otp = generateRandomString(); // Use your existing method to generate the OTP
	    String email = text_emailReset.getText();
	    boolean success = gp360EdDisc_GUIdriver.getDBHelper().setPassword(otp, email);
	    
	    LocalDateTime expirationTime = LocalDateTime.now().plus(1, ChronoUnit.HOURS);
	    
	    // Format the expiration time for display
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedExpirationTime = expirationTime.format(formatter);


	    if (success) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Password Reset");
	        alert.setHeaderText(null);
	        alert.setContentText("This Message has been sent to " + email + "\nA one-time password has been generated and set\nThe password is " + otp + 
	        		"\nPlease login and reset your password before this password exipres on " + formattedExpirationTime);
	        alert.showAndWait();
	    } else {
	        // Handle the case where the password update failed
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Failed to reset the password for user with email: " + email);
	        alert.setContentText("Please check the email and try again.");
	        alert.showAndWait();
	    }
	}
	
	private void handleDeleteUser(gp360EdDisc_GUIdriver driver) throws SQLException {
	    if (text_UserToDelete.getText().isEmpty()) {
	        showError(label_usernameForDeletion);
	        return;
	    }

	    String username = text_UserToDelete.getText();

	    // Check if user exists
	    if (!gp360EdDisc_GUIdriver.getDBHelper().usernameExistsInDB(username)) {
	        showError(label_usernameDoesNotExist);
	        return;
	    }

	    // If user is an admin, perform additional checks
	    if (gp360EdDisc_GUIdriver.getDBHelper().isAdminForUsers(username)) {
	        // Check if they're the only system admin
	        if (gp360EdDisc_GUIdriver.getDBHelper().isOnlyAdminInSystem(username)) {
	            //ONLY ADMIN IN SYSTEM
	            showError(label_onlySystemAdmin);
	            return;
	        }

	        // Check if they're the only admin for any groups
	        String groupsAsOnlyAdmin = gp360EdDisc_GUIdriver.getDBHelper().getGroupsWithUserAsOnlyAdmin(username);
	        if (!groupsAsOnlyAdmin.isEmpty()) {
	            // ONLY ADMIN FOR GROUP
	        	label_onlyGroupAdmin.setText(label_onlyGroupAdmin.getText());
	            //label_onlyGroupAdmin.setWrapText(true);
	            showError(label_onlyGroupAdmin);
	            return;
	        }
	    }

	    // If we get here, it's safe to show the deletion confirmation dialog
	    deleteStage.setTitle("Are you sure?");
	    deleteStage.initModality(Modality.APPLICATION_MODAL);
	    deleteStage.setOnCloseRequest(WindowEvent::consume);

	    noConfirm.setOnAction(e -> {
	        deleteStage.close();
	        driver.loadAdminAccount();
	    });

	    yesConfirm.setOnAction(e -> {
	        boolean success = gp360EdDisc_GUIdriver.getDBHelper().deleteUser(username);
	        if (success) {
	            System.out.println("successful delete by admin");
	            deleteStage.close();
	            driver.loadAdminAccount();
	        } else {
	            System.out.println("unsuccessful delete by admin");
	        }
	    });

	    deletePopup.setAlignment(Pos.CENTER);
	    deleteStage.setScene(popupDeleteScene);
	    deleteStage.showAndWait();
	}
	
	private void handleLogOut(gp360EdDisc_GUIdriver driver) {
		gp360EdDisc_GUIdriver.USERNAME = "";
		driver.loadloginPage();
	}
	
	private void handleShowRoles(gp360EdDisc_GUIdriver driver) {
		//check if user is in table
		boolean showRole = false;
		String AddorUsernameStr = text_AddorUsername.getText();
		try {
			showRole = gp360EdDisc_GUIdriver.getDBHelper().usernameExistsInDB(AddorUsernameStr);
		}catch(SQLException se){
			se.printStackTrace();
		}
		if(showRole) {
			showStage.setTitle("Shown User");
			showStage.initModality(Modality.APPLICATION_MODAL); //this is important because it prevents the user from doing anything other than in the pop-up scene
			showBack.setOnAction(e -> {
				showStage.close();
				driver.loadAdminAccount();
			});
			try {
				//setupLabelUI(Label l, String font, double fontSize, double width, Pos alignment, double x, double y)
				showPopupLabel.setText(gp360EdDisc_GUIdriver.getDBHelper().getRolesForSet(AddorUsernameStr));
				setupLabelUI(showPopupLabel, "Arial", 16, 300 - 10, Pos.TOP_CENTER, 75, 40);
				showPopupLabel.setStyle("-fx-font-weight: bold");
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			showVBox.setAlignment(Pos.TOP_CENTER);
			showStage.setScene(showScene);
			showStage.showAndWait();
		}
		else {
			//add label that says they arent in DB
			System.out.print("Username not in database\n");
			label_usernameDoesNotExist.setVisible(true);
	        label_usernameDoesNotExist.setManaged(true);
		}
	}
	
	private void handleSetRoles(gp360EdDisc_GUIdriver driver) {
	    String AddorUsernameStr2 = text_AddorUsername.getText();
	    boolean text = false;
	    boolean admin = false;
	    boolean student = false;
	    boolean instructor = false;

	    try {
	        text = gp360EdDisc_GUIdriver.getDBHelper().usernameExistsInDB(AddorUsernameStr2);
	    } catch(SQLException se) {
	        se.printStackTrace();
	    }

	    if (!text) {
	        showError(label_usernameDoesNotExist);
	        return;
	    }

	    if (!check_AddorAdmin.isSelected() && !check_Addorstudent.isSelected() && !check_AddorInstructor.isSelected()) {
	        showError(label_selectRoles);
	        return;
	    }

	    admin = check_AddorAdmin.isSelected();
	    instructor = check_AddorInstructor.isSelected();
	    student = check_Addorstudent.isSelected();

	    try {
	        // If we're removing admin role (current admin but not selected in new roles)
	        if (gp360EdDisc_GUIdriver.getDBHelper().isAdminForUsers(AddorUsernameStr2) && !admin) {
	            if (AddorUsernameStr2.equals(gp360EdDisc_GUIdriver.USERNAME)) {
	                showError(label_selfAdmin);
	                return;
	            }

	            // Check if they're the only system admin
	            if (gp360EdDisc_GUIdriver.getDBHelper().isOnlyAdminInSystem(AddorUsernameStr2)) {
	                showError(label_onlySystemAdmin);
	                return;
	            }

	            // Check if they're the only admin for any groups
	            String groupsAsOnlyAdmin = gp360EdDisc_GUIdriver.getDBHelper().getGroupsWithUserAsOnlyAdmin(AddorUsernameStr2);
	            if (!groupsAsOnlyAdmin.isEmpty()) {
	                //label_onlyGroupAdmin.setWrapText(true);
	                showError(label_onlyGroupAdmin);
	                return;
	            }

	            // If we're removing admin role and it's safe to do so, update the groups table
	            gp360EdDisc_GUIdriver.getDBHelper().removeAdminFromGroups(AddorUsernameStr2);
	        }

	        // All checks passed, proceed with role update
	        gp360EdDisc_GUIdriver.getDBHelper().adminRoleSet(admin, instructor, student, AddorUsernameStr2);
	        hideAllErrors();
	        driver.loadAdminAccount();

	    } catch(SQLException se) {
	        se.printStackTrace();
	    }
	}
	
////////////////////NEW FUNCTION JAKE
	private void handleListUsers(gp360EdDisc_GUIdriver driver) {
	    // Fetch the list of user accounts as a string
	    String userAccounts = gp360EdDisc_GUIdriver.getDBHelper().listUserAccounts();

	    // Create a new Stage (window)
	    Stage userListStage = new Stage();
	    userListStage.setTitle("User Accounts");

	    // Create a TextArea to display the user accounts
	    TextArea textArea = new TextArea(userAccounts);
	    textArea.setEditable(false); // Make it read-only
	    textArea.setWrapText(true); // Wrap text if too long

	    // Set the size of the TextArea
	    textArea.setPrefSize(500, 400);

	    // Create a scene and add the TextArea to it
	    Scene scene = new Scene(new StackPane(textArea), 500, 400);

	    // Set the scene to the Stage
	    userListStage.setScene(scene);

	    // Make sure this window doesn't block interaction with the main window
	    userListStage.initModality(Modality.NONE); 

	    // Show the new window
	    userListStage.show();
	}
	
	private void handleMenu(gp360EdDisc_GUIdriver driver) throws SQLException { 
		driver.showMenuPopUp(driver.CURRENT_SESSION);
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
    
    private void setupLabelUI(Label l, String font, double fontSize, double width, Pos alignment, double x, double y, String color) {
        l.setFont(Font.font(font, fontSize));
        l.setMinWidth(width);
        l.setAlignment(alignment);
        l.setLayoutX(x);
        l.setLayoutY(y);
        l.setStyle("-fx-text-fill: " + color + ";"); // set color of label
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
    
    private void hideAllErrors() {
        label_usernameDoesNotExist.setVisible(false);
        label_usernameDoesNotExist.setManaged(false);
        label_selfAdmin.setVisible(false);
        label_selfAdmin.setManaged(false);
        label_selectRoles.setVisible(false);
        label_selectRoles.setManaged(false);
        label_usernameForDeletion.setVisible(false);
        label_usernameForDeletion.setManaged(false);
        label_onlySystemAdmin.setVisible(false);
        label_onlySystemAdmin.setManaged(false);
        label_onlyGroupAdmin.setVisible(false);
        label_onlyGroupAdmin.setManaged(false);
        // Hide any other error labels you have
    }
    
    private void showError(Label errorToShow) {
        // Hide all existing errors first
        hideAllErrors();
        
        // Show the new error
        errorToShow.setVisible(true);
        errorToShow.setManaged(true);
    }
    
    
}