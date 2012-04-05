package ufit.weeklyplanner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import ufit.profilecreation.HomeScreen;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WeeklyPlannerActivity extends Activity implements OnClickListener{
	private Profile profile;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        int thisWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
        setOnClickListenerForViews(); //sets this activity as the onClickListener
        loadInformation(); //loads the information from the profile? from somewhere.
        profile = ( (MyApp)getApplication() ).getProfile();
 
    }
    
    private void loadInformation() {
    	
    }

	private void setOnClickListenerForViews() {
		View v;
		v = findViewById(R.id.planner_button_sunday);
		v.setOnClickListener(this);
		v = findViewById(R.id.planner_button_monday);
		v.setOnClickListener(this);
		v = findViewById(R.id.planner_button_tuesday);
		v.setOnClickListener(this);
		v = findViewById(R.id.planner_button_wednesday);
		v.setOnClickListener(this);
		v = findViewById(R.id.planner_button_thursday);
		v.setOnClickListener(this);
		v = findViewById(R.id.planner_button_friday);
		v.setOnClickListener(this);
		v = findViewById(R.id.planner_button_saturday);
		v.setOnClickListener(this);
		v = findViewById(R.id.planner_button_back);
		v.setOnClickListener(this);
	}

	public void onClick(View v) {
		int viewId = v.getId();
		String day = "";
		if( viewId == R.id.planner_button_back) {
			Intent intent = new Intent(this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); //this removes all activities on the stack between here and homescreen
			startActivity(intent);
		} else if(viewId == R.id.planner_button_sunday) {
			day = getString(R.string.sunday);
		} else if(viewId == R.id.planner_button_monday) {
			day = getString(R.string.monday);
		} else if(viewId == R.id.planner_button_tuesday) {
			day = getString(R.string.tuesday);
		} else if(viewId == R.id.planner_button_wednesday) {
			day = getString(R.string.wednesday);
		} else if(viewId == R.id.planner_button_thursday) {
			day = getString(R.string.thursday);
		} else if(viewId == R.id.planner_button_friday) {
			day = getString(R.string.friday);
		} else if(viewId == R.id.planner_button_saturday) {
			day = getString(R.string.saturday);
		} else {
			//error!
			Toast.makeText(this, "You've discovered an error! If only you could tell the developers.", Toast.LENGTH_SHORT).show();
		}
		
		if(day.length() > 0) {
			Intent intent = new Intent(this, TodaysExercisesActivity.class);
			intent.setAction("" + day); //this is to tell the next activity which day to display.
			startActivity(intent);
		}
	}
}