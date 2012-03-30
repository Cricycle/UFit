package UFit.DatabaseUtilities;

import java.util.ArrayList;

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
	
	MyQueryHelper(String f1, String f2, ArrayList<String> f3, String f4)
	{
		this.setFilter1(f1);
		this.setFilter2(f2);
		this.setFilter3(f3);
		this.setFilter4(f4);
		
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
		}
		this.setFilter4(temp);
		
		
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

	public void setFilter1(String filter1) {
		this.filter1 = filter1;
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
		String out;
		out = "("+ col_x +"="+this.getFilter1() +") AND (" +col_y+ "=" + this.getFilter2() +") AND ("+col_s+" IN "+this.getFilter4()+") AND (" +col_z+ " IN ('";
		ArrayList<String> temp = this.getFilter3();
		int size = temp.size();
		for(int i = 0; i<size-2; i++) //stop one before last element
		{
			out = out + temp.get(i) + "','";
		}
		out = out + temp.get(temp.size()-1) + "'))";
		
		return out;
		
		//example filter1 = 1, filter2 = 2 filter3[] = {Barbell, Machine)
		//out = (type=1) AND (muscle_int=2) AND (equipment IN ('Barbells','Machine'))
		
	}
	
	public String buildElseWhere()
	{
		String out;
		out = "((" +col_x+ " NOT IN ('"+this.getFilter1() +"')) OR (" +col_y+ " NOT IN('" + this.getFilter2() +"'))) AND ("+col_s+" IN "+this.getFilter4()+") AND (" +col_z+ " IN ('";
		ArrayList<String> temp = this.getFilter3();
		int size = temp.size();
		for(int i = 0; i<size-2; i++) //stop one before last element
		{
			out = out + temp.get(i) + "','";
		}
		out = out + temp.get(temp.size()-1) + "'))";
		
		return out;
		
		//example filter1 = strength, filter2 = arms filter3[] = {barbells, machine)
		// out = ((type NOT IN('strength')) OR (muscle_int NOT IN ('arms'))) AND (equipment IN ('barbells','machine'))
		
	}
	
	public String buildStoredWhere()
	{
		String out;
		out =  col_id + " IN ('";
		
		ArrayList<Integer> temp = this.storedExerciseIDs;
		if(temp != null)
		{
			int size = temp.size();
			for(int i = 0; i < size-2; i++)
			{
			out = out + temp.get(i)+ "','";
			}
			
			out = out + temp.get(temp.size()-1) +"')";
			
		}
						
		return out;
		
		//example storedExerciseIDs = 1 2 3 4 5
		// out = _id IN ('1','2','3','4','5')
	}

	public void setFilter4(String filter4) {
		this.filter4 = filter4;
	}

	public String getFilter4() {
		return filter4;
	}
	
	

}







