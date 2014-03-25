package no.ntnu.stud.ubilearn.db;

import no.ntnu.stud.ubilearn.fragments.wiki.Article;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class HandbookDAO extends DAO {

	public HandbookDAO(Context context) {
		super(context, "HandbookDAO");
	}
	
	public long insertArticle(Article article){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_ID, article.getId());
		values.put(DatabaseHandler.KEY_OBJECT_ID, article.getObjectId());
		values.put(DatabaseHandler.KEY_TITLE, article.getTitle());
		values.put(DatabaseHandler.KEY_CONTENT, article.getContent());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(article.getCreatedAt()));
		
		long articleId = database.insert(DatabaseHandler.TABLE_ARTICLE,null,values);
		
		return articleId;
	}
	
	public Article getArticle(long id){
		
		String query = "SELECT  * FROM " + DatabaseHandler.TABLE_ARTICLE + " WHERE "
	            + DatabaseHandler.KEY_ID + " = " + id;
		
		Log.e(LOG, query);
		
		Cursor result = database.rawQuery(query, null);
		
		if(result != null)
			result.moveToFirst();
		else 
			return null;
		
		id = result.getLong(result.getColumnIndex(DatabaseHandler.KEY_ID));
		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		String title = result.getString(result.getColumnIndex(DatabaseHandler.KEY_TITLE));
		String content = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CONTENT));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		Article article = new Article(id, objectId, title, content, stringToDate(createdAt));
		return article;
		
		
	}
	
	

}
