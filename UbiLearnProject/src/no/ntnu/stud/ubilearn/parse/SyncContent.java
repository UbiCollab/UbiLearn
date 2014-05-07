package no.ntnu.stud.ubilearn.parse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.db.HandbookDAO;
import no.ntnu.stud.ubilearn.db.TrainingDAO;
import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.CasePatient;
import no.ntnu.stud.ubilearn.models.CasePatientStatus;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.Patient;
import no.ntnu.stud.ubilearn.models.Quiz;
import no.ntnu.stud.ubilearn.models.SPPB;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SyncContent {
	
	//test date, until it is stored in LightSQL
	//public static Date lastUpdate = ParseUser.getCurrentUser().getCreatedAt();
	static Date lastUpdate;
	public static boolean isRetriving = false;
	public static boolean hasRetrived = false;
	
	public static void retriveNewContent(Context context){
		isRetriving = true;
		//checks if user is logged in
		if (ParseUser.getCurrentUser() == null  || hasRetrived) {
			return;
		}
		if (ParseUser.getCurrentUser().getDate("lastUpdate") != null) {
			lastUpdate = ParseUser.getCurrentUser().getDate("lastUpdate");			
		}
		fetchTrainingProgress();
		fetchHandBookCategoryAfterUpdated(context);
		fetchHandBookArticleAfterUpdate(context);
		fetchQuizesAfterUpdate(context);
		fetchCasePatient(context);
//		ParseUser.getCurrentUser().put("lastUpdate", new Date());
//		ParseUser.getCurrentUser().saveInBackground();
		
		isRetriving = false;
		hasRetrived = true;
		Log.v("Sync", "done");
		Toast.makeText(context, "Done syncing content", Toast.LENGTH_LONG).show();
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
					ArrayList<CasePatient> patientList = dao.getAllCasePatients();
					User.getInstance().setCasePatientList(patientList);
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
						list.add(new Quiz(o.getString("question"), downcastListOfObjectsToString(o.getList("answers")), o.getString("correct"), o.getObjectId(), ownerId, o.getCreatedAt()));
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

	public static void savePatient(Patient p){
		
		ParseObject patient = new ParseObject("Patient");
		patient.put("name", p.getName());
		patient.put("idSql", p.getId());
		patient.put("problems", p.getProblems());
		patient.put("age", p.getAge());
		patient.put("comment", p.getComment());
		patient.put("createdAtLocal", p.getCreatedAt());
		
		patient.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.v("Sync", "Saved patient");
				}else {
					Log.v("Sync", e.getMessage());
				}
				
			}
		});
		
		for (SPPB sppb : p.getTests()) {
			saveSPPB(sppb, patient);
		}
	}
	
	public static void fetchTrainingProgress(){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PatientCaseStatus");
		query.whereEqualTo("user", ParseUser.getCurrentUser().getObjectId());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					for (ParseObject o : objects) {
						User.getInstance().getMapCasePatientStatus().put(o.getString("user"), new CasePatientStatus(o.getInt("highScore"), o.getBoolean("isComplete")));						
					}
					
				}
			}
		});
//		if (ParseUser.getCurrentUser() != null) {
//			ParseRelation<ParseObject> pCaseComplete = ParseUser.getCurrentUser().getRelation("PatientCaseComplete");
//			Log.v("Sync", "Done fetching training progress");
//		}else{
//			Log.v("Sync", "Can't fetch Training Progress, user is not logget inn");
//		}
		
	}

	private static void saveSPPB(SPPB sppb, ParseObject parent) {
		if (sppb instanceof BalanceSPPB) {
			ParseObject ob = new ParseObject("BalanceSPPB");
			ob.put("name", sppb.getName());
			ob.put("idSql", sppb.getId());
			ob.put("patient", parent);
			ob.put("name", sppb.getName());
			ob.put("createdAtLocal", sppb.getCreatedAt());
			
			ob.put("pairedScore", ((BalanceSPPB) sppb).getPairedScore());
			ob.put("semiTandemScore", ((BalanceSPPB) sppb).getSemiTandemScore());
			ob.put("tandemScore", ((BalanceSPPB) sppb).getTandemScore());
			ob.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException e) {
					if (e == null) {
						Log.v("Sync", "Saved BalanceSPPB");
					}else {
						Log.v("Sync", e.getMessage());
					}
					
				}
			});
		}		
	}

	private static ArrayList<String> downcastListOfObjectsToString(List<Object> objects){
		ArrayList<String> stringList = new ArrayList<String>();
		for (Object object : objects) {
			if (object instanceof String) {
				stringList.add((String) object);
			}
		}
		return stringList;
	}
	
	
}


