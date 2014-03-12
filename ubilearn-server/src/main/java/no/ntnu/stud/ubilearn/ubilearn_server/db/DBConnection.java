package no.ntnu.stud.ubilearn.ubilearn_server.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Handles DB connectivity, function as wrapper for jdbc connection
 * 
 * @author Espen
 *
 */

public class DBConnection {

	private Connection conn = null;
	private Properties props = new Properties();

	private static DBConnection instance;

	//makes sure that all DAO's uses the same connection
	public static DBConnection getInstance(){
		return instance = (instance == null) ? new DBConnection() : instance;
	}

	public DBConnection(){

		try {
			props.load(new FileInputStream(new File("/database.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates and returns a new PreparedStatemt object
	 * 
	 * @param sql The sql to be used in the statement
	 * @return the new PrepatedStatement
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException{
		connect();
		PreparedStatement st;

		try{
		st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
		return st;
	}
	/**
	 * Closes the provided statement
	 * 
	 * @param st
	 */
	public void close(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
		}
	}

	/**
	 * Closes the provided parameters
	 * 
	 * @param st
	 * @param rs
	 */
	public void close(Statement st, ResultSet rs) {
		close(conn, st, rs);
	}

	/**
	 * Closes all input parameters
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			// if (conn != null)
			// conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the current connection
	 */
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sets up a connection to the database
	 * 
	 * @throws SQLException
	 */
	private void connect() throws SQLException{
		try{
			if(conn==null || conn.isClosed())
				conn = DriverManager.getConnection(props.getProperty("url"), props);
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
	}
}
