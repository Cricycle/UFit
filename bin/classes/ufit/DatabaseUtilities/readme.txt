		File			Version
		QueryHelper		R3
		MyDbAdapter		R3
		ExerciseInfo		R2
		MyDatabaseHelper	NA


code is not refined very much. A lot of useless/redundant declarations atm.

Some comments, but probably not as many as there should be.

ignore the Activity one lol;

Message me if you have questions or see something that needs to be fixed/changed/added.


R2:
	modified fetch methods to try to conserve some memory. I assume 	the fetchfocus and fetchother methods will only
	be called by the expert system, so they dont really need to 	store the drawables in those exerciseInfos?
	can easily be put back if we later decide they should be there 	anyway.
	
	
R3:
	Modified QueryHelper and the adaptor to work with a ArrayList of 	muscle integers instead of a single integer (needs the call from 	profile that returns the Arraylist of muscle integers IN (0-11) 		I add in the -1 for cardio within the helper)
	
	QueryHelper assumes if the muscle integer list is empty:
		that you want to do all of the muscle groups in a Strength 		plan
		that you want to do only cardio in a Cardio plan
		that you want to do all the muscle groups and cardio in 			the General plan
		
		hopefully...it works the way it does in my head >.<;

												