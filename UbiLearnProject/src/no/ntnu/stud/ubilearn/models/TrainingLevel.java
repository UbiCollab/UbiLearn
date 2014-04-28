package no.ntnu.stud.ubilearn.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a level in the training part of the application.
 */
public class TrainingLevel
{
	/**
	 * This variable represents whether this level is locked or not.
	 */
	boolean _isLocked;
	
	/**
	 * This variable represents the score the user has achieved in this level.
	 */
	int _userScore;
	
	/**
	 * This variable represents the maximum score possible for this level.
	 */
	int _maxScore;
	
	/**
	 * This variable represents the name of this level.
	 */
	String _name;
	
	/**
	 * This variable contains a list of the houses that is part of this level. 
	 */
	List<TrainingHouse> _houseList;
	
	//#########################################################################
	public TrainingLevel()
	{
		_isLocked 	= true;
		
		_userScore 	= 0;
		_maxScore	= 0;
		
		_name		= "";
		
		_houseList	= new ArrayList<TrainingHouse>();
	}
	//_------------------------------------------------------------------------
	/**
	 * The constructor initialize an instance of TrainingLevel with the 
	 * parameters given.
	 * 
	 * @param name - The name of the level.
	 * @param isLocked - A value indicating if this level is a locked or not.
	 * @param userScore - The user score for this level.
	 * @param maxScore - The maximum score possible for this level.
	 * @param houseList - A list of all the different houses this level
	 * consists of.
	 */
	public TrainingLevel(String name, boolean isLocked, 
			int userScore, int maxScore, List<TrainingHouse> houseList)
	{
		_isLocked		= isLocked;
		
		_userScore	= userScore;
		_maxScore	= maxScore;
		
		_name		= name;
		
		_houseList	= houseList;
	}
	//-------------------------------------------------------------------------
	/**
	 * The method returns a boolean indicating whether this level is locked or
	 * not.
	 * 
	 * @return A value indicating whether this level is locked or not.
	 */
	public boolean isLocked()
	{
		return _isLocked;
	}
	//-------------------------------------------------------------------------
	/**
	 * Returns the score the user has achieved on this level.
	 * 
	 * @return The user score.
	 */
	public int getUserScore()
	{
		return _userScore;
	}
	//-------------------------------------------------------------------------
	/**
	 * Method returns the maximum score possible on this level.
	 * 
	 * @return The maximum score possible.
	 */
	public int getMaxScore()
	{
		return _maxScore;
	}
	//-------------------------------------------------------------------------
	/**
	 * Method returns the name of this level.
	 * 
	 * @return The name of this level.
	 */
	public String getName()
	{
		return _name;
	}
	//-------------------------------------------------------------------------
	/**
	 * Method returns a list of houses that this level consists of.
	 * 
	 * @return The list of houses for this level.
	 */
	public List<TrainingHouse> getHouseList()
	{
		return _houseList;
	}
	//-------------------------------------------------------------------------
	/**
	 * This method sets the locked status for this level.
	 * 
	 * @param isLocked - A value indicating whether this level is locked or
	 * not.
	 */
	public void setLockStatus(boolean isLocked)
	{
		_isLocked = isLocked;
	}
	//-------------------------------------------------------------------------
	/**
	 * This method set the user score for this level.
	 * 
	 * @param userScore - The user score for this level.
	 */
	public void setUserScore(int userScore)
	{
		_userScore = userScore;
	}
	//-------------------------------------------------------------------------
	/**
	 * This method set the maximum score possible for this level.
	 * 
	 * @param maxScore - The maximum score for this level.
	 */
	public void setMaxScore(int maxScore)
	{
		_maxScore = maxScore;
	}
	//-------------------------------------------------------------------------
	/**
	 * Method set the name of this level.
	 * 
	 * @param name - The name for this level.
	 */
	public void setName(String name)
	{
		_name = name;
	}
	//-------------------------------------------------------------------------
	/**
	 * This method sets the list of houses that this level consists of.
	 * 
	 * @param houseList - The list of houses that this level consists of.
	 */
	public void setHouseList(List<TrainingHouse> houseList)
	{
		_houseList = houseList;
	}
}
