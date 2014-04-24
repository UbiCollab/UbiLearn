package no.ntnu.stud.ubilearn.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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

	public void open() throws SQLException {
		database = dbHandler.getWritableDatabase();
	}

	public void close() {
		dbHandler.close();
	}
	
	protected String dateToString(Date date){
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	        return dateFormat.format(date);
	}
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
	protected void log(String string){
		Log.i(LOG,string);
	}
	
	protected boolean exists(String table, String id){
		String query = selectWhere(table,DatabaseHandler.KEY_OBJECT_ID,id);
		Cursor result = database.rawQuery(query, null);
		return result.moveToFirst();
	}
	protected boolean exists(String table, int id){
		String query = selectWhere(table,DatabaseHandler.KEY_OBJECT_ID,id);
		Cursor result = database.rawQuery(query, null);
		return result.moveToFirst();
	}
	protected String selectWhere(String table, String column, String id){
		return "SELECT * FROM " + table + " WHERE "
	            + column + " = '" + id + "'";
	}
	protected String selectWhere(String table, String column, int id){
		return "SELECT * FROM " + table + " WHERE "
	            + column + " = '" + id + "'";
	}
	
	protected void printTable(String table){
		String query = "SELECT * FROM " + table;
		log(query);
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
					output.append(result.getString(i) + " | ");	
				}
			}while(result.moveToNext());
		}else
			return;
		log(output.toString());
	}

}
