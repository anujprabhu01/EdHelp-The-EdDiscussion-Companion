package groupProjectEdDisc;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class GroupManagerPageUI {
    private Label label_ApplicationTitle = new Label("Group Manager");
    private Button btn_logOut = new Button("Log Out");
    private Button btn_menu = new Button("Menu");

    // Create Group section
    private Label label_createGroup = new Label("Create Group");
    private Label label_specialAccess = new Label("Special Access");
    private CheckBox check_specialAccess = new CheckBox();
    private Label label_groupName = new Label("Group Name");
    private TextField text_createGroupName = new TextField();
    private Button btn_createGroup = new Button("Create");
    private VBox createGroupBox;

    // Delete Group section
    private Label label_deleteGroup = new Label("Delete Group");
    private Label label_deleteGroupName = new Label("Group Name");
    private TextField text_deleteGroupName = new TextField();
    private Button btn_deleteGroup = new Button("Delete");
    private VBox deleteGroupBox;

    // Add/Remove Student section
    private Label label_student = new Label("Add/Remove Student");
    private TextField text_studentName = new TextField();
    private Button btn_addStudent = new Button("Add");
    private Button btn_removeStudent = new Button("Remove");
    private VBox studentBox;

    // Add/Remove Instructor section
    private Label label_instructor = new Label("Add/Remove Instructor");
    private TextField text_instructorName = new TextField();
    private Button btn_addInstructor = new Button("Add");
    private Button btn_removeInstructor = new Button("Remove");
    private VBox instructorBox;

    // Add/Remove Admin section
    private Label label_admin = new Label("Add/Remove Admin");
    private TextField text_adminName = new TextField();
    private Button btn_addAdmin = new Button("Add");
    private Button btn_removeAdmin = new Button("Remove");
    private VBox adminBox;

    // List Access section
    private Label label_listAccess = new Label("List Access");
    private Label label_listGroupName = new Label("Group Name");
    private TextField text_listGroupName = new TextField();
    private Button btn_listStudents = new Button("List Students");
    private Button btn_listInstructors = new Button("List Instructors");
    private Button btn_listAdmins = new Button("List Admins");
    private VBox listAccessBox;

    public GroupManagerPageUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {
        // Setup top bar
        setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 200, 
            Pos.CENTER, 90, 10);
        label_ApplicationTitle.setStyle("-fx-font-weight: bold");

        btn_menu.setLayoutX(10);
        btn_menu.setLayoutY(10);
        btn_menu.setMaxWidth(100);

        btn_logOut.setLayoutX(390);
        btn_logOut.setLayoutY(10);
        btn_logOut.setMaxWidth(100);

        // Main container for all sections
        VBox mainContainer = new VBox(10); // 10 pixels spacing between sections
        mainContainer.setLayoutX(10);
        mainContainer.setLayoutY(50);
        mainContainer.setPrefWidth(480); // Leaving 10px margin on each side

        // Create Group section setup
        createGroupBox = new VBox(5);
        setupLabelUI(label_createGroup, "Arial", 14, 200, Pos.BASELINE_LEFT, 0, 0);
        
        HBox specialAccessBox = new HBox(10);
        specialAccessBox.getChildren().addAll(check_specialAccess, label_specialAccess);
        
        HBox createNameBox = new HBox(10);
        text_createGroupName.setPrefWidth(150);
        createNameBox.getChildren().addAll(label_groupName, text_createGroupName, btn_createGroup);
        
        createGroupBox.getChildren().addAll(label_createGroup, specialAccessBox, createNameBox);

        // Delete Group section setup
        deleteGroupBox = new VBox(5);
        setupLabelUI(label_deleteGroup, "Arial", 14, 200, Pos.BASELINE_LEFT, 0, 0);
        
        HBox deleteNameBox = new HBox(10);
        text_deleteGroupName.setPrefWidth(150);
        deleteNameBox.getChildren().addAll(label_deleteGroupName, text_deleteGroupName, btn_deleteGroup);
        
        deleteGroupBox.getChildren().addAll(label_deleteGroup, deleteNameBox);

        // Student section setup
        studentBox = new VBox(5);
        setupLabelUI(label_student, "Arial", 14, 200, Pos.BASELINE_LEFT, 0, 0);
        
        HBox studentControlBox = new HBox(10);
        text_studentName.setPrefWidth(150);
        studentControlBox.getChildren().addAll(text_studentName, btn_addStudent, btn_removeStudent);
        
        studentBox.getChildren().addAll(label_student, studentControlBox);

        // Instructor section setup
        instructorBox = new VBox(5);
        setupLabelUI(label_instructor, "Arial", 14, 200, Pos.BASELINE_LEFT, 0, 0);
        
        HBox instructorControlBox = new HBox(10);
        text_instructorName.setPrefWidth(150);
        instructorControlBox.getChildren().addAll(text_instructorName, btn_addInstructor, btn_removeInstructor);
        
        instructorBox.getChildren().addAll(label_instructor, instructorControlBox);

        // Admin section setup
        adminBox = new VBox(5);
        setupLabelUI(label_admin, "Arial", 14, 200, Pos.BASELINE_LEFT, 0, 0);
        
        HBox adminControlBox = new HBox(10);
        text_adminName.setPrefWidth(150);
        adminControlBox.getChildren().addAll(text_adminName, btn_addAdmin, btn_removeAdmin);
        
        adminBox.getChildren().addAll(label_admin, adminControlBox);

        // List Access section setup
        listAccessBox = new VBox(5);
        setupLabelUI(label_listAccess, "Arial", 14, 200, Pos.BASELINE_LEFT, 0, 0);
        
        HBox listNameBox = new HBox(10);
        text_listGroupName.setPrefWidth(150);
        listNameBox.getChildren().addAll(label_listGroupName, text_listGroupName);
        
        HBox listButtonsBox = new HBox(10);
        // Make buttons smaller
        btn_listStudents.setPrefWidth(100);
        btn_listInstructors.setPrefWidth(100);
        btn_listAdmins.setPrefWidth(100);
        listButtonsBox.getChildren().addAll(btn_listStudents, btn_listInstructors, btn_listAdmins);
        
        listAccessBox.getChildren().addAll(label_listAccess, listNameBox, listButtonsBox);

        // Add all sections to main container
        mainContainer.getChildren().addAll(
            createGroupBox, deleteGroupBox, studentBox,
            instructorBox, adminBox, listAccessBox
        );

        // Add event handlers
        btn_menu.setOnAction(e -> {
        	try {
        		// Handle menu click
            	handleMenu(driver);
        	}
        	catch(SQLException ex) {
        		ex.printStackTrace();
        	}
        });

        btn_logOut.setOnAction(e -> {
            handleLogOut(driver);
        });

        // Add all elements to the root pane
        theRoot.getChildren().addAll(
            label_ApplicationTitle, btn_menu, btn_logOut,
            mainContainer
        );
    }
    
    private void handleMenu(gp360EdDisc_GUIdriver driver) throws SQLException { 
		driver.showMenuPopUp(driver.CURRENT_SESSION);
	}

    private void handleLogOut(gp360EdDisc_GUIdriver driver) {
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