package ufit.profilecreation;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import ufit.namespace.R;
import ufit.profile.Profile;
import ufit.global.MyApp;

public class WeeklyPlannerActivity extends Activity  implements OnClickListener{
    /** Called when the activity is first created. */
    private MyApp application;
    private Profile profile;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weeklyplanner);
       
        application = (MyApp)getApplication();
        profile = ((MyApp)getApplication()).getProfile();
        initialiseButtons();
        /*
         * Current week, currently unused, not in XML file 
        int thisWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
        TextView thisWeek2 = (TextView) findViewById(R.id.weekly_thisweek);
		thisWeek2.setText("Week " + Integer.toString(thisWeek)); */
        
 
    }
    public void initialiseButtons() {
    	ArrayList<TextView> array = new ArrayList<TextView>();
    	array.add((TextView) findViewById(R.id.planner_textview_sunday));
    	array.add((TextView) findViewById(R.id.planner_textview_monday));
    	array.add((TextView) findViewById(R.id.planner_textview_tuesday));
    	array.add((TextView) findViewById(R.id.planner_textview_wednesday));
    	array.add((TextView) findViewById(R.id.planner_textview_thursday));
    	array.add((TextView) findViewById(R.id.planner_textview_friday));
    	array.add((TextView) findViewById(R.id.planner_textview_saturday));
        
        for (int i = profile.getNumWorkoutDays(); i < 7; i++)
    	{
        	array.get(i).setVisibility(8);
    	}
        
        // button goes to home screen
        Button home = (Button) findViewById(R.id.homescreenback);
        home.setOnClickListener(this);
        
        for (int i = 0; i < profile.getNumWorkoutDays(); i++)
    	{
        	array.get(i).setOnClickListener(this);
    	}
    }    
    //@Override
	public void onClick(View v) {
		if(v.getId() == R.id.planner_textview_sunday){
			Intent intent = new Intent(this,TodaysExercisesActivity.class);
			Bundle b = new Bundle();
			b.putString("day", this.getString(R.string.sunday));
			intent.putExtras(b);
			this.startActivity(intent);
			//finish();
	    }else if(v.getId() == R.id.planner_textview_monday) {
	    	Intent intent = new Intent(this,TodaysExercisesActivity.class);
			Bundle b = new Bundle();
			b.putString("day", this.getString(R.string.monday));
			intent.putExtras(b);
			this.startActivity(intent);
			//finish();		
		} else if (v.getId() == R.id.planner_textview_tuesday) {
			Intent intent = new Intent(this,TodaysExercisesActivity.class);
			Bundle b = new Bundle();
			b.putString("day", this.getString(R.string.tuesday));
			intent.putExtras(b);
			this.startActivity(intent);
			//finish();		
		} else if(v.getId() == R.id.planner_textview_wednesday) {
			Intent intent = new Intent(this,TodaysExercisesActivity.class);
			Bundle b = new Bundle();
			b.putString("day", this.getString(R.string.wednesday));
			intent.putExtras(b);
			this.startActivity(intent);
			//finish();
		} else if (v.getId() == R.id.planner_textview_thursday) {
			Intent intent = new Intent(this,TodaysExercisesActivity.class);
			Bundle b = new Bundle();
			b.putString("day", this.getString(R.string.thursday));
			intent.putExtras(b);
			this.startActivity(intent);
			//finish();
		} else if (v.getId() == R.id.planner_textview_friday) {
			Intent intent = new Intent(this,TodaysExercisesActivity.class);
			Bundle b = new Bundle();
			b.putString("day", this.getString(R.string.friday));
			intent.putExtras(b);
			this.startActivity(intent);
			//finish();
		} else if (v.getId() == R.id.planner_textview_saturday) {
			Intent intent = new Intent(this,TodaysExercisesActivity.class);
			Bundle b = new Bundle();
			b.putString("day", this.getString(R.string.saturday));
			intent.putExtras(b);
			this.startActivity(intent);
			//finish();
		} else if (v.getId() == R.id.homescreenback){
			Intent intent = new Intent(this,HomeScreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction("Go to Home");
			this.startActivity(intent);
		}
    }
	
}