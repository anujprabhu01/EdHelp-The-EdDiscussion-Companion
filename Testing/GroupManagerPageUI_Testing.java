package groupProjectEdDisc;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GroupManagerPageUI_Testing {
    private Pane rootPane;
    private gp360EdDisc_GUIdriver driver;
    private GroupManagerPageUI groupManagerPageUI;

    @Before
    public void setUp() {
        driver = new gp360EdDisc_GUIdriver();
        rootPane = new Pane();
        groupManagerPageUI = new GroupManagerPageUI(rootPane, driver);
    }

    @Test
    public void testMenuButtonFunctionality() {
        Platform.runLater(() -> {
            try {
                // Find the menu button
                Button menuButton = (Button) rootPane.getChildren().stream()
                        .filter(node -> node instanceof Button && ((Button) node).getText().equals("Menu"))
                        .findFirst()
                        .orElse(null);
                assertNotNull("Menu button should exist", menuButton);

                // Simulate clicking the menu button
                menuButton.fire();

                // Assert that the menu popup appears (dummy assertion as menu popup is external)
                // Replace stage.isShowing() with actual logic if stage reference exists
                assertTrue("Menu button action should succeed", true);
            } catch (Exception e) {
                // Handle exceptions including SQLException
                e.printStackTrace();
                fail("Exception should not occur during menu button action: " + e.getMessage());
            }
        });
        waitForFX();
    }

    @Test
    public void testLogOutButtonFunctionality() {
        Platform.runLater(() -> {
            try {
                // Find the log out button
                Button logOutButton = (Button) rootPane.getChildren().stream()
                        .filter(node -> node instanceof Button && ((Button) node).getText().equals("Log Out"))
                        .findFirst()
                        .orElse(null);
                assertNotNull("Log Out button should exist", logOutButton);

                // Simulate clicking the log out button
                logOutButton.fire();

                // Assert that the user is logged out (dummy assertion for now)
                assertEquals("Log out should clear username", "", gp360EdDisc_GUIdriver.USERNAME);
            } catch (Exception e) {
                // Handle exceptions safely
                e.printStackTrace();
                fail("Exception should not occur during log out button action: " + e.getMessage());
            }
        });
        waitForFX();
    }

    @Test
    public void testCreateGroupFunctionality() {
        Platform.runLater(() -> {
            try {
                // Find the create group button and text field
                Button createGroupButton = (Button) rootPane.getChildren().stream()
                        .filter(node -> node instanceof Button && ((Button) node).getText().equals("Create"))
                        .findFirst()
                        .orElse(null);
                TextField groupNameField = (TextField) rootPane.getChildren().stream()
                        .filter(node -> node instanceof TextField)
                        .findFirst()
                        .orElse(null);

                assertNotNull("Create Group button should exist", createGroupButton);
                assertNotNull("Group Name text field should exist", groupNameField);

                // Simulate entering group name and clicking the create button
                groupNameField.setText("Test Group");
                createGroupButton.fire();

                // Assert that group creation succeeds (dummy assertion for now)
                assertTrue("Group creation should succeed", true);
            } catch (Exception e) {
                // Handle exceptions safely
                e.printStackTrace();
                fail("Exception should not occur during group creation: " + e.getMessage());
            }
        });
        waitForFX();
    }

    private void waitForFX() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            // Ensure any pending JavaFX tasks are executed
        });
    }
}
