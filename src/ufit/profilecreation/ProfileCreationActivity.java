package ufit.profilecreation;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileCreationActivity extends Activity implements OnClickListener, OnKeyListener {
	private Profile profile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		profile = ((MyApp)getApplicationContext()).getProfile();
		setContentView(R.layout.profilecreation);
		
		//load the information from profile into the display boxes on the screen.
		setupSpinner();
		loadProfileView();
		
		//set the OnClickListener for the next button.
		View v = findViewById(R.id.profile_button_next);
		v.setOnClickListener(this);
		
		//set the OnKeyListener for the editTexts.
		//setOnKeyListenerForViews();=
		
		TextView v2 = (TextView) findViewById(R.id.profile_textview_bmidisplay);
		v2.setText("23");
	}
	
	//overriding the onPause method.
	protected void onPause() {
		super.onPause();
		updateInformation();
	}
	
	public void loadProfileView() {
		//for all the displays of the username, weight, etc., put what the profile has stored already.
		EditText et = (EditText) findViewById(R.id.profile_edittext_name);
		et.setText(profile.getUsername());
		
		DatePicker date = (DatePicker) findViewById(R.id.profile_birthdaypicker);
		date.updateDate(profile.getBirthYear(), profile.getBirthMonth() - 1, profile.getBirthDay());
		
		et = (EditText) findViewById(R.id.profile_edittext_curweight);
		et.setText("" + profile.getWeight()); //turn the double into a string to display it
		
		et = (EditText) findViewById(R.id.profile_edittext_targetweight);
		et.setText("" + profile.getTargetWeight());
		
		et = (EditText) findViewById(R.id.profile_edittext_height_feet);
		et.setText("" + ((int)(profile.getHeightInches())/12));
		
		et = (EditText) findViewById(R.id.profile_edittext_height_inches);
		et.setText("" + ((int)(profile.getHeightInches())%12));
		
		Spinner gender = (Spinner) findViewById(R.id.profile_spinner_gender);
		gender.setSelection( profile.getGender() - 1); //-1 because male is stored as 1, female as 2, and they are indexed 0, 1
	}
		
	public void onClick(View arg0) {
		//screw the previous button.  They should just use the back button on their phone.
		//check if all the information has been entered
		boolean allInfoEntered = true;//check doesn't exist for now
		
		if(arg0.getId() == R.id.profile_button_next && allInfoEntered){
			updateInformation();
			Intent intent = new Intent(this,GoalSelection.class);
			this.startActivity(intent);
		} else if(!allInfoEntered) {
			Toast.makeText(this, "You have not filled in all the forms yet", Toast.LENGTH_SHORT).show();
		}
	}
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		//we will only consider the editTexts where we can enter numbers
		//we will restrict them to entering only 6 digits (decimal counts)
		Toast.makeText(this, "Intercepted keyCode: " + keyCode, Toast.LENGTH_SHORT).show();
		int id = v.getId();
		if(id == R.id.profile_edittext_name) {
			//do nothing?  I have no idea if we want to restrict anything in the name.
			//w/e lets try restricting to 10 characters?
			if(keyCode != KeyEvent.KEYCODE_DEL) {
				EditText et = (EditText) v;
				if( (et.getText().toString().length()) >= 10)
						return true;
			}
		} else {
			if(keyCode != KeyEvent.KEYCODE_DEL){ //if they didn't hit backspace
				//check to see if there are already 6 characters typed into the box
				EditText et = (EditText) v;
				if( (et.getText().toString()).length() >= 6 ) {
					//we don't want anything to happen, because we want to restrict them to entering 6 characters
					return true; //this means we don't pass this event on?
				}
			}
		}
		return false;
	}
	
	public void updateInformation() {
		//if the user clicks on any of the set objects, they all save.
		//save the username
		EditText et = (EditText) findViewById(R.id.profile_edittext_name);
		String username = (et.getText().toString());
		if(username.length() > 0) //check to see that it is not the empty string, so the file naming isn't screwed up
				profile.setUsername(username);
		
		//save the birthday
		DatePicker date = (DatePicker) findViewById(R.id.profile_birthdaypicker);
		profile.setBirthDate(date.getMonth() + 1, date.getDayOfMonth(), date.getYear());
			
		//save the current weight
		EditText etWeight = (EditText) findViewById(R.id.profile_edittext_curweight);
		String weight = etWeight.getText().toString();
		if(weight.length() > 0 && !weight.equals(".")) {
			profile.setWeight(Double.parseDouble(weight));
		} else {
			profile.setWeight(0.0);
		}
			
		//save the target weight
		EditText etTarget = (EditText) findViewById(R.id.profile_edittext_targetweight);
		String targetWeight = etTarget.getText().toString();
		if(targetWeight.length() > 0 && !targetWeight.equals("."))
			profile.setTargetWeight(Double.parseDouble(targetWeight));
		else
			profile.setTargetWeight(0.0);
			
		//save the height
		
		EditText etFeet = (EditText) findViewById(R.id.profile_edittext_height_feet);
		EditText etInches = (EditText) findViewById(R.id.profile_edittext_height_inches);
		Double feet = 0.0;
		Double inches = 0.0;
		String temp = etFeet.getText().toString();
		//check to make sure that you can parse the input as a double.
		if(temp.length() > 0 && !temp.equals(".")) {
			feet = Double.parseDouble(temp);
		}
		temp = etInches.getText().toString();
		if(temp.length() > 0 && !temp.equals(".")) {
			inches = Double.parseDouble(temp);
		}
		profile.setHeightFeet(feet,inches);
		
		//save the gender
		Spinner gender = (Spinner) findViewById(R.id.profile_spinner_gender);
		String gen = ((TextView)gender.getSelectedView()).getText().toString();
		//Toast.makeText(this, "reporting: " + gen, Toast.LENGTH_SHORT).show();
		if(gen.equals("M")) {
			profile.setGender(1); //indicates male
		} else {
			profile.setGender(2); //indicates female
		}
	}
	public void setupSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.profile_spinner_gender);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.MF, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	}
	public void setOnKeyListenerForViews() {
		//sets the OnKeyListener for these editTexts.  Using this, we can disallow too long entries.
		View v;
		v = findViewById(R.id.profile_edittext_name);
		v.setOnKeyListener(this);
		v = findViewById(R.id.profile_edittext_curweight);
		v.setOnKeyListener(this);
		v = findViewById(R.id.profile_edittext_targetweight);
		v.setOnKeyListener(this);
		v = findViewById(R.id.profile_edittext_height_feet);
		v.setOnKeyListener(this);
		v = findViewById(R.id.profile_edittext_height_inches);
		v.setOnKeyListener(this);
	}
}