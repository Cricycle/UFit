package ufit.DatabaseUtilities;

import java.util.ArrayList;

import android.util.Log;

public class MyQueryHelper {
	

	private String filter1;	//type
	private String filter2; //group
	private ArrayList<String> filter3; //equipList
	private String filter4; //skill level
	private static final String col_x = "type";
	private static final String col_y = "muscle_int";
	private static final String col_z = "equipment";
	private static final String col_s = "skill_level";
	private static final String col_id = "_id";
	private ArrayList<Integer> storedExerciseIDs;
	private ArrayList<Integer> muscleAreas; //building support for multiple muscle Groups
	
	
	
	
	MyQueryHelper(String f1, String f2, String[] f3, String f4)
	{
		this.setFilter1(f1);
		this.setFilter2(f2);
		
		ArrayList<String> temp = new ArrayList<String>();
		int size = f3.length;
		System.out.println(size);
		int i = 0;
		while(i < size) //builds array list for later functions;
		{
			temp.add(f3[i]);
		}
		
		this.setFilter3(temp);
		this.setFilter4(f4);
		
		
	}
	
	//this is the constructor most should be using
	MyQueryHelper(String f1, ArrayList<Integer> f2, ArrayList<String> f3, String f4)
	{
		this.setFilter1(f1);
		this.setMuscleAreas(f2);
		this.setFilter3(f3);
		
		String temp; 
		if(f4.equalsIgnoreCase("3"))
		{
		temp = "('1','2','3')";
		}
		else if(f4.equalsIgnoreCase("2"))
		{
			temp = "('1','2')";
		}
		else if(f4.equalsIgnoreCase("1"))
		{
			temp = "('1')";
		}
		else
		{
			temp = "('1')";
			Log.wtf("skillLevel out of range", "SkillLevel was set to:" + f4);
		}
		this.setFilter4(temp);	
	}
	
	MyQueryHelper(int f1, int f2, ArrayList<String> f3, int f4)
	{
		String temp;
		temp = Integer.toString(f1);
		this.setFilter1(temp);
		
		temp = Integer.toString(f2);
		this.setFilter2(temp);
		
		this.setFilter3(f3);
		
		if(f4 == 3)
		{
		temp = "('1','2','3')";
		}
		else if(f4 == 2)
		{
			temp = "('1','2')";
		}
		else if(f4 ==1)
		{
			temp = "('1')";
		}
		else
		{
			temp = "('1')";
			Log.wtf("skillLevel out of range", "SkillLevel was set to:" + f4);
		}
		this.setFilter4(temp);
		
	}
	
	MyQueryHelper(int f1, int f2, String[] f3, int f4)
	{
		String temp;
		temp = Integer.toString(f1);
		this.setFilter1(temp);
		temp = Integer.toString(f2);
		this.setFilter2(temp);
		
		ArrayList<String> tempArr = new ArrayList<String>();
		int size = f3.length;
		System.out.println(size);
		int i = 0;
		while(i < size) //builds array list for later functions;
		{
			tempArr.add(f3[i]);
		}
		
		this.setFilter3(tempArr);
		
		if(f4 == 3)
		{
		temp = "('1','2','3')";
		}
		else if(f4 == 2)
		{
			temp = "('1','2')";
		}
		else if(f4 ==1)
		{
			temp = "('1')";
		}
		else
		{
			temp = "('1')";
			Log.wtf("skillLevel out of range", "SkillLevel was set to:" + f4);
		}
		this.setFilter4(temp);
		
		
	}
	
	MyQueryHelper(long f1, long f2, ArrayList<String> f3, long f4)
	{
		String temp;
		temp = Long.toString(f1);
		this.setFilter1(temp);
		temp = Long.toString(f2);
		this.setFilter2(temp);
		this.setFilter3(f3);
		
		if(f4 == 3)
		{
		temp = "('1','2','3')";
		}
		else if(f4 == 2)
		{
			temp = "('1','2')";
		}
		else if(f4 ==1)
		{
			temp = "('1')";
		}
		else
		{
			temp = "('1')";
			Log.wtf("skillLevel out of range", "SkillLevel was set to:" + f4);
		}
		this.setFilter4(temp);
		
	}
	
	MyQueryHelper(long f1, long f2, String[] f3, long f4)
	{
		String temp;
		temp = Long.toString(f1);
		this.setFilter1(temp);
		temp = Long.toString(f2);
		this.setFilter2(temp);
		
		ArrayList<String> tempArr = new ArrayList<String>();
		int size = f3.length;
		System.out.println(size);
		int i = 0;
		while(i < size) //builds array list for later functions;
		{
			tempArr.add(f3[i]);
		}
		
		this.setFilter3(tempArr);
		
		if(f4 == 3)
		{
		temp = "('1','2','3')";
		}
		else if(f4 == 2)
		{
			temp = "('1','2')";
		}
		else if(f4 ==1)
		{
			temp = "('1')";
		}
		else
		{
			temp = "('1')";
			Log.wtf("skillLevel out of range", "SkillLevel was set to:" + f4);
	    	
		}
		this.setFilter4(temp);
		
		
	}
	
	MyQueryHelper(int f1, ArrayList<Integer> f2, ArrayList<String> f3, int f4) 
	{
		this.setFilter1(Integer.toString(f1));
		this.setMuscleAreas(f2);
		this.setFilter3(f3);
		this.setFilter4(Integer.toString(f4));
	}
	
	MyQueryHelper(ArrayList<Integer> storedIDs)
	{
		this.storedExerciseIDs = storedIDs;
	}
	

	public void setFilter3(ArrayList<String> filter3) {
		this.filter3 = filter3;
	}

	public ArrayList<String> getFilter3() {
		return filter3;
	}

	public void setFilter1(String f1) {
		//adding a check for general option (type =3) to prevent the main query from returning nothing
		// this will cause the else query to return no results instead...preference?
		String temp;
		if(f1.equalsIgnoreCase("3"))
		{
			temp = "1','2";
		}
		else
		{
			temp = f1;
		}
		this.filter1 = temp;
	}

	public String getFilter1() {
		return filter1;
	}

	public void setFilter2(String filter2) {
		this.filter2 = filter2;
	}

	public String getFilter2() {
		return filter2;
	}
	
	public String buildMainWhere()
	{
		ArrayList<String> temp = this.getFilter3();
		ArrayList<Integer> Areas = this.getMuscleAreas();
		int size = temp.size();
		int sizeAreas = Areas.size();
		
			temp.add("None"); // if not, search the database for exercises with "None" for required equip
			size = temp.size(); //update to new size
		
		
		
		//if we make them click something this isn't needed and there's probably a better way to implement it lol;
		if(sizeAreas <= 0) //if no muscleAreas were selected;
		{
			if(this.getFilter1().contains("1")) //if strength or general
			{
				for(int i = 0; i < 12; i++) //add all of the muscle integers to the list (0:11) inclusive
				{
				Areas.add(i);
				}
			}
			
		}
	//
		
			if(this.getFilter1().contains("2")) //if the user selected cardio or general
			{
			Areas.add(11);
			Areas.add(-1);
			}
		
		sizeAreas = Areas.size(); //update to new size
		
		String out;
		
/*		  //migrating to new method
		if(size != 0)
		{
*/
		
		out = "("+ col_x +" IN ('"+this.getFilter1() +"')) AND (" +col_y+ " IN ('";
		
			for(int i = 0; i <=sizeAreas-2;i++) //stop one before last element
			{
				out = out + Areas.get(i) + "','";
			}
			
		out = out + Areas.get(sizeAreas-1) + "')) AND ("+col_s+" IN "+this.getFilter4()+") AND (" +col_z+ " IN ('";
		
			for(int i = 0; i<=size-2; i++) //stop one before last element
			{
				out = out + temp.get(i) + "','";
			}
		out = out + temp.get(temp.size()-1) + "'))";
/*			}

		else
		{
			out = "("+ col_x +" IN ('"+this.getFilter1() +"')) AND (" +col_y+ "=" + this.getFilter2() +") AND ("+col_s+" IN "+this.getFilter4()+") AND (" +col_z+ " IN ('None'))";
		}
*/
		
		return out;
		
		//example filter1 = 1, muscleAreas = <2,3,4,5> filter3[] = {Barbell, Machine} filter4 = ('1','2')
		//out = (type IN ('1')) AND (muscle_int IN ('2','3','4','5') AND (skill_level IN ('1','2') AND (equipment IN ('Barbells','Machine'))
		
		//example2 filter1 = 1, muscleAreas = <0,1,3,>, filter3[] = {}, filter4 = ('1','2')
			//out = (type=1) AND (muscle_int IN ('0','1','3') AND (skill_level IN ('1','2') AND (equipment IN ('None'))
		
	}
	
	public String buildElseWhere()
	{
		ArrayList<Integer> Areas = this.getMuscleAreas();
		ArrayList<String> temp = this.getFilter3();
		int size = temp.size();
		
			temp.add("None"); // if not, search the database for exercises with "None" for required equip
			size = temp.size(); //update to new size
		
		int sizeAreas = Areas.size();
		
		//if we make them click something this isn't needed and there's probably a better way to implement cardio lol;
		if(sizeAreas <= 0) //if no muscleAreas were selected;
		{
			
			if(this.getFilter1().contains("1")) // if strength or genearl
			{
				for(int i = 0; i < 12; i++) //add all of the muscle integers to the list (0:11) inclusive
				{
				Areas.add(i);
				}
			
			}
	
		}
		
		if(this.getFilter1().contains("2")) //if cardio or general
		{
			Areas.add(11);
			Areas.add(-1);
		}
		
		sizeAreas = Areas.size(); //update to new size
		//
		
		String out;
		
/*		if(size != 0)  //migrating to other method
		{
*/		if (this.getFilter1().equalsIgnoreCase("1','2"))
		{
			out = "((" +col_x+ " IN ('"+this.getFilter1() +"')) OR (" +col_y+ " NOT IN('";
		}
		else
		{
			out = "((" +col_x+ " NOT IN ('"+this.getFilter1() +"')) OR (" +col_y+ " NOT IN('";
		}	
			
		for(int i = 0; i <=sizeAreas-2;i++) //stop one before last element
		{
			out = out + Areas.get(i) + "','";
		}
				
				
		out = out + Areas.get(sizeAreas-1)	+"'))) AND ("+col_s+" IN "+this.getFilter4()+") AND (" +col_z+ " IN ('";

		for(int i = 0; i<=size-2; i++) //stop one before last element
		{
			out = out + temp.get(i) + "','";
		}
		out = out + temp.get(temp.size()-1) + "'))";
		
/*		}
		
		else
		{
			out = "((" +col_x+ " NOT IN ('"+this.getFilter1() +"')) OR (" +col_y+ " NOT IN('" + this.getFilter2() +"'))) AND ("+col_s+" IN "+this.getFilter4()+") AND (" +col_z+ " IN ('None'))";
	}
*/		
		
		return out;
		
		//example filter1 = strength, muscleAreas = <0,1,3>, filter3[] = {barbells, machine)
		// out = ((type NOT IN('strength')) OR (muscle_int NOT IN ('0','1','3'))) AND (equipment IN ('barbells','machine'))
		
	}
	
	public String buildStoredWhere()
	{
		String out;
		out =  col_id + " IN ('";
		
		ArrayList<Integer> temp = this.storedExerciseIDs;
		if(temp != null && temp.size() > 0)
		{
			int size = temp.size();
			for(int i = 0; i <= size-2; i++) //stop one before the end
			{
			out = out + temp.get(i)+ "','";
			}
			
			out = out + temp.get(temp.size()-1) +"')"; //add last element and finish
			
		}
		else out = null;
		
		return out;
		
		//example storedExerciseIDs = {1, 2, 3, 4, 5}
		// out = _id IN ('1','2','3','4','5')
	}

	public void setFilter4(String filter4) {
		this.filter4 = filter4;
	}

	public String getFilter4() {
		return filter4;
	}

	public ArrayList<Integer> getMuscleAreas() {
		return muscleAreas;
	}

	public void setMuscleAreas(ArrayList<Integer> muscleAreas) {
		this.muscleAreas = muscleAreas;
	}
	
	

}







