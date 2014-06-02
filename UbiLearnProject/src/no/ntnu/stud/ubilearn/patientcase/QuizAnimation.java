package no.ntnu.stud.ubilearn.patientcase;

import no.ntnu.stud.ubilearn.R;

import android.content.Context;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;

/**
 * class for animating the quiz button when answered
 * @author ingeborgoftedal
 *
 */
public class QuizAnimation {
	/**
	 * animates the right answer
	 * @param b the button pressed
	 * @param c application context
	 * @return the color green to the button and an aplha animation
	 */
	
	public static Animation correctAnimation(final Button b, Context c){
	Button b2 = b;
    Animation animAlpha = AnimationUtils.loadAnimation(c, R.anim.quiz_button_animation);
    animAlpha.setAnimationListener(new AnimationListener() {
    	
       	int c2 = Color.GREEN;
    	int c3 = 0x01060012;
		
		@Override
		public void onAnimationStart(Animation animation) {
			b.setBackgroundColor(c2);
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			
		}
	});
    
    return animAlpha;
	}
	/**
	 * animates a wrong answer
	 * @param b the button pressed
	 * @param c the application context
	 * @return the color red to the button and an aplha animation
	 */
	public static Animation wrongAnimation(final Button b, Context c){
    Animation animAlpha1 = AnimationUtils.loadAnimation(c, R.anim.quiz_button_animation);
    animAlpha1.setAnimationListener(new AnimationListener() {
    	
    	int c2 = Color.RED;
    	int c3 = 0x01060012;
    
		@Override
		public void onAnimationStart(Animation animation) {
			b.setBackgroundColor(c2);
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			
		}
	});
	return animAlpha1;
	}
}

