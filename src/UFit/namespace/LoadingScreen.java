package UFit.namespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadingScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.titlescreen);
		Thread logoTimer = new Thread(){
			public void run(){
			try{
				int timer=0;
				while(timer<1500){
					sleep(100);
					timer= timer +100;
				}
				startActivity(new Intent("com.UFit.namespace.DISCLAIMER"));
			}
				
			catch(Exception e){
				
			}
			finally{
			finish();	
			}
			}
		};
		logoTimer.start();
		}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
   
}