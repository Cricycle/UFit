package ufit.profilecreation;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DeleteProgress extends Activity implements OnClickListener
{
	private Profile profile;
	private MyApp application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deletegraph);
		
		application = (MyApp)getApplication();

		profile = ((MyApp)getApplication()).getProfile();
		
		Button yes = (Button) findViewById(R.id.deleteprogress_yes);
		Button no = (Button) findViewById(R.id.deleteprogress_no);
		
		no.setOnClickListener(this);
		yes.setOnClickListener(this);
	}
	
	
	//@Override
	public void onClick(View v) 
	{
		if (v.getId() == R.id.deleteprogress_yes)
		{
			String username = profile.getUsername();
			File fprogress = new File("/data/data/ufit.namespace/files/" + username + "_progress.txt");
			if(fprogress.delete())
			{
				this.createProgress(username);
			}
			else
				Log.wtf("Can't Delete Progress Tracker", "Woah we fudged up");
			
			Intent intent = new Intent(this,HomeScreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction("Go to Home");
			this.startActivity(intent);
		}
		else if (v.getId() == R.id.deleteprogress_no)
		{
			finish();
		}
		else
		{
			Log.wtf("delete error", "neither yes nor no pushed... WTF");
		}
	}
	
	private void createProgress(String username)
	{
		PrintWriter progress = null;
		
		String newTitle = username + "_progress.txt";		
		try{
			progress = new PrintWriter(openFileOutput(newTitle, Context.MODE_PRIVATE));
			progress.println(profile.getWeight());
			Date today = new Date();
			progress.println(today.getTime());
			progress.close();
			progress = null;
	} catch (Exception e) {
		Toast.makeText(this, "failed create new progress", Toast.LENGTH_SHORT).show();
		e.printStackTrace();
	}
		return;
	}
}