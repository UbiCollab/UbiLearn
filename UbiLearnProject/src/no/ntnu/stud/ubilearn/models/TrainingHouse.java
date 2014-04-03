package no.ntnu.stud.ubilearn.models;


public class TrainingHouse
{
	boolean _isLocked;
	
	int _userScore;
	int _maxScore;
	
	String _name;
	
	//#########################################################################
	public TrainingHouse()
	{
		_isLocked	= true;
		
		_userScore	= 0;
		_maxScore	= 0;
		
		_name		= "";
	}
	//-------------------------------------------------------------------------
	public TrainingHouse(boolean isLocked, int userScore, int maxScore,
			String name)
	{
		_isLocked		= isLocked;
		
		_userScore	= userScore;
		_maxScore	= maxScore;
		
		_name		= name;
	}
	//-------------------------------------------------------------------------
	public boolean isLocked()
	{
		return _isLocked;
	}
	//-------------------------------------------------------------------------
	public int getUserScore()
	{
		return _userScore;
	}
	//-------------------------------------------------------------------------
	public int getMaxScore()
	{
		return _maxScore;
	}
	//-------------------------------------------------------------------------
	public String getName()
	{
		return _name;
	}
	//-------------------------------------------------------------------------
	public void setLock(boolean isLocked)
	{
		_isLocked = isLocked;
	}
	//-------------------------------------------------------------------------
	public void setUserScore(int userScore)
	{
		_userScore = userScore;
	}
	//-------------------------------------------------------------------------
	public void setMaxScore(int maxScore)
	{
		_maxScore = maxScore;
	}
	//-------------------------------------------------------------------------
	public void setName(String name)
	{
		_name = name;
	}
}
