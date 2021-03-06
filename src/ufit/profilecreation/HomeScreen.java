package ufit.profilecreation;

import java.util.ArrayList;

import ufit.expertsystem.ExpertSystems;

import ufit.DatabaseUtilities.ExerciseInfo;
import ufit.DatabaseUtilities.MyDbAdapter;
import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Activity implements OnClickListener
{
	private Profile profile;
	private MyApp application;
	public  MyDbAdapter eDbAdaptor;
	private ExpertSystems magic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
		initializeButtons();
		
		System.gc();

		application = (MyApp)getApplication();
		onNewIntent(getIntent());

		profile = ((MyApp)getApplication()).getProfile();

		//here, this is where we save the profile!
		((MyApp)getApplication()).saveProfile();

		loadInformation();
		ImageView profilepic = (ImageView)this.findViewById(R.id.homescreen_imageview);
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
		//if (profile.hasBeenMonth() || profile.getID() == null || profile.getID().size() <= 0)
		if(profile.hasBeenMonth())
		{
			profile.setExpertSystemFlag(true);
		}
		
		if(profile.getExpertSystemFlag())
		{
			magic = new ExpertSystems(this,profile);
			profile.setID(magic.fetchPlan());
			profile.setTodaysDate();
			profile.setExpertSystemFlag(false);
		//utilizeDatabase();//DO NOT RUN THIS UNTIL OUR DATABSE IS COMPLETE AND IN THE ASSETS FOLDER
			((MyApp)getApplication()).saveProfile();
		}

	}

	/*private void utilizeDatabase() {
		//DO NOT RUN THIS UNTIL OUR DATABSE IS COMPLETE AND IN THE ASSETS FOLDER
		int workoutType;
		workoutType = profile.getWorkoutType(); //I have assumed the returned int matches the one in the DB

		int workoutGroup; //don't know where any of this is saved or how to call it, or if its one item or a list.
		//workoutGroup = profile.getWorkoutGroup(); //I have assumed its only one focus area, stored as an int.
		workoutGroup = 1; //default for now.

		int workoutSkill = profile.getSkill(); //I have assumed the returned int matches the one in the DB

		ArrayList<String> equipmentList = new ArrayList<String>();
		equipmentList = profile.getEquipmentList();

		//should this be profile.getID() ?
		if(profile.getID() == null) //if saved list of current exercises doesn't exist already
		{
			eDbAdaptor = new MyDbAdapter(this, workoutType, workoutGroup, workoutSkill, equipmentList);
		}
		else
		{
			ArrayList<Integer> storedList = profile.getID(); //should this be profile.getID() ?
			eDbAdaptor = new MyDbAdapter(this, workoutType,workoutGroup,workoutSkill, equipmentList, storedList);
		}

		//all valid focus group exercises should be in focusExercises when this completes
		ArrayList<ExerciseInfo> focusExercises;
		focusExercises = eDbAdaptor.fetchFocusExercises();
		// do what you want with them. NOTE: the array INDEXES do NOT match up to the EXERCISE ID stored in the database.

		//all valid non-focus group exercises should be in otherExercises when this completes
		ArrayList<ExerciseInfo> otherExercises;
		otherExercises = eDbAdaptor.fetchOtherExercises();
		//do what you want with them. NOTE: the array INDEXES do NOT match up to the EXERCISE ID stored in the database.

		//after the magical expert system returns a list of exercises, assumed to be an ArrayList<Integer> magicExerciseIDs
		ArrayList<Integer> magicExerciseIDs = null; //default for now
		//magicExerciseIDs = Magic.getPlannedExercises(); //magic doesn't exist yet.
		eDbAdaptor.setStoredExerciseIDs(magicExerciseIDs);

		//store the list in the profile too for later use

		 profile.setID(magicExerciseIDs); //again might need to be setID(magic) instead

		eDbAdaptor.close();
	}*/


	private void loadInformation() {
		TextView name = (TextView) findViewById(R.id.homescreen_textview_name);
		name.setText(profile.getUsername());
	}

	protected void onNewIntent(Intent i) {
		Bundle b = i.getExtras();
		if(b != null) {
			String name = b.getString("loadprofile");
			if(name != null) {
				application.setProfile(name, this);
				b.remove("loadprofile");
			}
			/*String[] details = (i.getAction()).split(" ");
			//Toast.makeText(this, i.getAction(), Toast.LENGTH_SHORT).show();
			if(details.length == 2 && details[0].equals("newprofile")) {
				application.setProfile(details[1], this);
				i.setAction(null);
			}*/

		}
	}

	//@Override
	public void onClick(View v) {
		if(v.getId() == R.id.homescreen_button_todaysexercises){
			Intent intent = new Intent(this,TodaysExercisesActivity.class);
			this.startActivity(intent);
		}  else if(v.getId() == R.id.homescreen_button_weeksexercises) {
			Intent intent = new Intent(this,WeeklyPlannerActivity.class);
			this.startActivity(intent);
		} else if (v.getId() == R.id.homescreen_button_modify) {
			Intent intent = new Intent(this,ModifyOptionsScreenActivity.class);
			this.startActivity(intent);
		} else if (v.getId() == R.id.home_progress) {
			Intent intent = new Intent(this,Progress.class);
			this.startActivity(intent);
		} else if (v.getId() == R.id.button0) {
			Intent intent = new Intent(this,SkillSelection.class);
			this.startActivity(intent);
		}
	}
    public void initializeButtons() {
        Button homeToday = (Button) findViewById(R.id.homescreen_button_todaysexercises);
        Button homeWeekly = (Button) findViewById(R.id.homescreen_button_weeksexercises);
        TextView homeModify = (TextView) findViewById(R.id.homescreen_button_modify);
        Button homeProgress = (Button) findViewById(R.id.home_progress);
        Button button0 = (Button) findViewById(R.id.button0);

        button0.setOnClickListener(this);
        homeToday.setOnClickListener(this);
        homeWeekly.setOnClickListener(this);
        homeModify.setOnClickListener(this);
        homeProgress.setOnClickListener(this);
    }
}
