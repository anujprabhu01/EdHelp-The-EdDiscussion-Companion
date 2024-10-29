package groupProjectEdDisc;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;


public class ArticleAPageUI { 
	private Label label_ApplicationTitle = new Label("Article Page");
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
	
	private Label label_listArticles = new Label("List Articles");
	private CheckBox check_All = new CheckBox("All");
    private CheckBox check_Eclipse = new CheckBox("Eclipse");
    private CheckBox check_IntelliJ = new CheckBox("IntelliJ");
    private Button btn_listArticles = new Button("Display List");
	
    private Label label_BackupandRestoreArticles = new Label("Backup and restore Articles");
    private Label label_fileName = new Label("File Name (.csv)");
    private TextField text_filename = new TextField();
    private Button btn_backupToFile = new Button("Backup to File");
	private Button btn_RestoreToBackup = new Button("Restore to Backup");
	private Button btn_MergeFromBackup = new Button("Merge from Backup");
	private Label label_noFile = new Label("Please enter a filename");
	
	
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
            
        // 
        btn_updateArticle.setOnAction(e -> {
        	try {
        	handleViewArticle(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        btn_updateArticle.setText("Update");
        btn_updateArticle.setLayoutX(180);
        btn_updateArticle.setLayoutY(144);
        theRoot.getChildren().add(btn_updateArticle);
            
        // 
        btn_updateArticle.setOnAction(e -> {
        	try {
        	handleUpdateArticle(driver);
        	} catch (SQLException ex) {
                ex.printStackTrace();
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
        
        //Setup for Admin check box when selecting roles for an invite
        check_Eclipse.setText("Eclipse Articles");
        check_Eclipse.setLayoutX(110);
        check_Eclipse.setLayoutY(210);
        theRoot.getChildren().add(check_Eclipse);
        
        //Setup for Instructor check box when selecting roles for an invite
        check_IntelliJ.setText("IntelliJ Articles");
        check_IntelliJ.setLayoutX(230);
        check_IntelliJ.setLayoutY(210);
        theRoot.getChildren().add(check_IntelliJ);
        
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
        
        
        theRoot.getChildren().addAll(label_ApplicationTitle ,label_createArticle, label_updateArticle, label_articleID, text_ID, label_listArticles, 
        		label_BackupandRestoreArticles, label_fileName, text_filename, label_noFile);
        
	    
	}
	
	

	private void handleLogOut(gp360EdDisc_GUIdriver driver) {
		gp360EdDisc_GUIdriver.USERNAME = "";
		driver.loadloginPage();
	}
	
	private void handleCreateArticle(gp360EdDisc_GUIdriver driver) throws SQLException{
		Stage createArticleStage = new Stage();  // New stage for the popup
	    createArticleStage.setTitle("Create New Article");
	    createArticleStage.initModality(Modality.APPLICATION_MODAL);  // Block main app interaction
	    
	    Label label_error = new Label("Please fill in all fields");
	    label_error.setStyle("-fx-text-fill: " + "red" + ";");
	    label_error.setManaged(false);
    	label_error.setVisible(false);

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

	    //Checkboxes for groups
	    Label groupLabel = new Label("Group:");
	    CheckBox eclipseGroup = new CheckBox("Eclipse Articles");
	    CheckBox intelliJGroup = new CheckBox("IntelliJ Articles");

	    HBox groupBox = new HBox(10, eclipseGroup, intelliJGroup);
	    groupBox.setAlignment(Pos.CENTER_LEFT);

	    //Permissions Checkboxes
	    Label permissionsLabel = new Label("Permissions: (Who can view this article)");
	    CheckBox studentPermission = new CheckBox("Student");
	    CheckBox instructorPermission = new CheckBox("Instructor");
	    CheckBox adminPermission = new CheckBox("Admin");

	    HBox permissionsBox = new HBox(10, studentPermission, instructorPermission, adminPermission);
	    permissionsBox.setAlignment(Pos.CENTER_LEFT);

	    VBox fieldsBox = new VBox(10);  // Vertical container for all text fields
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
	    bodyField.setPrefRowCount(8);  // Multiline input

	    Label bodyLabel = new Label("Body:");
	    VBox bodyBox = new VBox(5, bodyLabel, bodyField); 

	    TextField text_reference = new TextField();
	    Label label_reference = new Label("Reference:");
	    VBox referencebox = new VBox(5, label_reference, text_reference);

	    // Add all fields to the VBox
	    fieldsBox.getChildren().addAll(
	    		titlebox, descbox, keywordbox, bodyBox, referencebox
	    );

	    // === Create Button ===
	    Button createButton = new Button("Create Article");
	    createButton.setOnAction(e -> {
	    	String level = "";
	    	if (levelGroup.getSelectedToggle() != null) {
	    	    level = ((RadioButton) levelGroup.getSelectedToggle()).getText();
	    	} else {
	    	    label_error.setText("Please select a level.");  // Example: Set error message
	    	    label_error.setManaged(true);
	    	    label_error.setVisible(true);
	    	    return;  // Stop further processing if a level isn't selected
	    	}
	        boolean eclipseSelected = eclipseGroup.isSelected();
	        boolean intelliJSelected = intelliJGroup.isSelected();
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

	        if (text_title.getText().isEmpty() || text_descriptor.getText().isEmpty() || text_keywords.getText().isEmpty() || 
	        		bodyField.getText().isEmpty() || text_reference.getText().isEmpty() || levelGroup.getSelectedToggle() == null) {
	        	label_error.setManaged(true);
	        	label_error.setVisible(true);
	        	
	        } else {
	        	try {
	        		label_error.setManaged(false);
	            	label_error.setVisible(false);
		        	gp360EdDisc_GUIdriver.getDBHelper().createArticle(level, eclipseSelected, intelliJSelected, permissions, title, descriptor, keywords, body, reference);
	        	}
	        	catch (Exception ex) {
	        		ex.printStackTrace();
	        	}
	            createArticleStage.close();  // Close popup on success
	        }
	    });

	    VBox mainLayout = new VBox(15, 
	        levelLabel, levelBox, 
	        groupLabel, groupBox, 
	        permissionsLabel, permissionsBox, 
	        fieldsBox, label_error, createButton
	    );
	    mainLayout.setAlignment(Pos.TOP_LEFT);
	    mainLayout.setPadding(new Insets(20));

	    // === Set Scene and Show Popup ===
	    Scene scene = new Scene(mainLayout, 500, 650);
	    createArticleStage.setScene(scene);
	    createArticleStage.showAndWait();  // Wait for user input
	}
	
	private void handleViewArticle(gp360EdDisc_GUIdriver driver) throws SQLException{
		
	}
	
	private void handleUpdateArticle(gp360EdDisc_GUIdriver driver) throws SQLException{
		
	}
	
	private void handleDeleteArticle(gp360EdDisc_GUIdriver driver) throws SQLException{
		
	}
	
	private void handleListArticles(gp360EdDisc_GUIdriver driver) throws SQLException{
		
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
				label_noFile.setVisible(false);
		        label_noFile.setManaged(false);
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
		driver.showMenuPopUp();
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