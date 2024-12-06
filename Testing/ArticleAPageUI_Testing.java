package groupProjectEdDisc;

import static org.junit.Assert.*;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;

public class ArticleAPageUI_Testing {

    private Pane rootPane; // Root pane for UI components
    private gp360EdDisc_GUIdriver driver; // Reference to the main driver
    private ArticleAPageUI articleAPageUI; // Class under test

    @Before
    public void setUp() {
        // Initialize the root pane and driver
        rootPane = new Pane();
        driver = new gp360EdDisc_GUIdriver();
        articleAPageUI = new ArticleAPageUI(rootPane, driver); // Instantiate the UI
    }

    @Test
    public void testLabelSetup() {
        // Verify the application title label
        Label titleLabel = (Label) rootPane.getChildren().filtered(node -> node instanceof Label)
                                           .filtered(node -> ((Label) node).getText().equals("Article Manager"))
                                           .get(0);

        assertEquals("Article Manager", titleLabel.getText());
        assertEquals("Arial", titleLabel.getFont().getName());
        assertEquals(24.0, titleLabel.getFont().getSize(), 0.0);
    }

    @Test
    public void testLogOutButtonSetup() {
        // Verify the Log Out button
        Button logoutButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                               .filtered(node -> ((Button) node).getText().equals("Log Out"))
                                               .get(0);

        assertEquals("Log Out", logoutButton.getText());
        assertEquals(430, logoutButton.getLayoutX(), 0.0);
        assertEquals(10, logoutButton.getLayoutY(), 0.0);
        assertEquals(100, logoutButton.getMaxWidth(), 0.0);
    }

    @Test
    public void testMenuButtonSetup() {
        // Verify the Menu button
        Button menuButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                             .filtered(node -> ((Button) node).getText().equals("Menu"))
                                             .get(0);

        assertEquals("Menu", menuButton.getText());
        assertEquals(10, menuButton.getLayoutX(), 0.0);
        assertEquals(10, menuButton.getLayoutY(), 0.0);
        assertEquals(100, menuButton.getMaxWidth(), 0.0);
    }

    @Test
    public void testCreateArticleButton() {
        // Verify the Create Article button
        Button createButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                               .filtered(node -> ((Button) node).getText().equals("Create"))
                                               .get(0);

        assertEquals("Create", createButton.getText());
        assertEquals(20, createButton.getLayoutX(), 0.0);
        assertEquals(65, createButton.getLayoutY(), 0.0);
    }

    @Test
    public void testUpdateArticleButtonSetup() {
        // Verify the Update Article button
        Button updateButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                               .filtered(node -> ((Button) node).getText().equals("Update"))
                                               .get(0);

        assertEquals("Update", updateButton.getText());
        assertEquals(180, updateButton.getLayoutX(), 0.0);
        assertEquals(144, updateButton.getLayoutY(), 0.0);
    }

    @Test
    public void testViewArticleButtonSetup() {
        // Verify the View Article button
        Button viewButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                             .filtered(node -> ((Button) node).getText().equals("View"))
                                             .get(0);

        assertEquals("View", viewButton.getText());
        assertEquals(120, viewButton.getLayoutX(), 0.0);
        assertEquals(144, viewButton.getLayoutY(), 0.0);
    }

    @Test
    public void testDeleteArticleButtonSetup() {
        // Verify the Delete Article button
        Button deleteButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                               .filtered(node -> ((Button) node).getText().equals("Delete"))
                                               .get(0);

        assertEquals("Delete", deleteButton.getText());
        assertEquals(255, deleteButton.getLayoutX(), 0.0);
        assertEquals(144, deleteButton.getLayoutY(), 0.0);
    }

    @Test
    public void testBackupButtonSetup() {
        // Verify the Backup to File button
        Button backupButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                               .filtered(node -> ((Button) node).getText().equals("Backup"))
                                               .get(0);

        assertEquals("Backup", backupButton.getText());
        assertEquals(180, backupButton.getLayoutX(), 0.0);
        assertEquals(312, backupButton.getLayoutY(), 0.0);
    }

    @Test
    public void testRestoreButtonSetup() {
        // Verify the Restore to Backup button
        Button restoreButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                                .filtered(node -> ((Button) node).getText().equals("Restore"))
                                                .get(0);

        assertEquals("Restore", restoreButton.getText());
        assertEquals(250, restoreButton.getLayoutX(), 0.0);
        assertEquals(312, restoreButton.getLayoutY(), 0.0);
    }

    @Test
    public void testMergeButtonSetup() {
        // Verify the Merge from Backup button
        Button mergeButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                              .filtered(node -> ((Button) node).getText().equals("Merge"))
                                              .get(0);

        assertEquals("Merge", mergeButton.getText());
        assertEquals(320, mergeButton.getLayoutX(), 0.0);
        assertEquals(312, mergeButton.getLayoutY(), 0.0);
    }

    @Test
    public void testErrorLabelVisibility() {
        // Verify that the error label for invalid article ID is initially hidden
        Label errorLabel = (Label) rootPane.getChildren().filtered(node -> node instanceof Label)
                                           .filtered(node -> ((Label) node).getText().equals("Please enter a valid Article ID"))
                                           .get(0);

        assertFalse(errorLabel.isVisible());
        assertFalse(errorLabel.isManaged());
    }

    @Test
    public void testFileNameInputSetup() {
        // Verify the TextField for file name input
        TextField fileNameField = (TextField) rootPane.getChildren().filtered(node -> node instanceof TextField)
                                                      .filtered(node -> node.getLayoutY() == 310)
                                                      .get(0);

        assertTrue(fileNameField.isEditable());
        assertEquals("", fileNameField.getText());
    }
}
