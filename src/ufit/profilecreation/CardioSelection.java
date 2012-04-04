package ufit.profilecreation;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class CardioSelection extends Activity implements OnClickListener {
	private Profile profile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cardioscreen);
		profile = ( (MyApp)getApplication() ).getProfile();
		
		loadProfileView();
		setOnClickListenerForViews();
	}
	private void loadProfileView() {
		CheckBox curBox;
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_run);
		curBox.setChecked(profile.getRun());
		
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_swim);
		curBox.setChecked(profile.getSwim());
		
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_bike);
		curBox.setChecked(profile.getBike());
		
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_elliptical);
		curBox.setChecked(profile.getElliptical());
		
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_walk);
		curBox.setChecked(profile.getWalk());
		
		Button next = (Button) findViewById(R.id.cardioscreen_button_next);
		if(profile.getWorkoutType() == 2) { //2 is cardio, so this is the last stage for cardio.
			next.setText("Finish");
		} else {
			next.setText("Next");
		}
	}

	private void setOnClickListenerForViews() {
		View v = findViewById(R.id.cardioscreen_button_next);
		v.setOnClickListener(this);
	}

	public void onClick(View v) {
		saveCheckedInformation();
		if(v.getId() == R.id.cardioscreen_button_next){
			//determine which screen to continue to based off of the workoutType of the profile, or the goal.
			if(profile.getWorkoutType() == 2) { //indicates just cardio.
				//go straight to home screen, via the selection screen.
				Intent intent = new Intent(this,Selection.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction("Go to Home");
				this.startActivity(intent);
				finish();
			} else if(profile.getWorkoutType() == 3) { //indicates general
				//go to the strength track.
				Intent intent = new Intent(this,StrengthScreen.class);
				this.startActivity(intent);	
			} else {
				Toast.makeText(this, "Somehow, we have reached an erroneous state!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void saveCheckedInformation() {
		CheckBox curBox;
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_run);
		profile.setRun( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_swim);
		profile.setSwim( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_bike);
		profile.setBike( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_elliptical);
		profile.setElliptical( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.cardioscreen_checkbox_walk);
		profile.setWalk( curBox.isChecked() );
	}
	
	protected void onPause() {
		super.onPause();
		saveCheckedInformation();
	}
}
