package UFit.profile;
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
	final int workoutType = 1;
	
	Cardio(Context context)
	{
		super(context);
		run = false; 
		swim = false; 
		bike = false;
		walk = false;
		elliptical = false;
	}
	void saveProfile(PrintWriter out)
	{
		out.print(run + " ");
		out.print(swim + " ");
		out.print(bike + " ");
		out.print(walk + " ");
		out.println(elliptical + " ");
	}
	void loadProfile(BufferedReader b) throws IOException
	{
		String line = b.readLine();
		String[] result = line.split(" ");
		
		run = result[0].equals("true");
		swim = result[1].equals("true");
		bike = result[2].equals("true");
		walk = result[3].equals("true");
		elliptical = result[4].equals("true");
	}
	boolean getRun()
	{
		return run;
	}
	boolean getSwim()
	{
		return swim;
	}
	boolean getBike()
	{
		return bike;
	}
	boolean getWalk()
	{
		return walk;
	}
	boolean getElliptical()
	{
		return elliptical;
	}
	int getWorkoutType()
	{
		return workoutType;
	}
	
	void setRun(boolean r)
	{
		run = r;
	}
	void setSwim(boolean s)
	{
		swim = s;
	}
	void setBike(boolean b)
	{
		bike = b;
	}
	void setWalk(boolean w)
	{
		walk = w;
	}
	void setElliptical(boolean e)
	{
		elliptical =e;
	}
	
}