package ufit.profilecreation;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ufit.DatabaseUtilities.ExerciseInfo;
import ufit.DatabaseUtilities.ExerciseInfoAdapter;
import ufit.DatabaseUtilities.MyDbAdapter;
import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;


public class TodaysExercisesActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private MyApp application;
    private Profile profile;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaysexercises);
        application = (MyApp)getApplication();
        profile = ((MyApp)getApplication()).getProfile();
        initialiseButtons();
        
        ArrayList<Integer> extraCardio = new ArrayList<Integer>();
        extraCardio.add(35);
        extraCardio.add(41);
        if (profile.getElliptical())
        	extraCardio.add(37);
        if (profile.getBike())
        	extraCardio.add(38);
        
        System.gc();

        // pass in current day
        Bundle b = getIntent().getExtras();
        String whatDay = getBundleString(b,"day",this.getString(R.string.today));
        //Toast.makeText(this,whatDay, Toast.LENGTH_SHORT).show();
        loadInformation(whatDay);
        //name of items

        ArrayList <ExerciseInfo> listOfExercises = new ArrayList<ExerciseInfo>();
 
        MyDbAdapter db = new MyDbAdapter(this, profile.getID());
        db.open();
        listOfExercises = db.fetchStoredExercises();
        db.close();
        ArrayList <Integer> properOrder = profile.getID();
        int sizeList = properOrder.size();
        ArrayList <ExerciseInfo> OrderedExercises = new ArrayList <ExerciseInfo>();
        for(int i=0; i<sizeList; i++)			//sizelist greater than listOfExercises
        {
        	int target = properOrder.get(i);
        	for(int j=0; j<listOfExercises.size(); j++)	//no duplicates in dB 
        	{
        		if(listOfExercises.get(j).getID()== target)
        		{
        			OrderedExercises.add(listOfExercises.get(j));
        			break;
        		}
        		else
        		{
        			//Log.wtf("ordering list error", "the id wasn't found? WTF!");
        		}
        	}
        }
        
        listOfExercises.clear();
        listOfExercises.trimToSize();
        listOfExercises = null;
        properOrder = null;
        

    	int skill = profile.getSkill();
    	int exerciseNum = skill + 2;
        ArrayList<ExerciseInfo> DisplayExercises = new ArrayList<ExerciseInfo>();
        
        int q = 0; //week offset
        int days = profile.getNumWorkoutDays();
        
        if (profile.hasBeenThreeWeeks())
        {
        	q = 3 * days * exerciseNum;
        }
        else if (profile.hasBeenTwoWeeks())
        {
        	q = 2 * days * exerciseNum;
        }
        else if (profile.hasBeenWeek())
        {
        	q = days * exerciseNum;
        }
        else;
        
        if (whatDay.equalsIgnoreCase("Day 1"))
        {
        	for (int l = q+(0*exerciseNum); l < q+(1*exerciseNum); l++)
        	{
        		boolean found = false;
        		int pid = OrderedExercises.get(l).getpID();
        		for(int r = 0; r < DisplayExercises.size(); r++)
        		{
        			if (DisplayExercises.get(r).getID() == pid)
        				found = true;
        		}
        		if (found);
        		else
        			DisplayExercises.add(OrderedExercises.get(l));
        		//if(DisplayExercises.contains(OrderedExercises.get(l)))
        		//{
        		//}
        		//else
        			//DisplayExercises.add(OrderedExercises.get(l));
        	}
        }
        else if (whatDay.equalsIgnoreCase("Day 2"))
        {
        	for (int l = q+(1*exerciseNum); l < q+(2*exerciseNum); l++)
        	{
        		boolean found = false;
        		int pid = OrderedExercises.get(l).getpID();
        		for(int r = 0; r < DisplayExercises.size(); r++)
        		{
        			if (DisplayExercises.get(r).getID() == pid)
        				found = true;
        		}
        		if (found);
        		else
        			DisplayExercises.add(OrderedExercises.get(l));
        	}
        }
        else if (whatDay.equalsIgnoreCase("Day 3"))
        {
        	for (int l = q+(2*exerciseNum); l < q+(3*exerciseNum); l++)
        	{
        		boolean found = false;
        		int pid = OrderedExercises.get(l).getpID();
        		for(int r = 0; r < DisplayExercises.size(); r++)
        		{
        			if (DisplayExercises.get(r).getID() == pid)
        				found = true;
        		}
        		if (found);
        		else
        			DisplayExercises.add(OrderedExercises.get(l));
        	}
        }
        else if (whatDay.equalsIgnoreCase("Day 4"))
        {
        	for (int l = q+(3*exerciseNum); l < q+(4*exerciseNum); l++)
        	{
        		boolean found = false;
        		int pid = OrderedExercises.get(l).getpID();
        		for(int r = 0; r < DisplayExercises.size(); r++)
        		{
        			if (DisplayExercises.get(r).getID() == pid)
        				found = true;
        		}
        		if (found);
        		else
        			DisplayExercises.add(OrderedExercises.get(l));
        	}
        }
        else if (whatDay.equalsIgnoreCase("Day 5"))
        {
        	for (int l = q+(4*exerciseNum); l < q+(5*exerciseNum); l++)
        	{
        		boolean found = false;
        		int pid = OrderedExercises.get(l).getpID();
        		for(int r = 0; r < DisplayExercises.size(); r++)
        		{
        			if (DisplayExercises.get(r).getID() == pid)
        				found = true;
        		}
        		if (found);
        		else
        			DisplayExercises.add(OrderedExercises.get(l));
        	}
        }
        else if (whatDay.equalsIgnoreCase("Day 6"))
        {
        	for (int l = q+(5*exerciseNum); l < q+(6*exerciseNum); l++)
        	{
        		boolean found = false;
        		int pid = OrderedExercises.get(l).getpID();
        		for(int r = 0; r < DisplayExercises.size(); r++)
        		{
        			if (DisplayExercises.get(r).getID() == pid)
        				found = true;
        		}
        		if (found);
        		else
        			DisplayExercises.add(OrderedExercises.get(l));
        	}
        }
        else if (whatDay.equalsIgnoreCase("Day 7"))
        {
        	for (int l = q+(6*exerciseNum); l < q+(7*exerciseNum); l++)
        	{
        		boolean found = false;
        		int pid = OrderedExercises.get(l).getpID();
        		for(int r = 0; r < DisplayExercises.size(); r++)
        		{
        			if (DisplayExercises.get(r).getID() == pid)
        				found = true;
        		}
        		if (found);
        		else
        			DisplayExercises.add(OrderedExercises.get(l));
        	}
        }
        else
        {
        	Log.wtf("Day more than 7", "Why you no work?");
        }
        
        if (DisplayExercises.size() < exerciseNum)
        {
        	Random rand = new Random();
        	int x = rand.nextInt(extraCardio.size());
        	db.open();
            db.setExerciseID(extraCardio.get(x));
            ExerciseInfo Extra = db.fetchTheExercise();
            db.close();
        	
        	DisplayExercises.add(Extra);
        }
        
        ListView listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(new ExerciseInfoAdapter(this, DisplayExercises));
        
       
        
    }
    
    private void loadInformation(String s) {
		TextView name = (TextView) findViewById(R.id.today_day);	// loads today's date into title 
		name.setText(s+" Exercises");
	}
    public String getBundleString(Bundle b, String key, String def) // checks bundle if null onot
    {
    	if(b != null ){
    		String value = b.getString(key);
    		if (value == null)
    			value = def;
    		return value;
    	}
    	return def;
    }

    public void initialiseButtons() {
        Button weekly = (Button) findViewById(R.id.weeklyplanner);
        weekly.setOnClickListener(this);
        
    }    
    //@Override
	public void onClick(View v) {
		if(v.getId() == R.id.weeklyplanner){
			//Intent intent = new Intent(this,WeeklyPlannerActivity.class);
			//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//this.startActivity(intent);	
			finish();
		}
    }
    
}
