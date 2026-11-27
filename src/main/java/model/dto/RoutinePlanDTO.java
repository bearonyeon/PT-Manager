package model.dto;

import java.util.List;

public class RoutinePlanDTO {
	private String eName = null;   
    private List<String> bNames;
    private Double targetWeight;
    private int targetReps = 0;
    private int targetSets= 0;
    
	public RoutinePlanDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoutinePlanDTO(String exerciseName, List<String> bodyPartNames, Double targetWeight, int targetReps,
			int targetSets) {
		super();
		this.eName = exerciseName;
		this.bNames = bodyPartNames;
		this.targetWeight = targetWeight;
		this.targetReps = targetReps;
		this.targetSets = targetSets;
	}

	public String getEName() {
		return eName;
	}

	public void setEName(String eName) {
		this.eName = eName;
	}

	public List<String> getBNames() {
		return bNames;
	}

	public void setBNames(List<String> bNames) {
		this.bNames = bNames;
	}

	public Double getTargetWeight() {
		return targetWeight;
	}

	public void setTargetWeight(Double targetWeight) {
		this.targetWeight = targetWeight;
	}

	public int getTargetReps() {
		return targetReps;
	}

	public void setTargetReps(int targetReps) {
		this.targetReps = targetReps;
	}

	public int getTargetSets() {
		return targetSets;
	}

	public void setTargetSets(int targetSets) {
		this.targetSets = targetSets;
	}

	@Override
	public String toString() {
		return "RoutinePlanDTO [exerciseName=" + eName + ", bodyPartNames=" + bNames + ", targetWeight="
				+ targetWeight + ", targetReps=" + targetReps + ", targetSets=" + targetSets + "]";
	}
}
