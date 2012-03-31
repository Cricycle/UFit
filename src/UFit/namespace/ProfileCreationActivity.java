package UFit.namespace;
import UFit.namespace.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


public class ProfileCreationActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilecreation);
		Spinner spinner = (Spinner) findViewById(R.id.profile_spinner_gender);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.MF, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
		

		
		View v = findViewById(R.id.profile_button_next);
		TextView v2 = (TextView) findViewById(R.id.bmi2);
		v.setOnClickListener(this);
		v2.setText("23");
	}
	
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.profile_button_next){
			Intent intent = new Intent(this,GoalSelection.class);
			this.startActivity(intent);
		}

}
}
