package no.ntnu.stud.ubilearn.models;

import com.parse.ParseUser;


/**
 * This class represents a house in the training part of the application.
 */
public class TrainingHouse
{
	/**
	 * Indicates whether a house is completed or not.
	 */
	boolean _isComplete;
	
	/**
	 * The score the user has achieved for this house.
	 */
	int _userScore;
	
	/**
	 * The maximum possible score for this house.
	 */
	int _maxScore;
	
	/**
	 * The name that is given for this house.
	 */
	String _name;
	
	
	//#########################################################################
	/**
	 * The default constructor for this class.
	 */
	public TrainingHouse()
	{
		_isComplete	= true;
		
		_userScore	= 0;
		_maxScore	= 0;
		
		_name		= "";
	}
	//-------------------------------------------------------------------------
	/**
	 * Creates an instance of this class. 
	 * 
	 * @param name - The name of this house.
	 * @param isCompleted - Indicates whether this house is Completed or not.
	 * A house is "Completed" when the user has managed 75% or more of the
	 * questions for the house.
	 * @param userScore - The score the user has achieved for this house.
	 * @param maxScore - The maximum score possible for this house.
	 */
	public TrainingHouse(String name, boolean isComplete, 
			int userScore, int maxScore)
	{
		_isComplete	= isComplete;
		
		_userScore	= userScore;
		_maxScore	= maxScore;
		
		_name		= name;
	}
	//-------------------------------------------------------------------------
	/**
	 * Returns the "Completed" status for this house.
	 * 
	 * @return True if this house is completed or false if it is not completed.
	 */
	public boolean isCompleted()
	{
		return _isComplete;
	}
	//-------------------------------------------------------------------------
	/**
	 * Returns the score the user has achieved for this house.
	 * 
	 * @return The score achieved for this house.
	 */
	public int getUserScore()
	{
		return _userScore;
	}
	//-------------------------------------------------------------------------
	/**
	 * Returns the maximum score for this house.
	 * 
	 * @return The maximum score for this house.
	 */
	public int getMaxScore()
	{
		return _maxScore;
	}
	//-------------------------------------------------------------------------
	/**
	 * Returns the name for this house.
	 *
	 * @return The name of this house.
	 */
	public String getName()
	{
		return _name;
	}
	//-------------------------------------------------------------------------
	/**
	 * We set the lock status for this house.
	 * 
	 * @param isLocked - The status of the lock.
	 */
	public void setLockStatus(boolean isCompleted)
	{
		_isComplete = isCompleted;
	}
	//-------------------------------------------------------------------------
	/**
	 * Here we set the user score for this house.
	 * 
	 * @param userScore - The score for this house.
	 */
	public void setUserScore(int userScore)
	{
		_userScore = userScore;
	}
	//-------------------------------------------------------------------------
	/**
	 * This method set the maximum score possible for this house.
	 * 
	 * @param maxScore - The maximum score possible for this house.
	 */
	public void setMaxScore(int maxScore)
	{
		_maxScore = maxScore;
	}
	//-------------------------------------------------------------------------
	/**
	 * This method sets the name for this house.
	 * 
	 * @param name - The name of this house.
	 */
	public void setName(String name)
	{
		_name = name;
	}
}
