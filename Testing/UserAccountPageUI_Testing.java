package groupProjectEdDisc;

import static org.junit.Assert.*;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserAccountPageUI_Testing {

    private Pane rootPane;
    private gp360EdDisc_GUIdriver driver;
    private UserAccountPageUI userAccountPage;

    @BeforeClass
    public static void initJavaFX() {
        // Initialize JavaFX runtime
        Platform.startup(() -> {});
        Platform.setImplicitExit(false);
    }

    @Test
    public void testLogOutButtonFunctionality() {
        Platform.runLater(() -> {
            rootPane = new Pane();
            driver = new gp360EdDisc_GUIdriver();
            userAccountPage = new UserAccountPageUI(rootPane, driver);

            // Simulate a logged-in state
            gp360EdDisc_GUIdriver.USERNAME = "testUser";

            // Find the "Log Out" button
            Button logOutButton = (Button) rootPane.getChildren().stream()
                    .filter(node -> node instanceof Button && ((Button) node).getText().equals("Log Out"))
                    .findFirst()
                    .orElse(null);

            assertNotNull("Log Out button should exist", logOutButton);

            // Simulate clicking the "Log Out" button
            logOutButton.fire();

            // Verify that the username is cleared
            assertEquals("Username should be cleared after logging out", "", gp360EdDisc_GUIdriver.USERNAME);

            // Verify that the login page is loaded
            // Assuming driver.loadloginPage() sets a flag or updates CURRENT_SESSION
            assertEquals("Login page should be loaded after logging out", "LOGIN", driver.CURRENT_SESSION);
        });

        waitForFX();
    }

    @Test
    public void testApplicationTitle() {
        Platform.runLater(() -> {
            rootPane = new Pane();
            driver = new gp360EdDisc_GUIdriver();
            userAccountPage = new UserAccountPageUI(rootPane, driver);

            // Verify the application title label exists and has correct text
            var titleLabel = rootPane.getChildren().stream()
                    .filter(node -> node instanceof javafx.scene.control.Label && ((javafx.scene.control.Label) node).getText().equals("User Account"))
                    .findFirst()
                    .orElse(null);

            assertNotNull("Application title label should exist", titleLabel);
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
