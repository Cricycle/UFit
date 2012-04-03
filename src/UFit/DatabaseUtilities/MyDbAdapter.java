package ufit.DatabaseUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils.SimpleStringSplitter;

public class MyDbAdapter {
	// database fields
	public final String tName = "exerciseDB";
	public final String col_id = "_id";
	public final String col_pId = "parent_id";
	public final String col_exerc = "exercise";
	public final String col_muscle = "muscle_int";
	public final String col_type = "type";
	public final String col_equip = "equipment";
	public final String col_skill = "skill_level";
	public final String col_desc = "description";
	public final String col_iloc = "image_name_1";
	public final String col_iloc2 = "image_name_2";
	public final String col_iloc3 = "image_name_3";
	

	
	// common projections
	public final String[] proj_all = new String[] 
			{col_id, col_pId, col_exerc, col_muscle, col_type, col_equip, col_skill, col_desc, col_iloc, col_iloc2,col_iloc3};
	
	public final String[] proj_ids = new String[]
			{col_id, col_pId};
	
	public static final int query_type_string_array = 1;
	public static final int query_type_ExerciseInfo_obj = 2;
	
	private Context myContext;
	private SQLiteDatabase eDB;
	private MyDatabaseHelper eDbHelper;
	private Cursor queryResults;
	private int exerciseID = -1;

	
	/*passed to other adapters and other activities
	 * format ( [0] = type, [1] = muscle_group, [3] = skillLevel)
	 * */
	
	private String exerciseType;
	private String muscleGroup;
	private String skillLevel;
	private String[] equipmentArr;
	private ArrayList<String> equipmentList;
	private ArrayList<Integer> storedExerciseIDs;
	
	
	
	

	public MyDbAdapter(Context con)
	{
		myContext = con;
	}
	
	public MyDbAdapter(Context con, Cursor query)
	{
		myContext = con;
		this.setQueryResults(query);
	}
	
	
	public MyDbAdapter(Context con, String type, String Group, String Skill, ArrayList<String> equipList)
	{
		myContext = con;
		this.setExerciseType(type);
		this.setMuscleGroup(Group);
		this.setSkillLevel(Skill);
		this.setEquipmentList(equipList);
	}
	
	public MyDbAdapter(Context con, int exerciseId)
	{
		myContext = con;
		this.setExerciseID(exerciseId);
	}
	
	public MyDbAdapter(Context con,  String type, String Group, String Skill, ArrayList<String> equipList, Cursor query)
	{
		myContext = con;
		this.setExerciseType(type);
		this.setMuscleGroup(Group);
		this.setSkillLevel(Skill);
		this.setEquipmentList(equipList);
		this.setQueryResults(query);
	}
	
	public MyDbAdapter(Context con, ArrayList<Integer> exerciseList)
	{
		myContext = con;
		this.setStoredExerciseIDs(exerciseList);
	}
	
	public MyDbAdapter(Context con, String type, String Group, String Skill, ArrayList<String> equipList, ArrayList<Integer> exerciseList)
	{
		myContext = con;
		this.setExerciseType(type);
		this.setMuscleGroup(Group);
		this.setSkillLevel(Skill);
		this.setEquipmentList(equipList);
		this.setStoredExerciseIDs(exerciseList);
	}
	
	public SQLiteDatabase getDB()
	{
		return this.eDB;
	}
	
	public void setDB(SQLiteDatabase inDB)
	{
		this.eDB = inDB;
	}
	
	
	/*
	 * build and return a bundle to pass to another activity. 
	 * The bundle should be included in the intent with 'intent.putExtras(adapter.bundleFilters())'
	 */
	public Bundle bundleFilters()
	{
		Bundle B = new Bundle();
		B.putString("RecipeBookAp.exerciseType", getExerciseType());
		B.putString("UFit.muscleGroup", getMuscleGroup());
		B.putString("UFit.skillLevel", getSkillLevel());
		B.putStringArrayList("UFit.equipmentList", getEquipmentList());
		return B;
	}
	
	
	
	public MyDbAdapter open() throws SQLException {
		eDbHelper = new MyDatabaseHelper(myContext);
		
    try {
    	 
    	eDbHelper.createDataBase();

    } catch (IOException ioe) {

    	throw new Error("Unable to create database");

    }

    try {

    	eDbHelper.openDataBase();

    }catch(SQLiteException sqle){

    	throw sqle;

    }
		
		

		return this;
	}
	
	public void close(){
		eDbHelper.close();
	}
	

    
    public ArrayList<ExerciseInfo> fetchAllExercises() {
    	Cursor qResults = eDbHelper.queryH(tName, proj_all, null, null, null, null, null);	
    	
    	/*
    	 * public Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) 

    	 */
    	
    	
    	if(qResults == null) 
    	{
    	
    		return null;
    	}
    	
    	ArrayList<ExerciseInfo> listExercises = new ArrayList<ExerciseInfo>();
    	
    	qResults.moveToFirst();
    	
    	//copy into list
    	while(qResults.isAfterLast() == false){
    		String iloc;
    		int imageID;
    		
    		//fetch first image drawable
    		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image1Draw = myContext.getResources().getDrawable(imageID);
    		
    		//fetch second image drawable
       		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc2));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image2Draw = myContext.getResources().getDrawable(imageID);
    		
    		//fetch third image drawable
       		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc3));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image3Draw = myContext.getResources().getDrawable(imageID);
    		
    		//construct new ExerciceInfo and add to the ArrayList listExercises
    		//public ExerciseInfo(int id, int pid, String Exer, int mGroup, int type, String equip, int skill, String Descrip, Drawable i1, Drawable i2, Drawable i3)
    		listExercises.add(new ExerciseInfo(
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_id)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_pId)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_exerc)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_muscle)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_type)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_equip)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_skill)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_desc)),
    		Image1Draw,
    		Image2Draw,
    		Image3Draw
    		)
    		);
    		//move cursor to next row in result
    		qResults.moveToNext();	
    		
    	}
    	
    	qResults.close();
    	return listExercises;
    }
    
    
    public ArrayList<ExerciseInfo> fetchFocusExercises()
    {
    	Cursor qResults;
    	MyQueryHelper qHelp;
    	String WhereClause = null;
    	
    	//Insert null checks for filters
    	if((this.getExerciseType() != null) && (this.getMuscleGroup() != null) && (this.getSkillLevel() != null) && (this.getEquipmentList() != null))
    	{
    	qHelp = new MyQueryHelper(getExerciseType(), getMuscleGroup(), getEquipmentList(), getSkillLevel());
    	WhereClause = qHelp.buildMainWhere();
    	}
    	
    	//if all filters were set qResults is the list of focus exercises. Else, qResults is the list of ALL exercises;
    	qResults = eDbHelper.queryH(tName, proj_all, WhereClause, null, null, null, null);
    	
    	
    	if(qResults == null) 
    	{
    	
    		return null;
    	}
    	
    	ArrayList<ExerciseInfo> listExercises = new ArrayList<ExerciseInfo>();
    	
    	qResults.moveToFirst();
    	
    	//copy into list
    	while(qResults.isAfterLast() == false){
    		String iloc;
    		int imageID;
    		
    		//fetch first image drawable
    		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image1Draw = myContext.getResources().getDrawable(imageID);
    		
    		//fetch second image drawable
       		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc2));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image2Draw = myContext.getResources().getDrawable(imageID);
    		
    		//fetch third image drawable
       		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc3));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image3Draw = myContext.getResources().getDrawable(imageID);
    		
    		//construct new ExerciceInfo and add to the ArrayList listExercises
    		//public ExerciseInfo(int id, int pid, String Exer, int mGroup, int type, String equip, int skill, String Descrip, Drawable i1, Drawable i2, Drawable i3)
    		listExercises.add(new ExerciseInfo(
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_id)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_pId)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_exerc)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_muscle)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_type)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_equip)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_skill)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_desc)),
    		Image1Draw,
    		Image2Draw,
    		Image3Draw
    		)
    		);
    		//move cursor to next row in result
    		qResults.moveToNext();	
    		
    	}
    	
    	qResults.close();
    	return listExercises;
    }
    
    public ArrayList<ExerciseInfo> fetchOtherExercises()
    {
    	Cursor qResults;
    	MyQueryHelper qHelp;
    	String WhereClause = null;
    	
    	//Insert null checks for filters
    	if((this.getExerciseType() != null) && (this.getMuscleGroup() != null) && (this.getSkillLevel() != null) && (this.getEquipmentList() != null))
    	{
    	qHelp = new MyQueryHelper(getExerciseType(), getMuscleGroup(), getEquipmentList(), getSkillLevel());
    	WhereClause = qHelp.buildElseWhere();
    	}
    	
    	/*if all filters were set qResults is the list of nonFocus exercises, 
    	 * that still meet the Equipment and Skill requirements
    	 * Otherwise, qResults is the list of ALL exercises;
    	 * */
    	qResults = eDbHelper.queryH(tName, proj_all, WhereClause, null, null, null, null);
    	
    	
    	if(qResults == null) 
    	{
    	
    		return null;
    	}
    	
    	ArrayList<ExerciseInfo> listExercises = new ArrayList<ExerciseInfo>();
    	
    	qResults.moveToFirst();
    	
    	//copy into list
    	while(qResults.isAfterLast() == false){
    		String iloc;
    		int imageID;
    		
    		//fetch first image drawable
    		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image1Draw = myContext.getResources().getDrawable(imageID);
    		
    		//fetch second image drawable
       		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc2));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image2Draw = myContext.getResources().getDrawable(imageID);
    		
    		//fetch third image drawable
       		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc3));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image3Draw = myContext.getResources().getDrawable(imageID);
    		
    		//construct new ExerciceInfo and add to the ArrayList listExercises
    		//public ExerciseInfo(int id, int pid, String Exer, int mGroup, int type, String equip, int skill, String Descrip, Drawable i1, Drawable i2, Drawable i3)
    		listExercises.add(new ExerciseInfo(
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_id)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_pId)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_exerc)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_muscle)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_type)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_equip)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_skill)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_desc)),
    		Image1Draw,
    		Image2Draw,
    		Image3Draw
    		)
    		);
    		//move cursor to next row in result
    		qResults.moveToNext();	
    		
    	}
    	
    	qResults.close();
    	return listExercises;
    }
    
    public ExerciseInfo fetchTheExercise()
    {
    	Cursor qResults;
    	int id = this.getExerciseID();
    	
    	if(id == -1) //exerciseID not changed from default
    	{
    		return null;
    	}
    	
    	qResults = eDbHelper.queryH(tName, proj_all, col_id + "=" + id , null, null, null, null);
    	
       	if(qResults == null) 
    	{
    	
    		return null;
    	}
    	
    	ExerciseInfo theExercise;
    	
    	qResults.moveToFirst();
    	
    	//copy into list

		String iloc;
		int imageID;
		
		//fetch first image drawable
		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc));
		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
		Drawable Image1Draw = myContext.getResources().getDrawable(imageID);
		
		//fetch second image drawable
   		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc2));
		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
		Drawable Image2Draw = myContext.getResources().getDrawable(imageID);
		
		//fetch third image drawable
   		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc3));
		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
		Drawable Image3Draw = myContext.getResources().getDrawable(imageID);
		
		//construct new ExerciceInfo and add to the ArrayList listExercises
		//public ExerciseInfo(int id, int pid, String Exer, int mGroup, int type, String equip, int skill, String Descrip, Drawable i1, Drawable i2, Drawable i3)
		theExercise = new ExerciseInfo(
		qResults.getInt(qResults.getColumnIndexOrThrow(col_id)),
		qResults.getInt(qResults.getColumnIndexOrThrow(col_pId)),
		qResults.getString(qResults.getColumnIndexOrThrow(col_exerc)),
		qResults.getInt(qResults.getColumnIndexOrThrow(col_muscle)),
		qResults.getInt(qResults.getColumnIndexOrThrow(col_type)),
		qResults.getString(qResults.getColumnIndexOrThrow(col_equip)),
		qResults.getInt(qResults.getColumnIndexOrThrow(col_skill)),
		qResults.getString(qResults.getColumnIndexOrThrow(col_desc)),
		Image1Draw,
		Image2Draw,
		Image3Draw
		);

    	qResults.close();
    	
    	
    	
    	return theExercise;
    	
    }

    
    public ArrayList<ExerciseInfo> fetchStoredExercises()
    {
    	
    	Cursor qResults;
    	MyQueryHelper qHelp;
    	
    	ArrayList<ExerciseInfo> listExercises = new ArrayList<ExerciseInfo>();
    	if(this.getStoredExerciseIDs() == null)
    	{
    		return null;
    	}
    	
    	qHelp = new MyQueryHelper(this.getStoredExerciseIDs());
    	qResults = eDbHelper.queryH(tName, proj_all, qHelp.buildStoredWhere(), null, null, null, null);
    	
    	qResults.moveToFirst();
    	
    	//copy into list
    	while(qResults.isAfterLast() == false){
    		String iloc;
    		int imageID;
    		
    		//fetch first image drawable
    		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image1Draw = myContext.getResources().getDrawable(imageID);
    		
    		//fetch second image drawable
       		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc2));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image2Draw = myContext.getResources().getDrawable(imageID);
    		
    		//fetch third image drawable
       		iloc = qResults.getString(qResults.getColumnIndexOrThrow(col_iloc3));
    		imageID = myContext.getResources().getIdentifier(iloc, "drawable", myContext.getPackageName());
    		Drawable Image3Draw = myContext.getResources().getDrawable(imageID);
    		
    		//construct new ExerciceInfo and add to the ArrayList listExercises
    		//public ExerciseInfo(int id, int pid, String Exer, int mGroup, int type, String equip, int skill, String Descrip, Drawable i1, Drawable i2, Drawable i3)
    		listExercises.add(new ExerciseInfo(
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_id)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_pId)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_exerc)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_muscle)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_type)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_equip)),
    		qResults.getInt(qResults.getColumnIndexOrThrow(col_skill)),
    		qResults.getString(qResults.getColumnIndexOrThrow(col_desc)),
    		Image1Draw,
    		Image2Draw,
    		Image3Draw
    		)
    		);
    		//move cursor to next row in result
    		qResults.moveToNext();	
    		
    	}
    	
    	qResults.close();
    	return listExercises;
    	
    }
       
	/**
	 * @return the qResults
	 */
	public Cursor getQueryResults() {
		return queryResults;
	}


	/**
	 * @param qResults the qResults to set
	 */
	public void setQueryResults(Cursor qResults) {
		this.queryResults = qResults;
	}

	public int getExerciseID() {
		return exerciseID;
	}

	public void setExerciseID(int exerciseID) {
		this.exerciseID = exerciseID;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public String getMuscleGroup() {
		return muscleGroup;
	}

	public void setMuscleGroup(String muscleGroup) {
		this.muscleGroup = muscleGroup;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public ArrayList<String> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(ArrayList<String> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public String[] getEquipmentArr() {
		return equipmentArr;
	}

	public void setEquipmentArr(String[] equipmentArr) {
		this.equipmentArr = equipmentArr;
	}

	public ArrayList<Integer> getStoredExerciseIDs() {
		return storedExerciseIDs;
	}

	public void setStoredExerciseIDs(ArrayList<Integer> storedExerciseIDs) {
		this.storedExerciseIDs = storedExerciseIDs;
	}

    
	
}
