package no.ntnu.stud.ubilearn;

import java.util.Locale;

import no.ntnu.stud.ubilearn.fragments.*;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String[] menuOptions;
	private DrawerLayout menuDrawer;
	private ListView menuList;
	private ActionBarDrawerToggle drawerToggle;
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		menuOptions = getResources().getStringArray(R.array.menu_options);
		menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		menuList = (ListView) findViewById(R.id.left_drawer);
		// set the adapter for the listview
		menuList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuOptions));
//		menuList.setAdapter(new HeaderViewListAdapter(headerViewInfos, footerViewInfos, adapter));
		
		// set the lists click listener
		menuList.setOnItemClickListener(new DrawerItemClickListener());
		
		drawerToggle = new ActionBarDrawerToggle(this, menuDrawer, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
    	menuDrawer.setDrawerListener(drawerToggle);
    	getActionBar().setDisplayHomeAsUpEnabled(true);
    	getActionBar().setHomeButtonEnabled(true);
    	
//    	LayoutInflater inflater = getLayoutInflater();
//    	ViewGroup mTop = (ViewGroup) inflater.inflate(R.layout.header_listview_menu, menuList, false);
//    	menuList.addHeaderView(mTop, null,false);
	        
	        
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
		
		Fragment fragment;// = new TestFragment0();
		
		switch (position) {
		case 0: fragment = new Training();
			break;
		case 1: fragment = new TestFragment0();
			break;
		default: fragment = new TestFragmentDefault(); 
		}
		
		FragmentManager manager = getFragmentManager();
		manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		
		menuList.setItemChecked(position, true);
		setTitle(menuOptions[position]);
		menuDrawer.closeDrawer(menuList);
		
	}
}
