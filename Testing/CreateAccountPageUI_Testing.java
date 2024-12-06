package groupProjectEdDisc;

import static org.junit.Assert.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;

public class CreateAccountPageUI_Testing {

    private Pane rootPane; // Root pane for UI components
    private gp360EdDisc_GUIdriver driver; // Reference to the main driver
    private CreateAccountPageUI createAccountPageUI; // Class under test

    @Before
    public void setUp() {
        // Initialize the root pane and driver
        rootPane = new Pane();
        driver = new gp360EdDisc_GUIdriver();
        createAccountPageUI = new CreateAccountPageUI(rootPane, driver); // Instantiate the UI
    }

    @Test
    public void testApplicationTitleLabel() {
        // Verify the application title label
        Label titleLabel = (Label) rootPane.getChildren().filtered(node -> node instanceof Label)
            .filtered(node -> ((Label) node).getText().equals("Create EdHelp Account"))
            .get(0);
        assertNotNull("Title label should exist", titleLabel);
        assertEquals("Create EdHelp Account", titleLabel.getText());
    }

    @Test
    public void testCreateAccountButtonExists() {
        // Verify the "Create Account" button exists
        Button createAccountButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
            .filtered(node -> ((Button) node).getText().equals("Create Account"))
            .get(0);
        assertNotNull("Create Account button should exist", createAccountButton);
        assertEquals("Create Account", createAccountButton.getText());
    }

    @Test
    public void testBackToLoginButtonExists() {
        // Verify the "Back to Login" button exists
        Button backToLoginButton = (Button) rootPane.getChildren().filtered(node -> node instanceof Button)
            .filtered(node -> ((Button) node).getText().equals("Back to Login"))
            .get(0);
        assertNotNull("Back to Login button should exist", backToLoginButton);
        assertEquals("Back to Login", backToLoginButton.getText());
    }

    @Test
    public void testTextFieldsExist() {
        // Verify all required text fields exist
        TextField usernameField = (TextField) rootPane.getChildren().filtered(node -> node instanceof TextField)
            .filtered(node -> ((TextField) node).getPromptText() == null).get(0);
        assertNotNull("Username field should exist", usernameField);

        PasswordField passwordField = (PasswordField) rootPane.getChildren().filtered(node -> node instanceof PasswordField)
            .filtered(node -> ((PasswordField) node).getPromptText() == null).get(0);
        assertNotNull("Password field should exist", passwordField);

        PasswordField reenterPasswordField = (PasswordField) rootPane.getChildren().filtered(node -> node instanceof PasswordField)
            .filtered(node -> ((PasswordField) node).getPromptText() == null).get(1);
        assertNotNull("Re-enter Password field should exist", reenterPasswordField);
    }
}
