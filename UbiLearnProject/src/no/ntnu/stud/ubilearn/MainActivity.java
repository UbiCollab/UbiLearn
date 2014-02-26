package no.ntnu.stud.ubilearn;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.adapter.Model;
import no.ntnu.stud.ubilearn.adapter.HeaderAdapter;
import no.ntnu.stud.ubilearn.fragments.*;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
	
	private ArrayList<Model> drawerModels;
	private DrawerLayout activityView;
	private ListView drawerView;
	private ActionBarDrawerToggle drawerToggle;
	private Fragment visibleFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		activityView = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerView = (ListView) findViewById(R.id.left_drawer);
		
		//generates models that represent titles and text in the drawer
		drawerModels = new ArrayList<Model>();
		generateModels(getResources().getStringArray(R.array.menu_options));
		
		// set the adapter for the listview
		HeaderAdapter adapter = new HeaderAdapter(this, drawerModels);
		drawerView.setAdapter(adapter);

		// set the lists click listener
		drawerView.setOnItemClickListener(new DrawerItemClickListener());

		drawerToggle = new ActionBarDrawerToggle(this, activityView, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
    	activityView.setDrawerListener(drawerToggle);
    	getActionBar().setDisplayHomeAsUpEnabled(true);
    	getActionBar().setHomeButtonEnabled(true);
	    
	}
	//parses an array of strings and creates header and text models of it
	 private void generateModels(String[] menuOptions) {
		
		 for (int i = 0; i < menuOptions.length; i++) {
			 String s = menuOptions[i];
			 char type = s.charAt(1);
			if(type == 'h')//header
				drawerModels.add(new Model(s.substring(3)));
			else if(type == 't')//text
				drawerModels.add(new Model(R.drawable.ic_launcher, s.substring(3)));
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
		
		Model selected = drawerModels.get(position);
		
		Fragment fragment;
		
		switch (position) {
		case 1: fragment = new HomeFragment();
			break;
		case 3: fragment = new Training();
			break;
		case 4: fragment = new Practise();
			break;
		case 6: fragment = new DummyFragment();
			break;
		case 7: fragment = new DummyFragment();
			break;
		default: return; //when a field is pushed that does not link to a fragment. e.g a header
		}
		
		
		FragmentManager manager = getFragmentManager();
		manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		visibleFrag = fragment;
		
		drawerView.setItemChecked(position, true);
		setTitle(selected.getTitle());
		activityView.closeDrawer(drawerView);
		
	}
	
	public void houseClick(View v){
		((Training) visibleFrag).houseClick(v);
	}
}
