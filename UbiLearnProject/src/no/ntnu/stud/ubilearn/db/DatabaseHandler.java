package no.ntnu.stud.ubilearn.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * This class handles the creation and deleteion of the database. Whenever the database structur is updated, it will create a new database.
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper{

	// Logcat tag
    static final String LOG = "DatabaseHandler";
 
    // Database Version
    static final int DATABASE_VERSION = 21;


    // Database Name
    static final String DATABASE_NAME = "UbiLearn";
 
    // Table Names
    static final String TABLE_ARTICLE = "Article";
    static final String TABLE_CATEGORY = "Category";
    static final String TABLE_QUIZ = "Quiz";
    static final String TABLE_CASE_PATIENT = "CasePatient";
    static final String TABLE_PATIENT = "Patient";
    static final String TABLE_STANDUP_SPPB = "StandUpSPPB";
    static final String TABLE_BALANCE_SPPB = "BalanceSPPB";
    static final String TABLE_WALKING_SPPB = "WalkingSPPB";
    static final String TABLE_EXERCISE = "Exercise";
    static final String TABLE_EXERCISE_IMAGE = "ExerciseImage";
    
    // Common column names
    static final String KEY_OBJECT_ID = "objectId";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_CREATED_AT = "createdAt";
    static final String KEY_PARENT_ID = "parentId";
    static final String KEY_OWNER_ID = "ownerId";


    // Article Table - column names
    static final String KEY_TITLE = "title";
    static final String KEY_CONTENT = "content";
 
 
    // Patient Table - column names
    static final String KEY_AGE = "age";
    static final String KEY_GENDER = "gender";
    static final String KEY_INFO = "info";
    static final String KEY_LEVEL = "level";
    static final String KEY_PROBLEMS = "Problems";
    static final String KEY_COMMENT = "Comment";
    
    // quiz Table - column names
    static final String KEY_QUESTION = "question";
    static final String KEY_ANSWERS = "answer";
    static final String KEY_CORRECT = "correct";
    
    // tests Table - column names
    static final String KEY_PATIENT_ID = "patientId";
    static final String KEY_TIME = "Time";
    static final String KEY_NO_AID = "NoAid";
    static final String KEY_CRUTCHES = "Crutches";
    static final String KEY_ROLLATER = "Rollater";
    static final String KEY_OTHER = "Other";
    static final String KEY_PAIRED_SCORE = "PairedScore";
    static final String KEY_SEMI_TANDEM_SCORE = "SemiTandemScore";
    static final String KEY_TANDEM_SCORE = "TandemScore";
    static final String KEY_FAILED = "Failed";
    static final String KEY_SEAT_HEIGHT = "SeatHeight";
    
    //exercise - column names
    static final String  KEY_TEXT = "Text";

    //exerciseImage - column names
    static final String KEY_IMAGE = "Image";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//the syntax for creating tables
		final String CREATE_ARTICLE_TABLE = "CREATE TABLE " + TABLE_ARTICLE + "(" + KEY_OBJECT_ID + " TEXT PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_CONTENT + " TEXT, "
				+ KEY_CREATED_AT + " DATETIME," + KEY_PARENT_ID + " TEXT" + ")";


		final String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "(" + KEY_OBJECT_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_CREATED_AT + " DATETIME," + KEY_PARENT_ID + " TEXT" + ")";
		
		final String CREATE_QUIZ_TABLE = "CREATE TABLE " + TABLE_QUIZ + "(" + KEY_OBJECT_ID + " TEXT PRIMARY KEY," + KEY_QUESTION + " TEXT,"
				+ KEY_ANSWERS + " TEXT," + KEY_CORRECT + " TEXT," + KEY_OWNER_ID + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";
		
		final String CREATE_CASE_PATIENT_TABLE = "CREATE TABLE " + TABLE_CASE_PATIENT + "(" + KEY_OBJECT_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_AGE + " TEXT," + KEY_GENDER + " TEXT," + KEY_INFO + " TEXT," + KEY_LEVEL + " INTEGER," + KEY_CREATED_AT + " DATETIME" + ")";
		
		final String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_PATIENT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
				+ KEY_AGE + " TEXT," + KEY_PROBLEMS + " TEXT," + KEY_COMMENT + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

		final String CREATE_BALANCE_SPPB_TABLE = "CREATE TABLE " + TABLE_BALANCE_SPPB + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
				+ KEY_PATIENT_ID + " TEXT," + KEY_FAILED + " INTEGER," + KEY_PAIRED_SCORE + " INTEGER," + KEY_SEMI_TANDEM_SCORE + " INTEGER, " + KEY_TANDEM_SCORE + " INTEGER, " + KEY_CREATED_AT + " DATETIME" + ")";
		
		final String CREATE_WALKING_SPPB_TABLE = "CREATE TABLE " + TABLE_WALKING_SPPB + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
				+ KEY_PATIENT_ID + " TEXT," + KEY_FAILED + " INTEGER," + KEY_TIME + " REAL," + KEY_NO_AID + " INTEGER," + KEY_CRUTCHES + " INTEGER," + KEY_ROLLATER + " INTEGER, " + KEY_OTHER + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";
		
		final String CREATE_STANDUP_SPPB_TABLE = "CREATE TABLE " + TABLE_STANDUP_SPPB + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
				+ KEY_PATIENT_ID + " TEXT," + KEY_FAILED + " INTEGER," + KEY_TIME + " REAL," + KEY_SEAT_HEIGHT + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";
		final String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISE + "(" + KEY_OBJECT_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_TEXT + " TEXT, "
				+ KEY_CREATED_AT + " DATETIME" + ")";
		final String CREATE_EXERCISE_IMAGE_TABLE = "CREATE TABLE " + TABLE_EXERCISE_IMAGE + "(" + KEY_OBJECT_ID + " TEXT PRIMARY KEY," + KEY_IMAGE + " BLOB, "
				+ KEY_CREATED_AT + " DATETIME," + KEY_OWNER_ID + " TEXT" + ")";

		//invoking the method for creating the actual tables on the disk
		Log.i(LOG, "Creating tables");
		db.execSQL(CREATE_ARTICLE_TABLE);
		logColumns(db, TABLE_ARTICLE);
		
		db.execSQL(CREATE_CATEGORY_TABLE);
		logColumns(db, TABLE_CATEGORY);
		
		db.execSQL(CREATE_QUIZ_TABLE);
		logColumns(db, TABLE_QUIZ);
		
		db.execSQL(CREATE_CASE_PATIENT_TABLE);
		logColumns(db, TABLE_CASE_PATIENT);
		
		db.execSQL(CREATE_PATIENT_TABLE);
		logColumns(db,TABLE_PATIENT);
		
		db.execSQL(CREATE_STANDUP_SPPB_TABLE);
		logColumns(db,TABLE_STANDUP_SPPB);
		
		db.execSQL(CREATE_WALKING_SPPB_TABLE);
		logColumns(db,TABLE_WALKING_SPPB);
			
		db.execSQL(CREATE_BALANCE_SPPB_TABLE);
		logColumns(db,TABLE_BALANCE_SPPB);
		
		db.execSQL(CREATE_EXERCISE_TABLE);
		logColumns(db,TABLE_EXERCISE);
		
		db.execSQL(CREATE_EXERCISE_IMAGE_TABLE);
		logColumns(db,TABLE_EXERCISE_IMAGE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 Log.w(LOG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			   
		 		
		 		drop(db, TABLE_ARTICLE);
			    drop(db, TABLE_CATEGORY);
			    drop(db, TABLE_QUIZ);
			    drop(db, TABLE_CASE_PATIENT);
			    drop(db, TABLE_PATIENT);
			    drop(db, TABLE_BALANCE_SPPB);
			    drop(db, TABLE_WALKING_SPPB);
			    drop(db, TABLE_STANDUP_SPPB);
			    drop(db, TABLE_EXERCISE);
			    drop(db,TABLE_EXERCISE_IMAGE);
			    onCreate(db);		
	}
	/**
	 * Logs the names of the columns in a table
	 * @param db the database to look for the table
	 * @param table the table to be logged
	 */
	private void logColumns(SQLiteDatabase db, String table){
		Cursor result = db.rawQuery("PRAGMA table_info(" + table + ")", null);
		Log.i(LOG,"Successfully created table " + table);
	    if ( result.moveToFirst() ) {
	        do {
	            Log.i(LOG,"col: " + result.getString(1));
	        } while (result.moveToNext());
	    }
	}
	/**
	 * Deletes the whole table from the database
	 * @param db the database where the table will be deleted from
	 * @param table the name of the table to be deleted
	 */
	private void drop(SQLiteDatabase db, String table){
 		db.execSQL("DROP TABLE IF EXISTS " + table);
 		Log.i(LOG, "Dropping " + table);
	}

}
