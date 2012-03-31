package UFit.profile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.content.Context;

public class General extends Profile
{
	Cardio cProfile;
	Strength sProfile;
	final int workoutType = 3;
	
	General(Context context)
	{
		super(context);
		cProfile = new Cardio(context);
		sProfile = new Strength(context);
	}
	void saveProfile(PrintWriter out)
	{
		cProfile.saveProfile(out);
		sProfile.saveProfile(out);
	}
	void loadProfile(BufferedReader b)throws IOException
	{
		cProfile.loadProfile(b);
		sProfile.loadProfile(b);
	}
	boolean getRun()
	{
		return cProfile.getRun();
	}
	boolean getSwim()
	{
		return  cProfile.getSwim();
	}
	boolean getBike()
	{
		return  cProfile.getBike();
	}
	boolean getWalk()
	{
		return  cProfile.getWalk();
	}
	boolean getElliptical()
	{
		return  cProfile.getElliptical();
	}
	int getWorkoutType()
	{
		return workoutType;
	}
	
	void setRun(boolean r)
	{
		 cProfile.setRun(r);
	}
	void setSwim(boolean s)
	{
		cProfile.setSwim(s);
	}
	void setBike(boolean b)
	{
		cProfile.setBike(b);
	}
	void setWalk(boolean w)
	{
		cProfile.setWalk(w);
	}
	void setElliptical(boolean e)
	{
		cProfile.setElliptical(e);
	}


	void setLegs (boolean l)
	{
		sProfile.setLegs(l);
	}
	void setArms (boolean a)
	{
		sProfile.setArms(a);
	}
	void setTorso (boolean t)
	{
		sProfile.setTorso(t);
	}

	boolean getLegs ()
	{
		return sProfile.getLegs();
	}
	boolean getArms ()
	{
		return sProfile.getArms();
	}
	boolean getTorso ()
	{
		return sProfile.getTorso();
	}
}
