package groupProjectEdDisc;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class gp360EdDisc_GUIdriver_Testing {

    private static gp360EdDisc_GUIdriver driver;
    private static Pane rootPane;
    private static Stage stage;

    @BeforeClass
    public static void setUp() throws Exception {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});

        // Create a new instance of the driver
        driver = new gp360EdDisc_GUIdriver();

        // Set up a JavaFX stage and scene
        rootPane = new Pane();
        stage = new Stage();
        Scene scene = new Scene(rootPane, gp360EdDisc_GUIdriver.WINDOW_WIDTH, gp360EdDisc_GUIdriver.WINDOW_HEIGHT);
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
        });
    }

    @AfterClass
    public static void tearDown() {
        // Close the JavaFX application
        Platform.runLater(stage::close);
    }

    @Test
    public void testLoadLoginPage() {
        Platform.runLater(() -> {
            driver.loadloginPage(); // Call the method to test
            // You can verify behavior by checking what was added to rootPane
            assertTrue(rootPane.getChildren().stream().anyMatch(node -> node instanceof Pane));
        });
        waitForFX();
    }

    @Test
    public void testLoadCreateAccountPage() {
        Platform.runLater(() -> {
            driver.loadCreateAccountPage(); // Call the method to test
            // Check if the root pane contains expected children or properties
            assertTrue(rootPane.getChildren().stream().anyMatch(node -> node instanceof Pane));
        });
        waitForFX();
    }

    @Test
    public void testDatabaseConnection() {
        assertNotNull("Database helper should not be null", gp360EdDisc_GUIdriver.getDBHelper());
    }

    @Test
    public void testWindowDimensions() {
        Platform.runLater(() -> {
            assertEquals("Stage width should match the defined constant", gp360EdDisc_GUIdriver.WINDOW_WIDTH, stage.getWidth(), 0.1);
            assertEquals("Stage height should match the defined constant", gp360EdDisc_GUIdriver.WINDOW_HEIGHT, stage.getHeight(), 0.1);
        });
        waitForFX();
    }

    @Test
    public void testLoadFinishAccountSetup() {
        Platform.runLater(() -> {
            driver.loadFinishAccountSetup();
            assertTrue("Root pane should have children after loading FinishAccountSetup",
                rootPane.getChildren().size() > 0);
        });
        waitForFX();
    }

    private void waitForFX() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {}); // Ensure any pending JavaFX tasks are executed
    }
}
