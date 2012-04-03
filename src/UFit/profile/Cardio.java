package ufit.profile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.content.Context;
public class Cardio extends Profile
{
	boolean run;
	boolean swim;
	boolean bike;
	boolean walk;
	boolean elliptical;
	final static int workoutType = 2;
	
	public Cardio(Context context)
	{
		super(context);
		run = false; 
		swim = false; 
		bike = false;
		walk = false;
		elliptical = false;
	}
	
	public void saveProfile(PrintWriter out)
	{
		out.print(run + " ");
		out.print(swim + " ");
		out.print(bike + " ");
		out.print(walk + " ");
		out.println(elliptical + " ");
	}
	
	public void loadProfile(BufferedReader b) throws IOException
	{
		String line = b.readLine();
		String[] result = line.split(" ");
		
		run = result[0].equals("true");
		swim = result[1].equals("true");
		bike = result[2].equals("true");
		walk = result[3].equals("true");
		elliptical = result[4].equals("true");
	}
	public boolean getRun()
	{
		return run;
	}
	public boolean getSwim()
	{
		return swim;
	}
	public boolean getBike()
	{
		return bike;
	}
	public boolean getWalk()
	{
		return walk;
	}
	public boolean getElliptical()
	{
		return elliptical;
	}
	public int getWorkoutType()
	{
		return workoutType;
	}
	
	public void setRun(boolean r)
	{
		run = r;
	}
	public void setSwim(boolean s)
	{
		swim = s;
	}
	public void setBike(boolean b)
	{
		bike = b;
	}
	public void setWalk(boolean w)
	{
		walk = w;
	}
	public void setElliptical(boolean e)
	{
		elliptical = e;
	}
	
}