package ufit.profile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.content.Context;

public class Strength extends Profile
{
	boolean chest;
	boolean back;
	boolean legs;
	boolean biceps;
	boolean triceps;
	boolean shoulders;
	boolean quads;
	boolean hamstrings;
	boolean calves;
	boolean glutes;
	boolean fullBody;
	boolean abs;
	final static int workoutType = 1;

	public Strength(Context context)
	{
		super(context);
		chest = false;
		back = false;
		legs = false;
		biceps = false;
		triceps = false;
		shoulders = false;
		quads = false;
		hamstrings = false;
		calves = false;
		glutes = false;
		fullBody = false;
		abs = false;
	}
	public void saveProfile(PrintWriter out)
	{
		out.print(chest + " ");
		out.print(back + " ");
		out.print(legs + " ");
		out.print(biceps + " ");
		out.print(triceps + " ");
		out.print(shoulders + " ");
		out.print(quads + " ");
		out.print(hamstrings + " ");
		out.print(calves + " ");
		out.print(glutes + " ");
		out.print(fullBody + " ");
		out.println(abs + " ");
	}
	public void loadProfile(BufferedReader b) throws IOException
	{
		String line = b.readLine();
		String[] result = line.split(" ");

		chest = result[0].equals("true");
		back = result[1].equals("true");
		legs = result[2].equals("true");
		biceps = result[3].equals("true");
		triceps = result[4].equals("true");
		shoulders = result[5].equals("true");
		quads = result[6].equals("true");
		hamstrings = result[7].equals("true");
		calves = result[8].equals("true");
		glutes = result[9].equals("true");
		fullBody = result[10].equals("true");
		abs = result[11].equals("true");
	}

	public void setChest(boolean c)
	{
		chest = c;
	}
	public void setBack(boolean b)
	{
		back = b;
	}
	public void setLegs(boolean l)
	{
		legs = l;
	}
	public void setBiceps(boolean b)
	{
		biceps = b;
	}
	public void setTriceps(boolean t)
	{
		triceps = t;
	}
	public void setShoulders(boolean s)
	{
		shoulders = s;
	}
	public void setQuads(boolean q)
	{
		quads = q;
	}
	public void setHamstrings(boolean h)
	{
		hamstrings = h;
	}
	public void setCalves(boolean c)
	{
		calves = c;
	}
	public void setGlutes(boolean g)
	{
		glutes = g;
	}
	public void setFullBody(boolean fb)
	{
		fullBody = fb;
	}
	public void setAbs(boolean a)
	{
		abs = a;
	}

	public boolean getChest()
	{
		return chest;
	}
	public boolean getBack()
	{
		return back;
	}
	public boolean getLegs()
	{
		return legs;
	}
	public boolean getBiceps()
	{
		return biceps;
	}
	public boolean getTriceps()
	{
		return triceps;
	}
	public boolean getShoulders()
	{
		return shoulders;
	}
	public boolean getQuads()
	{
		return quads;
	}
	public boolean getHamstrings()
	{
		return hamstrings;
	}
	public boolean getCalves()
	{
		return calves;
	}
	public boolean getGlutes()
	{
		return glutes;
	}
	public boolean getFullBody()
	{
		return fullBody;
	}
	public boolean getAbs()
	{
		return abs;
	}
	public int getWorkoutType()
	{
		return workoutType;
	}
	public ArrayList<Integer> getMuscleGroupList() {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if(getChest())
			ret.add(0);
		if(getBack())
			ret.add(1);
		if(getLegs())
			ret.add(2);
		if(getBiceps())
			ret.add(3);
		if(getTriceps())
			ret.add(4);
		if(getShoulders())
			ret.add(5);
		if(getQuads())
			ret.add(6);
		if(getHamstrings())
			ret.add(7);
		if(getCalves())
			ret.add(8);
		if(getGlutes())
			ret.add(9);
		if(getFullBody())
			ret.add(10);
		if(getAbs())
			ret.add(11);
		return ret;
	}
}
