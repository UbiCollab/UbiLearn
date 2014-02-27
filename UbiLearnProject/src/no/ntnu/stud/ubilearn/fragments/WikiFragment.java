package no.ntnu.stud.ubilearn.fragments;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.R.layout;
import no.ntnu.stud.ubilearn.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class WikiFragment extends Fragment {
	private View root;
	private ListView categoryListView;
	private String[] categories;
	private Fragment pointerHax = this;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b){
		categories= getResources().getStringArray(R.array.wiki_main_options);
		
		root = inflater.inflate(R.layout.fragment_wiki, null);
		categoryListView = (ListView) root.findViewById(R.id.wikiListView);
		categoryListView.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.wiki_list_item, categories));
		
		categoryListView.setClickable(true);
		categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				categories = getResources().getStringArray(R.array.wiki_Symptomer_options);
				categoryListView.setAdapter(new ArrayAdapter<String>(pointerHax.getActivity(), R.layout.wiki_list_item, categories));
			}
			
		});
		return root;
	}
}
