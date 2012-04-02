package ufit.profilecreation;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeScreen extends Activity //implements OnClickListener 
{
	private Profile profile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
		profile = ((MyApp)getApplication()).getProfile();
		
		loadInformation();
		//View v = findViewById(R.id.create_profile);
		//v.setOnClickListener(this);
		
		
	}

	private void loadInformation() {
		TextView name = (TextView) findViewById(R.id.homescreen_textview_name);
		name.setText(profile.getUsername());
	}
//	public void onClick(View arg0) {
	//	if(arg0.getId() == R.id.create_profile){
		//	Intent intent = new Intent(this,Profile.class);
			//this.startActivity(intent);
	//	}
		
//	}
	
	

}
