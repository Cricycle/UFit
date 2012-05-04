package ufit.profilecreation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;

public class ModifyOptionsScreenActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private Profile profile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifyoptionsscreen);
        initialiseButtons();
        loadInformation();
        
        ImageView profilepic = (ImageView)this.findViewById(R.id.imageView1);
		if (profile.getWorkoutType() == 1)
		{
			profilepic.setImageResource(this.getResources().getIdentifier("stickfig_s", "drawable", this.getPackageName()));
		}
		else if (profile.getWorkoutType() == 3)
		{
			profilepic.setImageResource(this.getResources().getIdentifier("stickfig_g", "drawable", this.getPackageName()));
		}
		else
		{
			profilepic.setImageResource(this.getResources().getIdentifier("stickfig_c", "drawable", this.getPackageName()));
		}
        
    }
	private void loadInformation() {
		profile = ((MyApp)getApplication()).getProfile();
		TextView name = (TextView) findViewById(R.id.textview_name);
		name.setText(profile.getUsername());
	}
    public void initialiseButtons() {
        Button mod_ex = (Button) findViewById(R.id.delete_profile);
        Button mod_go = (Button) findViewById(R.id.mod_goals);
        Button mod_eq = (Button) findViewById(R.id.mod_equip);
        Button mod_et = (Button) findViewById(R.id.mod_targetweight);
        Button button0 = (Button) findViewById(R.id.button0);
        mod_ex.setOnClickListener(this);
        mod_eq.setOnClickListener(this);
        mod_go.setOnClickListener(this);
        button0.setOnClickListener(this);
        mod_et.setOnClickListener(this);
    }    
    //@Override
	public void onClick(View v) {
		if(v.getId() == R.id.delete_profile){
			Intent intent = new Intent(this,Deletion.class);
			this.startActivity(intent);	        
		} else if(v.getId() == R.id.mod_goals){
			Intent intent = new Intent(this,GoalSelection.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction("Go to Goal Selection");
			this.startActivity(intent);	
			profile.setExpertSystemFlag(true);        
		} else if(v.getId() == R.id.mod_equip){
			Intent intent = new Intent(this,MachineSelection.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction("Go to Machine Selection");
			this.startActivity(intent);	   
			profile.setExpertSystemFlag(true);     
		} else if(v.getId() == R.id.mod_targetweight){
			Intent intent = new Intent(this,EditTargetWeight.class);
			this.startActivity(intent);	        
		}else if(v.getId() == R.id.button0){
			Intent intent = new Intent(this,HomeScreen.class);
			this.startActivity(intent);	        
		}
    }
}