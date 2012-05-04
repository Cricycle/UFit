package ufit.profilecreation;

import java.util.ArrayList;

import ufit.DatabaseUtilities.ExerciseInfo;
import ufit.DatabaseUtilities.ExerciseInfoAdapter;
import ufit.DatabaseUtilities.MyDbAdapter;
import ufit.DatabaseUtilities.PictureAdapter2;
import ufit.global.MyApp;
import ufit.global.SwitchButton;
import ufit.namespace.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ExerciseInfoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exerciseinfoscreen);
        
        Bundle b = getIntent().getExtras();
        
        Integer exerciseID = b.getInt("ufit.namespace.exerciseClicked");
        initializeButtons();
        
        ExerciseInfo Exercise;
        MyDbAdapter db = new MyDbAdapter(this);
        db.setExerciseID(exerciseID);
        db.open();
        Exercise = db.fetchTheExercise();
        db.close();
        
        Converter c = new Converter();
        TextView v = (TextView)this.findViewById(R.id.ExerciseName);
        v.setText(Exercise.getExercise());
        v = (TextView)this.findViewById(R.id.Muscle_Group);
        v.setText(c.muscleConvert(Exercise.getMuscle_group()));
        //ImageView Vi = (ImageView)this.findViewById(R.id.imageView1);
        //Vi.setImageDrawable(Exercise.getImage1());
        

        //ImageView left = (ImageView)this.findViewById(R.id.leftid);
        //this.getResources().getIdentifier(Exercise.getIloc1(), "drawable", this.getPackageName());
        //left.setImageResource(this.getResources().getIdentifier(Exercise.getIloc1(), "drawable", this.getPackageName()));
        //left.setImageDrawable(Exercise.getImage1());
        //ImageView middle = (ImageView)this.findViewById(R.id.middleid);
        //middle.setImageResource(this.getResources().getIdentifier(Exercise.getIloc2(), "drawable", this.getPackageName()));
        //middle.setImageDrawable(Exercise.getImage2());
        //ImageView right = (ImageView)this.findViewById(R.id.rightid);
        //right.setImageResource(this.getResources().getIdentifier(Exercise.getIloc3(), "drawable", this.getPackageName()));
        //right.setImageDrawable(Exercise.getImage3());
        
        ImageView leftarrow =  (ImageView) this.findViewById(R.id.leftarrow);
        ImageView rightarrow = (ImageView) this.findViewById(R.id.rightarrow);
        
        PictureAdapter2 adapter = new PictureAdapter2(Exercise, leftarrow, rightarrow);
        ViewPager myPager = (ViewPager) findViewById(R.id.oneimagelist);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);
        
        //ArrayList<String> pics = new ArrayList<String>();
        //pics.add(Exercise.getIloc1());
        //pics.add(Exercise.getIloc2());
        //pics.add(Exercise.getIloc3());
        //ListView listView = (ListView) findViewById(R.id.oneimagelist);
        //listView.setAdapter(new PictureAdapter(this, Exercise));
        
        v = (TextView)this.findViewById(R.id.Exercise_Description);
        v.setText(Exercise.getDescription());
    }
    
    public void toggle(View v) {
    	//Toast.makeText(this,"Reached toggle", Toast.LENGTH_SHORT).show();
    	SwitchButton sb = (SwitchButton) v;
    	sb.toggle();
    	onClick(v);
    	//Toast.makeText(this,"passed click", Toast.LENGTH_SHORT).show();
    	if(sb.isChecked()) {
	    	int thisViewID = sb.getId();
	    	int b_like = R.id.button_like;
	    	int b_dislike = R.id.button_dislike;
	    	int[] list = {b_like, b_dislike};
	    	for(int id: list) {
	    		if(id != thisViewID) {
	    			( (SwitchButton)findViewById(id) ).setChecked(false);
	    		}
	    	}
	    	//set the other buttons to the off position.
    	} else {
    		//((MyApp)this.getApplication()).setGoal(0); //indicates no goal is set.
    	}
    }
    
    public void initializeButtons() {
    	
    	SwitchButton like = (SwitchButton) findViewById(R.id.button_like);
        SwitchButton dislike = (SwitchButton) findViewById(R.id.button_dislike);
        
        like.setResources(R.drawable.thumbsup, 0);
        like.setResources(R.drawable.thumbsupclicked, 1);
        dislike.setResources(R.drawable.thumbsdown, 0);
        dislike.setResources(R.drawable.thumbsdownclicked, 1);
    }
    
    public void onClick(View v) {
		if(v.getId() == R.id.button_like){
			//do like code
	        
	    }else if(v.getId() == R.id.button_dislike) {
			//do dislike code
			
		} 
    }
    
}