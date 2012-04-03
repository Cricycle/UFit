package ufit.profilecreation;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SkillSelection extends Activity implements OnClickListener {
	private Profile profile;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skillselection);
		MyApp application = (MyApp)getApplication();
		application.updateProfile(this); //this is to transform the regular profile into the specific type of profile
		profile = application.getProfile();
		setOnClickListenerForViews();
	}
	
	public void setOnClickListenerForViews() {
		 View v1 = findViewById(R.id.skill_button_beginner);
	     View v2 = findViewById(R.id.skill_button_intermediate);
	     View v3 = findViewById(R.id.skill_button_advanced);
         v1.setOnClickListener(this);
         v2.setOnClickListener(this);
         v3.setOnClickListener(this);
	}

	public void onClick(View v) {
		int viewID = v.getId();
		if(viewID == R.id.skill_button_beginner){
			profile.setSkill(1); //1 indicates beginner
		} else if(viewID == R.id.skill_button_intermediate) {
			profile.setSkill(2); //2 indicates intermediate
		} else {
			profile.setSkill(3); //3 indicates advanced
		}
		//next, determine which activity to start.
		int goal = ((MyApp)getApplication()).getGoal();
		if(goal == 1){
			Intent intent = new Intent(this,StrengthScreen.class);
			this.startActivity(intent);	
		} else if(goal == 2 || goal == 3) {
			Intent intent = new Intent(this,CardioSelection.class);
			this.startActivity(intent);	
		} else {
			Toast.makeText(this, "Somehow, there is a bug!", Toast.LENGTH_SHORT).show();
		}
	}
}

