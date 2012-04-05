package ufit.profilecreation;


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

public class WeeklyPlannerActivity extends Activity  implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weeklyplanner);
        initialiseButtons();
        
        /*
         * Current week, currently unused, not in XML file 
        int thisWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
        TextView thisWeek2 = (TextView) findViewById(R.id.weekly_thisweek);
		thisWeek2.setText("Week " + Integer.toString(thisWeek)); */
        
 
    }
    public void initialiseButtons() {
        Button exercisesSunday = (Button) findViewById(R.id.planner_button_sunday);
        Button exercisesMonday = (Button) findViewById(R.id.planner_button_monday);
        Button exercisesTuesday = (Button) findViewById(R.id.planner_button_tuesday);
        Button exercisesWednesday = (Button) findViewById(R.id.planner_button_wednesday);
        Button exercisesThursday = (Button) findViewById(R.id.planner_button_thursday);
        Button exercisesFriday = (Button) findViewById(R.id.planner_button_friday);
        Button exercisesSaturday = (Button) findViewById(R.id.planner_button_saturday);

        // button goes to home screen
        final Button button0 = (Button) findViewById(R.id.planner_button_back);
        button0.setOnClickListener(this);
        
        exercisesSunday.setOnClickListener(this);
        exercisesMonday.setOnClickListener(this);
        exercisesTuesday.setOnClickListener(this);
        exercisesWednesday.setOnClickListener(this);
        exercisesThursday.setOnClickListener(this);
        exercisesFriday.setOnClickListener(this);
        exercisesSaturday.setOnClickListener(this);

    }    
    //@Override
	public void onClick(View v) {
		if(v.getId() == R.id.planner_button_sunday){
			Intent intent = new Intent(this,SkillSelection.class);
			this.startActivity(intent);	        
	    }else if(v.getId() == R.id.planner_button_monday) {
	    	Intent intent = new Intent(this,SkillSelection.class);
			this.startActivity(intent);			
		} else if (v.getId() == R.id.planner_button_tuesday) {
			Intent intent = new Intent(this,SkillSelection.class);
			this.startActivity(intent);			
		} else if(v.getId() == R.id.planner_button_wednesday) {
			Intent intent = new Intent(this,SkillSelection.class);
			this.startActivity(intent);
		} else if (v.getId() == R.id.planner_button_thursday) {
			Intent intent = new Intent(this,SkillSelection.class);
			this.startActivity(intent);
		} else if (v.getId() == R.id.planner_button_friday) {
			Intent intent = new Intent(this,SkillSelection.class);
			this.startActivity(intent);
		} else if (v.getId() == R.id.planner_button_saturday) {
			Intent intent = new Intent(this,SkillSelection.class);
			this.startActivity(intent);
		} else if (v.getId() == R.id.button0){
			Intent intent = new Intent (this, HomeScreen.class);
			startActivity(intent);
		}
    }
	
}