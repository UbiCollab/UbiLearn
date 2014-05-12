package no.ntnu.stud.ubilearn.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import no.ntnu.stud.ubilearn.fragments.practise.SPPBTestComparator;
import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.CasePatient;
import no.ntnu.stud.ubilearn.models.Category;
import no.ntnu.stud.ubilearn.models.Exercise;
import no.ntnu.stud.ubilearn.models.ExerciseImage;
import no.ntnu.stud.ubilearn.models.Patient;
import no.ntnu.stud.ubilearn.models.SPPB;
import no.ntnu.stud.ubilearn.models.StandUpSPPB;
import no.ntnu.stud.ubilearn.models.WalkingSPPB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class PractiseDAO extends DAO {

	public PractiseDAO(Context context) {
		super(context, "PractiseDAO");
	}
	/**
	 * inserts a patient to the database
	 * @param patient the patient to be inserted
	 * @return the id of the inserted patient
	 */
	public int insertPatient(Patient patient){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_NAME, patient.getName());
		values.put(DatabaseHandler.KEY_AGE, patient.getAge());
		values.put(DatabaseHandler.KEY_PROBLEMS, patient.getProblems());
		values.put(DatabaseHandler.KEY_COMMENT, patient.getComment());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(patient.getCreatedAt()));
		
		log(values.toString());
		
		int rowId;
		if(!exists(DatabaseHandler.TABLE_PATIENT, patient.getId())){
			rowId = (int)database.insert(DatabaseHandler.TABLE_PATIENT,null,values);
			Patient p = getPatient(rowId);
			rowId = p.getId();
		}
		else{
			rowId = database.update(DatabaseHandler.TABLE_PATIENT,values, DatabaseHandler.KEY_ID + "=?" , new String[]{""+patient.getId()});
		}
		return rowId;
	}
	public void insertPatients(List<Patient> patients){
		for (Patient patient : patients) {
			insertPatient(patient);
		}
	}
	
	public Patient getPatient(int id){
		String query = "SELECT  * FROM " + DatabaseHandler.TABLE_PATIENT + " WHERE "
	            + DatabaseHandler.KEY_ID + " = '" + id + "'";
		log(query);
		
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			return getPatient(result);
		else 
			return null;
	}
	
	private Patient getPatient(Cursor result){
		int id = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_ID));
		String name = result.getString(result.getColumnIndex(DatabaseHandler.KEY_NAME));
		String age = result.getString(result.getColumnIndex(DatabaseHandler.KEY_AGE));
		String problems = result.getString(result.getColumnIndex(DatabaseHandler.KEY_PROBLEMS));
		String comment = result.getString(result.getColumnIndex(DatabaseHandler.KEY_COMMENT));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		
		return new Patient(id, name, age, problems, comment, getTests(id),stringToDate(createdAt));
		
	}
	public ArrayList<Patient> getPatients() {
		ArrayList<Patient> patients = new ArrayList<Patient>();
		String query = "SELECT * FROM " + DatabaseHandler.TABLE_PATIENT;
		log(query);
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			do{
				Patient patient = getPatient(result);
				patients.add(patient);
			}while(result.moveToNext());
		return patients;
	}
	
	
	private ArrayList<SPPB> getTests(int patientId) {
		ArrayList<SPPB> tests = new ArrayList<SPPB>();
		tests.addAll(getStandUpSPPBs(patientId));
		tests.addAll(getBalanceSPPBs(patientId));
		tests.addAll(getWalkingSPPBs(patientId));
		
//		Patient patient = getPatient(patientId);
		for (SPPB sppb : tests) {
			sppb.setPatientId(patientId);
		}
		
		return tests;
	}

	/**
	 * 
	 * @param id the id of the patient that theese tests are performed on
	 * @return all tests of this type that this patient has performed
	 */
	public ArrayList<StandUpSPPB> getStandUpSPPBs(int id) {
		ArrayList<StandUpSPPB> tests = new ArrayList<StandUpSPPB>();
		String query = selectWhere(DatabaseHandler.TABLE_STANDUP_SPPB, DatabaseHandler.KEY_PATIENT_ID, id);
		log(query);
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			do{
				tests.add(getStandupSPPB(result));
			}while(result.moveToNext());
		return tests;
	}

	private StandUpSPPB getStandupSPPB(Cursor result) {
//		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		int id = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_ID));
		String name = result.getString(result.getColumnIndex(DatabaseHandler.KEY_NAME));
		int patientId = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_PATIENT_ID));
		boolean failed = (result.getInt(result.getColumnIndex(DatabaseHandler.KEY_FAILED))==1);
		Double time = result.getDouble(result.getColumnIndex(DatabaseHandler.KEY_TIME));
		String seatHeight = result.getString(result.getColumnIndex(DatabaseHandler.KEY_SEAT_HEIGHT));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		return new StandUpSPPB(id, name, patientId, time, stringToDate(createdAt),failed, seatHeight);
	}
	/**
	 * 
	 * @param id the id of the patient that theese tests are performed on
	 * @return all tests of this type that this patient has performed
	 */
	public ArrayList<BalanceSPPB> getBalanceSPPBs(int id) {
		ArrayList<BalanceSPPB> tests = new ArrayList<BalanceSPPB>();
		String query = selectWhere(DatabaseHandler.TABLE_BALANCE_SPPB, DatabaseHandler.KEY_PATIENT_ID, id);
		log(query);
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			do{
				tests.add(getBalanceSPPB(result));
			}while(result.moveToNext());
		return tests;
	}

	private BalanceSPPB getBalanceSPPB(Cursor result) {
//		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		int id = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_ID));
		String name = result.getString(result.getColumnIndex(DatabaseHandler.KEY_NAME));
		int patientId = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_PATIENT_ID));
		boolean failed = (result.getInt(result.getColumnIndex(DatabaseHandler.KEY_FAILED))==1);
		int pairedScore = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_PAIRED_SCORE));
		int semiTandemScore = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_SEMI_TANDEM_SCORE));
		int tandemScore = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_TANDEM_SCORE));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		return new BalanceSPPB(id, name, patientId, stringToDate(createdAt),failed, pairedScore, semiTandemScore, tandemScore);
	}
	/**
	 * 
	 * @param id the id of the patient that theese tests are performed on
	 * @return all tests of this type that this patient has performed
	 */
	public ArrayList<WalkingSPPB> getWalkingSPPBs(int id) {
		ArrayList<WalkingSPPB> tests = new ArrayList<WalkingSPPB>();
		String query = selectWhere(DatabaseHandler.TABLE_WALKING_SPPB, DatabaseHandler.KEY_PATIENT_ID, id);
		log(query);
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			do{
				tests.add(getWalkingSPPB(result));
			}while(result.moveToNext());
		return tests;
	}
	
	private WalkingSPPB getWalkingSPPB(Cursor result) {
		int id = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_ID));
		String name = result.getString(result.getColumnIndex(DatabaseHandler.KEY_NAME));
		int patientId = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_PATIENT_ID));
		boolean failed = (result.getInt(result.getColumnIndex(DatabaseHandler.KEY_FAILED))==1);
		Double time = result.getDouble(result.getColumnIndex(DatabaseHandler.KEY_TIME));
		boolean noAid = (result.getInt(result.getColumnIndex(DatabaseHandler.KEY_NO_AID)) == 1);
		boolean crutches = (result.getInt(result.getColumnIndex(DatabaseHandler.KEY_CRUTCHES)) == 1);
		boolean rollater = (result.getInt(result.getColumnIndex(DatabaseHandler.KEY_ROLLATER)) == 1);
		String other = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OTHER));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		return new WalkingSPPB(id, name, patientId, stringToDate(createdAt),failed, time, noAid, crutches, rollater, other);
	}

	public void insertSBBP(SPPB test){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_NAME, test.getName());
		values.put(DatabaseHandler.KEY_PATIENT_ID, test.getPatientId());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(test.getCreatedAt()));
		values.put(DatabaseHandler.KEY_FAILED, test.failed());
		String table = "";
		if(test instanceof WalkingSPPB){
			insertWalkingSPPB((WalkingSPPB)test, values);
			table = DatabaseHandler.TABLE_WALKING_SPPB;
		}
		else if (test instanceof BalanceSPPB){
			insertBalanceSPPB((BalanceSPPB) test, values);
			table = DatabaseHandler.TABLE_BALANCE_SPPB;
		}
		else if (test instanceof StandUpSPPB){
			insertStandUpSPPB((StandUpSPPB) test, values);
			table = DatabaseHandler.TABLE_STANDUP_SPPB;
		}
		
		log(values.toString());
		
		long rowId;
		if(!exists(table, test.getId()))
			rowId = database.insert(table,null,values);
		else{
			rowId = database.update(table,values, DatabaseHandler.KEY_ID + "=?" , new String[]{""+test.getId()});
		}
	}
	private void insertWalkingSPPB(WalkingSPPB test, ContentValues values){
		values.put(DatabaseHandler.KEY_TIME, test.getTime());
		values.put(DatabaseHandler.KEY_NO_AID, test.isNoAid());
		values.put(DatabaseHandler.KEY_CRUTCHES, test.isCrutches());
		values.put(DatabaseHandler.KEY_ROLLATER, test.isRollater());
		values.put(DatabaseHandler.KEY_OTHER, test.getOther());
		
	}
	private void insertBalanceSPPB(BalanceSPPB test, ContentValues values){
		values.put(DatabaseHandler.KEY_PAIRED_SCORE, test.getPairedScore());
		values.put(DatabaseHandler.KEY_SEMI_TANDEM_SCORE, test.getSemiTandemScore());
		values.put(DatabaseHandler.KEY_TANDEM_SCORE, test.getTandemScore());
	}
	private void insertStandUpSPPB(StandUpSPPB test, ContentValues values){
		values.put(DatabaseHandler.KEY_TIME, test.getTime());
		values.put(DatabaseHandler.KEY_SEAT_HEIGHT, test.getSeatHeight());
	}
	
	public void printTables(){
		printTable(DatabaseHandler.TABLE_PATIENT);
		printTable(DatabaseHandler.TABLE_BALANCE_SPPB);
		printTable(DatabaseHandler.TABLE_STANDUP_SPPB);
		printTable(DatabaseHandler.TABLE_WALKING_SPPB);
	}
	public HashMap<String, SPPB> getBestResults(int patientId) {
		HashMap<String, SPPB> tests = new HashMap<String, SPPB>();
		
		ArrayList<WalkingSPPB> walkingTests = getWalkingSPPBs(patientId);
		Collections.sort(walkingTests,new SPPBTestComparator());
		if(walkingTests.isEmpty())
			tests.put("Walking", null);
		else
			tests.put("Walking", walkingTests.get(walkingTests.size()-1));
		
		ArrayList<BalanceSPPB> balanceTests = getBalanceSPPBs(patientId);
		Collections.sort(balanceTests,new SPPBTestComparator());
		if(balanceTests.isEmpty())
			tests.put("Balance", null);
		else
			tests.put("Balance", balanceTests.get(balanceTests.size()-1));
		
		ArrayList<StandUpSPPB> standUpTests = getStandUpSPPBs(patientId);
		Collections.sort(standUpTests,new SPPBTestComparator());
		if(standUpTests.isEmpty())
			tests.put("StandUp", null);
		else
			tests.put("StandUp", standUpTests.get(standUpTests.size()-1));

		return tests;
	}
	/**
	 * Deletes a patient and all his tests from the database
	 * 
	 * @param patient the patient that will be deleted
	 */
	public void deletePatientData(Patient patient){
		String query = "DELETE from " + DatabaseHandler.TABLE_PATIENT + " WHERE "
	            + DatabaseHandler.KEY_ID + " = '" + patient.getId() + "'";
		log(query);
		int rowsDeleted = database.delete(DatabaseHandler.TABLE_PATIENT, DatabaseHandler.KEY_ID + "=?" , new String[]{""+patient.getId()});
		if(rowsDeleted == 0)
			log("Patient " + patient.getName() + " was not found");
		else
			log(rowsDeleted + " patient " + patient.getName() + " was deleted");
		
		rowsDeleted = 0;
		rowsDeleted += database.delete(DatabaseHandler.TABLE_BALANCE_SPPB, DatabaseHandler.KEY_PATIENT_ID + "=?" , new String[]{""+patient.getId()});
		rowsDeleted += database.delete(DatabaseHandler.TABLE_STANDUP_SPPB, DatabaseHandler.KEY_PATIENT_ID + "=?" , new String[]{""+patient.getId()});
		rowsDeleted += database.delete(DatabaseHandler.TABLE_WALKING_SPPB, DatabaseHandler.KEY_PATIENT_ID + "=?" , new String[]{""+patient.getId()});
		if(rowsDeleted == 0)
			log("No tests from patient " + patient.getName() + " was found");
		else
			log("Deleted " + rowsDeleted + " tests from patient " + patient.getName());	
	}
	
	
	
	//-----------------EXERCISES--------------------
	
	
	public void insertExercise(Exercise exercise){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_OBJECT_ID, exercise.getObjectId());
		values.put(DatabaseHandler.KEY_NAME, exercise.getName());
		values.put(DatabaseHandler.KEY_TEXT, exercise.getText());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(exercise.getCreatedAt()));
		
		log(values.toString());
		
		if(!exists(DatabaseHandler.TABLE_EXERCISE, exercise.getObjectId())){
			database.insert(DatabaseHandler.TABLE_EXERCISE,null,values);
		}
		else{
			database.update(DatabaseHandler.TABLE_EXERCISE,values, DatabaseHandler.KEY_OBJECT_ID + "=?" , new String[]{exercise.getObjectId()});
		}
		if(!exercise.getImages().isEmpty())
			insertImages(exercise.getImages());
	}

	public Exercise getExercise(String objectId){
		String query = "SELECT  * FROM " + DatabaseHandler.TABLE_EXERCISE + " WHERE "
	            + DatabaseHandler.KEY_OBJECT_ID + " = '" + objectId + "'";
		log(query);
		
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			return getExercise(result);
		else 
			return null;
	}
	private Exercise getExercise(Cursor result){
		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		String name = result.getString(result.getColumnIndex(DatabaseHandler.KEY_NAME));
		String text = result.getString(result.getColumnIndex(DatabaseHandler.KEY_TEXT));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		Exercise ex = new Exercise(objectId, name, text,stringToDate(createdAt));
		ex.setImages(getExerciseImages(objectId));
		return ex;
	}

	public ArrayList<ExerciseImage> getExerciseImages(String exerciseId) {
		ArrayList<ExerciseImage> images = new ArrayList<ExerciseImage>();
		String query = "SELECT * FROM " + DatabaseHandler.TABLE_EXERCISE_IMAGE + " WHERE "
	            + DatabaseHandler.KEY_OWNER_ID + " = '" + exerciseId + "'";
		log(query);
		
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			do{
				images.add(getExerciseImage(result));
			}while(result.moveToNext());
		return images;
	}
	private ExerciseImage getExerciseImage(Cursor result) {
		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		byte[] bytes = result.getBlob(result.getColumnIndex(DatabaseHandler.KEY_IMAGE));
		String ownerId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OWNER_ID));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		return new ExerciseImage(objectId, bytes, ownerId, stringToDate(createdAt));
	}
	public void insertImage(ExerciseImage image){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_OBJECT_ID, image.getObjectId());
		values.put(DatabaseHandler.KEY_IMAGE, image.getBytes());
		values.put(DatabaseHandler.KEY_OWNER_ID, image.getOwnerId());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(image.getCreatedAt()));
		
		log(values.toString());
		
		if(!exists(DatabaseHandler.TABLE_EXERCISE_IMAGE, image.getObjectId())){
			database.insert(DatabaseHandler.TABLE_EXERCISE_IMAGE,null,values);
		}
		else{
			database.update(DatabaseHandler.TABLE_EXERCISE_IMAGE,values, DatabaseHandler.KEY_OBJECT_ID + "=?" , new String[]{image.getObjectId()});
		}
	}
	private void insertImages(ArrayList<ExerciseImage> images) {
		for (ExerciseImage exerciseImage : images) {
			insertImage(exerciseImage);
		}
	}
}
