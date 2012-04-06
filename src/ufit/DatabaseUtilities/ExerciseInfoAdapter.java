package ufit.DatabaseUtilities;

import ufit.namespace.*;
import ufit.profilecreation.ExerciseInfoActivity;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ExerciseInfoAdapter extends BaseAdapter implements ListAdapter{
    	private static List<ExerciseInfo> myExerciseList;
    	private LayoutInflater myInflater;
    	private Context myCon; 
    	
    	
    	public ExerciseInfoAdapter(Context c, List<ExerciseInfo> list){
    		myExerciseList = list;
    		myInflater = LayoutInflater.from(c);
    		myCon = c;
    	}
    	
    	//@Override
    	public int getCount()
    	{
    		return myExerciseList.size();
    	}
    	
    	//@Override
    	public Object getItem(int position)
    	{
    		return myExerciseList.get(position);
    	}
    	
    	//@Override
    	public long getItemId(int position)
    	{
    		return position;
    	}
    	
    	//@Override
    	public View getView(int position, View convertView, ViewGroup parent){
    		View view = convertView;
    		
    		if(view == null) {
    			view = myInflater.inflate(R.layout.exerciseslists,null); 
    			view.setClickable(true);
    			view.setOnClickListener(new OnClickListener()
    			{
    				//@Override
    				public void onClick(View v)
    				{
    					TextView txtV = (TextView) v.findViewById(R.id.txtEID);	
    					String temp;
    					temp = txtV.getText().toString().trim();
    					int exerciseIdInt = Integer.parseInt(temp);
    					Bundle ExtrasOut = new Bundle();
    					ExtrasOut.putInt("ufit.namespace.exerciseClicked", exerciseIdInt);	
    		    		Intent I = new Intent(myCon, ExerciseInfoActivity.class);	
    		    		I.putExtras(ExtrasOut);
    		    		myCon.startActivity(I);	
    				}
    			});	
    			
    			///change everything below this
    			
    			ViewHolder holder = new ViewHolder();
    			
    			holder.txtName = (TextView)view.findViewById(R.id.txtName);
    			holder.imgThumb = (ImageView)view.findViewById(R.id.imgThumb);
    			holder.txtEID = (TextView)view.findViewById(R.id.txtEID);
    			view.setTag(holder);
    		}	
    			
    		
    		ExerciseInfo exerciseinfo = myExerciseList.get(position);
    		String tempID = Integer.toString(exerciseinfo.getID());
    		
    		if(exerciseinfo != null)
    		{
    			ViewHolder holder = (ViewHolder)view.getTag();
    			
    			holder.txtName.setText(exerciseinfo.getExercise());		//adjust these	
    			holder.txtEID.setText(tempID);
    			
    			
    			if(exerciseinfo.getImage1() == null)
    			{
    			int thumbID = myCon.getResources().getIdentifier(exerciseinfo.getIloc1(), "drawable", this.myCon.getPackageName());
    			holder.imgThumb.setImageResource(thumbID);
    			}
    			else
    			{
    				holder.imgThumb.setImageDrawable(exerciseinfo.getImage1());
    			}
    			
    			
    		}
    		return view;
    	}
    
static class ViewHolder {

	private int exerciseID;
	private String exerciseName;
	private int exerciseImageNumber;
	
	private TextView txtEID;
	private TextView txtName;
	private ImageView imgThumb;
	}
}