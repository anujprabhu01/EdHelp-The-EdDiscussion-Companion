package groupProjectEdDisc;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
				+ "needsPassReset BOOLEAN)";
		
		String invitationTable = "CREATE TABLE IF NOT EXISTS invitations ("
	            + "code VARCHAR(255) PRIMARY KEY, "
	            + "role_admin BOOLEAN, "
	            + "role_instructor BOOLEAN, "
	            + "role_student BOOLEAN, "
	            + "is_used BOOLEAN DEFAULT FALSE)";
		
		statement.execute(userTable);
		statement.execute(invitationTable);
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

	public void register(String username, String password, boolean admin, boolean instructor, boolean student , boolean finishedSetup, boolean needsPassReset) throws SQLException {
		String insertUser = "INSERT INTO cse360users (username, password, email, firstName, middleName, lastName, prefName, admin, instructor, student, finishedSetup, needsPassReset) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

}
