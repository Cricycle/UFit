package ufit.weeklyplanner;

import java.util.ArrayList;

import ufit.namespace.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



/**
public class TodaysExercisesActivity extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
 
	public TodaysExercisesActivity(Context context, String[] values) {
		super(context, R.layout.exerciseslists, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.exerciseslists, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);
 
		// Change icon based on name
		String s = values[position];
 
		System.out.println(s);
 
		if (s.equals("WindowsMobile")) {
			imageView.setImageResource(R.drawable.bench_press);
		} else if (s.equals("iOS")) {
			imageView.setImageResource(R.drawable.bench_press);
		} else if (s.equals("Blackberry")) {
			imageView.setImageResource(R.drawable.bench_press);
		} else {
			imageView.setImageResource(R.drawable.bench_press);
		}
 
		return rowView;
	}
}
*/


public class TodaysExercisesActivity extends Activity  {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaysexercises);
        
        //name of items
        String[] items = {"red", "blue","green"};

        ListView listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        
        
        
        
        
        //go to weekly planner button
        final Button button1 = (Button) findViewById(R.id.button1);
 		button1.setOnClickListener(new View.OnClickListener() {
 		public void onClick(View v) {
 			// Perform action on click
 			// go to weekly planner
 			Intent intent = new Intent (TodaysExercisesActivity.this, TodaysExercisesActivity.class);
 			startActivity(intent);
 		}
 	});
         
    }
    
    
    
    
    public class ExercisesAdapter extends ArrayAdapter<ListExercises>{
    	private ArrayList<ListExercises> exercises;
    	
    	public ExercisesAdapter(Context context, int textViewResourceId, ArrayList<ListExercises> exercises){
    		super(context, textViewResourceId, exercises);
    		this.exercises = exercises;
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent){
    		View v = convertView;
    		
    		if(v == null) {
    			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			v = vi.inflate(R.layout.exerciseslists, null);
    		}
    		
    		ListExercises exercise = exercises.get(position);
    		if(exercise != null) {
    			TextView exerciseName = (TextView) v.findViewById(R.id.exerciseName);
    			
    			if(exerciseName != null){
    				exerciseName.setText(exercise.exerciseName);	
    			}
    		}
    		return v;
    	}
    }
    
    
    public class ListExercises {
    	public String exerciseName;
    	
    	public ListExercises(String exerciseName){
    		this.exerciseName = exerciseName;
    	}
    	
    	public void setText(String exerciseName)
    	{
    		this.exerciseName = exerciseName;
    	
    	}
    }
     
    protected void onNewIntent(Intent i) {
    	String action = i.getAction();
		if( action.equals( getString(R.string.sunday) ) ) {
			//set up stuff for sunday.
		} else if( action.equals( getString(R.string.monday))){
			
		} else if( action.equals( getString(R.string.tuesday))){
			
		} else if( action.equals( getString(R.string.wednesday))){
			
		} else if( action.equals( getString(R.string.thursday))){
			
		} else if( action.equals( getString(R.string.friday))){
			
		} else if( action.equals( getString(R.string.saturday))){
			
		} else {
			Toast.makeText(this, "Another bug, hooray!", Toast.LENGTH_SHORT).show();
		}
	}
}
