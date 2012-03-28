package UFit.namespace;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class GoalSelection extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goalselection);
        
        View v1 = findViewById(R.id.button1);
        View v2 = findViewById(R.id.button2);
        View v3 = findViewById(R.id.button3);
        View v4 = findViewById(R.id.button4);
        v1.setOnClickListener(this);
        v2.setOnClickListener(this);
        v3.setOnClickListener(this);
        v4.setOnClickListener(this);
    }
    //@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.button1){
			((MyApp)this.getApplication()).setGoal(1);
	            Button button = (Button) arg0;
	            button.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.test2));
	        }

		if(arg0.getId() == R.id.button2){
			((MyApp)this.getApplication()).setGoal(2);
			Button button = (Button) arg0;
			button.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.cardio2));
		}
		if(arg0.getId() == R.id.button3){
			((MyApp)this.getApplication()).setGoal(3);
			Button button = (Button) arg0;
			button.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.general2));
		}
		if(arg0.getId() == R.id.button4){
			Intent intent = new Intent(this,SkillSelection.class);
			this.startActivity(intent);
		}
    }
}