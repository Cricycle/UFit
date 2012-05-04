package ufit.profile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.content.Context;

public class General extends Profile
{
	Cardio cProfile;
	Strength sProfile;
	final static int workoutType = 3;

	public General(Context context)
	{
		super(context);
		cProfile = new Cardio(context);
		sProfile = new Strength(context);
	}
	public void saveProfile(PrintWriter out)
	{
		cProfile.saveProfile(out);
		sProfile.saveProfile(out);
	}
	public void loadProfile(BufferedReader b)throws IOException
	{
		cProfile.loadProfile(b);
		sProfile.loadProfile(b);
	}

	public int getWorkoutType()
	{
		return workoutType;
	}
	public boolean getRun()
	{
		return cProfile.getRun();
	}
	public boolean getSwim()
	{
		return  cProfile.getSwim();
	}
	public boolean getBike()
	{
		return  cProfile.getBike();
	}
	public boolean getWalk()
	{
		return  cProfile.getWalk();
	}
	public boolean getElliptical()
	{
		return  cProfile.getElliptical();
	}

	public void setRun(boolean r)
	{
		 cProfile.setRun(r);
	}
	public void setSwim(boolean s)
	{
		cProfile.setSwim(s);
	}
	public void setBike(boolean b)
	{
		cProfile.setBike(b);
	}
	public void setWalk(boolean w)
	{
		cProfile.setWalk(w);
	}
	public void setElliptical(boolean e)
	{
		cProfile.setElliptical(e);
	}

	public boolean getChest()
	{
		return sProfile.getChest();
	}
	public boolean getBack()
	{
		return sProfile.getBack();
	}
	public boolean getLegs()
	{
		return sProfile.getLegs();
	}
	public boolean getBiceps()
	{
		return sProfile.getBiceps();
	}
	public boolean getTriceps()
	{
		return sProfile.getTriceps();
	}
	public boolean getShoulders()
	{
		return sProfile.getShoulders();
	}
	public boolean getQuads()
	{
		return sProfile.getQuads();
	}
	public boolean getHamstrings()
	{
		return sProfile.getHamstrings();
	}
	public boolean getCalves()
	{
		return sProfile.getCalves();
	}
	public boolean getGlutes()
	{
		return sProfile.getGlutes();
	}
	public boolean getFullBody()
	{
		return sProfile.getFullBody();
	}
	public boolean getAbs()
	{
		return sProfile.getAbs();
	}
	public void setChest(boolean c)
	{
		sProfile.setChest(c);
	}
	public void setBack(boolean b)
	{
		sProfile.setBack(b);
	}
	public void setLegs(boolean l)
	{
		sProfile.setLegs(l);
	}
	public void setBiceps(boolean b)
	{
		sProfile.setBiceps(b);
	}
	public void setTriceps(boolean t)
	{
		sProfile.setTriceps(t);
	}
	public void setShoulders(boolean s)
	{
		sProfile.setShoulders(s);
	}
	public void setQuads(boolean q)
	{
		sProfile.setQuads(q);
	}
	public void setHamstrings(boolean h)
	{
		sProfile.setHamstrings(h);
	}
	public void setCalves(boolean c)
	{
		sProfile.setCalves(c);
	}
	public void setGlutes(boolean g)
	{
		sProfile.setGlutes(g);
	}
	public void setFullBody(boolean fb)
	{
		sProfile.setFullBody(fb);
	}
	public void setAbs(boolean a)
	{
		sProfile.setAbs(a);
	}
	public ArrayList<Integer> getMuscleGroupList() {
		return sProfile.getMuscleGroupList();
	}

}
