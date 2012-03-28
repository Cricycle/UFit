package UFit.namespace;
import UFit.namespace.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class Profile extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		View v = findViewById(R.id.profile_button_next);
		v.setOnClickListener(this);
	}
	
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.profile_button_next){
			Intent intent = new Intent(this,GoalSelection.class);
			this.startActivity(intent);
		}

}
}
