package no.ntnu.stud.ubilearn.server.db;

import java.util.ArrayList;

/**
 * Abstract base class for data access objects. Handles errors and connetion to db.
 * 
 * @author Espen
 *
 */
public abstract class DAO {

	protected DBConnection db = null;
	protected ArrayList<String> errors;

	protected DAO(){
		db = DBConnection.getInstance();
	}

	/**
	 * adds errors
	 * 
	 * @param msg
	 */
	protected void addError(String msg){
		errors.add(msg);
	}
}