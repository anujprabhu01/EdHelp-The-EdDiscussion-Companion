package groupProjectEdDisc;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;


import static org.junit.Assert.*;

public class FinishAccountSetupUI_Testing {
    private Pane rootPane;
    private FinishAccountSetupUI finishAccountSetupUI;
    private gp360EdDisc_GUIdriver driver;

    @Before
    public void setUp() {
        driver = new gp360EdDisc_GUIdriver();
        rootPane = new Pane();
        finishAccountSetupUI = new FinishAccountSetupUI(rootPane, driver);
    }

    @Test
    public void testEmptyFieldsShowErrorMessage() {
        // Simulate pressing the Confirm Details button with empty fields
        Button confirmButton = (Button) rootPane.getChildren().stream()
                .filter(node -> node instanceof Button)
                .findFirst()
                .orElse(null);
        assertNotNull("Confirm button should exist", confirmButton);

        Platform.runLater(() -> confirmButton.fire());
        waitForFX();

        // Verify the error message
        Label errorLabel = (Label) rootPane.getChildren().stream()
                .filter(node -> node instanceof Label && ((Label) node).getText().equals("please fill all text fields"))
                .findFirst()
                .orElse(null);
        assertNotNull("Error label should exist", errorLabel);
        assertTrue("Error label should be visible for empty fields", errorLabel.isVisible());
    }

    @Test
    public void testHandleConfirmDetailsWithValidData() {
        // Populate the text fields with valid data
        TextField emailField = (TextField) rootPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .findFirst()
                .orElse(null);
        TextField firstNameField = (TextField) rootPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .skip(1)
                .findFirst()
                .orElse(null);
        TextField lastNameField = (TextField) rootPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .skip(3)
                .findFirst()
                .orElse(null);

        assertNotNull(emailField);
        assertNotNull(firstNameField);
        assertNotNull(lastNameField);

        Platform.runLater(() -> {
            emailField.setText("test@example.com");
            firstNameField.setText("John");
            lastNameField.setText("Doe");

            Button confirmButton = (Button) rootPane.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .findFirst()
                    .orElse(null);
            assertNotNull(confirmButton);

            confirmButton.fire();
        });
        waitForFX();

        // Verify no error messages
        Label errorLabel = (Label) rootPane.getChildren().stream()
                .filter(node -> node instanceof Label && ((Label) node).getText().equals("please fill all text fields"))
                .findFirst()
                .orElse(null);
        assertNotNull("Error label should exist", errorLabel);
        assertFalse("Error label should not be visible for valid input", errorLabel.isVisible());
    }

    @Test
    public void testRoleBasedNavigation() throws SQLException {
        // Ensure that roles are set in the database before executing the test
        gp360EdDisc_GUIdriver.getDBHelper().connectToDatabase(); // Ensures the database connection is established
        gp360EdDisc_GUIdriver.getDBHelper().register("testUser", "password", true, false, false, true, false);
        gp360EdDisc_GUIdriver.USERNAME = "testUser"; // Set username for the session

        Platform.runLater(() -> {
            // Find the Confirm Button
            Button confirmButton = (Button) rootPane.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .findFirst()
                    .orElse(null);
            assertNotNull(confirmButton);

            // Fire the button
            confirmButton.fire();
        });

        waitForFX();

        // Check role-based navigation
        assertEquals("ADMIN", gp360EdDisc_GUIdriver.CURRENT_SESSION);
    }

    // Helper method to wait for JavaFX threads to complete
    private void waitForFX() {
        try {
            Thread.sleep(100); // Small delay for UI updates
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {});
    }

}
