package no.ntnu.stud.ubilearn.fragments.wiki;

import java.util.Arrays;

import no.ntnu.stud.ubilearn.R;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class WikiFragment extends Fragment {
	private View root;
	private ListView categoryListView;
	private WikiItem[] listItems;
	private Fragment pointerHax = this;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		System.out.println("test");
		generateTestData();
		System.out.println("test2");
		root = inflater.inflate(R.layout.fragment_wiki, null);
		categoryListView = (ListView) root.findViewById(R.id.wikiListView);
		categoryListView.setAdapter(new ArrayAdapter<WikiItem>(this.getActivity(), R.layout.wiki_list_item, listItems));
		
		categoryListView.setClickable(true);
		categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				if (listItems.length > position) {
					if (listItems[position] instanceof Catagory) {
						Catagory cat = (Catagory) listItems[position];
						if (cat.hasSub()) {
							listItems =  cat.getSub();
							categoryListView.setAdapter(new ArrayAdapter<WikiItem>(pointerHax.getActivity(), R.layout.wiki_list_item, listItems));
						}
					}else{
						Fragment article = new ArticleFragment();
						getFragmentManager().beginTransaction().replace(R.id.content_frame, article).commit();
					}
					
				}
			}
			
		});
		return root;
	}
	
	private void generateTestData(){
		WikiItem[] subCategories = new WikiItem[4];
		subCategories[0] = new Catagory("Category 1", null);
		subCategories[1] = new Catagory("Category 1", null);
		subCategories[2] = new Article("Some article", getResources().getString(R.string.gibberish));
		subCategories[3] = new Article("Some other article", getResources().getString(R.string.gibberish));
		listItems = new Catagory[4];
		listItems[0] = new Catagory("Category 1", subCategories);
		listItems[1] = new Catagory("Category 1", subCategories);
		listItems[2] = new Catagory("Category 1", subCategories);
		listItems[3] = new Catagory("Category 1", subCategories);
	}
}
