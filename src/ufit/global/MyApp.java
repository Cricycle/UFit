package ufit.global;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import ufit.profile.Profile;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class MyApp extends Application {
	private int goal = 0;
	private Profile profile;
	private ArrayList<String> usernames = null;
	private final String userFile = "usernames.txt"; //num at top indicates # of users
										//each user name is stored on a separate line

	public void setGoal(int n) {
		goal = n;
	}
	public int getGoal() {
		return goal;
	}

    public Profile getProfile()
    {
    	if(profile == null) {
    		profile = new Profile(this);
    		loadUsernames();
    	}
        return profile;
    }
    public Profile newProfile()
    {
    	profile = new Profile(this);
    	goal = 0;
        return profile;
    }
    private void loadUsernames() { //loads the usernames from the userFile.
    	usernames = new ArrayList<String>();
    	BufferedReader read = null;
    	try {
    		read = new BufferedReader(new InputStreamReader(openFileInput(userFile)));
    		int numProfiles = Integer.parseInt(read.readLine());
    		for(int i = 0; i < numProfiles; ++i) {
    			usernames.add(read.readLine());
    		}
    	} catch(Exception e) {
    		//Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
    		e.printStackTrace();
    	} finally {
    		if(read != null) {
    			try{
    	    		read.close();
    	    	}catch(Exception e){e.printStackTrace();}
    		}
    	}
    }

    public void saveUsernames() { //exists to initialize the user file.  unnecessary?
    	if(usernames == null || usernames.size() == 0) {
    		try {
        		PrintWriter out = new PrintWriter(openFileOutput(userFile, Context.MODE_PRIVATE));
        		out.write("0");
        		out.close();
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    }

    public void setProfile(String username, Context context)
    {
    	loadUsernames();
    	if(isAUser(username)) {
    		profile = new Profile(context);
    		profile.setUsername(username);
    		try {
    			profile = profile.loadProfile(username, context, openFileInput(profile.getFilename()));
    			goal = profile.getWorkoutType();
    		} catch(Exception e) {
    			e.printStackTrace();
    			//Toast.makeText(this, "error loading profile", Toast.LENGTH_SHORT).show();
    		}
    	} else {
    		//do nothing.
    	}
    }
    private boolean isAUser(String username) {
    	return usernames.contains(username);
	}
	public void updateProfile(Context context) {
    	profile = profile.extend(goal, context);
    }
	public void saveProfile() {
		if(!isAUser(profile.getUsername())) {
			addUser(profile.getUsername());
		}
		try{
			profile.saveProfile(profile, openFileOutput(profile.getFilename(), Context.MODE_PRIVATE));
		} catch(Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "error savingprofile", Toast.LENGTH_SHORT).show();
		}
		//Toast.makeText(this, "Saved profile: " + profile.getUsername(), Toast.LENGTH_SHORT).show();
	}
	private void addUser(String username) { //precondition: assumes that username is a new user!
		usernames.add(username);
		PrintWriter out = null;
		int count = 0;
		PrintWriter progress = null;
		
		String newTitle = username + "_progress.txt";		
		try {
			out = new PrintWriter(openFileOutput(userFile, Context.MODE_PRIVATE));
			out.println(usernames.size());
			for(String s: usernames) {
				out.println(s);
				++count;
			}
			out.close();
			out = null;
			progress = new PrintWriter(openFileOutput(newTitle, Context.MODE_PRIVATE));
			progress.println(profile.getWeight());
			Date today = new Date();
			progress.println(today.getTime());
			progress.close();
			progress = null;
			//Toast.makeText(this, "there are " + count + " users", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this, "failed to add user", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {
			if(out != null)
				out.close();
		}
	}
	public ArrayList<String> getUsernames() {
		loadUsernames();
		return usernames;
	}
	
	public void deleteUser(String username)
	{
		ArrayList<String> users = this.getUsernames();
		for (int i = 0; i < users.size(); i++)
		{
			if (users.get(i).equals(username))
			{
				users.remove(i);
				this.usernames = users;
				this.saveUsernameFile();
				File f = new File("/data/data/ufit.namespace/files/" + username + "Z.txt");
				if(f.delete());
				else
					Log.wtf("Can't Delete File", "Woah we fudged up");
				File fprogress = new File("/data/data/ufit.namespace/files/" + username + "_progress.txt");
				if(fprogress.delete());
				else
					Log.wtf("Can't Delete Progress Tracker", "Woah we fudged up");
			}
			else
			{
				Log.wtf("No delete", "why you no delete?");
			}
		}
	}
	
	public void saveUsernameFile()
	{
		PrintWriter out = null;
		int count = 0;
		try {
			out = new PrintWriter(openFileOutput(userFile, Context.MODE_PRIVATE));
			out.println(usernames.size());
			for(String s: usernames) {
				out.println(s);
				++count;
			}
			out.close();
			out = null;
			//Toast.makeText(this, "there are " + count + " users", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this, "failed to add user", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {
			if(out != null)
				out.close();
		}
	}
}
