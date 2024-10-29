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
        		label_BackupandRestoreArticles, label_fileName, text_filename);
        
	    
	}
	
	

	private void handleLogOut(gp360EdDisc_GUIdriver driver) {
		gp360EdDisc_GUIdriver.USERNAME = "";
		driver.loadloginPage();
	}
	
	private void handleCreateArticle(gp360EdDisc_GUIdriver driver) throws SQLException{
		
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
		try {
			String fileName = text_filename.getText();
			gp360EdDisc_GUIdriver.getDBHelper().backupDatabase(fileName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	private void handleRestoreToBackup(gp360EdDisc_GUIdriver driver) throws SQLException{
		
	}
	
	private void handleMergeFromBackup(gp360EdDisc_GUIdriver driver) throws SQLException{
		
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