package ufit.profilecreation;

import ufit.global.MyApp;
import ufit.global.SwitchButton;
import ufit.namespace.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SkillSelection extends Activity implements OnClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skillselection);
		 View v1 = findViewById(R.id.skill_button_beginner);
	     View v2 = findViewById(R.id.skill_button_intermediate);
	     View v3 = findViewById(R.id.skill_button_advanced);
         v1.setOnClickListener(this);
         v2.setOnClickListener(this);
         v3.setOnClickListener(this);
	        
		setSwitches();
	}
	//there is no method currently existing to go to the next screen from this screen, and the UI itself could probably be better
	
	//there might be a better way than this, but I think it is usable
	public void setSwitches() {
		SwitchButton s = (SwitchButton)findViewById(R.id.skill_button_beginner);
		s.setResources(R.drawable.button_orange, 0);
		s.setResources(R.drawable.button_orange2, 1);
		s = (SwitchButton)findViewById(R.id.skill_button_intermediate);
		s.setResources(R.drawable.button_orange, 0);
		s.setResources(R.drawable.button_orange2, 1);
		s = (SwitchButton)findViewById(R.id.skill_button_advanced);
		s.setResources(R.drawable.button_orange, 0);
		s.setResources(R.drawable.button_orange2, 1);
	}
	
	public void toggle(View v) { //careful, if the resources aren't set, calling 'toggle' will give an error.
		SwitchButton s = (SwitchButton) v;
		s.toggle();
	}

	public void onClick(View arg0) {
		if(((MyApp)this.getApplication()).getGoal()==1){
			Intent intent = new Intent(this,StrengthScreen.class);
			this.startActivity(intent);	
		}
		if(((MyApp)this.getApplication()).getGoal()==2){
			Intent intent = new Intent(this,CardioSelection.class);
			this.startActivity(intent);	
		}
		
	}
}

