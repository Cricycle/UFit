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
import android.widget.EditText;
import android.widget.TextView;

public class EditTargetWeight extends Activity implements OnClickListener
{
	private Profile profile;
	private MyApp application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.targetweight);
		
		application = (MyApp)getApplication();

		profile = ((MyApp)getApplication()).getProfile();
		this.loadInformation();
		
		Button save = (Button) findViewById(R.id.target_button_save);
		
		save.setOnClickListener(this);
	}
	
	private void loadInformation() {
		EditText et;
		et = (EditText) findViewById(R.id.edittext_targetweight);
		et.setText("" + profile.getTargetWeight());
	}
	
	//@Override
	public void onClick(View v) 
	{
		if (v.getId() == R.id.target_button_save)
		{
			EditText etWeight = (EditText) findViewById(R.id.edittext_targetweight);			String weight = etWeight.getText().toString();
			if(weight.length() > 0 && !weight.equals(".")) {
				profile.setTargetWeight(Double.parseDouble(weight));
			} else {
				profile.setTargetWeight(80.0);
			}
		    ((MyApp)getApplication()).saveProfile();
			Intent intent = new Intent(this,HomeScreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction("Go to Home");
			this.startActivity(intent);
		}
		else
		{
			Log.wtf("Target Weight Error", "What you wanna weigh?... WTF");
		}
	}
	public void onPause() {
	    super.onPause();
	    ((MyApp)getApplication()).saveProfile();
	}
}
