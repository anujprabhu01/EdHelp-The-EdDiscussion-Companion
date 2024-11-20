package groupProjectEdDisc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import org.h2.tools.*;


class DatabaseHelper {

	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/firstDatabase";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 
	
	private Connection connection = null;
	private Statement statement = null; 
	//	PreparedStatement pstmt
	
	private Server h2Console;
	
	
	
	public void startH2Console() {
        try {
            // Start the web console on port 8082
            h2Console = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("H2 console started at http://localhost:8082");
        } catch (SQLException e) {
            System.err.println("Failed to start H2 console: " + e.getMessage());
        }
    }

    public void stopH2Console() {
        if (h2Console != null) {
            h2Console.stop();
            System.out.println("H2 console stopped.");
        }
    }
    
	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement(); 
			createTables();  // Create the necessary tables if they don't exist
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}

	private void createTables() throws SQLException {
		String userTable = "CREATE TABLE IF NOT EXISTS cse360users ("
				+ "username VARCHAR(255) UNIQUE, "
				+ "password VARCHAR(255), "
				+ "email VARCHAR(255), "
				+ "firstName VARCHAR(255), "
				+ "middleName VARCHAR(255), "
				+ "lastName VARCHAR(255), "
				+ "prefName VARCHAR(255), "
				+ "admin BOOLEAN, "
				+ "instructor BOOLEAN, "
				+ "student BOOLEAN, "
				+ "finishedSetup BOOLEAN, "
				+ "needsPassReset BOOLEAN, "
				+ "specificRequests VARCHAR(255), "
				+ "generalRequests  VARCHAR(255)) ";
		
		String invitationTable = "CREATE TABLE IF NOT EXISTS invitations ("
	            + "code VARCHAR(255) PRIMARY KEY, "
	            + "role_admin BOOLEAN, "
	            + "role_instructor BOOLEAN, "
	            + "role_student BOOLEAN, "
	            + "is_used BOOLEAN DEFAULT FALSE)";
		
		String articleTable = "CREATE TABLE IF NOT EXISTS articles ("
				+ "id LONG AUTO_INCREMENT PRIMARY KEY, "
				+ "level VARCHAR(255), "
				+ "groups VARCHAR(255), "
				+ "permissions VARCHAR(255), "
				+ "title VARCHAR(255), "
				+ "descriptor VARCHAR(255), "
				+ "keywords VARCHAR(255), "
				+ "body VARCHAR(255), "
				+ "reference VARCHAR(255))";
		
		String groupsTable = "CREATE TABLE IF NOT EXISTS groups ("
				+ "id LONG AUTO_INCREMENT PRIMARY KEY, "
				+ "groupName VARCHAR(255), "
				+ "specialAccess BOOLEAN, "
				+ "admins VARCHAR(255), "
				+ "students VARCHAR(255), "
				+ "instructors VARCHAR(255), "
				+ "firstInstructor VARCHAR(255))";
			
		statement.execute(userTable);
		statement.execute(invitationTable);
		statement.execute(articleTable);
		statement.execute(groupsTable);
	}


	// Check if the database is empty
	public boolean isDatabaseEmpty() throws SQLException {
		String query = "SELECT COUNT(*) AS count FROM cse360users";
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			return resultSet.getInt("count") == 0;
		}
		return true;
	}
	
	public boolean groupExists(String group) {
	    String query = "SELECT COUNT(*) FROM groups WHERE groupName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, group);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // If the count is greater than 0, the group exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume group doesn't exist
	}
	
	public String search(String wp, String group, String level) {
	    String query = "SELECT * FROM articles WHERE 1=1";
	    
	    if (!"".equals(group)) {
	        query += " AND LOWER(groups) LIKE LOWER(?)";  
	    }
	    
	    if (!"all".equalsIgnoreCase(level)) {
	        query += " AND LOWER(level) = LOWER(?)";  
	    }
	    
	    query += " AND (LOWER(title) LIKE LOWER(?) OR LOWER(descriptor) LIKE LOWER(?) OR LOWER(keywords) LIKE LOWER(?))";

	    int count = 0;
	    StringBuilder searchResults = new StringBuilder();
	    String front = "Active Group: " + (group.isEmpty() ? "None" : group) + "\nArticles found: ";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        int index = 1;

	        // Set parameters for group filter, if necessary
	        if (!"".equalsIgnoreCase(group)) {
	            pstmt.setString(index++, "%" + group + "%"); 
	        }

	        // Set parameters for level filter, if necessary
	        if (!"all".equalsIgnoreCase(level)) {
	            pstmt.setString(index++, level);
	        }

	        String searchPattern = "%" + wp.trim() + "%";
	        pstmt.setString(index++, searchPattern);
	        pstmt.setString(index++, searchPattern);
	        pstmt.setString(index, searchPattern);
	        

	        // Execute the query and process the results
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            count++;
	            // Construct formatted search results
	            searchResults.append("ID: ").append(rs.getLong("id"))
	                         .append(", Title: ").append(rs.getString("title"))
	                         .append(", Level: ").append(rs.getString("level"))
	                         .append(", Descriptor: ").append(rs.getString("descriptor"))
	                         .append("\n");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "Error during search execution.";
	    }
	    String divider = "================================================\n";
	    return front + count + "\n" + divider + searchResults.toString();
	}
	
	public String listGroup(String group) {
	    StringBuilder result = new StringBuilder();
	    result.append("Articles in Group: ").append(group).append("\n");
	    String query = "SELECT * FROM articles WHERE groups LIKE ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, "%" + group + "%");
	        
	        ResultSet rs = pstmt.executeQuery();

	        int count = 0;
	        while (rs.next()) {
	            count++;
	            result.append("ID: ").append(rs.getLong("id"))
		              .append(", Title: ").append(rs.getString("title"))
		              .append(", Level: ").append(rs.getString("level"))
		              .append(", Descriptor: ").append(rs.getString("descriptor"))
		              .append("\n");
	        }

	        if (count == 0) {
	            result.append("\nNo articles found in this group.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "Error retrieving articles.";
	    }

	    return result.toString();
	}

	public void register(String username, String password, boolean admin, boolean instructor, boolean student , boolean finishedSetup, boolean needsPassReset) throws SQLException {
		String insertUser = "INSERT INTO cse360users (username, password, email, firstName, middleName, lastName, prefName, admin, instructor, student, finishedSetup, needsPassReset, specificRequests, generalRequests) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, "");
			pstmt.setString(4, "");
			pstmt.setString(5, "");
			pstmt.setString(6, "");
			pstmt.setString(7, "");
			pstmt.setBoolean(8, admin);
			pstmt.setBoolean(9, instructor);
			pstmt.setBoolean(10, student);
			pstmt.setBoolean(11, finishedSetup);
			pstmt.setBoolean(12, needsPassReset);
			pstmt.setString(13, "");
			pstmt.setString(14, "");
			
			pstmt.executeUpdate();
		}
	}
	
	public boolean inviteUser(String invCode, boolean isAdmin, boolean isInstructor, boolean isStudent) {
		String insertSQL = "INSERT INTO invitations (code, role_admin, role_instructor, role_student) VALUES (?, ?, ?, ?)";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, invCode);
            preparedStatement.setBoolean(2, isAdmin);
            preparedStatement.setBoolean(3, isInstructor);
            preparedStatement.setBoolean(4, isStudent);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User invited successfully with code: " + invCode);
                return true;
            } else {
                System.out.println("Failed to invite user.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public void finishSetupAccountDB(String email, String firstName, String middleName, String lastName, String prefName) throws SQLException {
		String user = gp360EdDisc_GUIdriver.USERNAME;
		String query = "UPDATE cse360users SET email = ?, firstName = ?, middleName = ?, lastName = ?, prefName = ?, finishedSetup = ? WHERE username = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, email);
			pstmt.setString(2, firstName);
			pstmt.setString(3, middleName);
			pstmt.setString(4, lastName);
			pstmt.setString(5, prefName);
			pstmt.setBoolean(6, true);
			pstmt.setString(7, user);
			pstmt.executeUpdate();
		}
	
	}
	
	public void sendGeneralRequest(String request) throws SQLException {
		String user = gp360EdDisc_GUIdriver.USERNAME;
		String querySelect = "SELECT generalRequests FROM cse360users WHERE username = ?";
	    String queryUpdate = "UPDATE cse360users SET generalRequests = ? WHERE username = ?";

	    try (PreparedStatement pstmtSelect = connection.prepareStatement(querySelect);
	            PreparedStatement pstmtUpdate = connection.prepareStatement(queryUpdate)) {

	           // Fetch the current value of generalRequests
	           pstmtSelect.setString(1, user);
	           ResultSet rs = pstmtSelect.executeQuery();
	           String currentRequests = "";
	           if (rs.next()) {
	               currentRequests = rs.getString("generalRequests");
	           }

	           // Append the new request
	           if (currentRequests != null && !currentRequests.isEmpty()) {
	               currentRequests += request + ";";
	           } else {
	               currentRequests = request + ";";
	           }

	           // Update the database
	           pstmtUpdate.setString(1, currentRequests);
	           pstmtUpdate.setString(2, user);
	           pstmtUpdate.executeUpdate();
	       }
	}
	
	public void sendSpecificRequest(String request) throws SQLException {
		String user = gp360EdDisc_GUIdriver.USERNAME;
	    String querySelect = "SELECT specificRequests FROM cse360users WHERE username = ?";
	    String queryUpdate = "UPDATE cse360users SET specificRequests = ? WHERE username = ?";

	    try (PreparedStatement pstmtSelect = connection.prepareStatement(querySelect);
	         PreparedStatement pstmtUpdate = connection.prepareStatement(queryUpdate)) {

	        // Fetch the current value of specificRequests
	        pstmtSelect.setString(1, user);
	        ResultSet rs = pstmtSelect.executeQuery();
	        String currentRequests = "";
	        if (rs.next()) {
	            currentRequests = rs.getString("specificRequests");
	        }

	        // Append the new request
	        if (currentRequests != null && !currentRequests.isEmpty()) {
	            currentRequests += request + ";";
	        } else {
	            currentRequests = request + ";";
	        }

	        // Update the database
	        pstmtUpdate.setString(1, currentRequests);
	        pstmtUpdate.setString(2, user);
	        pstmtUpdate.executeUpdate();
	    }
	}
	
	
	
	public boolean hasTwoOrMoreRoles() throws SQLException {
		String username = gp360EdDisc_GUIdriver.USERNAME;
	   
	    String query = "SELECT (CASE WHEN admin = true THEN 1 ELSE 0 END + " +
	                   "CASE WHEN instructor = true THEN 1 ELSE 0 END + " +
	                   "CASE WHEN student = true THEN 1 ELSE 0 END) AS roleCount " +
	                   "FROM cse360users WHERE username = ?";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, username);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                int roleCount = rs.getInt("roleCount");
	                return roleCount >= 2;  // Return true if two or more roles are true
	            }
	        }
	    }

	    return false;
	
	}
	
	public boolean getFinishSetup() throws SQLException {
		String username = gp360EdDisc_GUIdriver.USERNAME;
	   
		String query = "SELECT finishedSetup FROM cse360users WHERE username = ?";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, username);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                // Check the value of finishedSetup
	                boolean finishedSetup = rs.getBoolean("finishedSetup");
	                // Return true if setup is not finished, otherwise false
	                return finishedSetup;
	            }
	        }
	    }
	    return false;
	    
    }
	
	public String oneRoleReturn() throws SQLException {
	    // Check if the user has more than one role using the hasTwoOrMoreRoles method
	    if (!hasTwoOrMoreRoles()) {
	    	String username = gp360EdDisc_GUIdriver.USERNAME;
		    String query = "SELECT admin, instructor, student FROM cse360users WHERE username = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, username);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                if (rs.getBoolean("admin")) {
		                    return "admin";
		                } else if (rs.getBoolean("instructor")) {
		                    return "instructor";
		                } else if (rs.getBoolean("student")) {
		                    return "student";
		                }
		            }
		        }
		    } 
	    }
	    return null;
	}
    	

	public boolean login(String username, String password) throws SQLException {
		String query = "SELECT * FROM cse360users WHERE username = ? AND password = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	public boolean doesUserExist(String email) {
	    String query = "SELECT COUNT(*) FROM cse360users WHERE email = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, email);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // If the count is greater than 0, the user exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume user doesn't exist
	}
	
	public boolean usernameExistsInDB(String username) throws SQLException {
	    String query = "SELECT COUNT(*) FROM cse360users WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            // If the count is greater than 0, the username exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume the username doesn't exist
	}
	
	public boolean articleIdExists(Long id) throws SQLException {
		//Sam's code
		String query = "SELECT COUNT(*) FROM articles WHERE id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            // If the count is greater than 0, the id exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume the username doesn't exist
	}
	
	
	public boolean isInvCodeValid(String invCode) throws SQLException { //checks if code exists and if its unused: if yes, then valid code
	    String query = "SELECT COUNT(*) FROM invitations WHERE code = ? AND is_used = false";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, invCode);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            // If the count is greater than 0, the invite code exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume the invite code doesn't exist
	}
	
	public boolean isAdminForInvCode(String invCode) throws SQLException {
	    String query = "SELECT role_admin FROM invitations WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, invCode);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("role_admin"); // Returns true if role_admin is true, else false
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs or code doesn't exist, return false
	}

	public boolean isInstructorForInvCode(String invCode) throws SQLException {
	    String query = "SELECT role_instructor FROM invitations WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, invCode);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("role_instructor"); // Returns true if role_instructor is true, else false
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs or code doesn't exist, return false
	}

	public boolean isStudentForInvCode(String invCode) throws SQLException {
	    String query = "SELECT role_student FROM invitations WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, invCode);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("role_student"); // Returns true if role_student is true, else false
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs or code doesn't exist, return false
	}
	
	public boolean markInvCodeAsUsed(String invCode) {
	    String updateSQL = "UPDATE invitations SET is_used = ? WHERE code = ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
	        preparedStatement.setBoolean(1, true); // Set is_used to true
	        preparedStatement.setString(2, invCode); // Specify the invite code

	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0; // Return true if the update was successful
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Return false if an error occurs
	    }
	}
////////////////////NEW FUNCTION JAKE
	public boolean setPassword(String newPass, String email) {
		String query = "UPDATE cse360users SET  password = ?, needsPassReset = ? WHERE email = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, newPass);
			pstmt.setBoolean(2, true);
			pstmt.setString(3, email);
			int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false; // Return false if an error occurs
		}
	}
	
	public boolean setPasswordWithUSER(String newPass) {
		String query = "UPDATE cse360users SET  password = ?, needsPassReset = ? WHERE username = ?";
		String username = gp360EdDisc_GUIdriver.USERNAME;
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, newPass);
			pstmt.setBoolean(2, false);
			pstmt.setString(3, username);
			int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false; // Return false if an error occurs
		}
	}
	
	
	public boolean getNeedPassReset() throws SQLException {
		String username = gp360EdDisc_GUIdriver.USERNAME;
	   
		String query = "SELECT needsPassReset FROM cse360users WHERE username = ?";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, username);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                // Check the value of finishedSetup
	                boolean needsPassReset = rs.getBoolean("needsPassReset");
	                // Return true if setup is not finished, otherwise false
	                return needsPassReset;
	            }
	        }
	    }
	    return false;
	    
    }
	
	public boolean deleteUser(String username) {
	    String deleteSQL = "DELETE FROM cse360users WHERE username = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
	        preparedStatement.setString(1, username); // Set the username in the query

	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0; // Return true if the user was deleted
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Return false if an error occurs
	    }
	}
////////////////////Changed FUNCTION JAKE
	public String listUserAccounts() {
	    String query = "SELECT username, firstName, lastName, admin, instructor, student FROM cse360users";
	    StringBuilder result = new StringBuilder();

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        ResultSet rs = pstmt.executeQuery();

	        result.append("User Accounts:\n");
	        while (rs.next()) {
	            String username = rs.getString("username");
	            String firstName = rs.getString("firstName");
	            String lastName = rs.getString("lastName");
	            String fullName = firstName + " " + lastName; // Concatenate first and last name
	            
	            // Collect role codes
	            StringBuilder roles = new StringBuilder();
	            if (rs.getBoolean("admin")) {
	                roles.append("Admin ");
	            }
	            if (rs.getBoolean("instructor")) {
	                roles.append("Instructor ");
	            }
	            if (rs.getBoolean("student")) {
	                roles.append("Student ");
	            }

	            // Append user account details to the result StringBuilder
	            result.append("Username: ").append(username)
	                  .append(", Name: ").append(fullName)
	                  .append(", Roles: ").append(roles.toString().trim())
	                  .append("\n");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return result.toString();
	}
////////////
	public String getRolesForSet(String username) throws SQLException{
		String query = "SELECT admin, instructor, student FROM cse360users WHERE username = ?";
		String getRolesSQL = "Username: " + username + "\nAdmin: ";
		
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, username);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                if (rs.getBoolean("admin")) {
	                	getRolesSQL += "True, Instructor: ";
	                } else {
	                	getRolesSQL += "False, Instructor: ";
	                }
	                if (rs.getBoolean("instructor")) {
	                	getRolesSQL += "True, Student: ";
	                } else {
	                	getRolesSQL += "False, Student: ";
	                }
	                if (rs.getBoolean("student")) {
	                	getRolesSQL += "True";
	                }
	                else {
	                	getRolesSQL += "False";
	                }
	            }
	        }
	    }
	    return getRolesSQL;
	}
	
	public void adminRoleSet(boolean admin, boolean instructor, boolean student, String username) throws SQLException {
		String query = "UPDATE cse360users SET admin = ?, instructor = ?, student = ? WHERE username = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setBoolean(1, admin);
			pstmt.setBoolean(2, instructor);
			pstmt.setBoolean(3, student);
			pstmt.setString(4, username);
			pstmt.executeUpdate();
		}
	}


	public boolean isAdminForUsers(String user_username) throws SQLException {
	    String query = "SELECT admin FROM cse360users WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, user_username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("admin"); // Returns true if role_admin is true, else false
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs or code doesn't exist, return false
	}

	public boolean isInstructorForUsers(String user_username) throws SQLException {
	    String query = "SELECT instructor FROM cse360users WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, user_username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("instructor"); // Returns true if role_admin is true, else false
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs or code doesn't exist, return false
	}

	public boolean isStudentForUsers(String user_username) throws SQLException {
	    String query = "SELECT student FROM cse360users WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, user_username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("student"); // Returns true if role_admin is true, else false
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs or code doesn't exist, return false
	}
	
	//ARTICLES
	public void addArticleWithID(int id, String level, String groups, String permissions, String title, String descriptor, String keywords, String body, String references ) throws Exception {
		String query = "INSERT INTO articles (id, level, groups, permissions, title, descriptor, keywords, body, reference) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, level);
			pstmt.setString(3, groups);
			pstmt.setString(4, permissions);
			pstmt.setString(5, title);
			pstmt.setString(6, descriptor);
			pstmt.setString(7, keywords);
			pstmt.setString(8, body);
			pstmt.setString(9, references);
			
			pstmt.executeUpdate();
		}
	}
	
	public void createArticle(String level, String groups, String permissions, String title, String descriptor, String keywords, String body, String references ) throws Exception {
		String query = "INSERT INTO articles (level, groups, permissions, title, descriptor, keywords, body, reference) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, level);
			pstmt.setString(2, groups);
			pstmt.setString(3, permissions);
			pstmt.setString(4, title);
			pstmt.setString(5, descriptor);
			pstmt.setString(6, keywords);
			pstmt.setString(7, body);
			pstmt.setString(8, references);
			
			pstmt.executeUpdate();
		}
	}
	
	public void updateArticle(long id, String level, String groups, String permissions, String title, String descriptor, String keywords, String body, String references) throws Exception {
			// SQL query for updating an article by ID
			String query = "UPDATE articles SET title = ?, level = ?, groups = ?, permissions = ?, descriptor = ?, keywords = ?, body = ?, reference = ? WHERE id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				// Set values for each parameter in the SQL query
				pstmt.setString(1, title);
				pstmt.setString(2, level);
				pstmt.setString(3, groups);
				pstmt.setString(4, permissions);
				pstmt.setString(5, descriptor);
				pstmt.setString(6, keywords);
				pstmt.setString(7, body);
				pstmt.setString(8, references);
				pstmt.setLong(9, id);  // The article ID goes at the end (WHERE clause)
				
				pstmt.executeUpdate();
			}
			
	}
	public String getLevel(Long id) {
		String level;
		String query = "SELECT * FROM articles WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                level = rs.getString("level");
	                return level;
	            }
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "OOPS!";
	}
	public String getGroups(Long id) {
		String groups;
		String query = "SELECT * FROM articles WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                groups = rs.getString("groups");
	                return groups;
	            }
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "OOPS!";
	}
	
	public String getPermissions(Long id) {
		String permissions;
		String query = "SELECT * FROM articles WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                permissions = rs.getString("permissions");
	                return permissions;
	            }
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "OOPS!";
	}
	public String getTitle(Long id) {
		String title;
		String query = "SELECT * FROM articles WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                title = rs.getString("title");
	                return title;
	            }
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "OOPS!";
	}
	public String getDescriptor(Long id) {
		String descriptor;
		String query = "SELECT * FROM articles WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                descriptor = rs.getString("descriptor");
	                return descriptor;
	            }
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "OOPS!";
	}
	public String getKeywords(Long id) {
		String keywords;
		String query = "SELECT * FROM articles WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                keywords = rs.getString("keywords");
	                return keywords;
	            }
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "OOPS!";
	}
	public String getBody(Long id) {
		String body;
		String query = "SELECT * FROM articles WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                body = rs.getString("body");
	                return body;
	            }
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "OOPS!";
	}
	public String getReference(Long id) {
		String reference;
		String query = "SELECT * FROM articles WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                reference = rs.getString("reference");
	                return reference;
	            }
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "OOPS!";
	}
	
	//Backs up the database to a specified file name
	public void backupDatabase(String filename) throws Exception {
		String query = "SELECT id, level, groups, permissions, title, descriptor, keywords, body, reference FROM articles";
	        try (BufferedWriter w = new BufferedWriter(new FileWriter(filename)); //uses BufferedWriter to write to file
	        	PreparedStatement pstmt = connection.prepareStatement(query);
	            ResultSet rs = pstmt.executeQuery()) {
	        	
	        	while (rs.next()) {
	        		//grabbing each value and putting in String
	        		int id = rs.getInt("id");
	                String level = rs.getString("level");
//	                boolean eclipse = rs.getBoolean("eclipseGroup");
//	                boolean intellij = rs.getBoolean("intelliJGroup");
	                String groups = rs.getString("groups");
	                String permissions = rs.getString("permissions");
	                String title = rs.getString("title");
	                String descriptor = rs.getString("descriptor");
	                String keywords = rs.getString("keywords");
	                String body = rs.getString("body");
	                String references = rs.getString("reference");
	                System.out.print("Before the write");
	                w.write(String.format("%d,,,%s,,,%s,,,%s,,,%s,,,%s,,,%s,,,%s,,,%s", id, level, groups, permissions, title, descriptor, keywords, body, references)); //Writing into file in * delimited format
	                w.newLine();
	        	} 
	        }
	         catch (IOException | SQLException e) {
	        	 throw new Exception("Failed to back up the database", e); // Better error handling
	         }
	}
	
	//restores the database using a comma delimited file 
	public boolean restoreDatabase(String filename) throws Exception {
		clearDatabase(); //empties the database to be rep-opulated from the file
		String line;
			try (BufferedReader r = new BufferedReader(new FileReader(filename))) { //BufferedReader is used to read the file input
				while ((line = r.readLine()) != null) { //Loops through line by line
					String[] portions = line.split(",,,"); //Spits each line into sections delimited by the *
					
					int id = Integer.parseInt(portions[0]);
		            String level = portions[1];
//		            boolean eclipse = Boolean.parseBoolean(portions[2]);
//		            boolean intellij = Boolean.parseBoolean(portions[3]);
		            String groups = portions[2];
		            String permissions = portions[3];
		            String title = portions[4];
		            String descriptor = portions[5];
		            String keywords = portions[6];
		            String body = portions[7];
		            String references = portions[8];
		            
		            
		            addArticleWithID(id, level, groups, permissions, title, descriptor, keywords, body, references); //adds each article to the database   
				}
				return true;
			} catch (IOException | SQLException e) {
	       	 e.printStackTrace();
	       	 return false;
	        }
	}
	
	public void mergeDatabase(String filename) throws Exception {
		String line;
			try (BufferedReader r = new BufferedReader(new FileReader(filename))) { //BufferedReader is used to read the file input
				while ((line = r.readLine()) != null) { //Loops through line by line
					String[] portions = line.split(",,,"); //Spits each line into sections delimited by the *
					
					int id = Integer.parseInt(portions[0]);
		            String level = portions[1];
//		            boolean eclipse = Boolean.parseBoolean(portions[2]);
//		            boolean intellij = Boolean.parseBoolean(portions[3]);
		            String groups = portions[2];
		            String permissions = portions[3];
		            String title = portions[4];
		            String descriptor = portions[5];
		            String keywords = portions[6];
		            String body = portions[7];
		            String references = portions[8];
		            
		            if (idExistsInDatabase(id)) {
		            	continue;
		            }
		            addArticleWithID(id, level, groups, permissions, title, descriptor, keywords, body, references); //adds each article to the database   
				}
			} catch (IOException | SQLException e) {
	       	 e.printStackTrace();
	        }
	}
	
	public boolean idExistsInDatabase(int id) throws SQLException {
		String query = "SELECT 1 FROM articles WHERE id = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, id); // Bind the id parameter

	        try (ResultSet rs = pstmt.executeQuery()) {
	            return rs.next(); // Returns true if an article with the given ID exists
	        }
	    }
	}
	
	//Empties the database using DELETE FROM 
	public void clearDatabase() throws Exception {
		String deleteSQL = "DELETE FROM articles"; // Deletes all articles
		    try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
		        pstmt.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	
	public void clearTable() throws SQLException {
	    String query = "DROP TABLE cse360users"; // or "TRUNCATE TABLE cse360users";
	    try (Statement stmt = connection.createStatement()) {
	        stmt.executeUpdate(query);
	        System.out.println("All records deleted from cse360users table.");
	    }
	}
	
	public void closeConnection() {
		try{ 
			if(statement!=null) statement.close(); 
		} catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		try { 
			if(connection!=null) connection.close(); 
		} catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}
	
	// function to print articles, either by group, or all articles
	public String listArticles(String[] selectedGroups, boolean all) {
	    StringBuilder query = new StringBuilder("SELECT * FROM ARTICLES");
	    
	    StringBuilder result = new StringBuilder();
	    result.append("\n====================================\n");
	    result.append("              ARTICLES              ");
	    result.append("\n====================================\n\n");
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(query.toString())) {
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            // Get all fields from the article
	            int id = rs.getInt("ID");
	            String level = rs.getString("LEVEL");
	            String groups = rs.getString("GROUPS");
	            String permissions = rs.getString("PERMISSIONS");
	            String title = rs.getString("TITLE");
	            String descriptor = rs.getString("DESCRIPTOR");
	            String keywords = rs.getString("KEYWORDS");
	            String body = rs.getString("BODY");
	            String reference = rs.getString("REFERENCE");
	            
	            // Check if we should include this article
	            boolean includeArticle = all;  // Include if "All" is selected
	            
	            if (!all && selectedGroups.length > 0) {
	                // Split the article's groups by comma and trim each group
	                String[] articleGroups = groups.split(",\\s*");
	                
	                // Check if any of the selected groups match this article's groups
	                for (String selectedGroup : selectedGroups) {
	                    String trimmedSelectedGroup = selectedGroup.trim();
	                    for (String articleGroup : articleGroups) {
	                        if (articleGroup.trim().equalsIgnoreCase(trimmedSelectedGroup)) {
	                            includeArticle = true;
	                            break;
	                        }
	                    }
	                    if (includeArticle) break;  // No need to check other groups if we found a match
	                }
	            }
	            
	            // If we should include this article, add it to the result
	            if (includeArticle) {
	                result.append(String.format(
	                    "📑 Article ID: %d\n" +
	                    "------------------------------------\n" +
	                    "📚 Level: %s\n" +
	                    "🔷 Groups: %s\n" +
	                    "🔒 Permissions: %s\n\n" +
	                    "📌 Title: %s\n" +
	                    "📝 Descriptor: %s\n" +
	                    "🏷️ Keywords: %s\n\n" +
	                    "📄 Body:\n%s\n\n" +
	                    "📚 Reference:\n%s\n" +
	                    "\n====================================\n\n",
	                    id, 
	                    level, 
	                    groups, 
	                    permissions, 
	                    title, 
	                    descriptor, 
	                    keywords, 
	                    body, 
	                    reference));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "Error retrieving articles: " + e.getMessage();
	    }
	    
	    // Add footer if no results were found
	    if (result.toString().equals("\n====================================\n" +
	                                "              ARTICLES              " +
	                                "\n====================================\n\n")) {
	        result.append("No articles found matching the criteria.\n");
	        result.append("====================================\n");
	    }
	    
	    return result.toString();
	}
	
	public boolean deleteArticleWithID(int id) {
		String query = "DELETE FROM articles WHERE ID = ?";
		
		try(PreparedStatement pstmnt = connection.prepareStatement(query)) {
			pstmnt.setInt(1, id); // set the id in the query to var id
			
			int rowsAffected = pstmnt.executeUpdate();
			return rowsAffected > 0;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false; // return false if exception is caught
		}
	}
	
	public boolean doGroupsExist(String groupsString) throws SQLException {
	    // Split the groups string by semicolon and trim each group
	    String[] groups = groupsString.split(",");
	    
	    for (String group : groups) {
	        String trimmedGroup = group.trim();
	        if (!trimmedGroup.isEmpty()) {
	            String query = "SELECT COUNT(*) FROM groups WHERE groupName = ?";
	            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	                pstmt.setString(1, trimmedGroup);
	                ResultSet rs = pstmt.executeQuery();
	                if (rs.next()) {
	                    if (rs.getInt(1) == 0) {
	                        // If any group doesn't exist, return false
	                        return false;
	                    }
	                }
	            }
	        }
	    }
	    // All groups exist
	    return true;
	}
}