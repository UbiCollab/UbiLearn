package no.ntnu.stud.ubilearn.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.CasePatient;
import no.ntnu.stud.ubilearn.models.Category;
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
			System.out.println(rowId);
			Patient p = getPatient(rowId);
			rowId = p.getId();
			System.out.println(rowId);
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
	public ArrayList<SPPB> getStandUpSPPBs(int id) {
		ArrayList<SPPB> tests = new ArrayList<SPPB>();
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
		Double time = result.getDouble(result.getColumnIndex(DatabaseHandler.KEY_TIME));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		
		return new StandUpSPPB(id, name, patientId, time, stringToDate(createdAt));
	}
	/**
	 * 
	 * @param id the id of the patient that theese tests are performed on
	 * @return all tests of this type that this patient has performed
	 */
	public ArrayList<SPPB> getBalanceSPPBs(int id) {
		ArrayList<SPPB> tests = new ArrayList<SPPB>();
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
		int pairedScore = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_PAIRED_SCORE));
		int semiTandemScore = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_SEMI_TANDEM_SCORE));
		int tandemScore = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_TANDEM_SCORE));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		return new BalanceSPPB(id, name, patientId, stringToDate(createdAt), pairedScore, semiTandemScore, tandemScore);
	}
	/**
	 * 
	 * @param id the id of the patient that theese tests are performed on
	 * @return all tests of this type that this patient has performed
	 */
	public ArrayList<SPPB> getWalkingSPPBs(int id) {
		ArrayList<SPPB> tests = new ArrayList<SPPB>();
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
		int patientId = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_ID));
		Double time = result.getDouble(result.getColumnIndex(DatabaseHandler.KEY_TIME));
		boolean noAid = (result.getInt(result.getColumnIndex(DatabaseHandler.KEY_NO_AID)) == 1);
		boolean crutches = (result.getInt(result.getColumnIndex(DatabaseHandler.KEY_CRUTCHES)) == 1);
		boolean rollater = (result.getInt(result.getColumnIndex(DatabaseHandler.KEY_ROLLATER)) == 1);
		String other = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OTHER));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		return new WalkingSPPB(id, name, patientId, stringToDate(createdAt), time, noAid, crutches, rollater, other);
	}

	public void insertSBBP(SPPB test){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_NAME, test.getName());
		values.put(DatabaseHandler.KEY_PATIENT_ID, test.getPatientId());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(test.getCreatedAt()));
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
	}
	
	public void printTables(){
		printTable(DatabaseHandler.TABLE_PATIENT);
		printTable(DatabaseHandler.TABLE_BALANCE_SPPB);
		printTable(DatabaseHandler.TABLE_STANDUP_SPPB);
		printTable(DatabaseHandler.TABLE_WALKING_SPPB);
	}
	public HashMap<String, SPPB> getBestResults(int patientId) {
		// TODO Auto-generated method stub
		return null;
	}
}
