package no.ntnu.stud.ubilearn;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.adapter.HeaderAdapter;
import no.ntnu.stud.ubilearn.fragments.*;
import no.ntnu.stud.ubilearn.fragments.wiki.WikiFragment;
import no.ntnu.stud.ubilearn.models.AdapterModel;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		activityView = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerView = (ListView) findViewById(R.id.left_drawer);
		
		//generates models that represent titles and text in the drawer
		drawerModels = new ArrayList<AdapterModel>();
		generateModels(getResources().getStringArray(R.array.menu_options));
		
		// set the adapter for the listview
		HeaderAdapter adapter = new HeaderAdapter(this, drawerModels);
		drawerView.setAdapter(adapter);

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
				throw new IllegalArgumentException("couldnt identiy menu options tag");
		}
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
		case 4: fragment = new Practise();
			break;
		case 6: {
			fragment = new WikiFragment();
		}
			break;
		case 7: fragment = new DummyFragment();
			break;
		case 9: logout();
			return;
		default: return; //when a field is pushed that does not link to a fragment. e.g a header
		}
		
		
		FragmentManager manager = getFragmentManager();
		//checks if there are older items in the backstack
		if(manager.getBackStackEntryCount()>1)
			//clears the backstack
			manager.popBackStack(manager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
	
	private void logout() {
		Intent intent = new Intent(this, LoginActivity.class);
	    startActivity(intent);
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
