package groupProjectEdDisc;

import static org.junit.Assert.*;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

public class SearchPageUI_Testing {

    private Pane rootPane;
    private gp360EdDisc_GUIdriver driver; // Mock or initialized object
    private SearchPageUI searchPageUI;

    @BeforeClass
    public static void initJavaFX() {
        // Initialize JavaFX runtime for testing
        Platform.startup(() -> {
        });
    }

    @Before
    public void setUp() {
        // Initialize the root pane, driver, and the UI
        rootPane = new Pane();
        driver = new gp360EdDisc_GUIdriver(); // Assuming driver is a valid mock or initialized object
        searchPageUI = new SearchPageUI(rootPane, driver);
    }

    @Test
    public void testLogOutButtonFunctionality() {
        Platform.runLater(() -> {
            try {
                // Simulate log out button click
                searchPageUI.btn_logOut.fire();

                // Assert that the username is cleared, indicating logout
                assertEquals("Username should be cleared after logout", "", gp360EdDisc_GUIdriver.USERNAME);
            } catch (Exception e) {
                fail("Exception during testLogOutButtonFunctionality: " + e.getMessage());
            }
        });
        waitForFX();
    }

    @Test
    public void testSendGenericHelpMessage() {
        Platform.runLater(() -> {
            try {
                // Simulate selecting a help topic and clicking the button
                searchPageUI.helpComboBox.getSelectionModel().select("Help with Search");
                searchPageUI.btn_sendGeneric.fire();

                // Assert that the error label is hidden
                assertFalse("Generic help error label should not be visible", searchPageUI.label_GenericError.isVisible());
            } catch (Exception e) {
                fail("Exception during testSendGenericHelpMessage: " + e.getMessage());
            }
        });
        waitForFX();
    }

    @Test
    public void testSendSpecificHelpMessage() {
        Platform.runLater(() -> {
            try {
                // Simulate typing a specific help message and clicking the button
                searchPageUI.text_specificRequest.setText("Need help with a specific issue.");
                searchPageUI.btn_sendSpecific.fire();

                // Assert that the specific error label is hidden and the text field is cleared
                assertFalse("Specific help error label should not be visible", searchPageUI.label_SpecificError.isVisible());
                assertTrue("Specific request text field should be cleared", searchPageUI.text_specificRequest.getText().isEmpty());
            } catch (Exception e) {
                fail("Exception during testSendSpecificHelpMessage: " + e.getMessage());
            }
        });
        waitForFX();
    }

    @Test
    public void testSetGroup() {
        Platform.runLater(() -> {
            try {
                // Simulate entering a group name and clicking the set group button
                searchPageUI.text_group.setText("TestGroup");
                searchPageUI.btn_setCurrGroup.fire();

                // Assert that the current group is set correctly
                assertEquals("Current search group should be set", "TestGroup", gp360EdDisc_GUIdriver.CURRENT_SEARCH_GROUP);
                assertFalse("Group error should not be visible", searchPageUI.label_groupError.isVisible());
            } catch (Exception e) {
                fail("Exception occurred during testSetGroup: " + e.getMessage());
            }
        });
        waitForFX();
    }

    @Test
    public void testSearchByWordOrPhrase() {
        Platform.runLater(() -> {
            try {
                // Simulate entering a word/phrase and clicking the search button
                searchPageUI.text_WoP.setText("searchTerm");
                searchPageUI.btn_searchWoP.fire();

                // Assert that the search error label is hidden
                assertFalse("Search error label should not be visible", searchPageUI.label_searchError.isVisible());
            } catch (Exception e) {
                fail("Exception occurred during testSearchByWordOrPhrase: " + e.getMessage());
            }
        });
        waitForFX();
    }

    @Test
    public void testSearchByID() {
        Platform.runLater(() -> {
            try {
                // Simulate entering an article ID and clicking the view button
                searchPageUI.text_ID.setText("123");
                searchPageUI.btn_view.fire();

                // Assert that the ID error label is hidden
                assertFalse("ID error label should not be visible", searchPageUI.label_idError.isVisible());
            } catch (Exception e) {
                fail("Exception occurred during testSearchByID: " + e.getMessage());
            }
        });
        waitForFX();
    }

    /**
     * Helper method to wait for JavaFX tasks to complete.
     */
    private void waitForFX() {
        try {
            Thread.sleep(500); // Small delay to allow JavaFX tasks to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
