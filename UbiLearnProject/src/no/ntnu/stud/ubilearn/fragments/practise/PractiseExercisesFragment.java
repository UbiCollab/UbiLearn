package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.adapter.HandbookItemAdapter;
import no.ntnu.stud.ubilearn.db.HandbookDAO;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.fragments.handbook.ArticleFragment;
import no.ntnu.stud.ubilearn.fragments.handbook.CategoryFragment;
import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.Exercise;
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is used to represent {@link ListItem} thru a View, using a {@link ListView}, which again uses a custom adapter.
 * Each item in the ListView has a onItemClick() method which either brings you to a new {@link PractiseExercisesFragment}, or
 * a {@link ExcerciseFragment}. The class first fetches a {@link ArrayList} of {@link ListItem} from {@link User}.
 * It will then, either call onCreateView() on a instance of itself recursively or instantiate a {@link ArticleFragment},
 * depending on what type of {@link ListItem} that has been selected.
 *@see View
 *@see TextView
 *@see ListView
 *@see ListItem
 *@see HandbookItemAdapter
 */
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
					if (cat.hasSubs()) {
						PractiseExercisesFragment peFragment = new PractiseExercisesFragment();
						peFragment.setListItems(cat.getSubItems());
						getFragmentManager().beginTransaction().replace(R.id.content_frame, peFragment).addToBackStack(null).commit();
					}else{
						Toast.makeText(pointerHax.getActivity(), "This category is empty", Toast.LENGTH_SHORT).show();
					}
				}else{
					Fragment fragment = new ExcerciseFragment();
					((ExcerciseFragment)fragment).setExercise((Exercise) listItems.get(position));
					getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("practise").commit();
				}	
			}	
		});
		
		return root;
	}
	/**
	 * gets a {@link ArrayList} of {@link ListItem}'s form {@link User}
	 */
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

	/**
	 *This method should be called before onCreatView() if {@link PractiseExercisesFragment} has instantiated an instance of itself.
	 * @param listItems
	 */
	public void setListItems(ArrayList<ListItem> listItems) {
		this.listItems = listItems;
	}
}
