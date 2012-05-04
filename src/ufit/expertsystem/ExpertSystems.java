package ufit.expertsystem;

import java.util.ArrayList;
import java.util.Random;

import ufit.DatabaseUtilities.ExerciseInfo;
import ufit.DatabaseUtilities.MyDbAdapter;
import ufit.global.MyApp;
import ufit.profile.Profile;
import android.content.Context;

public class ExpertSystems  {

	public int exerciseNum;
	private Context context;
	public MyDbAdapter eDbAdaptor;
	private Profile profile;
	private ArrayList<ExerciseInfo> otherExercises;
	private ArrayList<ExerciseInfo> focusExercises;
	private int workoutDays;
	private int skill;


public ExpertSystems(Context context, Profile profile){
	this.context=context;
	this.profile = profile;
	skill=profile.getSkill();
	workoutDays = profile.getNumWorkoutDays();
}

private void utilizeDatabase() {
	//DO NOT RUN THIS UNTIL OUR DATABSE IS COMPLETE AND IN THE ASSETS FOLDER
	int workoutType;
	workoutType = profile.getWorkoutType(); //I have assumed the returned int matches the one in the DB

	//#PROFILE
	ArrayList<Integer> workoutGroup;
	workoutGroup = profile.getMuscleGroupList(); //need the ArrayList<Integer> of all selected muscle groups (not including the -1 for cardio)
	/*
	if (workoutType == 2)
		workoutGroup = -1; //default for now.
	else
		workoutGroup = 0;
		*/
	
	//workoutGroup = new ArrayList<Integer>(); //default empty list for testing, comment out when real call is finished
	
	//#PROFILE END
	
	int workoutSkill = profile.getSkill(); //I have assumed the returned int matches the one in the DB
	
	ArrayList<String> equipmentList = new ArrayList<String>();
	equipmentList = profile.getEquipmentList();
	
	//should this be profile.getID() ?
	if(profile.getID() == null) //if saved list of current exercises doesn't exist already
	{
		eDbAdaptor = new MyDbAdapter(this.context, workoutType, workoutGroup, workoutSkill, equipmentList);
	}
	else
	{
		ArrayList<Integer> storedList = profile.getID(); //should this be profile.getID() ?
		eDbAdaptor = new MyDbAdapter(this.context, workoutType,workoutGroup,workoutSkill, equipmentList, storedList);
	}
	
	//all valid focus group exercises should be in focusExercises when this completes
	eDbAdaptor.open();
	focusExercises = eDbAdaptor.fetchFocusExercises();
	// do what you want with them. NOTE: the array INDEXES do NOT match up to the EXERCISE ID stored in the database.
	
	//all valid non-focus group exercises should be in otherExercises when this completes
	otherExercises = eDbAdaptor.fetchOtherExercises();
	//do what you want with them. NOTE: the array INDEXES do NOT match up to the EXERCISE ID stored in the database.
	
	//after the magical expert system returns a list of exercises, assumed to be an ArrayList<Integer> magicExerciseIDs
	
	
	//store the list in the profile too for later use

	
	eDbAdaptor.close();
}

public ArrayList<Integer> fetchPlan(){
	exerciseNum = skill+2;
	ArrayList<Integer> plan=new ArrayList<Integer>();
if(focusExercises==null || focusExercises.size()==0){
	utilizeDatabase();
}
if(profile.getWorkoutType()==1){
	plan = this.generateStrength(workoutDays);
}
else if(profile.getWorkoutType()==2){
	plan= this.generateCardio(workoutDays);
		
}
else if(profile.getWorkoutType()==3){
	plan = this.generateGeneral(workoutDays);
	
}
else{//log error
	
}

System.gc();

return plan;
}

public ArrayList<Integer> generateCardio(int days){
	ArrayList<String> cardio = new ArrayList<String>();  // get preffered types from profile and store in arraylist
	if(profile.getBike())
		cardio.add("Stationary Bike");
	if(profile.getElliptical())
		cardio.add("Elliptical");
	if(profile.getRun()){
		cardio.add("Run");
		cardio.add("Sprints");
	}
	if(profile.getSwim())
		cardio.add("Swimming");
	if(profile.getWalk())
		cardio.add("Walk");
	int size = cardio.size();
	if (size == 0)
	{
		cardio.add("Stationary Bike");
		cardio.add("Elliptical");
		cardio.add("Run");
		cardio.add("Sprints");
		cardio.add("Swimming");
		cardio.add("Walk");
		size = cardio.size();
	}
	
	ArrayList<Integer> cardioPlan= new ArrayList<Integer>();
	if(size>0){ //make sure the list is not empty
	for(int i=0;i<days*4;i++){ //generate month. MASTER FOR LOOP
		ArrayList<Integer>daysList = new ArrayList<Integer>(); //stores one days workouts
		Random rand = new Random();
		int x= rand.nextInt(size);
		String search= cardio.get(x); //gets one random element from preferred type of cardio
		int workoutsPerDay=exerciseNum; 
		for(int j=0;j<focusExercises.size();j++){ //looks through the list of possible exercises to find the preferred random one we generated
			if(focusExercises.get(j).getExercise().equals(search)){
				daysList.add(focusExercises.get(j).getID());//if its succesfull we get here and add it to our daysList
				workoutsPerDay--;//subtracts one of that days workout number cuz we just found a focus exercise
				break;
			}
		}
		for (int p=0; p<workoutsPerDay;p++){ //generates the rest of the days plan
			if(p%3==0 || p%3==1 || otherExercises.size() == 0){ //to add a little variablity in what list we pull from, in favor of focus
				int counter=0; //checks to see if we generated a random number too many times
				boolean done=false;
				while(done==false){ //loops until we find an exercise to place in daysList
				int select= rand.nextInt(focusExercises.size()); //random index to search
				if(!daysList.contains(focusExercises.get(select).getID())){ //if our daysList doesnt already have this exercise we proceed
				daysList.add(focusExercises.get(select).getID()); //add to daysList
				done=true;// exits while loop
					}
				else counter++; // if we were unsuccesful we increment the counter and do it again
				if (counter>10 && otherExercises.size() != 0){ // if we have searched too many times we look at the other list now
					while(done==false){ //we do the same thing as above with the arrayList otherExercises
					select= rand.nextInt(otherExercises.size());
					if(!daysList.contains(otherExercises.get(select).getID())){
					daysList.add(otherExercises.get(select).getID());
					done=true;
						}
					}
				}
				else if (otherExercises.size() == 0)
					counter = 0;
		}
				
}
			else{ //We get here if p mod 3 ==2
				int counter=0;
				boolean done=false;
				while(done==false){
				int select= rand.nextInt(otherExercises.size()); //starts searching through the otherExercises list
				if(!daysList.contains(otherExercises.get(select).getID())){ //everything here is similar to the first if statement
				daysList.add(otherExercises.get(select).getID());
				done=true;
				}
				else counter++;
				if (counter>10){
					while(done==false){
					select= rand.nextInt(focusExercises.size());
					if(!daysList.contains(focusExercises.get(select).getID())){
					daysList.add(focusExercises.get(select).getID());
					done=true;
						}
					}
			}
				}
				
				
			}
			}
		cardioPlan.addAll(daysList);//appends the daysList to our monthly list
		daysList.clear(); //clears the list for another pass through our for loop
		}
		}	
	return cardioPlan; //return the MONTH plan
}
public ArrayList<Integer> generateStrength(int days){
	//ArrayList<Integer> strength = new ArrayList<Integer>();  // get preffered types from profile and store in arraylist
	//strength= profile.getMuscleGroupList();
	ArrayList<Integer> strengthPlan= new ArrayList<Integer>();
	int weekCounter=0;
	Random rand = new Random();
	ArrayList<ExerciseInfo> tempMuscleGroup = new ArrayList<ExerciseInfo>();
	int muscleGroupInt = -1;
	 //make sure the list is not empty
	for(int i=0;i<days*4;i++){ //generate month. MASTER FOR LOOP
		ArrayList<ExerciseInfo>daysList = new ArrayList<ExerciseInfo>(); //stores one days workouts
		if(days ==1){
				if(weekCounter%2==0){
					for(int a= 0; a<exerciseNum;a++){
						muscleGroupInt=a;
						boolean found=false;
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
					boolean done = false;
					boolean inThere = false;
					int counter =0;
					int counter1 =0;
					while(done==false){
					int x = rand.nextInt(tempMuscleGroup.size());
					for(int z=0; z<daysList.size();z++){
						if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
						inThere = true;
					}
					if(!inThere){
						daysList.add(tempMuscleGroup.get(x));
						done=true;
							}
					counter++;
					if (counter>30){
						
						for(int p=0;p<tempMuscleGroup.size();p++){
						
						boolean found1 = false;
						
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
							daysList.add(tempMuscleGroup.get(x));
							found1=true;
							done =true;
							break;
							}
						}
						if (found1) break;
						counter1++;
						if (counter1>30){
							daysList.add(tempMuscleGroup.get(0));
							done = true;
						}
					}
					}
					}
					}
					
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
						}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}

						//int x = rand.nextInt(tempMuscleGroup.size());
						//daysList.add(tempMuscleGroup.get(x));
					}
					tempMuscleGroup.clear();
				}
			}
				else{
					for(int a= 0; a<exerciseNum;a++){
						muscleGroupInt=a+exerciseNum;
						boolean found=false;
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							daysList.add(tempMuscleGroup.get(0));
							break;
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
						}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					tempMuscleGroup.clear();
				}
			
				}
				weekCounter++;	
		}
		else if(days==2){
			boolean found = false;
			if(i%2==0){
			for(int j=0; j<exerciseNum;j++){
				tempMuscleGroup.clear();
				if (j==0) muscleGroupInt =0;
				else if (j==1) muscleGroupInt=1;
				else if (j==2) muscleGroupInt=5;
				else if (j==3) muscleGroupInt=3;
				else if (j==4) muscleGroupInt=4;
				
				for(int k=0; k<focusExercises.size();k++){
					if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
						tempMuscleGroup.add(focusExercises.get(k));
						found=true;
					}
				}
				if(found){
					boolean done = false;
					boolean inThere = false;
					int counter =0;
					int counter1 =0;
					while(done==false){
					int x = rand.nextInt(tempMuscleGroup.size());
					for(int z=0; z<daysList.size();z++){
						if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
						inThere = true;
					}
					if(!inThere){
						daysList.add(tempMuscleGroup.get(x));
						done=true;
							}
					counter++;
if (counter>30){
						
						for(int p=0;p<tempMuscleGroup.size();p++){
						
						boolean found1 = false;
						
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
							daysList.add(tempMuscleGroup.get(x));
							found1=true;
							done =true;
							break;	
							}
						}
						if (found1) break;
						counter1++;
						if (counter1>30){
							daysList.add(tempMuscleGroup.get(0));
							done = true;
						}
					}
					}
					}
				}
				else{
					for(int k=0; k<otherExercises.size();k++){
						if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(otherExercises.get(k));
							found=true;
						}
						}
					if (!found)
					{
						muscleGroupInt = 11;
					
						for(int k=0; k<focusExercises.size();k++){
							if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(focusExercises.get(k));
								found=true;
							}
						}
						if(!found)
						{
							for(int k=0; k<otherExercises.size();k++){
								if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(otherExercises.get(k));
									found=true;
								}
							}
						}
					}
					boolean done = false;
					boolean inThere = false;
					int counter =0;
					int counter1 =0;
					while(done==false){
					int x = rand.nextInt(tempMuscleGroup.size());
					for(int z=0; z<daysList.size();z++){
						if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
						inThere = true;
					}
					if(!inThere){
						daysList.add(tempMuscleGroup.get(x));
						done=true;
							}
					counter++;
if (counter>30){
						
						for(int p=0;p<tempMuscleGroup.size();p++){
						
						boolean found1 = false;
						
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
							daysList.add(tempMuscleGroup.get(x));
							found1=true;
							done =true;
							break;
							}
						}
						if (found1) break;
						counter1++;
						if (counter1>30){
							daysList.add(tempMuscleGroup.get(0));
							done = true;
						}
					}
					}
					}
		}
			
		}
			}
			else{
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					if (j==0) muscleGroupInt =2;
					else if (j==1) muscleGroupInt=6;
					else if (j==2) muscleGroupInt=7;
					else if (j==3) muscleGroupInt=8;
					else if (j==4) muscleGroupInt=9;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
				
			}
	}
		else if(days==3){
			if(i%3==0){
				tempMuscleGroup.clear();
			
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =0;
					else if (j==1) muscleGroupInt=4;
					else if (j==2) muscleGroupInt=5;
					else if (j==3) muscleGroupInt=0;
					else if (j==4) muscleGroupInt=5;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
							}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
				}
			else if(i%3==1){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =1;
					else if (j==1) muscleGroupInt=3;
					else if (j==2) muscleGroupInt=1;
					else if (j==3) muscleGroupInt=3;
					else if (j==4) muscleGroupInt=7;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
				}
			else {
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =2;
					else if (j==1) muscleGroupInt=6;
					else if (j==2) muscleGroupInt=7;
					else if (j==3) muscleGroupInt=8;
					else if (j==4) muscleGroupInt=2;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
			}
		
		}
		else if(days==4){
			if(i%4==0){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =0;
					else if (j==1) muscleGroupInt=0;
					else if (j==2) muscleGroupInt=4;
					else if (j==3) muscleGroupInt=4;
					else if (j==4) muscleGroupInt=0;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}	
			else if(i%4==1){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =1;
					else if (j==1) muscleGroupInt=1;
					else if (j==2) muscleGroupInt=3;
					else if (j==3) muscleGroupInt=3;
					else if (j==4) muscleGroupInt=10;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%4==2){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =5;
					else if (j==1) muscleGroupInt=5;
					else if (j==2) muscleGroupInt=11;
					else if (j==3) muscleGroupInt=11;
					else if (j==4) muscleGroupInt=5;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else {
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =2;
					else if (j==1) muscleGroupInt=6;
					else if (j==2) muscleGroupInt=7;
					else if (j==3) muscleGroupInt=8;
					else if (j==4) muscleGroupInt=9;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			
		}
		else if (days==5){
			if(i%5==0){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =0;
					else if (j==1) muscleGroupInt=0;
					else if (j==2) muscleGroupInt=0;
					else if (j==3) muscleGroupInt=0;
					else if (j==4) muscleGroupInt=0;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%5==1){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =1;
					else if (j==1) muscleGroupInt=1;
					else if (j==2) muscleGroupInt=11;
					else if (j==3) muscleGroupInt=1;
					else if (j==4) muscleGroupInt=11;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%5==2){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =2;
					else if (j==1) muscleGroupInt=6;
					else if (j==2) muscleGroupInt=7;
					else if (j==3) muscleGroupInt=8;
					else if (j==4) muscleGroupInt=9;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%5==3){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =5;
					else if (j==1) muscleGroupInt=5;
					else if (j==2) muscleGroupInt=5;
					else if (j==3) muscleGroupInt=11;
					else if (j==4) muscleGroupInt=11;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%5==4){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =3;
					else if (j==1) muscleGroupInt=3;
					else if (j==2) muscleGroupInt=4;
					else if (j==3) muscleGroupInt=4;
					else if (j==4) muscleGroupInt=3;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			
		}
		
		else if (days == 6){
			if(i%6==0){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =0;
					else if (j==1) muscleGroupInt=0;
					else if (j==2) muscleGroupInt=0;
					else if (j==3) muscleGroupInt=0;
					else if (j==4) muscleGroupInt=0;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%6==1){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =1;
					else if (j==1) muscleGroupInt=1;
					else if (j==2) muscleGroupInt=11;
					else if (j==3) muscleGroupInt=1;
					else if (j==4) muscleGroupInt=11;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%6==2){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =-1;
					else if (j==1) muscleGroupInt=-1;
					else if (j==2) muscleGroupInt=11;
					else if (j==3) muscleGroupInt=-1;
					else if (j==4) muscleGroupInt=11;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%6==3){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =2;
					else if (j==1) muscleGroupInt=6;
					else if (j==2) muscleGroupInt=7;
					else if (j==3) muscleGroupInt=8;
					else if (j==4) muscleGroupInt=9;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%6==4){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =5;
					else if (j==1) muscleGroupInt=5;
					else if (j==2) muscleGroupInt=5;
					else if (j==3) muscleGroupInt=11;
					else if (j==4) muscleGroupInt=11;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else {
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =3;
					else if (j==1) muscleGroupInt=3;
					else if (j==2) muscleGroupInt=4;
					else if (j==3) muscleGroupInt=4;
					else if (j==4) muscleGroupInt=3;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
		}
		else if (days == 7){
			if(i%7==0){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =0;
					else if (j==1) muscleGroupInt=0;
					else if (j==2) muscleGroupInt=0;
					else if (j==3) muscleGroupInt=0;
					else if (j==4) muscleGroupInt=0;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
			}
				
			}
		}
			else if(i%7==1){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =1;
					else if (j==1) muscleGroupInt=1;
					else if (j==2) muscleGroupInt=11;
					else if (j==3) muscleGroupInt=1;
					else if (j==4) muscleGroupInt=11;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
			}
				
			}
		}
			else if(i%7==2){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =-1;
					else if (j==1) muscleGroupInt=-1;
					else if (j==2) muscleGroupInt=11;
					else if (j==3) muscleGroupInt=-1;
					else if (j==4) muscleGroupInt=11;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
			}
				
			}
		}
			else if(i%7==3){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =2;
					else if (j==1) muscleGroupInt=6;
					else if (j==2) muscleGroupInt=7;
					else if (j==3) muscleGroupInt=8;
					else if (j==4) muscleGroupInt=9;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}
						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
			}
				
			}
		}
			else if(i%7==4){
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =5;
					else if (j==1) muscleGroupInt=5;
					else if (j==2) muscleGroupInt=5;
					else if (j==3) muscleGroupInt=11;
					else if (j==4) muscleGroupInt=11;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
			}
				
			}
		}
			else if(i%7==5) {
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =3;
					else if (j==1) muscleGroupInt=3;
					else if (j==2) muscleGroupInt=4;
					else if (j==3) muscleGroupInt=4;
					else if (j==4) muscleGroupInt=3;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
			}
				
			}
		}
			else {
				tempMuscleGroup.clear();
				for(int j=0; j<exerciseNum;j++){
					boolean found = false;
					if (j==0) muscleGroupInt =11;
					else if (j==1) muscleGroupInt=-1;
					else if (j==2) muscleGroupInt=11;
					else if (j==3) muscleGroupInt=-1;
					else if (j==4) muscleGroupInt=-1;
					
					for(int k=0; k<focusExercises.size();k++){
						if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
							tempMuscleGroup.add(focusExercises.get(k));
							found=true;
						}
					}
					if(found){
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
					}
					else{
						for(int k=0; k<otherExercises.size();k++){
							if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
								tempMuscleGroup.add(otherExercises.get(k));
								found=true;
							}
							}
						if (!found)
						{
							muscleGroupInt = 11;
						
							for(int k=0; k<focusExercises.size();k++){
								if(focusExercises.get(k).getMuscle_group()==muscleGroupInt){
									tempMuscleGroup.add(focusExercises.get(k));
									found=true;
								}
							}
							if(!found)
							{
								for(int k=0; k<otherExercises.size();k++){
									if(otherExercises.get(k).getMuscle_group()==muscleGroupInt){
										tempMuscleGroup.add(otherExercises.get(k));
										found=true;
									}
								}
							}
						}
						boolean done = false;
						boolean inThere = false;
						int counter =0;
						int counter1 =0;
						while(done==false){
						int x = rand.nextInt(tempMuscleGroup.size());
						for(int z=0; z<daysList.size();z++){
							if(daysList.get(z).getpID()==tempMuscleGroup.get(x).getpID())
							inThere = true;
						}
						if(!inThere){
							daysList.add(tempMuscleGroup.get(x));
							done=true;
								}
						counter++;
						if (counter>30){
							
							for(int p=0;p<tempMuscleGroup.size();p++){
							
							boolean found1 = false;
							
							for(int z=0; z<daysList.size();z++){
								if(daysList.get(z).getpID()!=tempMuscleGroup.get(p).getpID()){
								daysList.add(tempMuscleGroup.get(x));
								found1=true;
								done =true;
								break;	
								}
							}
							if (found1) break;
							counter1++;
							if (counter1>30){
								daysList.add(tempMuscleGroup.get(0));
								done = true;
							}
						}
						}						}
			}
				
			}
		}
		}
		for(int x =0; x<daysList.size();x++){
		strengthPlan.add(daysList.get(x).getID());
		}
	}
	tempMuscleGroup.clear();
	tempMuscleGroup = null;
	
	
return strengthPlan; //return the MONTH plan
}
public ArrayList<Integer> generateGeneral(int days){
	ArrayList<Integer> global = new ArrayList<Integer>();
	
	ArrayList<Integer> cardio = this.generateCardio(days);
	ArrayList<Integer> weight = this.generateStrength(days);
	
	for (int i = 1; i <= (4 * days * this.exerciseNum); i++)
	{
		if (i % 2 == 0)
		{
			global.add(weight.get(i-1));
		}
		else
		{
			global.add(cardio.get(i-1));
		}
	}
	
	return global;
}

}



