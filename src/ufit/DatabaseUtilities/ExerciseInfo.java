package ufit.DatabaseUtilities;

import android.graphics.drawable.Drawable;

public class ExerciseInfo {

	private int ID;
	private int pID;
	private String exercise;
	private int muscle_group;
	private int type;
	private String equipment;
	private int skill_level;
	private String description;
	private String iloc1; //name of the image in the resource folder
	private String iloc2;
	private String iloc3;
	private Drawable image1; //actual drawable associated with the image location
	private Drawable image2;
	private Drawable image3;
	
	public ExerciseInfo(int id, int pid, String Exer, int mGroup, int type, String equip, int skill, String Descrip, String iloc1, String iloc2, String iloc3)
	{
		this.setID(id);
		this.setpID(pid);
		this.setExercise(Exer);
		this.setMuscle_group(mGroup);
		this.setType(type);
		this.setEquipment(equip);
		this.setSkill_level(skill);
		this.setDescription(Descrip);
		this.setIloc1(iloc1);
		this.setIloc2(iloc2);
		this.setIloc3(iloc3);
	}
	
	public ExerciseInfo(int id, int pid, String Exer, int mGroup, int type, String equip, int skill, String Descrip, String iloc1, String iloc2, String iloc3, Drawable i1, Drawable i2, Drawable i3)
	{
		this.setID(id);
		this.setpID(pid);
		this.setExercise(Exer);
		this.setMuscle_group(mGroup);
		this.setType(type);
		this.setEquipment(equip);
		this.setSkill_level(skill);
		this.setDescription(Descrip);
		this.setImage1(i1);
		this.setImage2(i2);
		this.setImage3(i3);
		this.setIloc1(iloc1);
		this.setIloc2(iloc2);
		this.setIloc3(iloc3);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getpID() {
		return pID;
	}

	public void setpID(int pID) {
		this.pID = pID;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public int getMuscle_group() {
		return muscle_group;
	}

	public void setMuscle_group(int muscle_group) {
		this.muscle_group = muscle_group;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public int getSkill_level() {
		return skill_level;
	}

	public void setSkill_level(int skill_level) {
		this.skill_level = skill_level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIloc1() {
		return iloc1;
	}

	public void setIloc1(String iloc1) {
		this.iloc1 = iloc1;
	}

	public String getIloc2() {
		return iloc2;
	}

	public void setIloc2(String iloc2) {
		this.iloc2 = iloc2;
	}

	public String getIloc3() {
		return iloc3;
	}

	public void setIloc3(String iloc3) {
		this.iloc3 = iloc3;
	}

	public Drawable getImage1() {
		return image1;
	}

	public void setImage1(Drawable image1) {
		this.image1 = image1;
	}

	public Drawable getImage2() {
		return image2;
	}

	public void setImage2(Drawable image2) {
		this.image2 = image2;
	}

	public Drawable getImage3() {
		return image3;
	}

	public void setImage3(Drawable image3) {
		this.image3 = image3;
	}


}
