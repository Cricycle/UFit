package ufit.profilecreation;

import java.util.ArrayList;

import ufit.global.MyApp;
import ufit.namespace.R;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Selection extends ListActivity implements OnClickListener {
	private MyApp application;
	private ListAdapter adapter;
	private ArrayList<Button> toBePut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		application = (MyApp) getApplication();
		loadUserNameDisplay();
		
		View v = findViewById(R.id.selection_button_createprofile);
		v.setOnClickListener(this);
		
		
	}

	private void loadUserNameDisplay() {
		Toast.makeText(this, "trying to load display", Toast.LENGTH_SHORT).show();
		toBePut = new ArrayList<Button>();
		ArrayList<String> usernames = application.getUsernames();
		int count = 1;
		for(String name: usernames) {
			Toast.makeText(this, "num usernames:" + count++ + name + "<<", Toast.LENGTH_SHORT).show();
			Button b = new Button(this);
			b.setVisibility(0);
			b.setText(name);
			b.setOnClickListener(this);
			b.setClickable(true);
			b.setFocusable(true);
			b.setBackgroundResource(R.drawable.button_orange);
			LayoutParams layout = new LayoutParams(-1, -1);
			b.setLayoutParams(layout);
			b.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			toBePut.add(b);
		}
		
		adapter = new MyListAdapter(this, R.layout.row, toBePut);
		setListAdapter(adapter);
		
		
	}

	public void onClick(View v) {
		if(v.getId() == R.id.selection_button_createprofile){
			Intent intent = new Intent(this,ProfileCreationActivity.class);
			this.startActivity(intent);
		} else { //this means it should be one of the dynamic buttons.
			Button b = (Button) v.findViewById(R.id.listview_button);
			application.setProfile(b.getText().toString(), this);
			
			Intent intent = new Intent(this, HomeScreen.class);
			startActivity(intent);
		}
	}
	
	protected void onNewIntent(Intent i) {
		if(i.getAction().equals("Go to Home")) {
			Intent intent = new Intent(this, HomeScreen.class);
			this.startActivity(intent);
		}
	}
	
	protected void onResume() {
		super.onResume();
		loadUserNameDisplay();
	}
}

class MyListAdapter extends ArrayAdapter<Button>{//desperate gambit.
	private ArrayList<Button> items;
	private Context context;
	private OnClickListener lis;
	
	public MyListAdapter(Context context, int textViewResourceId, ArrayList<Button> objects) {
		super(context, textViewResourceId, objects);
		items = objects;
		this.context = context;
	}
	
	public void setOnClickListener(OnClickListener o) {
		this.lis = o;
	}
	
	public View getView (int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row, null);
        }
        Button b = items.get(position);
        if (b != null) {
                Button bb = (Button) v.findViewById(R.id.listview_button);
                bb.setText( b.getText() );
                bb.setOnClickListener(lis);
                bb.setClickable(true);
        }
        v.setOnClickListener(lis);
        v.setClickable(true);
        return v;
	}
}