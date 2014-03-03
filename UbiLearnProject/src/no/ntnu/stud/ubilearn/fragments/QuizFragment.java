package no.ntnu.stud.ubilearn.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.patientcase.Patient;
import no.ntnu.stud.ubilearn.patientcase.Quiz;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class QuizFragment extends Fragment{
	private ArrayList<Quiz> quiz = new ArrayList<Quiz>();
	private Patient patient;
	private String _qst;
	int i;


	public QuizFragment(){
		
	}
	
	public QuizFragment(Patient patient){
		this.patient = patient;
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_training_quiz, container, false);
		
		
		quiz = generateQuiz(patient.getName());
		Log.v("PASIENTNAVN: ", patient.getName());
		String[] qtn = quiz.get(0).getAlternatives();
		
		
		
		
		TextView question = (TextView)rootView.findViewById(R.id.question);
		question.setText("yolo");
		Button ans1 = (Button)rootView.findViewById(R.id.quiz_button1);
		ans1.setText(qtn[0]);
		Button ans2 = (Button)rootView.findViewById(R.id.quiz_button2);
		ans2.setText(qtn[1]);
		Button ans3 = (Button)rootView.findViewById(R.id.quiz_button3);
		ans3.setText(qtn[2]);
		Button ans4 = (Button)rootView.findViewById(R.id.quiz_button4);
		ans4.setText(qtn[3]);
		
		ans1.setOnClickListener(onClickListener);
		ans2.setOnClickListener(onClickListener);
		ans3.setOnClickListener(onClickListener);
		ans4.setOnClickListener(onClickListener);
		return rootView;
	}
	private OnClickListener onClickListener = new OnClickListener() {

	    @Override
	    public void onClick(final View v) {
	             switch(v.getId()){
	                 case R.id.quiz_button1:
	                 case R.id.quiz_button2:
	                 case R.id.quiz_button3:
	                 case R.id.quiz_button4:
	                      //DO something
	                 break;
	              }

	    }
	};
	private ArrayList<Quiz> generateQuiz(String name){
		
		String json = null;
		ArrayList<Quiz> finalQuiz = new ArrayList<Quiz>();
	//	quiz = new ArrayList<Quiz>();
		try {
			InputStream is = getActivity().getAssets().open("quiz_questions.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			
			json = new String(buffer, "UTF-8");
			
		} catch (IOException ie) {
			Log.e("ERROR I/O", "error reading quiz file");
		}
		try {
			JSONObject jsonObj = new JSONObject(json);
			JSONArray quizArray = jsonObj.getJSONArray("questions");
			for (int i = 0; i < quizArray.length(); i++) {
				JSONObject jo = (JSONObject) quizArray.get(i);
				if(jo.getString("eier").equals(name)){
					finalQuiz.add(new Quiz(
							jo.getString("spm"),
							jo.getString("svar1"),
							jo.getString("svar2"),
							jo.getString("svar3"),
							jo.getString("riktigSvar")));
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("ERROR JSON", "error parsing json");
		}
		return finalQuiz;
	}


}
