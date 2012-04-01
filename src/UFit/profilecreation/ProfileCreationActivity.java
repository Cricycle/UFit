package ufit.profilecreation;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileCreationActivity extends Activity implements OnClickListener, OnFocusChangeListener {
	private int numberOfEntries = 6;
	private Profile profile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		profile = ((MyApp)getApplicationContext()).getProfile();
		setContentView(R.layout.profilecreation);
		
		//load the information from profile into the display boxes on the screen.
		loadProfileView();
		
		//set the OnClickListener for the next button.
		View v = findViewById(R.id.profile_button_next);
		v.setOnClickListener(this);
		
		//set the OnFocusChangeListner for all the entry boxes.
		setOnFocusChangeListenerForViews();
		
		Spinner spinner = (Spinner) findViewById(R.id.profile_spinner_gender);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.MF, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
		
		TextView v2 = (TextView) findViewById(R.id.profile_textview_bmidisplay);
		v2.setText("23");
	}
	
	public void setOnFocusChangeListenerForViews() {
		//sets this object as the onFocusChangeListener for the editable views.
		View v;
		v = findViewById(R.id.profile_edittext_name);
		v.setOnFocusChangeListener(this);
		v = findViewById(R.id.profile_birthdaypicker);
		v.setOnFocusChangeListener(this);
		v = findViewById(R.id.profile_edittext_curweight);
		v.setOnFocusChangeListener(this);
		v = findViewById(R.id.profile_edittext_targetweight);
		v.setOnFocusChangeListener(this);
		v = findViewById(R.id.profile_edittext_height_feet);
		v.setOnFocusChangeListener(this);
		v = findViewById(R.id.profile_edittext_height_inches);
		v.setOnFocusChangeListener(this);
		v = findViewById(R.id.profile_spinner_gender);
		v.setOnFocusChangeListener(this);
	}
	
	public void loadProfileView() {
		//for all the displays of the username, weight, etc., put what the profile has stored already.
		EditText et = (EditText) findViewById(R.id.profile_edittext_name);
		et.setText(profile.getUsername());
		
		DatePicker date = (DatePicker) findViewById(R.id.profile_birthdaypicker);
		date.init(profile.getYear(), profile.getMonth(), profile.getDay(), null);
		
		et = (EditText) findViewById(R.id.profile_edittext_curweight);
		et.setText("" + profile.getWeight()); //turn the double into a string to display it
		
		et = (EditText) findViewById(R.id.profile_edittext_targetweight);
		et.setText("" + profile.getTargetWeight());
		
		et = (EditText) findViewById(R.id.profile_edittext_height_feet);
		et.setText("" + (profile.getHeightInches()/12));
		
		et = (EditText) findViewById(R.id.profile_edittext_height_inches);
		et.setText("" + (profile.getHeightInches()%12));
		
		Spinner gender = (Spinner) findViewById(R.id.profile_spinner_gender);
		//unsure if this is valid.
		gender.setPrompt( (profile.getGender() == 1 ? "M" : "F"));
	}
		
	public void onClick(View arg0) {
		//screw the previous button.  They should just use the back button on their phone.
		//check if all the information has been entered
		boolean allInfoEntered = true;//check doesn't exist for now
		
		if(arg0.getId() == R.id.profile_button_next && allInfoEntered){
			Intent intent = new Intent(this,GoalSelection.class);
			this.startActivity(intent);
		} else if(!allInfoEntered) {
			//somehow give message to enter info.
		}
	}

	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus)
			return;
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.profile_edittext_name){ //save the username
			EditText et = (EditText) v;
			String username = (et.getText().toString());
			profile.setUsername(username);
		} else if(id == R.id.profile_birthdaypicker){ //save the birthday
			DatePicker date = (DatePicker) v;
			
			profile.setBirthDate(date.getMonth(),
								 date.getDayOfMonth(),
								 date.getYear());
		} else if(id == R.id.profile_edittext_curweight) { //save the current weight
			EditText weight = (EditText) v;
			profile.setWeight(Double.parseDouble(weight.getText().toString()));
			
		} else if(id == R.id.profile_edittext_targetweight) { //save the target weight
			
			EditText targetWeight = (EditText) v;
			profile.setTargetWeight(Double.parseDouble(targetWeight.getText().toString()));
			
		} else if(id == R.id.profile_edittext_height_feet){ //save the height
			
			EditText feet = (EditText) v;
			EditText inches = (EditText) findViewById(R.id.profile_edittext_height_inches);
			profile.setHeightFeet(Double.parseDouble(feet.getText().toString()), 
					Double.parseDouble(inches.getText().toString())); //beware of exception here.
			
		} else if(id == R.id.profile_edittext_height_inches){ //save the height

			EditText feet = (EditText) findViewById(R.id.profile_edittext_height_feet);
			EditText inches = (EditText) v;
			profile.setHeightFeet(Double.parseDouble(feet.getText().toString()), 
					Double.parseDouble(inches.getText().toString())); //beware of exception here.
			
		} else if(id == R.id.profile_spinner_gender){ //save the gender
			Spinner gender = (Spinner) v;
			String gen = gender.getPrompt().toString();
			if(gen.equals("F")) {
				profile.setGender(2); //indicates female
			} else {
				profile.setGender(1); //indicates male
			}
		}
	}
}