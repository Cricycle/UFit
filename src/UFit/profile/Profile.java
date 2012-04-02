package ufit.profile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ufit.namespace.R;
import android.content.Context;
import android.content.res.Resources;

public class Profile
{
	String username;
	String pictureLocation;
	double heightInches;
	double weight;
	double targetWeight;
	int age;
	int b_day;
	int b_month;
	int b_year;
	int day;
	int month;
	int year;
	int gender; //1 is male, 2 is female.
	static final int workoutType = 0;
	int numWorkoutDays;
	int skill; //1 indicates beginner, 2 indicates intermediate, 3 indicates advanced.
	ArrayList<Integer> id;
	
	boolean equipment[];
	String equipmentNames[]; //somehow use string.xml as a resource to get names.
	int equipmentID[];
	
	public Profile(Context context)
	{
	     username = "Albert";
		 setHeightFeet(6,0);
		 weight = 200;
		 targetWeight = 170;
		 b_day = 1;
		 b_month = 1;
		 b_year = 1990;
		 
		 Calendar cal = Calendar.getInstance();
		   day = cal.get(Calendar.DATE);
		   month = cal.get(Calendar.MONTH) + 1;
		   year = cal.get(Calendar.YEAR);
		   age = computeAge(day, month, year);
		 
		 gender = 1;
		 numWorkoutDays = 0;
		 skill = 1;
		 Resources res = context.getResources();
		 equipmentNames = res.getStringArray(R.array.equipmentNames);
		 equipment = new boolean[equipmentNames.length];
		 pictureLocation = "NULL//@NoSpace";
		 id = new ArrayList<Integer>();
	}
	
	//the purpose of this function is to extend a regular Profile to a specified type
	public Profile extend(int workOutType, Context context) { 
		Profile p = null;
		if(workOutType == 1) {
			p = new Strength(context);
		} else if(workOutType == 2) {
			p = new Cardio(context);
		} else if(workOutType == 3){
			p = new General(context);
		} else {
			//there shall be errors!!!#error
		}
		
		p.username = this.username;
		p.pictureLocation = this.pictureLocation;
		p.heightInches = this.heightInches;
		p.weight = this.weight;
		p.targetWeight= this.targetWeight;
		p.age = this.age;
		p.b_day = this.b_day;
		p.b_month = this.b_month;
		p.b_year = this.b_year;
		p.day = this.day;
		p.month = this.month;
		p.year = this.year;
		p.gender = this.gender;
		p.numWorkoutDays = this.numWorkoutDays;
		p.skill = this.skill;
		p.id = this.id;
		p.equipment = this.equipment;
		p.equipmentNames = this.equipmentNames;
		p.equipmentID = this.equipmentID;
		
		p.setRun( this.getRun() );
		p.setSwim( this.getSwim() );
		p.setBike( this.getBike() );
		p.setElliptical( this.getElliptical() );
		p.setWalk( this.getWalk() );
		
		
		return p;
	}
	public void saveProfile(Profile p) 
	{
		try{
			PrintWriter out = new PrintWriter(new FileWriter(p.username + ".txt"));
			out.print(p.getWorkoutType() + " ");
			out.print(p.getUsername() + " ");
			out.print(p.getHeightInches() + " ");
			out.print(p.getWeight() + " ");
			out.print(p.getTargetWeight() + " ");
			out.print(p.getBirthMonth() + " ");
			out.print(p.getBirthDay() + " ");
			out.print(p.getBirthYear() + " ");
			out.print(p.getGender() + " ");
			out.print(p.getSkill() + " ");
			out.println(p.getNumWorkoutDays() + " ");
			
			for(int i=0; i<equipment.length; i++)
			{
				out.print(equipment[i] + " ");
			}
			out.println();
			
			if(p.getWorkoutType()==1)
			{
				Strength s = (Strength) p;
				s.saveProfile(out);
			}
			else if(p.getWorkoutType()==2)
			{
				Cardio c = (Cardio) p;
				c.saveProfile(out);
			}
			else if(p.getWorkoutType()==3)
			{
				General g = (General) p;
				g.saveProfile(out);
			}
			out.println(p.getPictureLocation());
			out.println(id.size());
			
			for(int i=0; i<id.size();i++)
			{
				out.print(id.get(i)+" ");
			}
			out.println();
			out.println(p.month + " " + p.day + " " + p.year);
			
			
			out.close();
	  }catch (Exception e){System.out.println("File Error: Save Profile");}
	}
	public final static Profile loadProfile(String un, Context context)
	{
		try{
		  BufferedReader b = new BufferedReader(new FileReader(un + ".txt"));
	
		  String line1 = b.readLine();
		  Profile p = null;
		  
		  String[] result = line1.split(" ");
		  int type =  new Integer(result[0]);
		  
		  if(type==1)
		  {
			  p = new Strength(context);  
		  }
		  else if(type==2)
		  {
			  p = new Cardio(context);
		  }
		  else if(type==3)
		  {
			  p = new General(context);
		  }
		  else
		  {
			  throw new Exception("loadProfile Type Error");
		  }
		  p.setUsername(result[1]);
		  
		  p.setHeightInches(new Double(result[2]));
		  p.setWeight(new Double(result[3]));
		  p.setTargetWeight(new Double(result[4]));
		  p.setBirthDate(new Integer(result[5]),new Integer(result[6]),new Integer(result[7]));
		  p.setGender(new Integer(result[8]));
		  p.setSkill(new Integer(result[9]));
		  p.setNumWorkoutDays(new Integer(result[10]));
		  
		  
		  Calendar cal = Calendar.getInstance();
		  int d = cal.get(Calendar.DATE);
		  int m = cal.get(Calendar.MONTH) + 1;
		  int y = cal.get(Calendar.YEAR);
		  p.setAge(p.computeAge(d, m, y));
		  
		  String line2 = b.readLine();
		  result = line2.split(" ");
		  
		  for(int i=0; i<result.length; i++) //this could cause array out of bounds exception
		  {
			  p.equipment[i] = result[i].equals("true");
		  }
		  
		  if(type==1)
		  {
			  Strength s = (Strength) p;
			  s.loadProfile(b);
		  }
		  else if(type==2)
		  {
			  Cardio c = (Cardio) p; 
			  c.loadProfile(b);
		  }
		  else if(type==3)
		  {
			  General g = (General) p;
			  g.loadProfile(b);
		  }
		  
		  p.pictureLocation = b.readLine();
		  int size = new Integer(b.readLine());
		  
		  String line = b.readLine();
		  result = line.split(" ");
		  ArrayList<Integer> a = new ArrayList<Integer>();
		  
		  for(int i=0; i<size; i++)
		  { 
			  a.add(new Integer(result[i]));
		  }
		  p.setID(a);
		  line = b.readLine();
		  result = line.split(" ");
		  p.setDate(new Integer(result[0]),new Integer(result[1]),new Integer(result[2]));
		  
		  b.close();
		  return p;
		  
		  
		}catch (Exception e){System.out.println("File Error: Get Profile");}
		return null;
	}
	public void setUsername(String un)
	{
		username = un;
	}
	public void setHeightFeet(double f, double i)
	{
		heightInches = (12 * f)+i;
	}
	public void setHeightInches(double i)
	{
		heightInches = i;
	}
	public void setWeight(double w) 
	{
		weight = w;
	}
	public void setTargetWeight(double w) 
	{
		targetWeight = w;
	}
	public void setBirthDate(int m, int d, int y)
	{
		b_month = m;
		b_day = d;
		b_year = y;
	}
	public void setDate(int m, int d, int y)
	{
		month = m;
		day = d;
		year = y;
	}
	public void setAge(int a) 
	{
		age = a;
	}
	public void setGender(int g) 
	{ //1 is male, 2 is female.
		gender = g;
	}
	public void setSkill(int s)
	{
		skill=s;
	}
	public void setNumWorkoutDays(int d)
	{
		numWorkoutDays=d;
	}
	public void setPictureLocation(String p)
	{
		pictureLocation=p;
	}
	public boolean setEquipment(String e, boolean have)
	{
		int index = -1;
		boolean ret = false;
		for(int i=0; i<equipmentNames.length; i++)
		{
			if(equipmentNames[i].equals(e))
			{
				index = i;
			}
		}
		if(index != -1)
		{
			ret = true;
			equipment[index]=have;
		}
		return ret;	
	}
	public void setID(ArrayList<Integer> a)
	{
		id = a;
	}
	
	
	public String getUsername()
	{
		return username;
	}
	public double getHeightInches() 
	{
		return heightInches;
	}
	public double getWeight() 
	{
		return weight;
	}
	public double getTargetWeight() 
	{
		return targetWeight;
	}
	public int getAge() 
	{
		return age;
	}
	public int getBirthMonth()
	{
		return b_month;
	}
	public int getBirthDay()
	{
		return b_day;
	}
	public int getBirthYear()
	{
		return b_year;
	}
	public int getMonth()
	{
		return month;
	}
	public int getDay()
	{
		return day;
	}
	public int getYear()
	{
		return year;
	}
	public int getGender() 
	{
		return gender;
	}
	public int getWorkoutType()
	{
		return workoutType;
	}
	public int getSkill()
	{
		return skill;
	}
	public int getNumWorkoutDays()
	{
		return numWorkoutDays;
	}
	public String getPictureLocation()
	{
		return pictureLocation;
	}
	public boolean getEquipment(String e)
	{
		boolean ret = false;
		for(int i=0; i<equipmentNames.length; i++)
		{
			if(equipmentNames[i].equals(e))
			{
				ret = equipment[i];
			}
		}
		return ret;
	}
	public ArrayList<String> getEquipmentList()
	{
		ArrayList<String> a = new ArrayList<String>();
		String e;
		
		for(int i=0; i<equipment.length; i++)
		{
			if(equipment[i])
					{
						e = equipmentNames[i];
						a.add(new String(e));
					}
		}
		return a;
	}
	public int computeAge(int d, int m, int y)
	{
		int a = y-b_year;
		if(b_month>m || (b_month==m && b_day>d))
		{
			a=a-1;
		}
		return a;
	}
	public double computeBMI()
	{
		double bmi_value = (weight*703) / (heightInches*heightInches);
		bmi_value=(double)(Math.round(bmi_value*10))/10;
		return bmi_value;
	}
	public double getCalories()
	{
		double BMR;
		double calories;
		if(gender==1)
		{
			BMR = (66 + (6.23*weight) + (12.7*heightInches) - (6.76*age));
		}
		else
		{
			BMR = (655 + (4.35*weight) +(4.7*heightInches) - (4.7*age));
		}
		
		if(numWorkoutDays<=3 && numWorkoutDays>=1)
		{
			calories = BMR * 1.375;
		}
		else if(numWorkoutDays<=5 && numWorkoutDays>=3)
		{
			calories = BMR * 1.55;
		}
		else if(numWorkoutDays>5)
		{
			calories = BMR * 1.725;
		}
		else
		{
			calories = BMR * 1.2;
		}
		return calories;
	}
	public ArrayList<Integer> getID()
	{
		return id;
	}
	public boolean hasBeenWeek()
	{
		Calendar cal = Calendar.getInstance();
		  int d = cal.get(Calendar.DATE);
		  int m = cal.get(Calendar.MONTH) + 1;
		  int y = cal.get(Calendar.YEAR);

		  Calendar cal1 = new GregorianCalendar();
		  Calendar cal2 = new GregorianCalendar();

		  cal1.set(y, m, d); 
		  cal2.set(year, month, day);
		  Date d1 = (cal1.getTime());
		  Date d2 = (cal2.getTime());
		  long diff = d1.getTime()-d2.getTime();
		  int difference = (int)(diff / (1000 * 60 * 60 * 24));
		  boolean week =false;
		  
		  if(difference>6)week=true;
		  return week;
				
	}
	
	//I chose to add these 10 methods here so that I can make use of polymorphism in the cardioselection screens
	public boolean getRun()
	{
		return false;
	}
	public boolean getSwim()
	{
		return false;
	}
	public boolean getBike()
	{
		return false;
	}
	public boolean getWalk()
	{
		return false;
	}
	public boolean getElliptical()
	{
		return false;
	}
	
	public void setRun(boolean r){}
	public void setSwim(boolean s){}
	public void setBike(boolean b){}
	public void setWalk(boolean w){}
	public void setElliptical(boolean e){}
	
	
}
