package UFit.namespace;

import UFit.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileCreationActivity extends Activity implements OnClickListener {
	private int numberOfEntries = 6;
	private Profile profile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		profile = new Profile(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilecreation);
		
		View v = findViewById(R.id.profile_button_next);
		v.setOnClickListener(this);
		
		Spinner spinner = (Spinner) findViewById(R.id.profile_spinner_gender);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.MF, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
		
		TextView v2 = (TextView) findViewById(R.id.bmi2);
		v2.setText("23");
	}
		
	public void onClick(View arg0) {
		//check if all the information has been entered
		boolean allInfoEntered = true;
		/*for(int boxId = 0; boxId < numberOfEntries; ++boxId) {
			allInfoEntered = allInfoEntered && (retrieveInfo(boxId));
		}*/
		if(arg0.getId() == R.id.profile_button_next && allInfoEntered){
			Intent intent = new Intent(this,GoalSelection.class);
			this.startActivity(intent);
		} else if(!allInfoEntered) {
			//somehow give message to enter info.
		}
	}
	/*
	public boolean retrieveInfo(int id) {
		if(id == 0) { //get the username
			EditText et = (EditText) findViewById(R.id.profile_edittext_name);
			String username = (et.getText().toString());
			profile.setUsername(username);
			return true;
		} else if(id == 1) { //get the birthday
			EditText day = (EditText) findViewById(R.id.profile_edittext_bday);
			EditText month = (EditText) findViewById(R.id.profile_edittext_bmonth);
			EditText year = (EditText) findViewById(R.id.profile_edittext_byear);
			
			profile.setBirthDate(Integer.parseInt(month.getText().toString()),
								 Integer.parseInt(day.getText().toString()),
								 Integer.parseInt(year.getText().toString()));
			return true;
		} else if(id == 2) {
			EditText weight = (EditText) findViewById(R.id.profile_edittext_curweight);
			profile.setWeight(Double.parseDouble(weight.getText().toString()));
			return true;
		} else if(id == 3) {
			EditText targetWeight = (EditText) findViewById(R.id.profile_edittext_targetweight);
			profile.setTargetWeight(Double.parseDouble(targetWeight.getText().toString()));
			return true;
		} else if(id == 4) {
			EditText feet = (EditText) findViewById(R.id.profile_edittext_height_feet);
			EditText inches = (EditText) findViewById(R.id.profile_edittext_height_inches);
			profile.setHeightFeet(Double.parseDouble(feet.getText().toString()), 
					Double.parseDouble(inches.getText().toString()));
			return true;
		} else if(id == 5)
		{
			
		}
		
		return false;
	}*/
}