package no.ntnu.stud.ubilearn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{

	// Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "UbiLearn";
 
    // Table Names
    private static final String TABLE_ARTICLE = "Article";
    private static final String TABLE_CATEGORY = "Category";
    private static final String TABLE_CATEGORY_ARTICLE_CATEGORY = "CategoryArticleCategory";
 
    // Common column names
    private static final String KEY_ID = "objectId";
    private static final String KEY_CREATED_AT = "createdAt";
    private static final String KEY_TITLE = "title";
 
    // Article Table - column names
    private static final String KEY_CONTENT = "content";
 
    // Category Table - column names
 
    // CategoryArticleCategory Table - column names
    private static final String KEY_PARENT_CATEGORY_ID = "parent_category_id";
    private static final String KEY_CHILD_CATEGORY_ID = "child_cateory_id";
    private static final String KEY_CHILD_ARTICLE_ID = "child_article_id";

    

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//the syntax for creating tables
		final String CREATE_ARTICLE_TABLE = "CREATE TABLE " + TABLE_ARTICLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_CONTENT + " TEXT, "
				+ KEY_CREATED_AT + " DATETIME" + ")";

		final String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
				+ KEY_CREATED_AT + " DATETIME" + ")";
		
		final String CREATE_CATEGORY_ARTICLE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY_ARTICLE_CATEGORY + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PARENT_CATEGORY_ID + " INTEGER," + KEY_CHILD_ARTICLE_ID + " INTEGER, "
				+ KEY_CHILD_CATEGORY_ID + " INTEGER," + KEY_CREATED_AT + " DATETIME" + ")";

		//invoking the method for creating the actual tables on the disk
		db.execSQL(CREATE_ARTICLE_TABLE);
		db.execSQL(CREATE_CATEGORY_TABLE);
		db.execSQL(CREATE_CATEGORY_ARTICLE_CATEGORY_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 Log.w(LOG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			    db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
			    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
			    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY_ARTICLE_CATEGORY);
			    onCreate(db);		
	}

}
