package no.ntnu.stud.ubilearn.parse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ntnu.stud.ubilearn.db.HandbookDAO;
import no.ntnu.stud.ubilearn.models.Category;
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
	
	public static void retriveNewContent(Context context){
		//checks if user is logged in
		if (ParseUser.getCurrentUser() == null) {
			return;
		}
		if (ParseUser.getCurrentUser().getDate("lastUpdate") != null) {
			lastUpdate = ParseUser.getCurrentUser().getDate("lastUpdate");			
		}
		getHandBookCategoryUpdatedAfter(context);
//		ParseUser.getCurrentUser().put("lastUpdate", new Date());
//		ParseUser.getCurrentUser().saveInBackground();
	}
	public static void getHandBookCategoryUpdatedAfter(final Context context){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Category");
		if (lastUpdate != null) {
			query.whereGreaterThan("updatedAt", lastUpdate);			
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					List<Category> list = new ArrayList<Category>();
					for (int i = 0; i < objects.size(); i++) {
						ParseObject o = objects.get(i);
						String parentId = null;
						if (o.getParseObject("parent") != null) {
							parentId = o.getParseObject("parent").getObjectId();
						}
						list.add(new Category(o.getString("objectId"), o.getString("name"), o.getCreatedAt(), parentId));
					}
					//HandbookDAO dao = new HandbookDAO(context);
					//dao.insertCategories(list);
				}else{
					Log.v("SyncContent", e.getMessage());
				}
				
			}
		});
	}
}
