package no.ntnu.stud.ubilearn.fragments.handbook;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.adapter.HandbookItemAdapter;
import no.ntnu.stud.ubilearn.db.HandbookDAO;
import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.CasePatientStatus;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.ListItem;
import no.ntnu.stud.ubilearn.parse.SyncContent;
import android.os.Bundle;
import android.app.Fragment;
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
 * Each item in the ListView has a onItemClick() method which either brings you to a new {@link CategoryFragment}, or
 * a {@link ArticleFragment}. The class first fetches a {@link ArrayList} of {@link ListItem} from a {@link HandbookDAO}.
 * It will then, either call itself recursively or instantiate a {@link ArticleFragment}, depending on what type of {@link ListItem}
 * that has been selected.
 *@see View
 *@see TextView
 *@see ListView
 *@see ListItem
 *@see HandbookItemAdapter
 */
public class CategoryFragment extends Fragment {
	private View root;
	private ListView categoryListView;
	private ArrayList<ListItem> listItems = null;
	private Fragment pointerHax = this;
	

	/**
	 * @see CategoryFragment
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		fetchDataFromDAO();
		root = inflater.inflate(R.layout.fragment_handbook, null);
		categoryListView = (ListView) root.findViewById(R.id.wikiListView);
		categoryListView.setAdapter(new HandbookItemAdapter(this.getActivity(), listItems));
		
		categoryListView.setClickable(true);
		categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,long arg3) {
			
				if (listItems.get(position) instanceof Category) {
					Category cat = (Category) listItems.get(position);
					if (cat.hasSubs()) {
						CategoryFragment handbook = new CategoryFragment();
						handbook.setListItems(cat.getSub());
						getFragmentManager().beginTransaction().replace(R.id.content_frame, handbook).addToBackStack(null).commit();
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
	
	/**
	 * This method should be called before onCreatView() if {@link CategoryFragment} has instantiated an instance of itself.
	 * @param listItems
	 */
	public void setListItems(ArrayList<ListItem> listItems) {
		this.listItems = listItems;
	}
	
	/**
	 * fetches data from {@link HandbookDAO}
	 */
	private void fetchDataFromDAO(){
		if (listItems == null) {
			HandbookDAO dao = new HandbookDAO(getActivity());
			dao.open();
			listItems = (ArrayList<ListItem>) dao.getHandbook();
			dao.close();
		}
	}
	/**
	 *Simple method for generating test data.
	 */
	public void generateTestData(){
		if (listItems == null) {
			ArrayList<ListItem> sub = new ArrayList<ListItem>();
//			sub.add(new Catagory("Sub-category 1", null));
//			sub.add(new Catagory("Sub-category 2", null));
//			sub.add(new Catagory("Sub-category 3", null));
//			sub.add(new Catagory("Sub-category 4", null));
			sub.add(new Article("Article 1", getResources().getString(
					R.string.gibberish)));
			sub.add(new Article("Article 2", getResources().getString(
					R.string.gibberish)));
			sub.add(new Article("Article 3",
					getResources().getString(R.string.gibberish)));
			listItems = new ArrayList<ListItem>();
			listItems.add(new Category("Category 1", sub));
			listItems.add(new Category("Category 2", sub));
			listItems.add(new Category("Category 3", sub));
			listItems.add(new Category("Category 4", sub));
			listItems.add(new Category("Category 5", sub));
			listItems.add(new Category("Category 6", sub));
			listItems.add(new Category("Category 7", sub));
			listItems.add(new Category("Category 8", sub));
			listItems.add(new Category("Category 9", sub));
			listItems.add(new Category("Category 10", sub));
		}
	}
}
