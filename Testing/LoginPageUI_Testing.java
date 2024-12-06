package groupProjectEdDisc;

import static org.junit.Assert.*;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.junit.BeforeClass;
import org.junit.Test;

public class LoginPageUI_Testing {

    private Pane rootPane;
    private gp360EdDisc_GUIdriver driver;
    private LoginPageUI loginPage;

    @BeforeClass
    public static void initJavaFX() {
        // Initialize JavaFX runtime
        Platform.startup(() -> {});
        Platform.setImplicitExit(false);
    }

    @Test
    public void testLoginWithEmptyFields() {
        Platform.runLater(() -> {
            rootPane = new Pane();
            driver = new gp360EdDisc_GUIdriver();
            loginPage = new LoginPageUI(rootPane, driver);

            // Get UI elements
            TextField usernameField = (TextField) rootPane.getChildren().stream()
                    .filter(node -> node instanceof TextField)
                    .findFirst()
                    .orElse(null);

            PasswordField passwordField = (PasswordField) rootPane.getChildren().stream()
                    .filter(node -> node instanceof PasswordField)
                    .findFirst()
                    .orElse(null);

            Label errorLabel = (Label) rootPane.getChildren().stream()
                    .filter(node -> node instanceof Label && ((Label) node).getText().equals("username/passoword is missing"))
                    .findFirst()
                    .orElse(null);

            assertNotNull(usernameField);
            assertNotNull(passwordField);
            assertNotNull(errorLabel);

            // Simulate empty fields
            usernameField.setText("");
            passwordField.setText("");

            // Simulate login button click
            Button loginButton = (Button) rootPane.getChildren().stream()
                    .filter(node -> node instanceof Button && ((Button) node).getText().equals("Login"))
                    .findFirst()
                    .orElse(null);

            assertNotNull(loginButton);
            loginButton.fire();

            // Verify error label visibility
            assertTrue("Error label should be visible for empty fields", errorLabel.isVisible());
        });

        waitForFX();
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        Platform.runLater(() -> {
            rootPane = new Pane();
            driver = new gp360EdDisc_GUIdriver();
            loginPage = new LoginPageUI(rootPane, driver);

            // Mock invalid login credentials
            TextField usernameField = (TextField) rootPane.getChildren().stream()
                    .filter(node -> node instanceof TextField)
                    .findFirst()
                    .orElse(null);

            PasswordField passwordField = (PasswordField) rootPane.getChildren().stream()
                    .filter(node -> node instanceof PasswordField)
                    .findFirst()
                    .orElse(null);

            Label incorrectCredsLabel = (Label) rootPane.getChildren().stream()
                    .filter(node -> node instanceof Label && ((Label) node).getText().equals("incorrect credentials"))
                    .findFirst()
                    .orElse(null);

            assertNotNull(usernameField);
            assertNotNull(passwordField);
            assertNotNull(incorrectCredsLabel);

            // Enter invalid credentials
            usernameField.setText("wrongUser");
            passwordField.setText("wrongPass");

            // Simulate login button click
            Button loginButton = (Button) rootPane.getChildren().stream()
                    .filter(node -> node instanceof Button && ((Button) node).getText().equals("Login"))
                    .findFirst()
                    .orElse(null);

            assertNotNull(loginButton);
            loginButton.fire();

            // Verify incorrect credentials label visibility
            assertTrue("Incorrect credentials label should be visible for invalid credentials", incorrectCredsLabel.isVisible());
        });

        waitForFX();
    }

    @Test
    public void testHaveCodeButton() {
        Platform.runLater(() -> {
            rootPane = new Pane();
            driver = new gp360EdDisc_GUIdriver();
            loginPage = new LoginPageUI(rootPane, driver);

            // Find the "Have a Code" button
            Button haveCodeButton = (Button) rootPane.getChildren().stream()
                    .filter(node -> node instanceof Button && ((Button) node).getText().contains("Have a Code"))
                    .findFirst()
                    .orElse(null);

            assertNotNull(haveCodeButton);

            // Simulate button click
            haveCodeButton.fire();

            // Verify that the "Create Account" page is loaded
            // (Assuming driver.loadCreateAccountPage() updates a property or sets a flag)
            assertTrue("Create Account page should be loaded", driver.CURRENT_SESSION.equals("CREATE_ACCOUNT"));
        });

        waitForFX();
    }

    /**
     * Helper method to wait for JavaFX tasks to complete.
     */
    private void waitForFX() {
        try {
            Thread.sleep(500); // Small delay to allow JavaFX tasks to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
