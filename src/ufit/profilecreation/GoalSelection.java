package ufit.profilecreation;

import ufit.global.MyApp;
import ufit.global.SwitchButton;
import ufit.namespace.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GoalSelection extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goalselection);
        
        initializeButtons();
        loadProfileView();
    }
    private void loadProfileView() {
    	int goal = ( (MyApp)getApplication()).getGoal();
    	SwitchButton sb;
    	if(goal == 1) {
    		sb = (SwitchButton) findViewById(R.id.goalselection_button_strength);
    		sb.toggle();
    	} else if(goal == 2) {
    		sb = (SwitchButton) findViewById(R.id.goalselection_button_cardio);
    		sb.toggle();
    	} else if(goal == 3) {
    		sb = (SwitchButton) findViewById(R.id.goalselection_button_general);
    		sb.toggle();
    		
    	}
    }
    
    public void initializeButtons() {
        Button next = (Button) findViewById(R.id.goalselection_button_next);
        next.setOnClickListener(this);
    	
    	SwitchButton strength = (SwitchButton) findViewById(R.id.goalselection_button_strength);
        SwitchButton cardio = (SwitchButton) findViewById(R.id.goalselection_button_cardio);
        SwitchButton general = (SwitchButton)findViewById(R.id.goalselection_button_general);
        
        strength.setResources(R.drawable.strength_off, 0);
        strength.setResources(R.drawable.strength_on, 1);
        cardio.setResources(R.drawable.cardio_off, 0);
        cardio.setResources(R.drawable.cardio_on, 1);
        general.setResources(R.drawable.general_off, 0);
        general.setResources(R.drawable.general_on, 1);
    }
    
    public void toggle(View v) {
    	//Toast.makeText(this,"Reached toggle", Toast.LENGTH_SHORT).show();
    	SwitchButton sb = (SwitchButton) v;
    	sb.toggle();
    	onClick(v);
    	//Toast.makeText(this,"passed click", Toast.LENGTH_SHORT).show();
    	if(sb.isChecked()) {
	    	int thisViewID = sb.getId();
	    	int strengthID = R.id.goalselection_button_strength;
	    	int cardioID = R.id.goalselection_button_cardio;
	    	int generalID = R.id.goalselection_button_general;
	    	int[] list = {strengthID, cardioID, generalID};
	    	for(int id: list) {
	    		if(id != thisViewID) {
	    			( (SwitchButton)findViewById(id) ).setChecked(false);
	    		}
	    	}
	    	//set the other buttons to the off position.
    	} else {
    		((MyApp)this.getApplication()).setGoal(0); //indicates no goal is set.
    	}
    }
    
    //@Override
	public void onClick(View v) {
		if(v.getId() == R.id.goalselection_button_strength){
			((MyApp)this.getApplication()).setGoal(1); //indicates strength training as goal
	        
	    }else if(v.getId() == R.id.goalselection_button_cardio) {
			((MyApp)this.getApplication()).setGoal(2); //indicates cardio training as goal
			
		} else if (v.getId() == R.id.goalselection_button_general) {
			((MyApp)this.getApplication()).setGoal(3);
			
		} else if(v.getId() == R.id.goalselection_button_next) {
			if( ((MyApp)this.getApplication()).getGoal() != 0) {
				Intent intent = new Intent(this,SkillSelection.class);
				this.startActivity(intent);
			} else {
				Toast.makeText(this, "You have not selected a training option yet", Toast.LENGTH_SHORT).show();
			}
		}
    }
}