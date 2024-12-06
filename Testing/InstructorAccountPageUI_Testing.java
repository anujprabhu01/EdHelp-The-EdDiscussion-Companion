package groupProjectEdDisc;

import static org.junit.Assert.*;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

import org.junit.BeforeClass;
import org.junit.Test;

public class InstructorAccountPageUI_Testing {

    @BeforeClass
    public static void initJavaFX() {
        // Initialize JavaFX runtime
        Platform.startup(() -> {}); // Required to start the JavaFX platform
        Platform.setImplicitExit(false);
    }

    @Test
    public void testHandleLogOut() {
        // Run the test on the JavaFX Application Thread
        Platform.runLater(() -> {
            try {
                // Create the driver and simulate a logged-in state
                gp360EdDisc_GUIdriver driver = new gp360EdDisc_GUIdriver();
                gp360EdDisc_GUIdriver.USERNAME = "testUser";

                // Create a root Pane for UI setup
                Pane rootPane = new Pane();

                // Instantiate the InstructorAccountPageUI
                InstructorAccountPageUI ui = new InstructorAccountPageUI(rootPane, driver);

                // Use reflection to access and invoke the private handleLogOut method
                var handleLogOutMethod = InstructorAccountPageUI.class.getDeclaredMethod("handleLogOut", gp360EdDisc_GUIdriver.class);
                handleLogOutMethod.setAccessible(true);
                handleLogOutMethod.invoke(ui, driver);

                // Assert that USERNAME is cleared after logging out
                assertEquals("", gp360EdDisc_GUIdriver.USERNAME);

            } catch (Exception e) {
                fail("Exception occurred during testHandleLogOut: " + e.getMessage());
            }
        });

        // Wait for the JavaFX application thread to complete execution
        waitForRunLater();
    }

    @Test
    public void testHandleMenu() {
        // Run the test on the JavaFX Application Thread
        Platform.runLater(() -> {
            try {
                // Create the driver
                gp360EdDisc_GUIdriver driver = new gp360EdDisc_GUIdriver();

                // Create a root Pane for UI setup
                Pane rootPane = new Pane();

                // Instantiate the InstructorAccountPageUI
                InstructorAccountPageUI ui = new InstructorAccountPageUI(rootPane, driver);

                // Use reflection to access and invoke the private handleMenu method
                var handleMenuMethod = InstructorAccountPageUI.class.getDeclaredMethod("handleMenu", gp360EdDisc_GUIdriver.class);
                handleMenuMethod.setAccessible(true);
                handleMenuMethod.invoke(ui, driver);

                // Assert that the CURRENT_SESSION is updated to "INSTRUCTOR"
                assertEquals("INSTRUCTOR", gp360EdDisc_GUIdriver.CURRENT_SESSION);

            } catch (Exception e) {
                fail("Exception occurred during testHandleMenu: " + e.getMessage());
            }
        });

        // Wait for the JavaFX application thread to complete execution
        waitForRunLater();
    }

    /**
     * Helper method to wait for the JavaFX Application Thread to finish execution.
     */
    private void waitForRunLater() {
        try {
            Thread.sleep(500); // Small delay to allow JavaFX tasks to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
