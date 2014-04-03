package no.ntnu.stud.ubilearn.models;

import java.util.ArrayList;
import java.util.List;


public class TrainingLevel
{
	boolean _isLocked;
	
	int _userScore;
	int _maxScore;
	
	String _name;
	
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
	public TrainingLevel(boolean isLocked, int userScore, int maxScore,
			String name, List<TrainingHouse> houseList)
	{
		_isLocked		= isLocked;
		
		_userScore	= userScore;
		_maxScore	= maxScore;
		
		_name		= name;
		
		_houseList	= houseList;
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
	public List<TrainingHouse> getHouseList()
	{
		return _houseList;
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
	//-------------------------------------------------------------------------
	public void setHouseList(List<TrainingHouse> houseList)
	{
		_houseList = houseList;
	}
}
