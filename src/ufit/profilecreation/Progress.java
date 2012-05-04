package ufit.profilecreation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import ufit.global.MyApp;
import ufit.namespace.R;
import ufit.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;


public class Progress extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private Profile profile;
	private MyApp application;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        application = (MyApp)getApplication();

        profile = application.getProfile();
        initializeButtons();
        loadInformation();
        
    }
    
    public Intent initGraph() {
   	 ArrayList<Double> storedWeights= new ArrayList<Double>();
		ArrayList<Long> storedDates = new ArrayList<Long>();
        BufferedReader read = null;
        String newTitle = profile.getUsername() + "_progress.txt";
        
        //long offset = 1000*60*60*4;
        long offset = 0;
        
        try {
			read = new BufferedReader(new InputStreamReader(openFileInput(newTitle)));
			
			
			while (read.ready()){
			storedWeights.add(Double.parseDouble(read.readLine()));
			storedDates.add(Long.parseLong(read.readLine()));
			}
			read.close();
			read = null;
			}
			catch (Exception e) {
				Toast.makeText(this, "Progress Reading Failed", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
        
        Date[] x = new Date[storedDates.size()];
        Double[] y = new Double[storedWeights.size()];
        
        for (int i =0; i<storedDates.size(); i++){
        	x[i] = new Date(storedDates.get(i) - offset);
        	y[i] = storedWeights.get(i);
        }
        double minWeight = y[0];
		double maxWeight = y[0];
		
		for (int i=0; i<storedDates.size(); i++){
			if (y[i] < minWeight){
				minWeight = y[i];
			}
			if (y[i] > maxWeight){
				maxWeight = y[i];
			}
			
		}
		
		if (minWeight > profile.getTargetWeight() ) {
			minWeight = profile.getTargetWeight();
		}
		
		if (maxWeight < profile.getTargetWeight() ) {
			maxWeight = profile.getTargetWeight();
		}
		
		TimeSeries series = new TimeSeries("Weight (lbs)");
		for (int i =0; i < x.length; i++) 
		{
			series.add(x[i],y[i]);
		}
		
		TimeSeries target = new TimeSeries("Target Weight");
		target.add(x[0], profile.getTargetWeight());
		target.add(x[(x.length-1)], profile.getTargetWeight());
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		dataset.addSeries(target);
	
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.YELLOW);
		mRenderer.addSeriesRenderer(renderer);
		
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(Color.RED);
		mRenderer.addSeriesRenderer(renderer2);
		mRenderer.setXTitle("Date");
		mRenderer.setYTitle("Weight");
		if ((minWeight - 30) < 0) {
			minWeight = 30;
		}
		mRenderer.setYAxisMin(minWeight-30);
		mRenderer.setYAxisMax(maxWeight+30);
		
		
		Intent lineIntent = ChartFactory.getTimeChartIntent(this, dataset, mRenderer, "Weight Loss Progress");
		return lineIntent;
   }
    
    
	public void initializeButtons() {
    
        Button getProgressGraph = (Button) findViewById(R.id.progress_button_graph);
        Button save = (Button) findViewById(R.id.progress_button_save);
        Button back = (Button) findViewById(R.id.progress_button_back);
        Button reset = (Button) findViewById(R.id.reset_progress);
        
        getProgressGraph.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        reset.setOnClickListener(this);
	}
	private void loadInformation() {
		EditText et;
		et = (EditText) findViewById(R.id.progress_edittext_curweight);
		et.setText("" + profile.getWeight());
		TextView name = (TextView) findViewById(R.id.progress_textview_bmidisplay);
		name.setText(Double.toString(profile.computeBMI()));
		name = (TextView) findViewById(R.id.progress_textview_caloriedisplay);
		name.setText(Double.toString(profile.getCalories()));
	}
private void updateInformation() {
		
		EditText etWeight = (EditText) findViewById(R.id.progress_edittext_curweight);
		String weight = etWeight.getText().toString();
		if(weight.length() > 0 && !weight.equals(".")) {
			profile.setWeight(Double.parseDouble(weight));
		} else {
			profile.setWeight(0.0);
		}
		((MyApp)getApplication()).saveProfile();
		PrintWriter progress = null;
		
		BufferedReader read = null;
		String newTitle = profile.getUsername() + "_progress.txt";
		try {
			read = new BufferedReader(new InputStreamReader(openFileInput(newTitle)));
			ArrayList<Double> storedWeights= new ArrayList<Double>();
			ArrayList<Long> storedDates = new ArrayList<Long>();
			
			while (read.ready()){
			storedWeights.add(Double.parseDouble(read.readLine()));
			storedDates.add(Long.parseLong(read.readLine()));
			}
			read.close();
			
			progress = new PrintWriter(openFileOutput(newTitle, Context.MODE_PRIVATE));
			for (int i =0; i <storedWeights.size(); i++){
				progress.println(storedWeights.get(i));
				progress.println(storedDates.get(i));
			}
			progress.println(weight);
			Date currentDate = new Date();
			progress.println(currentDate.getTime());
			progress.close();
			progress = null;
		}
		catch (Exception e) {
			Toast.makeText(this, "Progress Tracker Failed", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {
			if(progress != null)
				progress.close();
		}
		
		
		/*application.saveProfile();
		String u = profile.getUsername();
		application.setProfile(u, application);*/
		loadInformation();
	}

	
    //@Override
	public void onClick(View v) {
		if(v.getId() == R.id.progress_button_graph){
			startActivity(initGraph());
		}
		else if(v.getId() == R.id.progress_button_back){
			Intent intent = new Intent(this,HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.startActivity(intent);
			//finish();
		}
		else if(v.getId() == R.id.reset_progress){
			Intent intent = new Intent(this,DeleteProgress.class);
			this.startActivity(intent);
		}
		else if(v.getId() == R.id.progress_button_save){
			updateInformation();
		}
    }
	public void onPause() {
	    super.onPause();
	    ((MyApp)getApplication()).saveProfile();
	}

}
