package no.ntnu.stud.ubilearn.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.db.TrainingDAO;
import no.ntnu.stud.ubilearn.models.CasePatient;
import no.ntnu.stud.ubilearn.models.Quiz;
import no.ntnu.stud.ubilearn.patientcase.QuizAnimation;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class QuizFragment extends Fragment{
	private ArrayList<Quiz> quiz = new ArrayList<Quiz>();
	private CasePatient patient;

	private int i;

	private TextView question;
	private Button ans1;
	private Button ans2;
	private Button ans3;
	private Button ans4;
	private int points;
	private Button nextBtn;
	private int correctCounter = 0;
	private TrainingDAO trainingDAO;

	public QuizFragment(){

	}

	public QuizFragment(CasePatient patient){
		this.patient = patient;
		i = 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_training_quiz, container, false);

		//----------GSON---------------
//		quiz = generateQuiz(patient.getName());
		
		//---------SQLite---------------
		trainingDAO = new TrainingDAO(getActivity());
		trainingDAO.open();
		quiz = trainingDAO.getPatientQuizzes(patient);
		trainingDAO.close();
		

		question = (TextView)rootView.findViewById(R.id.question);
		ans1 = (Button)rootView.findViewById(R.id.quiz_button1);
		ans2 = (Button)rootView.findViewById(R.id.quiz_button2);
		ans3 = (Button)rootView.findViewById(R.id.quiz_button3);
		ans4 = (Button)rootView.findViewById(R.id.quiz_button4);
		nextBtn = (Button)rootView.findViewById(R.id.quiz_next_button);

		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(quiz.size()-1 > i){
					i++;
					nextBtn.setVisibility(View.INVISIBLE);
					setQuiz();
				}
				else {
					setDialog();
				}

			}
		});

		ans1.setOnClickListener(new CustomClick(ans1));
		ans2.setOnClickListener(new CustomClick(ans2));
		ans3.setOnClickListener(new CustomClick(ans3));
		ans4.setOnClickListener(new CustomClick(ans4));


		setQuiz();
		return rootView;
	}


	private void setQuiz(){
		enableButtons(true);
		String[] qtn = quiz.get(i).getAlternatives();
		question.setText(quiz.get(i).getQuestion());
		ans1.setText(qtn[0]);
		ans1.setBackgroundResource(android.R.drawable.btn_default);
		//Color.parseColor();

		ans2.setText(qtn[1]);
		ans2.setBackgroundResource(android.R.drawable.btn_default);

		ans3.setText(qtn[2]);
		ans3.setBackgroundResource(android.R.drawable.btn_default);

		ans4.setText(qtn[3]);
		ans4.setBackgroundResource(android.R.drawable.btn_default);


	}

	@SuppressLint("NewApi")
	public class CustomClick implements OnClickListener{

		Button b;

		public CustomClick(Button b){
			this.b = b;
		}


		@Override
		public void onClick(View v) {
			enableButtons(false);
			if(quiz.get(i).checkAnswer(b.getText().toString())){
				correctCounter++;
				Log.v("tekst", b.getText().toString());
				Toast.makeText(getActivity(), "riktig svar", Toast.LENGTH_SHORT).show();
				b.setAnimation(QuizAnimation.correctAnimation(b, getActivity()));
				b.startAnimation(QuizAnimation.correctAnimation(b, getActivity()));

			}
			else {
				Toast.makeText(getActivity(), "feil svar", Toast.LENGTH_SHORT).show();
				b.setAnimation(QuizAnimation.wrongAnimation(b, getActivity()));
				b.startAnimation(QuizAnimation.wrongAnimation(b, getActivity()));
			}

			nextBtn.setVisibility(View.VISIBLE);

			Log.v("Knapp trykket:", b.getText().toString());



		}

	}
	public void setDialog(){
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.fragment_finish_quiz);
		dialog.setTitle("Ingen flere spørsmål igjen");

		Button ok = (Button) dialog.findViewById(R.id.finish_quiz_button);
		TextView correctQ = (TextView)dialog.findViewById(R.id.quiz_spm_correct);
		correctQ.setText("Du klarte "+correctCounter + " av " + quiz.size());
		//TODO oppdatere status på huset
		User.getInstance().setHouseStatus(correctCounter, quizCleared(), patient.getObjectId());
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View vi) {

				Bundle data = new Bundle();
				Fragment fragment = new Training();
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Training").commit();
				int temp = getFragmentManager().getBackStackEntryCount();
				for(int i = 0; i < temp;i++){
					getFragmentManager().popBackStack();
				}
				dialog.dismiss();
			}
		});

		dialog.show();
	}
	public void enableButtons(boolean b){
		ans1.setEnabled(b);
		ans2.setEnabled(b);
		ans3.setEnabled(b);
		ans4.setEnabled(b);
	}
	private boolean quizCleared(){
		if(correctCounter >= ((int)(quiz.size()*0.75))){
			return true;
		}else{
			return false;
		}
	}

}
