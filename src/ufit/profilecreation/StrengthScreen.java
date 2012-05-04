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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;


public class StrengthScreen extends Activity implements OnClickListener {
	private Profile profile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		profile = ( (MyApp)getApplication() ).getProfile();
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.strengthscreen);
	
		loadProfileView();
		setOnClickListenerForViews();
	}
	private void setOnClickListenerForViews() {
		View v = findViewById(R.id.strengthscreen_button_next);
		v.setOnClickListener(this);
	}
	public void onClick(View arg0) {
		saveCheckedInformation();
		if(arg0.getId() == R.id.strengthscreen_button_next){
			Intent intent = new Intent(this,MachineSelection.class);
			this.startActivity(intent);
		}
	}
	
	
	private void saveCheckedInformation() {
		CheckBox curBox;
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_chest);
		profile.setChest( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_back);
		profile.setBack( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_legs);
		profile.setLegs( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_biceps);
		profile.setBiceps( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_triceps);
		profile.setTriceps( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_shoulders);
		profile.setShoulders( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_quads);
		profile.setQuads( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_hamstrings);
		profile.setHamstrings( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_calves);
		profile.setCalves( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_glutes);
		profile.setGlutes( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_abs);
		profile.setAbs( curBox.isChecked() );
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_fullBody);
		if (curBox.isChecked())
		{
			profile.setFullBody( curBox.isChecked() );
			profile.setAbs( curBox.isChecked() );
			profile.setGlutes( curBox.isChecked() );
			profile.setCalves( curBox.isChecked() );
			profile.setHamstrings( curBox.isChecked() );
			profile.setQuads( curBox.isChecked() );
			profile.setShoulders( curBox.isChecked() );
			profile.setTriceps( curBox.isChecked() );
			profile.setBiceps( curBox.isChecked() );
			profile.setLegs( curBox.isChecked() );
			profile.setBack( curBox.isChecked() );
			profile.setChest( curBox.isChecked() );
			
			this.loadProfileView();
		}
		
	}
	
	private void loadProfileView() {
		CheckBox curBox;
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_chest);
		curBox.setChecked(profile.getChest());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_back);
		curBox.setChecked(profile.getBack());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_legs);
		curBox.setChecked(profile.getLegs());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_biceps);
		curBox.setChecked(profile.getBiceps());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_triceps);
		curBox.setChecked(profile.getTriceps());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_shoulders);
		curBox.setChecked(profile.getShoulders());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_quads);
		curBox.setChecked(profile.getQuads());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_hamstrings);
		curBox.setChecked(profile.getHamstrings());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_calves);
		curBox.setChecked(profile.getCalves());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_glutes);
		curBox.setChecked(profile.getGlutes());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_abs);
		curBox.setChecked(profile.getAbs());
		
		curBox = (CheckBox) findViewById(R.id.strengthscreen_checkbox_fullBody);
		curBox.setChecked(profile.getFullBody());
		
	}
	protected void onPause() {
		super.onPause();
		saveCheckedInformation();
	}
	
}

	

