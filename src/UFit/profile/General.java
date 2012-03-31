package ufit.profile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.content.Context;

public class General extends Profile
{
	Cardio cProfile;
	Strength sProfile;
	final int workoutType = 3;
	
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
	public int getWorkoutType()
	{
		return workoutType;
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
	public void setLegs(boolean l)
	{
		sProfile.setLegs(l);
	}
	public void setArms(boolean a)
	{
		sProfile.setArms(a);
	}
	public void setTorso(boolean t)
	{
		sProfile.setTorso(t);
	}
	public boolean getLegs()
	{
		return sProfile.getLegs();
	}
	public boolean getArms()
	{
		return sProfile.getArms();
	}
	public boolean getTorso()
	{
		return sProfile.getTorso();
	}
}
