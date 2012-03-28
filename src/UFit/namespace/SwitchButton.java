package UFit.namespace;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.Checkable;

public class SwitchButton extends Button implements Checkable {
	
	private boolean isChecked = false;
	private int[] resources = new int[2]; //resources[0] is false, resources[1] is true
	
	public SwitchButton(Context context) {
		super(context);
		//created only so that the default constructor doesn't exist.
	}
	
	public SwitchButton(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public SwitchButton(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
	}
	
	public void setResources(int res, int index) {//this can cause exceptions if bad indicies are passed in
		resources[index] = res;
	}
	
	
	
	public boolean isChecked() {
		return isChecked;
	}


	public void setChecked(boolean arg) {
		isChecked = arg;
		setBackgroundResource(getResource(isChecked));
	}

	public void toggle() {
		isChecked = !isChecked;
		setBackgroundResource(getResource(isChecked));
	}
	
	public int getResource(boolean isChecked) { //make sure to set the resources before doing anything
		if(isChecked)
				return resources[1];
		return resources[0];
	}

}
