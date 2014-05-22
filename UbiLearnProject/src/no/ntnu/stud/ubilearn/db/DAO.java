package no.ntnu.stud.ubilearn.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public abstract class DAO {

	// database management
	protected SQLiteDatabase database;
	protected DatabaseHandler dbHandler;
	protected final String LOG;


	public DAO(Context context, String childClass) {
		dbHandler = new DatabaseHandler(context);
		LOG = childClass;
	}
	/**
	 * opens a connection to the database
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHandler.getWritableDatabase();
	}
	/**
	 * closes the connection to the database
	 */
	public void close() {
		dbHandler.close();
	}
	/**
	 * converts the Date object to a string, which uses the SQLite format
	 * @param date the date to be converted
	 * @return the converted string
	 */
	protected String dateToString(Date date){
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	        return dateFormat.format(date);
	}
	/**
	 * converts a string in the "yyyy-MM-dd HH:mm:ss" format to a date object
	 * @param date the string to be converted
	 * @return an initialized date object with the correct date
	 */
	protected Date stringToDate(String date){
		 SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Loggs the string with the tag of the class invoking the method
	 * @param string the message to be logged
	 */
	protected void log(String string){
		Log.i(LOG,string);
	}
	/**
	 * Checks if a id exists in a table
	 * @param table the table to ceck for the id
	 * @param id the id to look for
	 * @return a boolean. False if no id found. True if same id found in the table.
	 */
	protected boolean exists(String table, String id){
		String query = selectWhere(table,DatabaseHandler.KEY_OBJECT_ID,id);
		Cursor result = database.rawQuery(query, null);
		return result.moveToFirst();
	}
	/**
	 * Checks if a id exists in a table
	 * @param table the table to ceck for the id
	 * @param id the id to look for
	 * @return a boolean. False if no id found. True if same id found in the table.
	 */
	protected boolean exists(String table, int id){
		String query = selectWhere(table,DatabaseHandler.KEY_ID,id);
		Cursor result = database.rawQuery(query, null);
		return result.moveToFirst();
	}
	/**
	 * Generates a query-string with SQL syntax for the values in the parameters, for an select query on foreign keys
	 * @param table The table to select the values from
	 * @param column the name of the column containing the foreign key
	 * @param id the foreign key
	 * @return a query-string with SQL syntax
	 */
	protected String selectWhere(String table, String column, String id){
		return "SELECT * FROM " + table + " WHERE "
	            + column + " = '" + id + "'";
	}
	/**
	 * Generates a query-string with SQL syntax for the values in the parameters, for an select query on foreign keys
	 * @param table The table to select the values from
	 * @param column the name of the column containing the foreign key
	 * @param id the foreign key
	 * @return a query-string with SQL syntax
	 */
	protected String selectWhere(String table, String column, int id){
		return "SELECT * FROM " + table + " WHERE "
	            + column + " = '" + id + "'";
	}
	/**
	 * Loggs an entire table.
	 * @param table the name of table to be logged
	 */
	protected void printTable(String table){
		String query = "SELECT * FROM " + table;
		log("Printing content of " + table);
		Cursor result = database.rawQuery(query, null);
		StringBuilder output = new StringBuilder();
		if(result.moveToFirst()){
			String[] columns = result.getColumnNames();
			output.append(" | ");
			for (int i = 0; i < columns.length; i++) {
				output.append(columns[i] + " | ");
			}
			do{
				output.append("\n | ");
				int n = result.getColumnCount();
				for (int i = 0; i < n; i++) {
					try{
					output.append(result.getString(i) + " | ");	
					}catch(SQLiteException e){
						output.append("BLOB" + " | ");
					}
				}
			}while(result.moveToNext());
		}else
			return;
		log(output.toString());
	}

}
