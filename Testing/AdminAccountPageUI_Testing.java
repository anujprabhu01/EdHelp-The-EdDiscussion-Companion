package groupProjectEdDisc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AdminAccountPageUI_Testing {

    private Pane rootPane; // Root pane for UI components
    private gp360EdDisc_GUIdriver driver; // Instance of the driver
    private AdminAccountPageUI adminAccountPageUI; // Class under test

    @Before
    public void setUp() {
        // Initialize the root pane and driver
        rootPane = new Pane();
        driver = new gp360EdDisc_GUIdriver(); // Use actual driver class
        adminAccountPageUI = new AdminAccountPageUI(rootPane, driver); // Initialize the UI
    }

    @Test
    public void testLabelSetup() {
        // Verify the application title label
        Label label = (Label) rootPane.getChildren().filtered(node -> node instanceof Label)
                                      .filtered(node -> ((Label) node).getText().equals("Admin User Control"))
                                      .get(0);

        assertEquals("Admin User Control", label.getText());
        assertEquals("Arial", label.getFont().getName());
        assertEquals(24.0, label.getFont().getSize(), 0.0);
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
    public void testInviteUserUIElements() {
        // Verify the Invite User label
        Label inviteLabel = (Label) rootPane.getChildren().filtered(node -> node instanceof Label)
                                            .filtered(node -> ((Label) node).getText().equals("Invite User"))
                                            .get(0);
        assertEquals("Invite User", inviteLabel.getText());

        // Verify the Email text field
        TextField emailField = (TextField) rootPane.getChildren().filtered(node -> node instanceof TextField)
                                                   .filtered(node -> node.getLayoutY() == 107) // Match position
                                                   .get(0);
        assertTrue(emailField.isEditable());
        assertEquals("", emailField.getText()); // Initially empty
    }

    @Test
    public void testSendInviteButtonSetup() {
        // Verify the Send Invite button
        Button sendInviteButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                                   .filtered(node -> ((Button) node).getText().equals("Send Invite"))
                                                   .get(0);

        assertEquals("Send Invite", sendInviteButton.getText());
        assertEquals(300, sendInviteButton.getLayoutX(), 0.0);
        assertEquals(110, sendInviteButton.getLayoutY(), 0.0);
    }

    @Test
    public void testDeleteAccountButtonSetup() {
        // Verify the Delete Account button
        Button deleteButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                               .filtered(node -> ((Button) node).getText().equals("Delete Account"))
                                               .get(0);

        assertEquals("Delete Account", deleteButton.getText());
        assertEquals(300, deleteButton.getLayoutX(), 0.0);
        assertEquals(283, deleteButton.getLayoutY(), 0.0);
    }

    @Test
    public void testCheckBoxesForInviteUser() {
        // Verify the Admin checkbox
        CheckBox adminCheckBox = (CheckBox) rootPane.getChildren().filtered(node -> node instanceof CheckBox)
                                                    .filtered(node -> ((CheckBox) node).getText().equals("Admin"))
                                                    .get(0);
        assertNotNull(adminCheckBox);
        assertFalse(adminCheckBox.isSelected()); // Initially not selected

        // Verify the Student checkbox
        CheckBox studentCheckBox = (CheckBox) rootPane.getChildren().filtered(node -> node instanceof CheckBox)
                                                      .filtered(node -> ((CheckBox) node).getText().equals("Student"))
                                                      .get(0);
        assertNotNull(studentCheckBox);
        assertFalse(studentCheckBox.isSelected()); // Initially not selected

        // Verify the Instructor checkbox
        CheckBox instructorCheckBox = (CheckBox) rootPane.getChildren().filtered(node -> node instanceof CheckBox)
                                                         .filtered(node -> ((CheckBox) node).getText().equals("Instructor"))
                                                         .get(0);
        assertNotNull(instructorCheckBox);
        assertFalse(instructorCheckBox.isSelected()); // Initially not selected
    }

    @Test
    public void testListUsersButtonSetup() {
        // Verify the List Users button
        Button listUsersButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
                                                  .filtered(node -> ((Button) node).getText().equals("List All Users"))
                                                  .get(0);

        assertEquals("List All Users", listUsersButton.getText());
        assertEquals(20, listUsersButton.getLayoutX(), 0.0);
        assertEquals(450, listUsersButton.getLayoutY(), 0.0);
    }
}
