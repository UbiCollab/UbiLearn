package no.ntnu.stud.ubilearn.parse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import no.ntnu.stud.ubilearn.db.HandbookDAO;
import no.ntnu.stud.ubilearn.db.TrainingDAO;
import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.CasePatient;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.Quiz;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class SyncContent {
	
	//test date, until it is stored in LightSQL
	//public static Date lastUpdate = ParseUser.getCurrentUser().getCreatedAt();
	static Date lastUpdate;
	public static boolean isRetriving = false;
	public static boolean hasRetrived = false;
	public static void retriveNewContent(Context context){
		isRetriving = true;
		//checks if user is logged in
		if (ParseUser.getCurrentUser() == null) {
			return;
		}
		if (ParseUser.getCurrentUser().getDate("lastUpdate") != null) {
			lastUpdate = ParseUser.getCurrentUser().getDate("lastUpdate");			
		}
		fetchHandBookCategoryAfterUpdated(context);
		fetchHandBookArticleAfterUpdate(context);
		fetchQuizesAfterUpdate(context);
		fetchCasePatient(context);
//		ParseUser.getCurrentUser().put("lastUpdate", new Date());
//		ParseUser.getCurrentUser().saveInBackground();
		
		isRetriving = false;
		hasRetrived = true;
	}
	
	private static void fetchCasePatient(final Context context) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PatientCase");
		if (lastUpdate != null) {
			query.whereGreaterThan("updatedAt", lastUpdate);			
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					ArrayList<CasePatient> list = new ArrayList<CasePatient>();
					for (ParseObject o : objects) {
						list.add(new CasePatient(o.getObjectId(), o.getString("name"), o.getString("age"), o.getString("gender"), o.getString("info"), o.getInt("level"), o.getCreatedAt()));
					}
					TrainingDAO dao = new TrainingDAO(context);
					dao.open();
					dao.insertCasePatients(list);
					dao.close();
				}else{
					Log.v("SyncContent", e.getMessage());
				}
			}
		});
	}

	public static void fetchQuizesAfterUpdate (final Context context){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Quiz");
		if (lastUpdate != null) {
			query.whereGreaterThan("updatedAt", lastUpdate);			
		}
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				List<Quiz> list = new ArrayList<Quiz>();
				if (e == null) {
					for (ParseObject o : objects) {
						String ownerId = null;
						if (o.getParseObject("owner") != null) {
							ownerId = o.getParseObject("owner").getObjectId();
						}
						list.add(new Quiz(o.getString("question"), downcastListOfObjects(o.getList("answers")), o.getString("correct"), o.getObjectId(), ownerId, o.getCreatedAt()));
					}
					TrainingDAO dao = new TrainingDAO(context);
					dao.open();
					dao.insertQuizzes(list);
					dao.close();
				}else {
					Log.v("SyncContent", e.getMessage());
				}
			}
		});
	}
	
	public static void fetchHandBookArticleAfterUpdate(final Context context){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Article");
		if (lastUpdate != null) {
			query.whereGreaterThan("updatedAt", lastUpdate);			
		}
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					List<Article> list = new ArrayList<Article>();
					for (ParseObject object : objects) {
						String parentId = null;
						if (object.getParseObject("parent") != null) {
							parentId = object.getParseObject("parent").getObjectId();
						}
						list.add(new Article(object.getObjectId(), object.getString("title"), object.getString("content"), object.getCreatedAt(), parentId));
					}
					HandbookDAO dao = new HandbookDAO(context);
					dao.open();
					dao.insertArticles(list);
					dao.close();
				}else{
					Log.v("SyncContent", e.getMessage());
				}
			}
		});
	}
	
	public static void fetchHandBookCategoryAfterUpdated(final Context context){

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Category");
		if (lastUpdate != null) {
			query.whereGreaterThan("updatedAt", lastUpdate);			
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					List<Category> list = new ArrayList<Category>();
					for (ParseObject o : objects) {
						String parentId = null;
						if (o.getParseObject("parent") != null) {
							parentId = o.getParseObject("parent").getObjectId();
						}
						list.add(new Category(o.getObjectId(), o.getString("name"), o.getCreatedAt(), parentId));						
					}
					HandbookDAO dao = new HandbookDAO(context);
					dao.open();
					dao.insertCategories(list);
					dao.close();
				}else{
					Log.v("SyncContent", e.getMessage());
				}
				
			}
		});
	}

	private static ArrayList<String> downcastListOfObjects(List<Object> objects){
		ArrayList<String> stringList = new ArrayList<String>();
		for (Object object : objects) {
			if (object instanceof String) {
				stringList.add((String) object);
			}
		}
		return stringList;
	}
}


