package no.ntnu.stud.ubilearn.fragments.handbook;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.db.HandbookDAO;
import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.CasePatientStatus;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.WikiItem;
import no.ntnu.stud.ubilearn.parse.SyncContent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class CategoryFragment extends Fragment {
	private View root;
	private ListView categoryListView;
	private ArrayList<WikiItem> listItems;
	private Fragment pointerHax = this;
	

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
	
	
	public void setListItems(ArrayList<WikiItem> listItems) {
		this.listItems = listItems;
	}

	public void fetchDataFromDAO(){
		if (listItems == null) {
			HandbookDAO dao = new HandbookDAO(getActivity());
			dao.open();
			listItems = (ArrayList<WikiItem>) dao.getHandbook();
			dao.close();
		}
	}
	
	public void generateTestData(){
		if (listItems == null) {
			ArrayList<WikiItem> sub = new ArrayList<WikiItem>();
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
			listItems = new ArrayList<WikiItem>();
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
