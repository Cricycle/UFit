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
	
	Strength(Context context)
	{
		super(context);
		legs = false;
		arms = false;
		torso = false;
	}
	void saveProfile(PrintWriter out)
	{
		out.print(legs + " ");
		out.print(arms + " ");
		out.println(torso + " ");
	}
	void loadProfile(BufferedReader b) throws IOException
	{
		String line = b.readLine();
		String[] result = line.split(" ");
		
		legs = result[0].equals("true");
		arms = result[1].equals("true");
		torso = result[2].equals("true");
	}

	void setLegs (boolean l)
	{
		legs = l;
	}
	void setArms (boolean a)
	{
		arms = a;
	}
	void setTorso (boolean t)
	{
		torso = t;
	}
	boolean getLegs ()
	{
		return legs;
	}
	boolean getArms ()
	{
		return arms;
	}
	boolean getTorso ()
	{
		return torso;
	}
	int getWorkoutType()
	{
		return workoutType;
	}
}
