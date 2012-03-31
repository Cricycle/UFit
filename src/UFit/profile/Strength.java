package UFit.profile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.content.Context;

public class Strength extends Profile
{
	boolean legs;
	boolean arms;
	boolean torso;
	final int workoutType = 2 ;
	
	public Strength(Context context)
	{
		super(context);
		legs = false;
		arms = false;
		torso = false;
	}
	public void saveProfile(PrintWriter out)
	{
		out.print(legs + " ");
		out.print(arms + " ");
		out.println(torso + " ");
	}
	public void loadProfile(BufferedReader b) throws IOException
	{
		String line = b.readLine();
		String[] result = line.split(" ");
		
		legs = result[0].equals("true");
		arms = result[1].equals("true");
		torso = result[2].equals("true");
	}

	public void setLegs (boolean l)
	{
		legs = l;
	}
	public void setArms (boolean a)
	{
		arms = a;
	}
	public void setTorso (boolean t)
	{
		torso = t;
	}
	public boolean getLegs ()
	{
		return legs;
	}
	public boolean getArms ()
	{
		return arms;
	}
	public boolean getTorso ()
	{
		return torso;
	}
	public int getWorkoutType()
	{
		return workoutType;
	}
}
