package UFit.namespace;
import UFit.namespace.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class StrengthScreen extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.strengthscreen);
		View v = findViewById(R.id.button1);
		v.setOnClickListener(this);
	}
	
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.button1){
			Intent intent = new Intent(this,MachineSelection.class);
			this.startActivity(intent);
		}

}
}
