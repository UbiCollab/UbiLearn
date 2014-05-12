package no.ntnu.stud.ubilearn.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.security.auth.callback.Callback;

import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.db.HandbookDAO;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.db.TrainingDAO;
import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.CasePatient;
import no.ntnu.stud.ubilearn.models.CasePatientStatus;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.Exercise;
import no.ntnu.stud.ubilearn.models.ExerciseCategory;
import no.ntnu.stud.ubilearn.models.ExerciseImage;
import no.ntnu.stud.ubilearn.models.ListItem;
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
import com.parse.ParseFile;
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
		//Asynchronous
		fetchHandBookCategoryAfterUpdated(context);
		fetchHandBookArticleAfterUpdate(context);
		//updateExerciseImages(context);
		fetchExerciseCategories(context);

//		ParseUser.getCurrentUser().put("lastUpdate", new Date());
//		ParseUser.getCurrentUser().saveInBackground();
		isRetriving = false;
		hasRetrived = true;
		
		Log.v("Sync", "done");
		//Fungerer ikke fordi metodene over kaller asyncrone metoder
		//Toast.makeText(context, "Done syncing content", Toast.LENGTH_LONG).show();
	}

	
	public static void fetchCasePatient(final Context context) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PatientCase");
		if (lastUpdate != null) {
			query.whereGreaterThan("updatedAt", lastUpdate);			
		}
		try {
			List<ParseObject> objects = query.find();
			ArrayList<CasePatient> list = new ArrayList<CasePatient>();
			for (ParseObject o : objects) {
				list.add(new CasePatient(o.getObjectId(), o.getString("name"), o.getString("age"), o.getString("gender"), o.getString("info"), o.getInt("level"), o.getCreatedAt()));
			}
			TrainingDAO dao = new TrainingDAO(context);
			dao.open();
			dao.insertCasePatients(list);
			ArrayList<CasePatient> patientList = dao.getAllCasePatients();
			Collections.sort(patientList);
			User.getInstance().setCasePatientList(patientList);
			dao.close();
			Log.v("Order", "1");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void fetchQuizesAfterUpdate (final Context context){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Quiz");
		if (lastUpdate != null) {
			query.whereGreaterThan("updatedAt", lastUpdate);			
		}
		try {
			List<ParseObject> objects = query.find();
			List<Quiz> list = new ArrayList<Quiz>();
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
		} catch (ParseException e) {
			Log.v("SyncContent", e.getMessage());
		}
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
	

	private static void fetchExercises(final ArrayList<ListItem> list, final HashMap<String, ExerciseCategory> map, Context context) {
		final PractiseDAO dao = new PractiseDAO(context);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Exercises");
		
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					for (ParseObject o : objects) {
						Exercise ex = new Exercise(o.getObjectId(), o.getString("name"), o.getString("text"),o.getCreatedAt());
						dao.open();
						ex.setImages(dao.getExerciseImages(o.getObjectId()));
						dao.close();
						if (o.getParseObject("parent") != null) {
							map.get(o.getParseObject("parent").getObjectId()).getSubItems().add(ex);
						}else{
							list.add(ex);
						}
					}							
				}else{
					Log.v("Sync", e.getMessage());
				}
			}
		});
	}
	
	public static List<Exercise> fetchExercises(){
		final List<Exercise> list= new ArrayList<Exercise>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Exercises");
//		query.findInBackground(new FindCallback<ParseObject>() {
//			@Override
//			public void done(List<ParseObject> objects, ParseException e) {
//				if (e == null) {
//					System.out.println(objects.size());
//					for (ParseObject object : objects) {
//						Exercise ex = new Exercise(object.getObjectId(), object.getString("name"), object.getString("text"));
//						ex.setImages(fetchImages(ex.getObjectId()));
//						list.add(ex);
//					}
//				}else{
//					Log.v("SyncContent", e.getMessage());
//				}
//			}
//		});
		
		try {
			List<ParseObject> objects = query.find();
			for (ParseObject object : objects) {
				Exercise ex = new Exercise(object.getObjectId(), object.getString("name"), object.getString("text"),object.getCreatedAt());
				ex.setImages(fetchImages(object));
				list.add(ex);
			}
		} catch (ParseException e1) {
			Log.v("SyncContent", e1.getMessage());
		}
		
		return list;
	}
	
	private static ArrayList<ExerciseImage> fetchImages(ParseObject exercise){
		final ArrayList<ExerciseImage> images = new ArrayList<ExerciseImage>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ExerciseImages");
		
//		ParseObject po = new ParseObject("Exercises");
//		po.setObjectId(exerciseId);
		query.whereEqualTo("exercise", exercise);

//		query.findInBackground(new FindCallback<ParseObject>() {
//			@Override
//			public void done(List<ParseObject> objects, ParseException e) {
//				if (e == null) {
//					for (ParseObject object : objects) {
//						ParseFile file = object.getParseFile("image");
//						try {
//							images.add(file.getData());
//						} catch (ParseException e1) {
//							e1.printStackTrace();
//						}
//					}
//				}else{
//					Log.v("SyncContent", e.getMessage());
//				}
//			}
//		});
		
		try {
			List<ParseObject> objects = query.find();
			System.out.println(objects.size());
			for (ParseObject object : objects) {
				ParseFile file = object.getParseFile("image");
				try {
					images.add(new ExerciseImage(object.getObjectId(),file.getData(),exercise.getObjectId(),object.getCreatedAt()));
				} catch (ParseException e1) {
					e1.printStackTrace();
					Log.v("SyncContent", e1.getMessage());
				}
			}
		} catch (ParseException e1) {
			Log.v("SyncContent", e1.getMessage());

		}

		
		return images;	
	}
	
	public static void updateExerciseImages(Context context){
		final PractiseDAO dao = new PractiseDAO(context);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ExerciseImages");
		if (lastUpdate != null) {
			query.whereGreaterThan("updatedAt", lastUpdate);			
		}
		try {
			List<ParseObject> objects = query.find();
			dao.open();
			System.out.println(objects.size());
			for (ParseObject object : objects) {
				ParseFile file = object.getParseFile("image");
				try {
					dao.insertImage(new ExerciseImage(object.getObjectId(),file.getData(),object.getParseObject("exercise").getObjectId(),object.getCreatedAt()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			dao.close();
		} catch (ParseException e) {
			Log.v("Sync", e.getMessage());
		}
	}
	
	public static void updateExerciseImagesInBackground(Context context) {
		final PractiseDAO dao = new PractiseDAO(context);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ExerciseImages");
		if (lastUpdate != null) {
			query.whereGreaterThan("updatedAt", lastUpdate);			
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					dao.open();
					System.out.println(objects.size());
					for (ParseObject object : objects) {
						ParseFile file = object.getParseFile("image");
						try {
							dao.insertImage(new ExerciseImage(object.getObjectId(),file.getData(),object.getParseObject("exercise").getObjectId(),object.getCreatedAt()));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
					dao.close();
				}else{
					Log.v("SyncContent", e.getMessage());
				}
			}
		});
		
	}
	
	public static void fetchExerciseCategories(final Context context){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ExerciseCategory");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				if (e == null) {
					ArrayList<ListItem> list = new ArrayList<ListItem>();
					HashMap<String, ExerciseCategory> map = new HashMap<String, ExerciseCategory>();
					for(ParseObject o : objects){						
						ExerciseCategory ec;
						if (o.getParseObject("parent") != null) {
							ec = new ExerciseCategory(o.getString("name"), o.getObjectId(), o.getParseObject("parent").getObjectId());
						}else{
							ec = new ExerciseCategory(o.getString("name"), o.getObjectId(), null);
						}
						map.put(o.getObjectId(), ec);
					}
					
					for (ExerciseCategory ec : map.values()) {
						if (!ec.isTopLevel()) {
							map.get(ec.getParentId()).getSubItems().add(ec);
						}
					}
					for (ExerciseCategory ec : map.values()) {
						if (ec.isTopLevel()) {
							list.add(ec);
						}
					}
					User.getInstance().setExerciseCategory(list);
					fetchExercises(list, map, context);
					Log.v("Sync", "done fetching exercise categories");
				}else{
					e.getMessage();
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
		if (User.getInstance() == null) {
			return;
		}
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PatientCaseStatus");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		
		try {
			List<ParseObject> objects = query.find();
			for (ParseObject o : objects) {
				User.getInstance().getMapCasePatientStatus().put(o.getParseObject("patientCase").getObjectId(), new CasePatientStatus(o.getInt("highScore"), o.getBoolean("isComplete"), o.getObjectId()));						
			}
			Log.v("Sync:", "done fetching training progress");
			Log.v("Order", "2");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			Log.v("Sync:", e1.getMessage());
		}
	}
	
	public static void saveTrainingProgress(){
		HashMap<String, CasePatientStatus> map = User.getInstance().getMapCasePatientStatus();
		
		ArrayList<ParseObject> list = new ArrayList<ParseObject>();
		
		for (Map.Entry<String, CasePatientStatus> entry : map.entrySet()) {
			CasePatientStatus value = entry.getValue();
		    ParseObject o = new ParseObject("PatientCaseStatus");
		    if (value.getObjectId() != null) {
				o.setObjectId(value.getObjectId());
			}
		    o.put("user", ParseUser.getCurrentUser());
		    ParseObject patientPointer = new ParseObject("PatientCase");
		    patientPointer.setObjectId(entry.getKey());
		    o.put("patientCase", patientPointer);
		    o.put("highScore", value.getHighScore());
		    o.put("isComplete", value.isComplete());
		    
		    list.add(o);
		}
		ParseObject.saveAllInBackground(list, new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.v("Sync:", "Done saving training progress");
				}else{
					Log.v("Sync:", e.getMessage());		
				}
			}
		});
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
	
	public static void calculateLastUpdate(){
		if (ParseUser.getCurrentUser() == null  || hasRetrived) {
			return;
		}
		if (ParseUser.getCurrentUser().getDate("lastUpdate") != null) {
			lastUpdate = ParseUser.getCurrentUser().getDate("lastUpdate");			
		}
	}


	public static void fetchDataBeforeLogin(final Context context, final CallbackTest callback) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				calculateLastUpdate();
				fetchCasePatient(context);
				fetchTrainingProgress();
				fetchQuizesAfterUpdate(context);
				updateExerciseImages(context);
				callback.done(null);
			}
		});
		t.start();
	}
}




