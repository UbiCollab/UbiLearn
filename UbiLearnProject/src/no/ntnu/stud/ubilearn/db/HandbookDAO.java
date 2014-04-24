package no.ntnu.stud.ubilearn.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.WikiItem;
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
		values.put(DatabaseHandler.KEY_OBJECT_ID, article.getObjectId());
		values.put(DatabaseHandler.KEY_TITLE, article.getTitle());
		values.put(DatabaseHandler.KEY_CONTENT, article.getContent());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(article.getCreatedAt()));
		values.put(DatabaseHandler.KEY_PARENT_ID, article.getParentId());

		log(values.toString());
		
		
		long rowId;
		if(!exists(DatabaseHandler.TABLE_ARTICLE, article.getObjectId()))	
			rowId= database.insert(DatabaseHandler.TABLE_ARTICLE,null,values);
		else{
			values.remove(DatabaseHandler.KEY_OBJECT_ID);
			rowId= database.update(DatabaseHandler.TABLE_ARTICLE,values,null,null);	
		}
		return rowId;
	}
	public void insertArticles(List<Article> articles){	
		for (Article article : articles) {
			insertArticle(article);
		}	
	}
	
	public Article getArticle(String id){
		
		String query = "SELECT * FROM " + DatabaseHandler.TABLE_ARTICLE + " WHERE "
	            + DatabaseHandler.KEY_OBJECT_ID + " = '" + id + "'";
		
		log(query);
		
		Cursor result = database.rawQuery(query, null);
				
		if(result.moveToFirst())
			return getArticle(result);
		else 
			return null;
		
	}
	
	private Article getArticle(Cursor result){
		
		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		String title = result.getString(result.getColumnIndex(DatabaseHandler.KEY_TITLE));
		String content = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CONTENT));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		String parentId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_PARENT_ID));
		
		Article article = new Article(objectId, title, content, stringToDate(createdAt), parentId);
		return article;
	}
	
	public List<WikiItem> getHandbook(){
		HashMap<String,Category> categories = new HashMap<String, Category>();
		List<WikiItem> topLevelItems = new ArrayList<WikiItem>();
		
		String query = "SELECT * FROM " + DatabaseHandler.TABLE_CATEGORY;
		log(query);
		Cursor categoriesResult = database.rawQuery(query, null);
		
		if(categoriesResult.moveToFirst())
			do{
				//gets each category in the database
				Category category = getCategory(categoriesResult);
				categories.put(category.getObjectId(), category);
				
			}while(categoriesResult.moveToNext());
		else
			//there is no categories in the database
			return null;
		
		//sets child categories as a sub item of their parent
		for (Category category : categories.values()) {
			
			if(category.isTopLevel())
				topLevelItems.add(category);
			else{
				Category parent = categories.get(category.getParentId());
				parent.addSubItem(category);
			}
				
		}
		query = "SELECT * FROM " + DatabaseHandler.TABLE_ARTICLE;
		log(query);
		
		List<Article> articles = new ArrayList<Article>();
		Cursor articlesResult = database.rawQuery(query, null);
		
		//iterates thru the result and adds all articles to the list
		if(articlesResult.moveToFirst())
			do{
				articles.add(getArticle(articlesResult));
			}while(articlesResult.moveToNext());
		
		//iterates thru all articles and adds them to their parent category
		for (Article article : articles) {
			if(article.isTopLevel())
				topLevelItems.add(article);
			else
				categories.get(article.getParentId()).addSubItem(article);
		}
		
		
		return topLevelItems;
	}
	
	
	public long insertCategory(Category category){
		System.out.println(category);
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_OBJECT_ID, category.getObjectId());
		values.put(DatabaseHandler.KEY_NAME, category.getName());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(category.getCreatedAt()));
		values.put(DatabaseHandler.KEY_PARENT_ID, category.getParentId());

		log(values.toString());
		long rowId;
		if(!exists(DatabaseHandler.TABLE_CATEGORY,category.getObjectId()))
			rowId = database.insert(DatabaseHandler.TABLE_CATEGORY,null,values);
		else{
			values.remove(DatabaseHandler.KEY_OBJECT_ID);
			rowId = database.update(DatabaseHandler.TABLE_CATEGORY,values, null,null);
		}
		return rowId;
	}
	public void insertCategories(List<Category> categories){
		for (Category category : categories) {
			insertCategory(category);
		}
	}
	public Category getCategory(String id){
		String query = "SELECT  * FROM " + DatabaseHandler.TABLE_CATEGORY + " WHERE "
	            + DatabaseHandler.KEY_OBJECT_ID + " = '" + id + "'";
		log(query);
		
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			return getCategory(result);
		else 
			return null;
	}
	
	private Category getCategory(Cursor result){
		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		String name = result.getString(result.getColumnIndex(DatabaseHandler.KEY_NAME));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		String parentId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_PARENT_ID));
		
		Category category = new Category(objectId, name, stringToDate(createdAt), parentId);
		return category;
	}
	public void printTable(){
		printTable(DatabaseHandler.TABLE_ARTICLE);
		printTable(DatabaseHandler.TABLE_CATEGORY);
	}

}
