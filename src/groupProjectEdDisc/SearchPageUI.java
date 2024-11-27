package groupProjectEdDisc;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;




public class SearchPageUI { 
	
    
	private Label label_ApplicationTitle = new Label("Search & Help");
	private Button btn_logOut = new Button("Log Out");
	private Button btn_menu = new Button("menu");
    
	private Label label_genericHelp = new Label("Generic Help");
	private ComboBox<String> helpComboBox = new ComboBox<>();
	private Button btn_sendGeneric = new Button("Send Help Message");
	private Label label_GenericError = new Label("Select a message before sending");
	
	private Label label_specificHelp = new Label("Specific Help");
	private TextField text_specificRequest = new TextField();
	private Button btn_sendSpecific = new Button("Send Help Message");
	private Label label_SpecificError = new Label("Type a message before sending");
	
	private Label label_SearchArticles = new Label("Search Articles");
	private Label label_group = new Label("Group");
	private TextField text_group = new TextField();
	private Button btn_setCurrGroup = new Button("Set group");
	private Button btn_clearCurrGroup = new Button("Clear group");
	private Button btn_listGroup = new Button("List Group");
	private Label label_groupError = new Label("Enter a valid group");
	private Label label_NoAccess = new Label("No Access to group");
	
	private Label label_contentLevel = new Label("Content level");
	RadioButton radio_All = new RadioButton("All");
	RadioButton radio_Beginner = new RadioButton("Beginner");
	RadioButton radio_Intermediate = new RadioButton("Intermediate");
	RadioButton radio_Advanced = new RadioButton("Advanced");
	RadioButton radio_Expert = new RadioButton("Expert");
	ToggleGroup levelGroup = new ToggleGroup();
	
	private Label label_searchByWordorPhrase = new Label("Search by Word or phrase");
	private TextField text_WoP = new TextField();
	private Button btn_searchWoP = new Button("Search");
	private Label label_searchByID = new Label("Search by ID");
	private TextField text_ID = new TextField();
	private Button btn_view = new Button("View");
	private Label label_idError = new Label("ID does\nnot exist");
	private Label label_errorViewAccess = new Label("No\nAccess");
	private Label label_searchError = new Label("Invalid\nsearch term");
	
	private ScrollPane resultsPane = new ScrollPane();
    private VBox searchResultsContainer = new VBox();
	
	
	public SearchPageUI(Pane theRoot, gp360EdDisc_GUIdriver driver) {		
		
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
	        
        // Setup for the application title at the top, centered
	    setupLabelUI(label_ApplicationTitle, "Arial", 24, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 200, 
	    		Pos.CENTER, 90, 10);
	    label_ApplicationTitle.setStyle("-fx-font-weight: bold");
	    
	    btn_logOut.setText("Log Out");
        btn_logOut.setLayoutX(430);
        btn_logOut.setLayoutY(10);
        btn_logOut.setMaxWidth(100);
        theRoot.getChildren().add(btn_logOut);
            
        btn_logOut.setOnAction(e -> {
        	handleLogOut(driver);
        });
        
        //
        setupLabelUI(label_genericHelp, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 40);
        label_genericHelp.setStyle("-fx-font-weight: bold");
        
        // Setup the ComboBox for help options
        helpComboBox.getItems().addAll(
            "Help with Search",
            "Help with Groups",
            "Help with Account"
        );
        helpComboBox.setLayoutX(20);
        helpComboBox.setLayoutY(65);
        helpComboBox.setPromptText("Select Help Topic");
        theRoot.getChildren().add(helpComboBox);
            
        btn_sendGeneric.setText("Send Help Message");
        btn_sendGeneric.setLayoutX(200);
        btn_sendGeneric.setLayoutY(65);
        btn_sendGeneric.setMaxWidth(150);
        theRoot.getChildren().add(btn_sendGeneric);
            
        btn_sendGeneric.setOnAction(e -> {
        	try {
        		handleSendGeneric(driver);
            	} catch (Exception ex) {
                    ex.printStackTrace();
                }
        });
        
        setupLabelUI(label_GenericError, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 250, 45, "red");
        label_GenericError.setVisible(false);
        label_GenericError.setManaged(false);
        
        
        
        //
        setupLabelUI(label_specificHelp, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 100);
        label_specificHelp.setStyle("-fx-font-weight: bold");
        
        setupTextUI(text_specificRequest, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 340,
                Pos.BASELINE_LEFT, 20, 130, true);
        text_specificRequest.maxHeight(10);
        
        btn_sendSpecific.setText("Send Help Message");
        btn_sendSpecific.setLayoutX(200);
        btn_sendSpecific.setLayoutY(133);
        btn_sendSpecific.setMaxWidth(150);
        theRoot.getChildren().add(btn_sendSpecific);
            
        btn_sendSpecific.setOnAction(e -> {
        	try {
        		handleSendSpecific(driver);
            	} catch (Exception ex) {
                    ex.printStackTrace();
                }
        });
        
        
        setupLabelUI(label_SpecificError, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 250, 110, "red");
        label_SpecificError.setVisible(false);
    	label_SpecificError.setManaged(false);
    	
    	
    	//
        setupLabelUI(label_SearchArticles, "Arial", 20, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 170);
        label_SearchArticles.setStyle("-fx-font-weight: bold");
        
        setupLabelUI(label_group, "Arial", 16, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 20, 190);
        
        setupTextUI(text_group, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 340,
                Pos.BASELINE_LEFT, 20, 210, true);
        text_group.maxHeight(10);
        
        btn_setCurrGroup.setText("Set group");
        btn_setCurrGroup.setLayoutX(200);
        btn_setCurrGroup.setLayoutY(214);
        btn_setCurrGroup.setMaxWidth(150);
        theRoot.getChildren().add(btn_setCurrGroup);
            
        btn_setCurrGroup.setOnAction(e -> {
        	try {
        		handleSetCurrGroup(driver);
            	} catch (Exception ex) {
                    ex.printStackTrace();
                }
        });
        
        btn_clearCurrGroup.setText("Clear group");
        btn_clearCurrGroup.setLayoutX(280);
        btn_clearCurrGroup.setLayoutY(214);
        btn_clearCurrGroup.setMaxWidth(150);
        theRoot.getChildren().add(btn_clearCurrGroup);
            
        btn_clearCurrGroup.setOnAction(e -> {
        	try {
        		gp360EdDisc_GUIdriver.CURRENT_SEARCH_GROUP = "";
        		Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Current Group cleared");
		        alert.setHeaderText(null);
		        alert.setContentText("You no longer have a current group set.");
		        alert.showAndWait();
            	} catch (Exception ex) {
                    ex.printStackTrace();
                }
        });
        
        btn_listGroup.setText("List group"); 
        btn_listGroup.setLayoutX(370); 
        btn_listGroup.setLayoutY(214); 
        btn_listGroup.setMaxWidth(150);
        theRoot.getChildren().add(btn_listGroup);
            
        btn_listGroup.setOnAction(e -> {
        	try {
        		if (gp360EdDisc_GUIdriver.CURRENT_SEARCH_GROUP != "") {
        			label_groupError.setVisible(false);
        			label_groupError.setManaged(false);
        			handleListGroup(driver);
        		}
        		else {
        			label_groupError.setVisible(true);
        			label_groupError.setManaged(true);
        		}
        		
            	} catch (Exception ex) {
                    ex.printStackTrace();
                }
        });
    	
        setupLabelUI(label_groupError, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 250, 190, "red");
        label_groupError.setVisible(false);
        label_groupError.setManaged(false);
        
        setupLabelUI(label_NoAccess, "Arial", 14, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 250, 190, "red");
        label_NoAccess.setVisible(false);
        label_NoAccess.setManaged(false);
        
        //
        setupLabelUI(label_contentLevel, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 250);
        label_contentLevel.setStyle("-fx-font-weight: bold");
        
        radio_All.setToggleGroup(levelGroup);
        radio_Beginner.setToggleGroup(levelGroup);
        radio_Intermediate.setToggleGroup(levelGroup);
        radio_Advanced.setToggleGroup(levelGroup);
        radio_Expert.setToggleGroup(levelGroup);
        
        radio_All.setSelected(true);
        
        radio_All.setLayoutX(20);
        radio_All.setLayoutY(275);
        theRoot.getChildren().add(radio_All);

        radio_Beginner.setLayoutX(80);
        radio_Beginner.setLayoutY(275);
        theRoot.getChildren().add(radio_Beginner);

        radio_Intermediate.setLayoutX(165);
        radio_Intermediate.setLayoutY(275);
        theRoot.getChildren().add(radio_Intermediate);

        radio_Advanced.setLayoutX(270);
        radio_Advanced.setLayoutY(275);
        theRoot.getChildren().add(radio_Advanced);

        radio_Expert.setLayoutX(360);
        radio_Expert.setLayoutY(275);
        theRoot.getChildren().add(radio_Expert);
        
        setupLabelUI(label_searchByWordorPhrase, "Arial", 20, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 10, 300);
        label_searchByWordorPhrase.setStyle("-fx-font-weight: bold");
        
        setupLabelUI(label_searchByID, "Arial", 20, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, 
                Pos.BASELINE_LEFT, 300, 300);
        label_searchByID.setStyle("-fx-font-weight: bold");
        
        setupTextUI(text_WoP, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 340,
                Pos.BASELINE_LEFT, 20, 330, true);
        text_WoP.maxHeight(10);
        
        setupLabelUI(label_searchError, "Arial", 10, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 255, 340, "red");
        label_searchError.setVisible(false);
        label_searchError.setManaged(false);
        
        btn_searchWoP.setText("Search");
        btn_searchWoP.setLayoutX(197);
        btn_searchWoP.setLayoutY(333);
        btn_searchWoP.setMaxWidth(150);
        theRoot.getChildren().add(btn_searchWoP);
        
        btn_searchWoP.setOnAction(e -> {
        	try {
        		handleSearch(driver);
            	} catch (Exception ex) {
                    ex.printStackTrace();
                }
        });
        
        setupTextUI(text_ID, "Arial", 18, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 440,
                Pos.BASELINE_LEFT, 310, 330, true);
        text_ID.maxHeight(10);
        
        setupLabelUI(label_idError, "Arial", 10, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 437, 340, "red");
        label_idError.setVisible(false);
        label_idError.setManaged(false);
        
        
        setupLabelUI(label_errorViewAccess, "Arial", 10, gp360EdDisc_GUIdriver.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 437, 340, "red");
		label_errorViewAccess.setVisible(false);
		label_errorViewAccess.setManaged(false);
        
        btn_view.setText("View");
        btn_view.setLayoutX(390);
        btn_view.setLayoutY(333);
        btn_view.setMaxWidth(150);
        theRoot.getChildren().add(btn_view);
        
        btn_view.setOnAction(e -> {
        	boolean update2 = false;
        	if(text_ID.getText().isEmpty()) {
        		label_idError.setVisible(true);
        		label_idError.setManaged(true);
        		
        	}else {
        		try {
        			label_idError.setVisible(false);
        			label_idError.setManaged(false);
        			label_errorViewAccess.setVisible(false);
					label_errorViewAccess.setManaged(false);
        			long id = Long.parseLong(text_ID.getText());
        			update2 =  gp360EdDisc_GUIdriver.getDBHelper().articleIdExists(id);
        			if(update2) {
        				if (gp360EdDisc_GUIdriver.getDBHelper().hasAccesstoArticle(id)) {
        					handleViewArticle(driver, id);
        				}
        				else {
        					label_errorViewAccess.setVisible(true);
        					label_errorViewAccess.setManaged(true);
        				}
        				
        			}
        			else {
        				//setupLabelUI(Label l, String font, double fontSize, double width, Pos alignment, double x, double y, String color)
        				label_idError.setVisible(true);
        				label_idError.setManaged(true);
        			}
                	
                	} catch (SQLException ex) {
                        ex.printStackTrace();
                    }
        		
        	}
        });
        
        
        resultsPane.setContent(searchResultsContainer);
        resultsPane.setLayoutX(20); // Set X position of the scroll pane
        resultsPane.setLayoutY(370); // Set Y position of the scroll pane
        resultsPane.setPrefSize(gp360EdDisc_GUIdriver.WINDOW_WIDTH - 40, 120); // Set width and height of the scroll pane
        resultsPane.setFitToWidth(true);
        resultsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
        resultsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        resultsPane.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        
        // Adding the elements to the root pane
        theRoot.getChildren().addAll(label_ApplicationTitle, label_genericHelp, label_GenericError, label_specificHelp, 
        		text_specificRequest, label_SpecificError, label_SearchArticles, label_group, text_group, label_groupError,
        		label_contentLevel, label_searchByWordorPhrase, label_searchByID, text_WoP, text_ID, resultsPane, label_idError,
        		label_searchError, label_NoAccess, label_errorViewAccess);
    }

    /**********************************************************************************************
     * Helper Methods for Setting Up UI Elements
     **********************************************************************************************/
	private void handleLogOut(gp360EdDisc_GUIdriver driver) {
		//Log Out
		gp360EdDisc_GUIdriver.USERNAME = "";
		driver.loadloginPage();
	}
	
	private void handleMenu(gp360EdDisc_GUIdriver driver) throws SQLException { 
		driver.showMenuPopUp(driver.CURRENT_SESSION);
	}
	
	private void handleSendGeneric(gp360EdDisc_GUIdriver driver) {
		try {
			// Get the selected help topic from the ComboBox
		    String selectedOption = helpComboBox.getValue();
	
		    if (selectedOption != null) {
		    	label_GenericError.setVisible(false);
		        label_GenericError.setManaged(false);
		        label_SpecificError.setVisible(false);
	            label_SpecificError.setManaged(false);
		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Help Message Sent");
		        alert.setHeaderText(null);
		        alert.setContentText("Your help message for has been sent successfully.");
		        alert.showAndWait();
		        gp360EdDisc_GUIdriver.getDBHelper().sendGeneralRequest(selectedOption);
		        
		    } else {
		        label_GenericError.setVisible(true);
		        label_GenericError.setManaged(true);
		    }
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void handleSendSpecific(gp360EdDisc_GUIdriver driver) {
		try {
	        // Get the text from the TextField
	        String selectedOption = text_specificRequest.getText();

	        // Check if the input is empty or null
	        if (selectedOption != null && !selectedOption.trim().isEmpty()) {
	            // Hide the error label
	            label_SpecificError.setVisible(false);
	            label_SpecificError.setManaged(false);

	            // Show a confirmation alert
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Help Message Sent");
	            alert.setHeaderText(null);
	            alert.setContentText("Your help message has been sent successfully.");
	            alert.showAndWait();
	            gp360EdDisc_GUIdriver.getDBHelper().sendSpecificRequest(selectedOption);
	            text_specificRequest.clear();
	        } else {
	            // Show the error label if the input is empty
	            label_SpecificError.setVisible(true);
	            label_SpecificError.setManaged(true);
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	private void handleSetCurrGroup(gp360EdDisc_GUIdriver driver) {
		try {
	        // Get the text from the TextField
	        String group = text_group.getText();

	        // Check if the input is empty or null
	        if (group != null && !group.trim().isEmpty()) {
	            // Hide the error label
	            label_groupError.setVisible(false);
	            label_groupError.setManaged(false);
	            label_NoAccess.setVisible(false);
        		label_NoAccess.setManaged(false);
	            if (gp360EdDisc_GUIdriver.getDBHelper().groupExists(group)) {
	            	if (gp360EdDisc_GUIdriver.getDBHelper().isSpecialAccess(group)) {
		            	if (gp360EdDisc_GUIdriver.getDBHelper().hasAccesstoGroup(group)) {
		            		gp360EdDisc_GUIdriver.CURRENT_SEARCH_GROUP = group;
			            	text_group.clear();
			            	Alert alert = new Alert(AlertType.INFORMATION);
					        alert.setTitle("Current Group Set");
					        alert.setHeaderText(null);
					        alert.setContentText("Your current group has been set as " + group);
					        alert.showAndWait();
		            	}
		            	else {
		            		label_NoAccess.setVisible(true);
		            		label_NoAccess.setManaged(true);
		            		return;
		            	}
	            	}
				    else {
				    	gp360EdDisc_GUIdriver.CURRENT_SEARCH_GROUP = group;
		            	text_group.clear();
		            	Alert alert = new Alert(AlertType.INFORMATION);
				        alert.setTitle("Current Group Set");
				        alert.setHeaderText(null);
				        alert.setContentText("Your current group has been set as " + group);
				        alert.showAndWait();
				    }
	            }
	            else {
	            	label_groupError.setVisible(true);
		        	label_groupError.setManaged(true);
		        	return;
	            }
	        } else {
	            // Show the error label if the input is empty
	        	label_groupError.setVisible(true);
	        	label_groupError.setManaged(true);
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	private void handleSearch(gp360EdDisc_GUIdriver driver) {
		try {
			String wp = text_WoP.getText();
			String group = gp360EdDisc_GUIdriver.CURRENT_SEARCH_GROUP;
			RadioButton selectedRadioButton = (RadioButton) levelGroup.getSelectedToggle();
			String level = selectedRadioButton.getText();
			
			if (wp != null && !wp.trim().isEmpty()) {
				label_searchError.setVisible(false);
				label_searchError.setManaged(false);
				String searchResults = gp360EdDisc_GUIdriver.getDBHelper().search(wp, group, level);
				searchResultsContainer.getChildren().clear();
				Text resultsText = new Text(searchResults);
				resultsText.setWrappingWidth(380);
				searchResultsContainer.getChildren().add(resultsText);

			}
			else {
				label_searchError.setVisible(true);
				label_searchError.setManaged(true);
			}
	
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void handleListGroup(gp360EdDisc_GUIdriver driver) {
		try {
			String group = gp360EdDisc_GUIdriver.CURRENT_SEARCH_GROUP;
			String result = gp360EdDisc_GUIdriver.getDBHelper().listGroup(group);
			searchResultsContainer.getChildren().clear();
			Text resultsText = new Text(result);
			resultsText.setWrappingWidth(380);
			searchResultsContainer.getChildren().add(resultsText);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void handleViewArticle(gp360EdDisc_GUIdriver driver, long id) throws SQLException{
		// === Call to get Article Values === use id
		String level = gp360EdDisc_GUIdriver.getDBHelper().getLevel(id);
		String title =gp360EdDisc_GUIdriver.getDBHelper().getTitle(id);
		String descriptor = gp360EdDisc_GUIdriver.getDBHelper().getDescriptor(id);
		String body = gp360EdDisc_GUIdriver.getDBHelper().getBody(id);
		String keywords = gp360EdDisc_GUIdriver.getDBHelper().getKeywords(id);
		String reference = gp360EdDisc_GUIdriver.getDBHelper().getReference(id);
		
		String currentUser = gp360EdDisc_GUIdriver.USERNAME;
		String currentRole = gp360EdDisc_GUIdriver.CURRENT_SESSION;
		// Create a new Stage
	    Stage viewArticleStage = new Stage();
	    viewArticleStage.setTitle("Article #"+id);
	    // TextArea to display the user accounts
	    Label titleLabel = new Label(title);
	    titleLabel.setWrapText(true);
	    titleLabel.setFont(Font.font("Arial", 24));
	    titleLabel.setStyle("-fx-font-weight: bold");
	    
	    Label levelLabel = new Label("Level: "+level);
	    levelLabel.setWrapText(true);
	    levelLabel.setFont(Font.font("Arial", 18));
	    levelLabel.setStyle("-fx-font-weight: bold");
	    
	    Label descriptorLabel = new Label(descriptor);
	    descriptorLabel.setWrapText(true);
	    descriptorLabel.setFont(Font.font("Arial", 18));
	    //descriptorLabel.setStyle("-fx-font-weight: bold");
	    
	    Label keywordsLabel = new Label("Keywords: "+keywords);
	    keywordsLabel.setWrapText(true);
	    keywordsLabel.setFont(Font.font("Arial", 18));
	    //keywordsLabel.setStyle("-fx-font-weight: bold");
	    
	    
	    boolean view = gp360EdDisc_GUIdriver.getDBHelper().canViewArticle(id, currentUser, currentRole);
	    
	    Label bodyLabel;
	    if(view) {
	    	char[] body3 = gp360EdDisc_GUIdriver.getDBHelper().decryptBody(body);
	        String body4 = new String(body3);
	    	
	    	bodyLabel = new Label(body4);
	    	bodyLabel.setWrapText(true);
		    bodyLabel.setFont(Font.font("Arial", 18));
	    }else {
	    	bodyLabel = new Label(body);
	    	bodyLabel.setWrapText(true);
		    bodyLabel.setFont(Font.font("Arial", 18));
		  //bodyLabel.setStyle("-fx-font-weight: bold");
	    }
	   
	    
	    
	    
	    
	    Label referenceLabel = new Label(reference);
	    referenceLabel.setWrapText(true);
	    referenceLabel.setFont(Font.font("Arial", 18));
	    referenceLabel.setStyle("-fx-font-style: italic");
	    

	    
	    // Make sure this window doesn't block interaction with the main window
	    viewArticleStage.initModality(Modality.NONE); 

	    
	    VBox mainLayout = new VBox(8, 
	    		titleLabel, levelLabel, descriptorLabel, keywordsLabel, bodyLabel, referenceLabel
		    );
		    mainLayout.setAlignment(Pos.TOP_CENTER);
		    mainLayout.setPadding(new Insets(20));

		    // === Set Scene and Show Popup ===
		    Scene scene = new Scene(mainLayout, 600, 400);
		    viewArticleStage.setScene(scene);
		    viewArticleStage.showAndWait();  // Wait for user input
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