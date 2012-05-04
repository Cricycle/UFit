package ufit.profilecreation;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MachineSelection extends Activity implements OnClickListener {
	private Profile profile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.machineselection);
		profile = ( (MyApp)getApplication()).getProfile();

		setupSpinner();
		
		loadInformation(); //this loads the stored information from profile into the display
		setOnClickListenerForViews(); //this sets up listeners for the necessary buttons.
		
		
	}
	
	public void setupSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.workdaysspinner);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.daysoftheweek, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	}

	private void setOnClickListenerForViews() {
		View v = findViewById(R.id.machineselect_button_finish);
		v.setOnClickListener(this);
		/*CheckBox box; //we don't need to listen to these buttons.
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_dumbbells);
		box.setOnClickListener(this);
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_barbells);
		box.setOnClickListener(this);
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_benchpress);
		box.setOnClickListener(this);
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_chestfly);
		box.setOnClickListener(this);
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_shoulderpress);
		box.setOnClickListener(this);
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_legpress);
		box.setOnClickListener(this);
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_hamstringcurl);
		box.setOnClickListener(this);
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_pulley);
		box.setOnClickListener(this);*/
	}

	private void loadInformation() {
		CheckBox box;
		//this sucks, since I need to assume things are spelled in the same way, but whatever
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_dumbbells);
		box.setChecked( profile.getEquipment( getString(R.string.dumbbells) ));
		
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_barbells);
		box.setChecked( profile.getEquipment( getString(R.string.barbells) ));
		
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_benchpress);
		box.setChecked( profile.getEquipment( getString(R.string.chestfly) ));
/*		
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_chestfly);
		box.setChecked( profile.getEquipment( getString(R.string.chestfly) ));
		
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_shoulderpress);
		box.setChecked( profile.getEquipment( getString(R.string.shoulderpress) ));
		
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_legpress);
		box.setChecked( profile.getEquipment( getString(R.string.legpress) ));
		
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_hamstringcurl);
		box.setChecked( profile.getEquipment( getString(R.string.hamstringcurl) ));
		
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_pulley);
		box.setChecked( profile.getEquipment( getString(R.string.pulley) ));
*/	
	}

	public void onClick(View arg0) {
		saveCheckedInformation();
		if(arg0.getId() == R.id.machineselect_button_finish){
			
			if (profile.getWorkoutType() == 1 || profile.getWorkoutType() == 3)
			{
				if (profile.getEquipmentList() == null || profile.getEquipmentList().size() <= 0)
				{
					Toast.makeText(this, "Strength plan requires equipment. Please either select your available equipment or go back and select Cardio as your goal", Toast.LENGTH_LONG).show();
				}
				else
				{
					Intent intent = new Intent(this,Selection.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction("Go to Home");
					this.startActivity(intent);
				}
			}
			else
			{
				Intent intent = new Intent(this,Selection.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction("Go to Home");
				this.startActivity(intent);
			}
		}
		
	}

	private void saveCheckedInformation() {
		CheckBox box; 
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_dumbbells);
		profile.setEquipment( getString(R.string.dumbbells) , box.isChecked());
		
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_barbells);
		profile.setEquipment( getString(R.string.barbells) , box.isChecked());
		
		box = (CheckBox) findViewById(R.id.machineselect_checkbox_benchpress);
		profile.setEquipment( getString(R.string.chestfly) , box.isChecked());	
	}
	
	protected void onPause() {
		super.onPause();

		Spinner workDays = (Spinner) findViewById(R.id.workdaysspinner);
		String work = ((TextView)workDays.getSelectedView()).getText().toString();
		//Toast.makeText(this, "reporting: " + gen, Toast.LENGTH_SHORT).show();
		if(work.equals("1")) {
			profile.setNumWorkoutDays(1); 
		}
		else if(work.equals("2")) {
			profile.setNumWorkoutDays(2); 
		}
		else if(work.equals("3")) {
			profile.setNumWorkoutDays(3); 
		}
		else if(work.equals("4")) {
			profile.setNumWorkoutDays(4); 
		}
		else if(work.equals("5")) {
			profile.setNumWorkoutDays(5); 
		}
		else if(work.equals("6")) {
			profile.setNumWorkoutDays(6); 
		}
		else {
			profile.setNumWorkoutDays(7); 
		}
		saveCheckedInformation();
	}

}
