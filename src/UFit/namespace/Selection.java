package UFit.namespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Selection extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		View v = findViewById(R.id.create_profile);
		v.setOnClickListener(this);
		
		
	}

	public void onClick(View arg0) {
		if(arg0.getId() == R.id.create_profile){
			Intent intent = new Intent(this,ProfileCreationActivity.class);
			this.startActivity(intent);
		}
		
	}
	
	

}
