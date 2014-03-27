package no.ntnu.stud.ubilearn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{

	// Logcat tag
    static final String LOG = "DatabaseHandler";
 
    // Database Version
    static final int DATABASE_VERSION = 1;
 
    // Database Name
    static final String DATABASE_NAME = "UbiLearn";
 
    // Table Names
    static final String TABLE_ARTICLE = "Article";
    static final String TABLE_CATEGORY = "Category";
    static final String TABLE_CATEGORY_ARTICLE_CATEGORY = "CategoryArticleCategory";
 
    // Common column names
    static final String KEY_ID = "id";
    static final String KEY_OBJECT_ID = "objectId";
    static final String KEY_NAME = "name";
    static final String KEY_CREATED_AT = "createdAt";
    static final String KEY_PARENT_ID = "parentId";

 
    // Article Table - column names
    static final String KEY_TITLE = "title";
    static final String KEY_CONTENT = "content";
 
    // Category Table - column names
 
    // CategoryArticleCategory Table - column names
    static final String KEY_PARENT_CATEGORY_ID = "parent_category_id";
    static final String KEY_CHILD_CATEGORY_ID = "child_cateory_id";
    static final String KEY_CHILD_ARTICLE_ID = "child_article_id";

    

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//the syntax for creating tables
		final String CREATE_ARTICLE_TABLE = "CREATE TABLE " + TABLE_ARTICLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OBJECT_ID + " TEXT," + KEY_TITLE + " TEXT," + KEY_CONTENT + " TEXT, "
				+ KEY_CREATED_AT + " DATETIME" + KEY_PARENT_ID + " INTEGER" + ")";


		final String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OBJECT_ID + " TEXT," + KEY_NAME + " TEXT,"
				+ KEY_CREATED_AT + " DATETIME" + ")";

		
		final String CREATE_CATEGORY_ARTICLE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY_ARTICLE_CATEGORY + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OBJECT_ID + " TEXT," + KEY_PARENT_CATEGORY_ID + " INTEGER," + KEY_CHILD_ARTICLE_ID + " INTEGER, "
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