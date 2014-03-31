package no.ntnu.stud.ubilearn.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
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

}
