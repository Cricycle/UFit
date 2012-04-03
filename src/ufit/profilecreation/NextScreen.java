package ufit.profilecreation;

import ufit.namespace.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class NextScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.disclaimer);
		Thread th = new Thread(){
		public void run(){
			try{
				sleep(3000);
				/*int timer=0;
				while(timer<3000){
					sleep(100);
					timer= timer +100;
				}*/
				//startActivity(new Intent(this, Selection.class));
				startActivity(new Intent("com.UFit.namespace.SELECTION"));
			}
				
			catch(Exception e){
				
			}
			finally{
			finish();	
			}	
			
		}
		};
		th.start();
	

}
}
