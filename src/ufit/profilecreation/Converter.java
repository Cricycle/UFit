package ufit.profilecreation;

import android.util.Log;

public class Converter 
{
	public Converter()
	{
	}
	
	public String muscleConvert(int muscle)
	{
		if (muscle == 0)
		{
			return "Chest";
		}
		else if (muscle == 1)
		{
			return "Back";
		}
		else if (muscle == 2)
		{
			return "Legs";
		}
		else if (muscle == 3)
		{
			return "Biceps";
		}
		else if (muscle == 4)
		{
			return "Triceps";
		}
		else if (muscle == 5)
		{
			return "Shoulders";
		}
		else if (muscle == 6)
		{
			return "Quads";
		}
		else if (muscle == 7)
		{
			return "Hamstrings";
		}
		else if (muscle == 8)
		{
			return "Calves";
		}
		else if (muscle == 9)
		{
			return "Glutes";
		}
		else if (muscle == 10)
		{
			return "Full Body";
		}
		else if (muscle == 11)
		{
			return "Abs";
		}
		else if (muscle == -1)
		{
			return "Cardio";
		}
		else
		{
			Log.wtf("Muscle Int", "Muscle Group not in list");
			return "Error! (Robot Voice)";
		}
	}
}
