package no.ntnu.stud.ubilearn;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class Temp extends FragmentActivity{
	
	@Override
	protected void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temp_acitivty);
		
		Fragment fragment = new Training();
		
		FragmentManager framan = (FragmentManager) getSupportFragmentManager();
		FragmentTransaction ft = framan.beginTransaction();
		ft.replace(R.id.main_temp_conatiner, fragment);
		ft.commit();
	}

}
