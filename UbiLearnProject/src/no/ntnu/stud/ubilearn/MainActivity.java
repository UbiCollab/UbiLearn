package no.ntnu.stud.ubilearn;

import java.util.Locale;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String[] menuOptions;
	private DrawerLayout menuDrawer;
	private ListView menuList;
	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		menuOptions = getResources().getStringArray(R.array.menu_options);
		menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		menuList = (ListView) findViewById(R.id.left_drawer);
		
		// set the adapter for the listview
		menuList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuOptions));
		
		// set the lists click listener
		menuList.setOnItemClickListener(new DrawerItemClickListener());
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
	Fragment fragmentTest = new Fragment();
		
		//FragmentManager fragmentManager = getFragmentManager();
		//fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentTest).commit();
		
		menuList.setItemChecked(position, true);
		setTitle(menuOptions[position]);
		menuDrawer.closeDrawer(menuList);
		
	}

}
