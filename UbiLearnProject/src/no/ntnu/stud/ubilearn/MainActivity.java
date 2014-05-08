package no.ntnu.stud.ubilearn;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parse.Parse;
import com.parse.ParseUser;

import no.ntnu.stud.ubilearn.adapter.HeaderAdapter;
import no.ntnu.stud.ubilearn.db.HandbookDAO;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.db.TrainingDAO;
import no.ntnu.stud.ubilearn.fragments.*;
import no.ntnu.stud.ubilearn.fragments.handbook.CategoryFragment;
import no.ntnu.stud.ubilearn.fragments.practise.PractiseFragment;
import no.ntnu.stud.ubilearn.models.AdapterModel;
import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.CasePatient;
import no.ntnu.stud.ubilearn.models.Patient;
import no.ntnu.stud.ubilearn.models.Quiz;
import no.ntnu.stud.ubilearn.models.SPPB;
import no.ntnu.stud.ubilearn.models.StandUpSPPB;
import no.ntnu.stud.ubilearn.models.WikiItem;
import no.ntnu.stud.ubilearn.parse.SyncContent;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ArrayList<AdapterModel> drawerModels;
	private DrawerLayout activityView;
	private ListView drawerView;
	private ActionBarDrawerToggle drawerToggle;
	private Fragment visibleFrag;
	private int lastMenuPos = -1;
	ArrayList<CasePatient> patientList;
	private TrainingDAO trainingDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SyncContent.retriveNewContent(this);

		Patient p = new Patient("Espen", "gammel", "mye rart", "drfg", new Date());
		p.getTests().add(new BalanceSPPB("something", -1, new Date(), 23, 15, 17));

		SyncContent.savePatient(p);

		activityView = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerView = (ListView) findViewById(R.id.left_drawer);

		//generates models that represent titles and text in the drawer
		drawerModels = new ArrayList<AdapterModel>();
		generateModels(getResources().getStringArray(R.array.menu_options));

		// set the adapter for the listview
		HeaderAdapter adapter = new HeaderAdapter(this, drawerModels);
		drawerView.setAdapter(adapter);

		//---------GSON------------------
		//		generatePatients();
		//		patientList = User.getInstance().getPatientList();

		//----------SQLite--------------

		//doing it in SyncContent for now.
		//		trainingDAO = new TrainingDAO(this);
		//		trainingDAO.open();
		//		patientList = trainingDAO.getAllCasePatients();
		//		User.getInstance().setPatientList(patientList);
		//		trainingDAO.close();			
		//		

		// set the lists click listener
		drawerView.setOnItemClickListener(new DrawerItemClickListener());





		drawerToggle = new MyActionBarDrawerToggle(this, activityView, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
		activityView.setDrawerListener(drawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		getFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();

	}
	@Override
	protected void onResume(){
		super.onResume();
		//sets the home fragment as the start up screen everytime the main activity resumes.
	}
	//parses an array of strings and creates header and text models of it
	private void generateModels(String[] menuOptions) {

		for (int i = 0; i < menuOptions.length; i++) {
			String s = menuOptions[i];
			char type = s.charAt(1);
			if(type == 'h')//header
				drawerModels.add(new AdapterModel(s.substring(3)));
			else if(type == 't')//text
				drawerModels.add(new AdapterModel(R.drawable.ic_launcher, s.substring(3)));
			else
				throw new IllegalArgumentException("couldnt identify menu options tag");
		}


		//home
		drawerModels.get(1).setIcon(R.drawable.ic_home_white);
		//training
		drawerModels.get(3).setIcon(R.drawable.ic_training_white);
		//practice
		drawerModels.get(4).setIcon(R.drawable.ic_practice_white);
		//handbook
		drawerModels.get(6).setIcon(R.drawable.ic_handbook_white);
		//first aid
		drawerModels.get(7).setIcon(R.drawable.ic_first_aid_white);
		//log out
		drawerModels.get(9).setIcon(R.drawable.ic_logout_white);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class DrawerItemClickListener implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			selectItem(position);
		}

	}
	// swaps fragments in the main contentview
	public void selectItem(int position) {

		AdapterModel selected = drawerModels.get(position);

		Fragment fragment;

		switch (position) {
		case 1: fragment = new HomeFragment();
		break;
		case 3: fragment = new Training();
		break;
		case 4: fragment = new PractiseFragment();
		break;
		case 6: {
			fragment = new CategoryFragment();
		}
		break;
		case 7: fragment = new DummyFragment();
		break;
		case 9: logout();
		return;
		default: return; //when a field is pushed that does not link to a fragment. e.g a header
		}

		clearBackstack();

		FragmentManager manager = getFragmentManager();

		//sets the homefragment to the only fragment in the backstack
		manager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
		//changes to the new fragment
		manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		visibleFrag = fragment;

		drawerView.setItemChecked(position, true);
		lastMenuPos = position;
		setTitle(selected.getTitle());
		activityView.closeDrawer(drawerView);
	}

	private void clearBackstack(){
		FragmentManager manager = getFragmentManager();
		//checks if there are older items in the backstack
		if(manager.getBackStackEntryCount()>=1)
			//clears the backstack
			manager.popBackStack(manager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	private void logout() {

		clearBackstack();
		ParseUser.logOut();
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

		//-----------------------------DATABASE TESTING--------------------------------------

		//		HandbookDAO hb = new HandbookDAO(this);
		//		hb.open();
		//		hb.printTable();
		//		hb.insertCategory(new Category("abc123", "Tests", new Date(), null));
		//		hb.insertCategory(new Category("asd123", "subTests", new Date(), "abc123"));
		//		hb.insertArticle(new Article("qwe123", "testArtikkel", "detter er en test bla bla bla, massse kult innhold jippiii", new Date(), "asd123"));
		//		hb.printTable();
		//		
		//		
		////		Log.d("MAIN",hb.getCategory("abc123").printContent());
		//		
		//		List<WikiItem> content = hb.getHandbook();
		//		System.out.println(content.size());
		//		for (WikiItem item : content) {
		//			Log.d("SUPER",item.printContent());
		//			if(item instanceof Category){
		//				for (WikiItem sub : ((Category)item).getSub()) {
		//					Log.d("SUB",sub.printContent());
		//					if(item instanceof Category){
		//						for (WikiItem subsub : ((Category)item).getSub()) {
		//							Log.d("SUBSUB",subsub.printContent());
		//
		//						}
		//					}
		//				}
		//			}
		//		}
		//		hb.close();

		//		TrainingDAO tDAO = new TrainingDAO(this);
		//		tDAO.open();
		////		tDAO.insertPatients(patientList);
		//		
		//		for (Patient patient : patientList) {
		//			tDAO.insertQuizs(generateQuiz(patient));
		//		}
		//		tDAO.close();
		//	    PractiseDAO pDAO = new PractiseDAO(this);
		//	    pDAO.open();
		//	    Patient p1 = new Patient("Hans", "69", "everything", "nothing", new Date());
		//	    p1.setId(pDAO.insertPatient(p1));
		//	    Patient p2 = new Patient("Jon", "64", "everything", "nothing", new Date());
		//	    pDAO.insertPatient(p2);
		//	    SPPB test = new StandUpSPPB("Vandringstest", p1.getId(), 5, new Date());
		//	    pDAO.insertSBBP(test);
		//	    List<SPPB> tests = pDAO.getStandUpSPPBs(p1.getId());
		//	    for (SPPB sppb : tests) {
		//			Log.d("test", sppb.toString());
		//		}
		//	    pDAO.printTables();
		//	    pDAO.close();

		//---------------------------------------------------------------------------------
	}
	public void houseClick(View v){
			((Training) visibleFrag).houseClick(v);
	
		
	}

	//creates own version of this to reach the method that listens on the drawer close event
	public class MyActionBarDrawerToggle extends ActionBarDrawerToggle{
		public MyActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
		}

		@Override
		public void onDrawerClosed(View view) {
			super.onDrawerClosed(view);
			if(lastMenuPos>=0)
				drawerView.setItemChecked(lastMenuPos, false);
		}
	}
}
