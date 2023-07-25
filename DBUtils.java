package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Student Name: Ricky Wong, Emma Zhang
 * Student ID: N01581738, N01587845
 * Section: IGA
 * Logic: The class contains the IO methods related to the database.
 */

public class DBUtils {

	//Declare the connection object and database credentials
	private Connection connection;
	private final String URL = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
	private final String USERNAME = "n01587845";
	private final String PASSWORD = "oracle";

	//create the constructor
	public DBUtils() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	//prepared statement for viewing a staff information base on the id
	public PreparedStatement getPreparedViewStatement() throws SQLException {
		String pstmtViem = "SELECT * FROM Staff WHERE id = ?";
		return connection.prepareStatement(pstmtViem);
	}
	
	//prepared statement for inserting a new data into the table
	public PreparedStatement getPreparedInsertStatement() throws SQLException {
		String pstmtInsert = "INSERT INTO Staff(id, lastName, firstName, mi, address, city, state, telephone)"
				+ "Values(?, ?, ?, ?, ?, ?, ?, ?)";
		return connection.prepareStatement(pstmtInsert);
	}

	//prepared statement for updating a data stored in the table, set all columns except the ID to be update-able
	public PreparedStatement getPreparedUpdateStatement() throws SQLException {
		String pstmtUpdate = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, "
				+ "address = ?, city = ?, state = ?, telephone = ?" + "WHERE id = ?";
		return connection.prepareStatement(pstmtUpdate);
	}
	
	//check if the id exists in the database
	public boolean idExists(String id) throws SQLException {
		PreparedStatement checkId = connection.prepareStatement("SELECT * FROM Staff WHERE id = ?");
		checkId.setString(1, id);
		ResultSet rs = checkId.executeQuery();

		return rs.next();
	}

	//close the database connection
	public void closeConnection() throws SQLException, NullPointerException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Error: Unable to close the database connection.");
				e.printStackTrace();
				throw new SQLException();
			} catch (NullPointerException e) {
				System.out.println("Error: Unable to close the database connection.");
				e.printStackTrace();
				throw new NullPointerException();
			}
		}
	}
}