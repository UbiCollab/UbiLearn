package no.ntnu.stud.ubilearn.parse;

import java.util.Date;
import java.util.List;

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

	public static void retriveNewContent(){
		//checks if user is logged in
		if (ParseUser.getCurrentUser() == null) {
			return;
		}
		//This is just a dummy date, should be stored in liteSQL when implemented.
		Date lastUpdate = ParseUser.getCurrentUser().getCreatedAt();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
		query.whereGreaterThan("updatedAt", lastUpdate);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					System.out.println("1");
					Log.d("Testobjects", ""+ objects.size());
				}else{
					System.out.println("2");
					Log.d("Testobjects", "error: "+ e.getMessage());
				}
				
			}
		});
	}
	public static void getHandBookCategoryUpdatedAfter(Date date){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Category");
		query.whereGreaterThan("updatedAt", date);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					
				}else{
					
				}
				
			}
		});
	}
	public static void populateTestObject(){
		ParseObject po = new ParseObject("TestObject");
		po.put("foo", "test");
		po.saveInBackground();
	}
}
