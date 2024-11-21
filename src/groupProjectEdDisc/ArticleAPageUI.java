package groupProjectEdDisc;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;


public class ArticleAPageUI { 
	private Label label_ApplicationTitle = new Label("Article Manager");
	private Button btn_logOut = new Button("Log Out");
	private Button btn_menu = new Button("menu");
	
	private Label label_createArticle = new Label("Create Article");
	private Button btn_createArticle = new Button("Create");
	
	private Label label_updateArticle = new Label("View or Update or Delete Article");
	private Label label_articleID = new Label("Article ID");
	private TextField text_ID = new TextField();
	private Button btn_updateArticle = new Button("update");
	private Button btn_viewArticle = new Button("view");
	
	private Button btn_deleteArticle = new Button("delete");
	private Label errorId = new Label("Please enter a valid Article ID");
	private Label label_enterIDToDelete = new Label("Please enter ID of article to delete");
	private Label label_idInvalid = new Label("Article with given ID does not exist");
	
	private Label label_listArticles = new Label("List Articles");
	private CheckBox check_All = new CheckBox("All");
	private TextField text_groups = new TextField();
	private Label label_groups = new Label("Enter groups (comma-separated):");
    private Button btn_listArticles = new Button("Display List");
	
    private Label label_BackupandRestoreArticles = new Label("Backup and restore Articles");
    private Label label_fileName = new Label("File Name (.csv)");
    private TextField text_filename = new TextField();
    private Button btn_backupToFile = new Button("Backup to File");
	private Button btn_RestoreToBackup = new Button("Restore to Backup");
	private Button btn_MergeFromBackup = new Button("Merge from Backup");
	private Label label_noFile = new Label("Please enter a filename");
	
	private void configureButtonsForAdmin() {
	    try {
	        // Check if current user is logged in as admin session
	        if (gp360EdDisc_GUIdriver.CURRENT_SESSION.equals("ADMIN")) {
	            // Disable all article interaction buttons
	            btn_viewArticle.setDisable(true);
	            btn_updateArticle.setDisable(true);
	            btn_listArticles.setDisable(true);
	            
	            // Optional: Add tooltip to explain why buttons are disabled
	            btn_viewArticle.setTooltip(new Tooltip("Administrators cannot view articles"));
	            btn_updateArticle.setTooltip(new Tooltip("Administrators cannot modify articles"));
	            btn_listArticles.setTooltip(new Tooltip("Administrators cannot list articles"));
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	
	public ArticleAPageUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {
		// Setup for the application title at the top, centered
	    setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 200, 
	    		Pos.CENTER, 90, 10);
	    label_ApplicationTitle.setStyle("-fx-font-weight: bold");
	    
	    btn_logOut.setText("Log Out");
        btn_logOut.setLayoutX(430);
        btn_logOut.setLayoutY(10);
        btn_logOut.setMaxWidth(100);
        theRoot.getChildren().add(btn_logOut);
            
        // 
        btn_logOut.setOnAction(e -> {
        	handleLogOut(driver);
        });
        
        btn_menu.setText("Menu");
        btn_menu.setLayoutX(10);
        btn_menu.setLayoutY(10);
        btn_menu.setMaxWidth(100);
        theRoot.getChildren().add(btn_menu);
            
        // Handle send invite
        btn_menu.setOnAction(e -> {
        	try {
        	handleMenu(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        //
        setupLabelUI(label_createArticle, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 40);
        label_createArticle.setStyle("-fx-font-weight: bold");
        
        btn_createArticle.setText("Create");
        btn_createArticle.setLayoutX(20);
        btn_createArticle.setLayoutY(65);
        theRoot.getChildren().add(btn_createArticle);
            
        // 
        btn_createArticle.setOnAction(e -> {
        	try {
        	handleCreateArticle(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        
        //
        setupLabelUI(label_updateArticle, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 100);
        label_updateArticle.setStyle("-fx-font-weight: bold");
        
        setupLabelUI(label_articleID, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, 120);
        
        setupTextUI(text_ID, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 430,
                Pos.BASELINE_LEFT, 20, 140, true);
        text_ID.maxHeight(20);
        
        btn_viewArticle.setText("View");
        btn_viewArticle.setLayoutX(120);
        btn_viewArticle.setLayoutY(144);
        theRoot.getChildren().add(btn_viewArticle);
            
      //error message for id
        setupLabelUI(errorId, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 200, 80, "red");
        errorId.setVisible(false);
        errorId.setManaged(false);
        theRoot.getChildren().add(errorId);
        // 
        btn_viewArticle.setOnAction(e -> {												//TODO check for article
            //Sam's code
            	//TODO add functionality to make sure the id is in the table and then 
            	
            	boolean update2 = false;
            	if(text_ID.getText().isEmpty()) {
            		errorId.setVisible(true);
            		errorId.setManaged(true);
            	}else {
            		try {
            			errorId.setVisible(false);
            			errorId.setManaged(false);
            			long id = Long.parseLong(text_ID.getText());
            			update2 =  gp360EdDisc_GUIdriver.getDBHelper().articleIdExists(id);
            			if(update2) {
            				handleViewArticle(driver, id);
            			}
            			else {
            				//setupLabelUI(Label l, String font, double fontSize, double width, Pos alignment, double x, double y, String color)
            				errorId.setVisible(true);
            				errorId.setManaged(true);
            			}
                    	
                    	} catch (SQLException ex) {
                            ex.printStackTrace();
                        }
            		
            	}
            	
            });
        //Sam's code
        btn_updateArticle.setText("Update");
        btn_updateArticle.setLayoutX(180);
        btn_updateArticle.setLayoutY(144);
        theRoot.getChildren().add(btn_updateArticle);
        

        btn_updateArticle.setOnAction(e -> {												//TODO check for article
        //Sam's code
        	//TODO add functionality to make sure the id is in the table and then 
        	
        	boolean update2 = false;
        	if(text_ID.getText().isEmpty()) {
        		errorId.setVisible(true);
        		errorId.setManaged(true);
        	}else {
        		try {
        			errorId.setVisible(false);
        			errorId.setManaged(false);
        			long id = Long.parseLong(text_ID.getText());
        			update2 =  gp360EdDisc_GUIdriver.getDBHelper().articleIdExists(id);
        			if(update2) {
        				handleUpdateArticle(driver, id);
        			}
        			else {
        				//setupLabelUI(Label l, String font, double fontSize, double width, Pos alignment, double x, double y, String color)
        				errorId.setVisible(true);
        				errorId.setManaged(true);
        			}
                	
                	} catch (SQLException ex) {
                        ex.printStackTrace();
                    }
        		
        	}
        	
        });
        
        btn_deleteArticle.setText("Delete");
        btn_deleteArticle.setLayoutX(255);
        btn_deleteArticle.setLayoutY(144);
        theRoot.getChildren().add(btn_deleteArticle);
            
        // 
        btn_deleteArticle.setOnAction(e -> {
        	try {
        	handleDeleteArticle(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        
        setupLabelUI(label_listArticles, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 180);
        label_listArticles.setStyle("-fx-font-weight: bold");
        
        check_All.setText("All Articles");
        check_All.setLayoutX(20);
        check_All.setLayoutY(210);
        theRoot.getChildren().add(check_All);
        
        //Setup for groups fields when listing articles
        VBox groupInputBox = new VBox(5, label_groups, text_groups);
        groupInputBox.setLayoutX(110);
        groupInputBox.setLayoutY(210);
        theRoot.getChildren().add(groupInputBox);
        
        btn_listArticles.setText("Display List");
        btn_listArticles.setLayoutX(20);
        btn_listArticles.setLayoutY(235);
        theRoot.getChildren().add(btn_listArticles);
            
        // 
        btn_listArticles.setOnAction(e -> {
        	try {
        	handleListArticles(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        configureButtonsForAdmin();
        
        
        setupLabelUI(label_BackupandRestoreArticles, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 270);
        label_BackupandRestoreArticles.setStyle("-fx-font-weight: bold");
        
        setupLabelUI(label_fileName, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, 290);
        
        setupTextUI(text_filename, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 350,
                Pos.BASELINE_LEFT, 20, 310, true);
        text_ID.maxHeight(20);
        
        setupLabelUI(label_noFile, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 20, 355, "red");
        label_noFile.setVisible(false);
        label_noFile.setManaged(false);
        
        btn_backupToFile.setText("Backup");
        btn_backupToFile.setLayoutX(180);
        btn_backupToFile.setLayoutY(312);
        theRoot.getChildren().add(btn_backupToFile);
            
        // 
        btn_backupToFile.setOnAction(e -> {
        	try {
        		handleBackupToFile(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        btn_RestoreToBackup.setText("Restore");
        btn_RestoreToBackup.setLayoutX(250);
        btn_RestoreToBackup.setLayoutY(312);
        theRoot.getChildren().add(btn_RestoreToBackup);
            
        // 
        btn_RestoreToBackup.setOnAction(e -> {
        	try {
        		handleRestoreToBackup(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        btn_MergeFromBackup.setText("Merge");
        btn_MergeFromBackup.setLayoutX(320);
        btn_MergeFromBackup.setLayoutY(312);
        theRoot.getChildren().add(btn_MergeFromBackup);
            
        // 
        btn_MergeFromBackup.setOnAction(e -> {
        	try {
        	handleMergeFromBackup(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        setupLabelUI(label_enterIDToDelete, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 20, 355, "red");
        label_enterIDToDelete.setManaged(false);
        label_enterIDToDelete.setVisible(false);
        
        setupLabelUI(label_idInvalid, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 20, 355, "red");
        label_idInvalid.setManaged(false);
        label_idInvalid.setVisible(false);
        
        
        theRoot.getChildren().addAll(label_ApplicationTitle ,label_createArticle, label_updateArticle, label_articleID, text_ID, label_listArticles, 
        		label_BackupandRestoreArticles, label_fileName, text_filename, label_noFile, label_enterIDToDelete, label_idInvalid);
        
	    
	}
	
	

	private void handleLogOut(gp360EdDisc_GUIdriver driver) {
		gp360EdDisc_GUIdriver.USERNAME = "";
		driver.loadloginPage();
	}
	
	private void handleCreateArticle(gp360EdDisc_GUIdriver driver) throws SQLException {
	    Stage createArticleStage = new Stage();
	    createArticleStage.setTitle("Create New Article");
	    createArticleStage.initModality(Modality.APPLICATION_MODAL);

	    Label label_error = new Label("Please fill in all fields");
	    label_error.setStyle("-fx-text-fill: " + "red" + ";");
	    label_error.setManaged(false);
	    label_error.setVisible(false);

	    Label label_groupError = new Label("One or more groups do not exist");
	    label_groupError.setStyle("-fx-text-fill: " + "red" + ";");
	    label_groupError.setManaged(false);
	    label_groupError.setVisible(false);

	    //Radio buttons for level
	    Label levelLabel = new Label("Select Level:");
	    ToggleGroup levelGroup = new ToggleGroup();
	    RadioButton beginner = new RadioButton("Beginner");
	    RadioButton intermediate = new RadioButton("Intermediate");
	    RadioButton advanced = new RadioButton("Advanced");
	    RadioButton expert = new RadioButton("Expert");

	    beginner.setToggleGroup(levelGroup);
	    intermediate.setToggleGroup(levelGroup);
	    advanced.setToggleGroup(levelGroup);
	    expert.setToggleGroup(levelGroup);

	    HBox levelBox = new HBox(10, beginner, intermediate, advanced, expert);
	    levelBox.setAlignment(Pos.CENTER_LEFT);

	    //Groups input
	    TextField groupsText = new TextField();
	    Label groupLabel = new Label("Group: (Separated by ; )");
	    VBox groupBox = new VBox(5, groupLabel, groupsText);

	    //Permissions Checkboxes
	    Label permissionsLabel = new Label("Permissions: (Who can view this article)");
	    CheckBox studentPermission = new CheckBox("Student");
	    CheckBox instructorPermission = new CheckBox("Instructor");
	    CheckBox adminPermission = new CheckBox("Admin");

	    HBox permissionsBox = new HBox(10, studentPermission, instructorPermission, adminPermission);
	    permissionsBox.setAlignment(Pos.CENTER_LEFT);

	    VBox fieldsBox = new VBox(10);
	    fieldsBox.setPadding(new Insets(10));

	    TextField text_title = new TextField();
	    Label label_title = new Label("Title:");
	    VBox titlebox = new VBox(5, label_title, text_title);

	    TextField text_descriptor = new TextField();
	    Label label_descriptor = new Label("Descriptor:");
	    VBox descbox = new VBox(5, label_descriptor, text_descriptor);

	    TextField text_keywords = new TextField();
	    Label label_keywords = new Label("Keywords:");
	    VBox keywordbox = new VBox(5, label_keywords, text_keywords);

	    TextArea bodyField = new TextArea();
	    bodyField.setPrefRowCount(8);

	    Label bodyLabel = new Label("Body:");
	    VBox bodyBox = new VBox(5, bodyLabel, bodyField);

	    TextField text_reference = new TextField();
	    Label label_reference = new Label("Reference:");
	    VBox referencebox = new VBox(5, label_reference, text_reference);

	    fieldsBox.getChildren().addAll(
	        titlebox, descbox, keywordbox, bodyBox, referencebox
	    );

	    Button createButton = new Button("Create Article");
	    createButton.setOnAction(e -> {
	        String level = "";
	        if (levelGroup.getSelectedToggle() != null) {
	            level = ((RadioButton) levelGroup.getSelectedToggle()).getText();
	        } else {
	            label_error.setText("Please select a level.");
	            label_error.setManaged(true);
	            label_error.setVisible(true);
	            return;
	        }

	        String groups = groupsText.getText().trim();
	        boolean student = studentPermission.isSelected();
	        boolean instructor = instructorPermission.isSelected();
	        boolean admin = adminPermission.isSelected();
	        String permissions = "";
	        if (student) {
	            permissions += "student;";
	        }
	        if (instructor) {
	            permissions += "instructor;";
	        }
	        if (admin) {
	            permissions += "admin;";
	        }
	        if (permissions.endsWith(";")) {
	            permissions = permissions.substring(0, permissions.length() - 1);
	        }

	        String title = text_title.getText();
	        String descriptor = text_descriptor.getText();
	        String keywords = text_keywords.getText();
	        String body = bodyField.getText();
	        String reference = text_reference.getText();

	        if (text_title.getText().isEmpty() || text_descriptor.getText().isEmpty() || 
	            text_keywords.getText().isEmpty() || bodyField.getText().isEmpty() || 
	            text_reference.getText().isEmpty() || levelGroup.getSelectedToggle() == null) {
	            label_error.setManaged(true);
	            label_error.setVisible(true);
	            label_groupError.setManaged(false);
	            label_groupError.setVisible(false);
	        } else {
	            try {
	                // Add new check for special access groups
	                if (!gp360EdDisc_GUIdriver.getDBHelper().canCreateArticleForGroups(groups, 
	                        gp360EdDisc_GUIdriver.USERNAME, gp360EdDisc_GUIdriver.CURRENT_SESSION)) {
	                    // Add a new label for this error if you haven't already
	                    label_error.setText("You don't have permission to create articles for one or more of the specified groups");
	                    label_error.setManaged(true);
	                    label_error.setVisible(true);
	                    return;
	                }

	                // If groups are not empty, verify they exist
	                if (!groups.isEmpty() && !gp360EdDisc_GUIdriver.getDBHelper().doGroupsExist(groups)) {
	                    label_groupError.setManaged(true);
	                    label_groupError.setVisible(true);
	                    label_error.setManaged(false);
	                    label_error.setVisible(false);
	                    return;
	                }

	                label_error.setManaged(false);
	                label_error.setVisible(false);
	                label_groupError.setManaged(false);
	                label_groupError.setVisible(false);
	                
	                gp360EdDisc_GUIdriver.getDBHelper().createArticle(level, groups, permissions, 
	                    title, descriptor, keywords, body, reference);
	                createArticleStage.close();
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    });

	    VBox mainLayout = new VBox(15,
	        levelLabel, levelBox,
	        groupLabel, groupBox,
	        permissionsLabel, permissionsBox,
	        fieldsBox, label_error, label_groupError, createButton
	    );
	    mainLayout.setAlignment(Pos.TOP_LEFT);
	    mainLayout.setPadding(new Insets(20));

	    Scene scene = new Scene(mainLayout, 500, 650);
	    createArticleStage.setScene(scene);
	    createArticleStage.showAndWait();
	}
	
	private void handleViewArticle(gp360EdDisc_GUIdriver driver, long id) throws SQLException {
	    try {
	        // First check if the user has permission to view this article
	        boolean canView = gp360EdDisc_GUIdriver.getDBHelper().canViewArticle(id, gp360EdDisc_GUIdriver.USERNAME);
	        
	        if (!canView) {
	            // Show error message - user doesn't have permission
	            errorId.setText("You don't have permission to view this article");
	            errorId.setVisible(true);
	            errorId.setManaged(true);
	            return;
	        }

	        // If we get here, user has permission to view the article
	        String level = gp360EdDisc_GUIdriver.getDBHelper().getLevel(id);
	        String title = gp360EdDisc_GUIdriver.getDBHelper().getTitle(id);
	        String descriptor = gp360EdDisc_GUIdriver.getDBHelper().getDescriptor(id);
	        String body = gp360EdDisc_GUIdriver.getDBHelper().getBody(id);
	        String keywords = gp360EdDisc_GUIdriver.getDBHelper().getKeywords(id);
	        String reference = gp360EdDisc_GUIdriver.getDBHelper().getReference(id);

	        // Create a new Stage
	        Stage viewArticleStage = new Stage();
	        viewArticleStage.setTitle("Article #" + id);

	        Label titleLabel = new Label(title);
	        titleLabel.setWrapText(true);
	        titleLabel.setFont(Font.font("Arial", 24));
	        titleLabel.setStyle("-fx-font-weight: bold");
	        
	        Label levelLabel = new Label("Level: " + level);
	        levelLabel.setWrapText(true);
	        levelLabel.setFont(Font.font("Arial", 18));
	        levelLabel.setStyle("-fx-font-weight: bold");
	        
	        Label descriptorLabel = new Label(descriptor);
	        descriptorLabel.setWrapText(true);
	        descriptorLabel.setFont(Font.font("Arial", 18));
	        
	        Label keywordsLabel = new Label("Keywords: " + keywords);
	        keywordsLabel.setWrapText(true);
	        keywordsLabel.setFont(Font.font("Arial", 18));
	        
	        Label bodyLabel = new Label(body);
	        bodyLabel.setWrapText(true);
	        bodyLabel.setFont(Font.font("Arial", 18));
	        
	        Label referenceLabel = new Label(reference);
	        referenceLabel.setWrapText(true);
	        referenceLabel.setFont(Font.font("Arial", 18));
	        referenceLabel.setStyle("-fx-font-style: italic");

	        viewArticleStage.initModality(Modality.NONE);

	        VBox mainLayout = new VBox(8, 
	            titleLabel, levelLabel, descriptorLabel, keywordsLabel, bodyLabel, referenceLabel
	        );
	        mainLayout.setAlignment(Pos.TOP_CENTER);
	        mainLayout.setPadding(new Insets(20));

	        Scene scene = new Scene(mainLayout, 600, 400);
	        viewArticleStage.setScene(scene);
	        viewArticleStage.showAndWait();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        errorId.setText("Error accessing article");
	        errorId.setVisible(true);
	        errorId.setManaged(true);
	    }
	}
	
	private void handleUpdateArticle(gp360EdDisc_GUIdriver driver, long id) throws SQLException {
	    Stage createArticleStage = new Stage();
	    createArticleStage.setTitle("Update Article");
	    createArticleStage.initModality(Modality.APPLICATION_MODAL);

	    Label label_error = new Label("Please fill in all fields");
	    label_error.setStyle("-fx-text-fill: " + "red" + ";");
	    label_error.setManaged(false);
	    label_error.setVisible(false);

	    Label label_groupError = new Label("One or more groups do not exist");
	    label_groupError.setStyle("-fx-text-fill: " + "red" + ";");
	    label_groupError.setManaged(false);
	    label_groupError.setVisible(false);

	    // Get current article values
	    String level2 = gp360EdDisc_GUIdriver.getDBHelper().getLevel(id);
	    String groups2 = gp360EdDisc_GUIdriver.getDBHelper().getGroups(id);
	    String title2 = gp360EdDisc_GUIdriver.getDBHelper().getTitle(id);
	    String permissions2 = gp360EdDisc_GUIdriver.getDBHelper().getPermissions(id);
	    String descriptor2 = gp360EdDisc_GUIdriver.getDBHelper().getDescriptor(id);
	    String keywords2 = gp360EdDisc_GUIdriver.getDBHelper().getKeywords(id);
	    String body2 = gp360EdDisc_GUIdriver.getDBHelper().getBody(id);
	    String reference2 = gp360EdDisc_GUIdriver.getDBHelper().getReference(id);

	    //Radio buttons for level
	    Label levelLabel = new Label("Select Level:");
	    ToggleGroup levelGroup = new ToggleGroup();
	    RadioButton beginner = new RadioButton("Beginner");
	    RadioButton intermediate = new RadioButton("Intermediate");
	    RadioButton advanced = new RadioButton("Advanced");
	    RadioButton expert = new RadioButton("Expert");

	    beginner.setToggleGroup(levelGroup);
	    intermediate.setToggleGroup(levelGroup);
	    advanced.setToggleGroup(levelGroup);
	    expert.setToggleGroup(levelGroup);

	    // Set current level
	    if (level2.equals("Expert")) {
	        levelGroup.selectToggle(expert);
	    } else if (level2.equals("Beginner")) {
	        levelGroup.selectToggle(beginner);
	    } else if (level2.equals("Intermediate")) {
	        levelGroup.selectToggle(intermediate);
	    } else if (level2.equals("Advanced")) {
	        levelGroup.selectToggle(advanced);
	    }

	    HBox levelBox = new HBox(10, beginner, intermediate, advanced, expert);
	    levelBox.setAlignment(Pos.CENTER_LEFT);

	    //Groups input
	    TextField groupsText = new TextField();
	    Label groupLabel = new Label("Group: (Separated by ; )");
	    VBox groupBox = new VBox(5, groupLabel, groupsText);
	    groupsText.setText(groups2);

	    //Permissions Checkboxes
	    Label permissionsLabel = new Label("Permissions: (Who can view this article)");
	    CheckBox studentPermission = new CheckBox("Student");
	    CheckBox instructorPermission = new CheckBox("Instructor");
	    CheckBox adminPermission = new CheckBox("Admin");

	    // Set current permissions
	    String checkStudent = "student";
	    String checkInstructor = "instructor";
	    String checkAdmin = "admin";
	    if (permissions2.contains(checkStudent)) {
	        studentPermission.setSelected(true);
	    }
	    if (permissions2.contains(checkInstructor)) {
	        instructorPermission.setSelected(true);
	    }
	    if (permissions2.contains(checkAdmin)) {
	        adminPermission.setSelected(true);
	    }

	    HBox permissionsBox = new HBox(10, studentPermission, instructorPermission, adminPermission);
	    permissionsBox.setAlignment(Pos.CENTER_LEFT);

	    VBox fieldsBox = new VBox(10);
	    fieldsBox.setPadding(new Insets(10));

	    TextField text_title = new TextField();
	    Label label_title = new Label("Title:");
	    VBox titlebox = new VBox(5, label_title, text_title);
	    text_title.setText(title2);

	    TextField text_descriptor = new TextField();
	    Label label_descriptor = new Label("Descriptor:");
	    VBox descbox = new VBox(5, label_descriptor, text_descriptor);
	    text_descriptor.setText(descriptor2);

	    TextField text_keywords = new TextField();
	    Label label_keywords = new Label("Keywords:");
	    VBox keywordbox = new VBox(5, label_keywords, text_keywords);
	    text_keywords.setText(keywords2);

	    TextArea bodyField = new TextArea();
	    bodyField.setPrefRowCount(8);
	    Label bodyLabel = new Label("Body:");
	    VBox bodyBox = new VBox(5, bodyLabel, bodyField);
	    bodyField.setText(body2);

	    TextField text_reference = new TextField();
	    Label label_reference = new Label("Reference:");
	    VBox referencebox = new VBox(5, label_reference, text_reference);
	    text_reference.setText(reference2);

	    fieldsBox.getChildren().addAll(
	        titlebox, descbox, keywordbox, bodyBox, referencebox
	    );

	    Button revertButton = new Button("Revert Changes");
	    revertButton.setOnAction(e -> {
	        createArticleStage.close();
	    });

	    Button updateButton = new Button("Save Changes");
	    updateButton.setOnAction(e -> {
	        String level = "";
	        if (levelGroup.getSelectedToggle() != null) {
	            level = ((RadioButton) levelGroup.getSelectedToggle()).getText();
	        } else {
	            label_error.setText("Please select a level.");
	            label_error.setManaged(true);
	            label_error.setVisible(true);
	            return;
	        }

	        String groups = groupsText.getText().trim();
	        boolean student = studentPermission.isSelected();
	        boolean instructor = instructorPermission.isSelected();
	        boolean admin = adminPermission.isSelected();
	        String permissions = "";
	        if (student) {
	            permissions += "student;";
	        }
	        if (instructor) {
	            permissions += "instructor;";
	        }
	        if (admin) {
	            permissions += "admin;";
	        }
	        if (permissions.endsWith(";")) {
	            permissions = permissions.substring(0, permissions.length() - 1);
	        }

	        String title = text_title.getText();
	        String descriptor = text_descriptor.getText();
	        String keywords = text_keywords.getText();
	        String body = bodyField.getText();
	        String reference = text_reference.getText();

	        if (text_title.getText().isEmpty() || text_descriptor.getText().isEmpty() || 
	            text_keywords.getText().isEmpty() || bodyField.getText().isEmpty() || 
	            text_reference.getText().isEmpty() || levelGroup.getSelectedToggle() == null) {
	            label_error.setManaged(true);
	            label_error.setVisible(true);
	            label_groupError.setManaged(false);
	            label_groupError.setVisible(false);
	        } else {
	            try {
	                // Add new check for special access groups
	                if (!gp360EdDisc_GUIdriver.getDBHelper().canUpdateArticleForGroups(groups, 
	                        gp360EdDisc_GUIdriver.USERNAME)) {
	                    // Add a new label for this error if you haven't already
	                    label_error.setText("You don't have permission to update articles for one or more of the specified groups");
	                    label_error.setManaged(true);
	                    label_error.setVisible(true);
	                    return;
	                }

	                // If groups are not empty, verify they exist
	                if (!groups.isEmpty() && !gp360EdDisc_GUIdriver.getDBHelper().doGroupsExist(groups)) {
	                    label_groupError.setManaged(true);
	                    label_groupError.setVisible(true);
	                    label_error.setManaged(false);
	                    label_error.setVisible(false);
	                    return;
	                }

	                label_error.setManaged(false);
	                label_error.setVisible(false);
	                label_groupError.setManaged(false);
	                label_groupError.setVisible(false);

	                gp360EdDisc_GUIdriver.getDBHelper().updateArticle(id, level, groups, permissions, 
	                    title, descriptor, keywords, body, reference);
	                createArticleStage.close();
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    });

	    HBox buttonBox = new HBox(10, updateButton, revertButton);
	    buttonBox.setAlignment(Pos.CENTER);

	    VBox mainLayout = new VBox(15,
	        levelLabel, levelBox,
	        groupLabel, groupBox,
	        permissionsLabel, permissionsBox,
	        fieldsBox, label_error, label_groupError, buttonBox
	    );
	    mainLayout.setAlignment(Pos.TOP_LEFT);
	    mainLayout.setPadding(new Insets(20));

	    Scene scene = new Scene(mainLayout, 500, 650);
	    createArticleStage.setScene(scene);
	    createArticleStage.showAndWait();
	}
	
	private void handleDeleteArticle(gp360EdDisc_GUIdriver driver) throws SQLException {
	    Stage deleteArticleStage = new Stage();
	    deleteArticleStage.setTitle("Are you sure?");
	    deleteArticleStage.initModality(Modality.APPLICATION_MODAL);

	    // confirmation label
	    Label label_deleteArticle = new Label("Are you sure you want to delete this article? This action cannot be undone.");

	    // confirmation buttons
	    Button btn_no = new Button("No");
	    Button btn_yes = new Button("Yes");

	    HBox buttonBox = new HBox(10, btn_no, btn_yes);

	    VBox deleteArticleBox = new VBox(20, label_deleteArticle, buttonBox);

	    if(text_ID.getText().isEmpty()) {
	        label_enterIDToDelete.setVisible(true);
	        label_enterIDToDelete.setManaged(true);
	        label_idInvalid.setVisible(false);
	        label_idInvalid.setManaged(false);
	        return;
	    }
	    else {
	        int articleID = Integer.parseInt(text_ID.getText());
	        if(!driver.getDBHelper().idExistsInDatabase(articleID)) {
	            label_enterIDToDelete.setVisible(false);
	            label_enterIDToDelete.setManaged(false);
	            label_idInvalid.setVisible(true);
	            label_idInvalid.setManaged(true);
	            return;
	        }
	        else {
	            // Add check for special access groups
	            if (!driver.getDBHelper().canDeleteArticleWithID(articleID, 
	                    gp360EdDisc_GUIdriver.USERNAME, 
	                    gp360EdDisc_GUIdriver.CURRENT_SESSION)) {
	                label_enterIDToDelete.setVisible(false);
	                label_enterIDToDelete.setManaged(false);
	                label_idInvalid.setText("You don't have permission to delete this article");
	                label_idInvalid.setVisible(true);
	                label_idInvalid.setManaged(true);
	                return;
	            }

	            label_enterIDToDelete.setVisible(false);
	            label_enterIDToDelete.setManaged(false);
	            label_idInvalid.setVisible(false);
	            label_idInvalid.setManaged(false);

	            btn_no.setOnAction(e -> {
	                deleteArticleStage.close();
	                text_ID.setText(""); // set field to empty string because deletion did not go through
	            });
	            btn_yes.setOnAction(e -> {
	                boolean success = driver.getDBHelper().deleteArticleWithID(articleID);

	                if(success) {
	                    System.out.println("deleted article with given id: " + articleID);
	                }
	                else {
	                    System.out.println("deletion of article did not go through.");
	                }
	                deleteArticleStage.close();
	                text_ID.setText(""); // set field to empty string after deletion process
	            });

	            deleteArticleBox.setAlignment(Pos.CENTER);
	            deleteArticleBox.setPadding(new Insets(10));

	            buttonBox.setAlignment(Pos.CENTER);
	            buttonBox.setPadding(new Insets(10));

	            // create scene for stage
	            Scene scene = new Scene(deleteArticleBox);
	            // set scene on stage
	            deleteArticleStage.setScene(scene);
	            deleteArticleStage.setMinHeight(250);
	            deleteArticleStage.setMinWidth(150);

	            deleteArticleStage.showAndWait();
	        }
	    }
	}
	
	private void handleListArticles(gp360EdDisc_GUIdriver driver) throws SQLException {
	    // Create a new stage for the popup
	    Stage listArticlesStage = new Stage();
	    listArticlesStage.setTitle("Article List");
	    listArticlesStage.initModality(Modality.APPLICATION_MODAL);
	    
	    // Get checkbox state for "All Articles"
	    boolean allSelected = check_All.isSelected();
	    
	    // Get groups from the text field and split by semicolon
	    String groupsInput = text_groups.getText().trim();
	    String[] selectedGroups = groupsInput.isEmpty() ? new String[0] : 
	                            groupsInput.split(";");
	    
	    // Check permissions if groups are specified and "All" is not selected
	    if (!allSelected && selectedGroups.length > 0) {
	        if (!gp360EdDisc_GUIdriver.getDBHelper().canListArticlesForGroups(groupsInput, 
	                gp360EdDisc_GUIdriver.USERNAME)) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Access Denied");
	            alert.setHeaderText(null);
	            alert.setContentText("You don't have permission to list articles for one or more of the specified groups.");
	            alert.showAndWait();
	            return;
	        }
	    }
	    
	    // Generate articles list
	    String articlesList = gp360EdDisc_GUIdriver.getDBHelper().listArticles(selectedGroups, allSelected);
	    
	    // Scrollable text area to display articlesList
	    TextArea articlesDisplay = new TextArea(articlesList);
	    articlesDisplay.setEditable(false);
	    articlesDisplay.setWrapText(true);
	    articlesDisplay.setPrefRowCount(20);
	    articlesDisplay.setPrefWidth(600);
	    articlesDisplay.setPrefHeight(400);
	    
	    // Button to close out of popup
	    Button closeButton = new Button("Close");
	    closeButton.setOnAction(e -> listArticlesStage.close());
	    
	    VBox mainLayout = new VBox(10);
	    mainLayout.setPadding(new Insets(10));
	    mainLayout.getChildren().addAll(articlesDisplay, closeButton);
	    mainLayout.setAlignment(Pos.CENTER);
	    
	    Scene scene = new Scene(mainLayout);
	    listArticlesStage.setScene(scene);
	    
	    listArticlesStage.setMinWidth(650);
	    listArticlesStage.setMinHeight(500);
	    listArticlesStage.showAndWait();
	}
	
	private void handleBackupToFile(gp360EdDisc_GUIdriver driver) throws SQLException{
		if (text_filename.getText().equals("")) {
			label_noFile.setVisible(true);
	        label_noFile.setManaged(true);
		}
		else {
			try {
				label_noFile.setVisible(false);
		        label_noFile.setManaged(false);
				String fileName = text_filename.getText();
				if (fileName == "") {
					//set error label visibility for no entry
					return;
				}
				gp360EdDisc_GUIdriver.getDBHelper().backupDatabase(fileName);
				text_filename.setText("");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Backup Successful");
				alert.setHeaderText(null);
				alert.setContentText("A backup of the article database has been made at: " + fileName);
		        alert.showAndWait();
			}
			catch (Exception e) {
				e.printStackTrace();
				
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Backup Failed");
		        alert.setHeaderText(null);
		        alert.setContentText("An error occurred while creating the backup.");
		        alert.showAndWait();
			}
		}	
	}
	
	private void handleRestoreToBackup(gp360EdDisc_GUIdriver driver) throws SQLException{
		if (text_filename.getText().equals("")) {
			label_noFile.setVisible(true);
	        label_noFile.setManaged(true);
		}
		else {
			try {
				label_noFile.setVisible(false);
		        label_noFile.setManaged(false);
				String fileName = text_filename.getText();
				
				Stage restoreStage = new Stage();
				restoreStage.setTitle("Are you sure?");
				restoreStage.initModality(Modality.APPLICATION_MODAL); //this is important because it prevents the user from doing anything other than in the pop-up scene
				restoreStage.setOnCloseRequest(WindowEvent::consume);
				
				Label restoreWarning = new Label("Are you sure you want to restore from backup?\nThe current article database will be erased.");
				Button noConfirm = new Button("No");
				Button yesConfirm = new Button("Yes");
				
				noConfirm.setOnAction(e -> {
					restoreStage.close();
					text_filename.setText("");
				});
						
				yesConfirm.setOnAction(e -> {
					try {
						gp360EdDisc_GUIdriver.getDBHelper().restoreDatabase(fileName);
						restoreStage.close();
						text_filename.setText("");
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				});
				
				VBox deletePopup = new VBox(10, restoreWarning, noConfirm, yesConfirm);
				deletePopup.setAlignment(Pos.CENTER);
				deletePopup.setPrefSize(350, 150);
				Scene popupRestoreScene = new Scene(deletePopup);
				
				restoreStage.setScene(popupRestoreScene);
				restoreStage.showAndWait();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	private void handleMergeFromBackup(gp360EdDisc_GUIdriver driver) throws SQLException{
		if (text_filename.getText().equals("")) {
			label_noFile.setVisible(true);
		    label_noFile.setManaged(true);
		}
		else {
			try {
				label_noFile.setVisible(false);
			    label_noFile.setManaged(false);
			    String fileName = text_filename.getText();
				Stage mergeStage = new Stage();
				mergeStage.setTitle("Are you sure?");
				mergeStage.initModality(Modality.APPLICATION_MODAL); //this is important because it prevents the user from doing anything other than in the pop-up scene
				mergeStage.setOnCloseRequest(WindowEvent::consume);			
					
				Label restoreWarning = new Label("Are you sure you want to merge from backup?\nThe articles in " + fileName + " will be added to the article database.");
				Button btn_noConfirm = new Button("No");
				Button btn_yesConfirm = new Button("Yes");
					
				btn_noConfirm.setOnAction(e -> {
					mergeStage.close();
					text_filename.setText("");
				});
							
				btn_yesConfirm.setOnAction(e -> {
					try {
						gp360EdDisc_GUIdriver.getDBHelper().mergeDatabase(fileName);
						mergeStage.close();
						text_filename.setText("");
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				});
					
				VBox mergePopup = new VBox(10, restoreWarning, btn_noConfirm, btn_yesConfirm);
				mergePopup.setAlignment(Pos.CENTER);
				mergePopup.setPrefSize(350, 150);
				Scene popupMergeScene = new Scene(mergePopup);
					
				mergeStage.setScene(popupMergeScene);
				mergeStage.showAndWait();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
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

}