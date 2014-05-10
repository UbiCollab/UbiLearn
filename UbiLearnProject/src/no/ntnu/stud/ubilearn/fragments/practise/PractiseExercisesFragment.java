package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.fragments.handbook.ArticleFragment;
import no.ntnu.stud.ubilearn.fragments.handbook.CategoryFragment;
import no.ntnu.stud.ubilearn.fragments.handbook.HandbookItemAdapter;
import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.ExerciseCategory;
import no.ntnu.stud.ubilearn.models.ListItem;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class PractiseExercisesFragment extends Fragment 
{
	private View root;
	private ListView listView;
	private ArrayList<ListItem> listItems = null;
	private Fragment pointerHax = this;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		getTopLevelCategories();
		root = inflater.inflate(R.layout.fragment_handbook, container, false);
		listView = (ListView) root.findViewById(R.id.wikiListView);
		listView.setAdapter(new HandbookItemAdapter(this.getActivity(), listItems));
		
		listView.setClickable(true);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,long arg3) {
			
				if (listItems.get(position) instanceof ExerciseCategory) {
					ExerciseCategory cat = (ExerciseCategory) listItems.get(position);
					Log.v("CatFrag", "sub size: "+ cat.getSubItems().size());
					if (cat.hasSubs()) {
						PractiseExercisesFragment peFragment = new PractiseExercisesFragment();
						peFragment.setListItems(cat.getSubItems());
						getFragmentManager().beginTransaction().replace(R.id.content_frame, peFragment).addToBackStack(null).commit();
					}else{
						Toast.makeText(pointerHax.getActivity(), "This category is empty", Toast.LENGTH_SHORT).show();
					}
				}else{
					ArticleFragment articleFragment = new ArticleFragment();
					articleFragment.setArticle((Article) listItems.get(position));
					getFragmentManager().beginTransaction().replace(R.id.content_frame, articleFragment).addToBackStack(null).commit();
				}	
			}	
		});
		
		return root;
	}
	
	private void getTopLevelCategories() {
		if (listItems == null) {
			if (User.getInstance().getExercises() == null) {
				listItems = new ArrayList<ListItem>();
				Toast.makeText(this.getActivity(), "Somthing went wrong, exercises are missing.", Toast.LENGTH_LONG).show();
			} else {
				listItems = User.getInstance().getExercises();
			}
		}
	}

	public void setListItems(ArrayList<ListItem> listItems) {
		this.listItems = listItems;
	}
}
