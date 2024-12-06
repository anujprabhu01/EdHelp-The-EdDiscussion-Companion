package groupProjectEdDisc;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper_Testing {

    private DatabaseHelper dbHelper;

    @Before
    public void setUp() throws SQLException {
        dbHelper = new DatabaseHelper();
        dbHelper.connectToDatabase(); // Ensure the database is initialized
    }

    @After
    public void tearDown() {
        dbHelper.closeConnection(); // Close the connection after tests
    }

    // Test if tables are created successfully
    @Test
    public void testCreateTables() throws SQLException {
        assertTrue("Users table should exist", tableExists("CSE360USERS"));
        assertTrue("Invitations table should exist", tableExists("INVITATIONS"));
        assertTrue("Articles table should exist", tableExists("ARTICLES"));
        assertTrue("Groups table should exist", tableExists("GROUPS"));
    }

    // Test if the database is empty initially
    @Test
    public void testIsDatabaseEmpty() throws SQLException {
        assertTrue("Database should be empty initially", dbHelper.isDatabaseEmpty());
    }

    // Test user registration
    @Test
    public void testRegisterUser() throws SQLException {
        dbHelper.register("testUser", "password123", true, false, false, false, false);
        assertFalse("Database should not be empty after registration", dbHelper.isDatabaseEmpty());
    }

    // Test inviting a user
    @Test
    public void testInviteUser() {
        boolean success = dbHelper.inviteUser("INVITE123", true, false, true);
        assertTrue("User invite should succeed", success);
    }

    // Test article addition and retrieval
    @Test
    public void testAddAndRetrieveArticle() throws Exception {
        dbHelper.addArticleWithID(1, "Advanced", "Group1", "Read", "Sample Title", "Descriptor", "Keywords", "Body Content", "References");
        assertTrue("Article ID 1 should exist", dbHelper.articleIdExists(1L));
        assertEquals("Title of the article should match", "Sample Title", dbHelper.getTitle(1L));
    }

    // Test deleting an article
    @Test
    public void testDeleteArticle() throws Exception {
        dbHelper.addArticleWithID(2, "Intermediate", "Group2", "Write", "Delete Me", "Descriptor", "Keywords", "Body", "Ref");
        assertTrue("Article ID 2 should exist", dbHelper.articleIdExists(2L));
        boolean deleted = dbHelper.deleteArticleWithID(2);
        assertTrue("Article ID 2 should be deleted", deleted);
        assertFalse("Article ID 2 should no longer exist", dbHelper.articleIdExists(2L));
    }

    // Test backing up and restoring the database
    @Test
    public void testBackupAndRestoreDatabase() throws Exception {
        String backupFile = "backupTest.csv";

        // Add an article, back it up, then clear and restore
        dbHelper.addArticleWithID(3, "Basic", "Group3", "Execute", "Backup Title", "Descriptor", "Keywords", "Body", "Ref");
        dbHelper.backupDatabase(backupFile);

        dbHelper.clearDatabase();
        assertFalse("Database should be empty after clearing", dbHelper.articleIdExists(3L));

        dbHelper.restoreDatabase(backupFile);
        assertTrue("Database should contain restored article", dbHelper.articleIdExists(3L));

        // Clean up backup file
        new File(backupFile).delete();
    }

    // Test merging databases
    @Test
    public void testMergeDatabase() throws Exception {
        String mergeFile = "mergeTest.csv";

        // Add an article, back it up, then merge it into the database
        dbHelper.addArticleWithID(4, "Merge", "Group4", "Read", "Merge Title", "Descriptor", "Keywords", "Body", "Ref");
        dbHelper.backupDatabase(mergeFile);

        dbHelper.clearDatabase();
        dbHelper.mergeDatabase(mergeFile);
        assertTrue("Database should contain merged article", dbHelper.articleIdExists(4L));

        // Clean up merge file
        new File(mergeFile).delete();
    }

    // Helper method to check if a table exists in the database
    private boolean tableExists(String tableName) throws SQLException {
        // Directly use the connection from dbHelper's private connection field via reflection
        try {
            java.lang.reflect.Field connectionField = DatabaseHelper.class.getDeclaredField("connection");
            connectionField.setAccessible(true); // Allow access to the private field
            Connection connection = (Connection) connectionField.get(dbHelper);

            String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, tableName.toUpperCase());
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Unable to access the connection field in DatabaseHelper", e);
        }
    }
}
