package ufit.profilecreation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import ufit.namespace.R;


public class TodaysExercisesActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaysexercises);
        initialiseButtons();

        // pass in current day
        Bundle b = getIntent().getExtras();
        String whatDay = getBundleString(b,"day",this.getString(R.string.today));
        Toast.makeText(this,whatDay, Toast.LENGTH_SHORT).show();
        loadInformation(whatDay);
        //name of items

        ArrayList <ExerciseInfo> listOfExercises = new ArrayList<ExerciseInfo>();
 
        listOfExercises.add(new ExerciseInfo(1, 1, "Bench Press", 1, 1, "Bench Press Machine", 2, "Description", "benchpress1", "ic_launcher", "runningman"));
        listOfExercises.add(new ExerciseInfo(2, 2, "Calf Raise", 1, 1, "Bar", 2, "Description2", "bcalfraise1", "ic_launcher", "runningman"));
        
        ListView listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(new ExerciseInfoAdapter(this, listOfExercises));
        
    }
    
    private void loadInformation(String s) {
		TextView name = (TextView) findViewById(R.id.today_day);	// loads today's date into title 
		name.setText(s+"'s Exercises");
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
			Intent intent = new Intent(this,WeeklyPlannerActivity.class);
			this.startActivity(intent);	        
		}
    }
    
}
