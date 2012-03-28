package UFit.namespace;

import android.app.Application;

public class MyApp extends Application {
private int goal =0;

public void setGoal(int n){
	goal = n;
}
public int getGoal(){
return goal;	
}
}


