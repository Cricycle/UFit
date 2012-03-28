package UFit.namespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MachineSelection extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.machineselection);
		View v = findViewById(R.id.button0);
		v.setOnClickListener(this);
		
		
	}

	public void onClick(View arg0) {
		if(arg0.getId() == R.id.button0){
			Intent intent = new Intent(this,HomeScreen.class);
			this.startActivity(intent);
		}
		
	}
	
	

}
