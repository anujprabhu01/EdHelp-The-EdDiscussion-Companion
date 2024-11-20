package groupProjectEdDisc;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class GroupManagerPageUI {
    private Label label_ApplicationTitle = new Label("Group Manager");
    private Button btn_logOut = new Button("Log Out");
    private Button btn_menu = new Button("Menu");

    // Active Group Section
    private Label label_activeGroup = new Label("Currently active group:");
    private CheckBox check_activeGroup = new CheckBox();
    private TextField text_groupNameDisplay = new TextField();

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
    private Button btn_listStudents = new Button("List Students");
    private Button btn_listInstructors = new Button("List Instructors");
    private Button btn_listAdmins = new Button("List Admins");
    private VBox listAccessBox;
    
    private Label label_groupNameEmpty = new Label("Please enter a group name");
    private Label label_groupExists = new Label("A group with this name already exists");
    private Label label_systemError = new Label("System error occurred. Please try again");

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
        VBox mainContainer = new VBox(10);
        mainContainer.setLayoutX(10);
        mainContainer.setLayoutY(50);
        mainContainer.setPrefWidth(480);

        // Active Group Section setup
        HBox activeGroupBox = new HBox(10);
        setupLabelUI(label_activeGroup, "Arial", 14, 200, Pos.BASELINE_LEFT, 0, 0);
        check_activeGroup.setSelected(false);
        activeGroupBox.getChildren().addAll(check_activeGroup, label_activeGroup, text_groupNameDisplay);

        // Create Group section setup
        createGroupBox = new VBox(5);
        setupLabelUI(label_createGroup, "Arial", 14, 200, Pos.BASELINE_LEFT, 0, 0);
        
        HBox specialAccessBox = new HBox(10);
        specialAccessBox.getChildren().addAll(check_specialAccess, label_specialAccess);
        
        HBox createNameBox = new HBox(10);
        text_createGroupName.setPrefWidth(150);
        createNameBox.getChildren().addAll(label_groupName, text_createGroupName, btn_createGroup);
        
        btn_createGroup.setOnAction(e -> handleCreateGroup());
        
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
        
        HBox listButtonsBox = new HBox(10);
        btn_listStudents.setPrefWidth(100);
        btn_listInstructors.setPrefWidth(100);
        btn_listAdmins.setPrefWidth(100);
        listButtonsBox.getChildren().addAll(btn_listStudents, btn_listInstructors, btn_listAdmins);
        
        listAccessBox.getChildren().addAll(label_listAccess, listButtonsBox);

        // Add all sections to main container
        mainContainer.getChildren().addAll(
            activeGroupBox, createGroupBox, deleteGroupBox, studentBox,
            instructorBox, adminBox, listAccessBox
        );

        // Set up initial state of controls and role-based access
        updateControlStates();
        configureRoleBasedAccess();

        // Add checkbox listener for active group
        check_activeGroup.selectedProperty().addListener((obs, oldVal, newVal) -> {
            updateControlStates();
            configureRoleBasedAccess();
        });

        // Add event handlers for buttons
        btn_menu.setOnAction(e -> {
            try {
                handleMenu(driver);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        });

        btn_logOut.setOnAction(e -> {
            handleLogOut(driver);
        });
        
        setupLabelUI(label_groupNameEmpty, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, createNameBox.getLayoutY() + 30, "red");
            label_groupNameEmpty.setVisible(false);
            label_groupNameEmpty.setManaged(false);

            setupLabelUI(label_groupExists, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, createNameBox.getLayoutY() + 30, "red");
            label_groupExists.setVisible(false);
            label_groupExists.setManaged(false);

            setupLabelUI(label_systemError, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, createNameBox.getLayoutY() + 30, "red");
            label_systemError.setVisible(false);
            label_systemError.setManaged(false);

        // Add all elements to the root pane
        theRoot.getChildren().addAll(
            label_ApplicationTitle, btn_menu, btn_logOut,
            mainContainer, label_groupNameEmpty, label_groupExists, label_systemError
        );
    }
    
    private void handleCreateGroup() {
        // Clear any existing errors
        hideAllErrors();

        // Get the group name from the text field
        String groupName = text_createGroupName.getText().trim();
        
        // Validate group name
        if (groupName.isEmpty()) {
            showError(label_groupNameEmpty);
            return;
        }

        // Get special access status
        boolean isSpecialAccess = check_specialAccess.isSelected();

        try {
            boolean success = gp360EdDisc_GUIdriver.getDBHelper().createGroup(groupName, isSpecialAccess);
            if (success) {
                // Clear the input fields
                text_createGroupName.clear();
                check_specialAccess.setSelected(false);
                hideAllErrors();
                
                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Group created successfully!");
                alert.showAndWait();
            } else {
                // Show error that group name already exists
            	text_createGroupName.clear();
                check_specialAccess.setSelected(false);
                showError(label_groupExists);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError(label_systemError);
        }
    }

    private void configureRoleBasedAccess() {
        String currentSession = gp360EdDisc_GUIdriver.CURRENT_SESSION;
        
        if (currentSession.equals("ADMIN")) {
            // Admins cannot create or delete groups
            createGroupBox.setDisable(true);
            createGroupBox.setOpacity(0.5);
            check_specialAccess.setDisable(true);
            text_createGroupName.setDisable(true);
            btn_createGroup.setDisable(true);
            btn_createGroup.setTooltip(new Tooltip("Administrators cannot create article groups"));

            deleteGroupBox.setDisable(true);
            deleteGroupBox.setOpacity(0.5);
            text_deleteGroupName.setDisable(true);
            btn_deleteGroup.setDisable(true);
            btn_deleteGroup.setTooltip(new Tooltip("Administrators cannot delete article groups"));

            // Admins can manage all access rights
            enableAccessManagement(true, true, true);
        } 
        else if (currentSession.equals("INSTRUCTOR")) {
            // Instructors can create and delete groups
            createGroupBox.setDisable(false);
            createGroupBox.setOpacity(1.0);
            deleteGroupBox.setDisable(false);
            deleteGroupBox.setOpacity(1.0);

            // Instructors can only manage student access
            enableAccessManagement(true, false, false);

            // Add tooltips for disabled actions
            btn_addInstructor.setTooltip(new Tooltip("Instructors cannot modify instructor access"));
            btn_removeInstructor.setTooltip(new Tooltip("Instructors cannot modify instructor access"));
            btn_addAdmin.setTooltip(new Tooltip("Instructors cannot modify admin access"));
            btn_removeAdmin.setTooltip(new Tooltip("Instructors cannot modify admin access"));
            btn_listInstructors.setTooltip(new Tooltip("Instructors cannot view instructor list"));
            btn_listAdmins.setTooltip(new Tooltip("Instructors cannot view admin list"));
        }
    }

    private void enableAccessManagement(boolean students, boolean instructors, boolean admins) {
        // Student access management
        text_studentName.setDisable(!students || !check_activeGroup.isSelected());
        btn_addStudent.setDisable(!students || !check_activeGroup.isSelected());
        btn_removeStudent.setDisable(!students || !check_activeGroup.isSelected());
        btn_listStudents.setDisable(!students || !check_activeGroup.isSelected());

        // Instructor access management
        text_instructorName.setDisable(!instructors || !check_activeGroup.isSelected());
        btn_addInstructor.setDisable(!instructors || !check_activeGroup.isSelected());
        btn_removeInstructor.setDisable(!instructors || !check_activeGroup.isSelected());
        btn_listInstructors.setDisable(!instructors || !check_activeGroup.isSelected());

        // Admin access management
        text_adminName.setDisable(!admins || !check_activeGroup.isSelected());
        btn_addAdmin.setDisable(!admins || !check_activeGroup.isSelected());
        btn_removeAdmin.setDisable(!admins || !check_activeGroup.isSelected());
        btn_listAdmins.setDisable(!admins || !check_activeGroup.isSelected());

        // Set opacity for visual feedback
        studentBox.setOpacity(students ? 1.0 : 0.5);
        instructorBox.setOpacity(instructors ? 1.0 : 0.5);
        adminBox.setOpacity(admins ? 1.0 : 0.5);
    }

    private void updateControlStates() {
        boolean isActiveGroup = check_activeGroup.isSelected();

        // Only update the Create Group section if not an admin
        if (!gp360EdDisc_GUIdriver.CURRENT_SESSION.equals("ADMIN")) {
            check_specialAccess.setDisable(isActiveGroup);
            text_createGroupName.setDisable(isActiveGroup);
            btn_createGroup.setDisable(isActiveGroup);
        }

        // Only update the Delete Group section if not an admin
        if (!gp360EdDisc_GUIdriver.CURRENT_SESSION.equals("ADMIN")) {
            text_deleteGroupName.setDisable(!isActiveGroup);
            btn_deleteGroup.setDisable(!isActiveGroup);
        }

        // Reapply role-based access controls
        configureRoleBasedAccess();
    }

    private void handleMenu(gp360EdDisc_GUIdriver driver) throws SQLException { 
        driver.showMenuPopUp(driver.CURRENT_SESSION);
    }

    private void handleLogOut(gp360EdDisc_GUIdriver driver) {
        gp360EdDisc_GUIdriver.USERNAME = "";
        driver.loadloginPage();
    }
    
    private void hideAllErrors() {
        label_groupNameEmpty.setVisible(false);
        label_groupNameEmpty.setManaged(false);
        label_groupExists.setVisible(false);
        label_groupExists.setManaged(false);
        label_systemError.setVisible(false);
        label_systemError.setManaged(false);
    }

    private void showError(Label errorToShow) {
        // Hide all existing errors first
        hideAllErrors();
        
        // Show the new error
        errorToShow.setVisible(true);
        errorToShow.setManaged(true);
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
        l.setStyle("-fx-text-fill: " + color + ";");
    }
}