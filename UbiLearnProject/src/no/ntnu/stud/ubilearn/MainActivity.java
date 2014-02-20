package no.ntnu.stud.ubilearn;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;

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
		menuList.setOnItemClickListener(new Drawer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener{
		
	}

}
