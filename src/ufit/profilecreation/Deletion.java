package ufit.profilecreation;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Deletion extends Activity implements OnClickListener
{
	private Profile profile;
	private MyApp application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
		
		application = (MyApp)getApplication();

		profile = ((MyApp)getApplication()).getProfile();
		
		Button yes = (Button) findViewById(R.id.delete_yes);
		Button no = (Button) findViewById(R.id.delete_no);
		
		no.setOnClickListener(this);
		yes.setOnClickListener(this);
	}
	
	
	//@Override
	public void onClick(View v) 
	{
		if (v.getId() == R.id.delete_yes)
		{
			String username = profile.getUsername();
			application.deleteUser(username);
			Intent intent = new Intent(this,Selection.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction("Go to Selection");
			this.startActivity(intent);
		}
		else if (v.getId() == R.id.delete_no)
		{
			finish();
		}
		else
		{
			Log.wtf("delete error", "neither yes nor no pushed... WTF");
		}
	}
}
