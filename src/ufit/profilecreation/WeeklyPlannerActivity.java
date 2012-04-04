package ufit.profilecreation;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ufit.global.MyApp;
import ufit.global.SwitchButton;
import ufit.namespace.R;
import android.widget.Toast;



public class WeeklyPlannerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weeklyplanner);
        
       //  int thisWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
        // must add a variable here so that the *ACTUAL* date is displayed in the layout 
        
        //go to exercises for Sunday
        final Button exercisesSunday = (Button) findViewById(R.id.sunday);
        exercisesSunday.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			// go next
			Intent intent = new Intent (WeeklyPlannerActivity.this, WeeklyPlannerActivity.class);  		//need to change this
			startActivity(intent);
		}
		});

      //go to exercises for Monday
        final Button exercisesMonday = (Button) findViewById(R.id.monday);
        exercisesMonday.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			// go next
			Intent intent = new Intent (WeeklyPlannerActivity.this, WeeklyPlannerActivity.class);  		//need to change this
			startActivity(intent);
		}
		});
        
      //go to exercises for Tuesday
        final Button exercisesTuesday = (Button) findViewById(R.id.tuesday);
        exercisesTuesday.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			// go next
			Intent intent = new Intent (WeeklyPlannerActivity.this, WeeklyPlannerActivity.class);  		//need to change this
			startActivity(intent);
		}
		});
        
      //go to exercises for Wednesday
        final Button exercisesWednesday = (Button) findViewById(R.id.wednesday);
        exercisesWednesday.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			// go next
			Intent intent = new Intent (WeeklyPlannerActivity.this, WeeklyPlannerActivity.class);  		//need to change this
			startActivity(intent);
		}
		});
        
      //go to exercises for Thursday
        final Button exercisesThursday = (Button) findViewById(R.id.thursday);
        exercisesThursday.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			// go next
			Intent intent = new Intent (WeeklyPlannerActivity.this, WeeklyPlannerActivity.class);  		//need to change this
			startActivity(intent);
		}
		});
        
      //go to exercises for Friday
        final Button exercisesFriday = (Button) findViewById(R.id.friday);
        exercisesFriday.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			// go next
			Intent intent = new Intent (WeeklyPlannerActivity.this, WeeklyPlannerActivity.class);  		//need to change this
			startActivity(intent);
		}
		});
        
      //go to exercises for Saturday
        final Button exercisesSaturday = (Button) findViewById(R.id.saturday);
        exercisesSaturday.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			// go next
			Intent intent = new Intent (WeeklyPlannerActivity.this, WeeklyPlannerActivity.class);  		//need to change this
			startActivity(intent);
		}
		});
        
        //next goes back to home screen
        final Button button0 = (Button) findViewById(R.id.button0);
		button0.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			// go next
			Intent intent = new Intent (WeeklyPlannerActivity.this, WeeklyPlannerActivity.class);  		//need to change this
			startActivity(intent);
		}
		});
 
    }
}