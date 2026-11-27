package model.dto;

import java.util.List;

public class RoutineItemDTO {
	private String eName = null;   
    private List<String> bNames;
    
	public RoutineItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RoutineItemDTO(String eName) {
		super();
		this.eName = eName;
	}
	public RoutineItemDTO(String eName, List<String> bNames) {
		super();
		this.eName = eName;
		this.bNames = bNames;
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
	@Override
	public String toString() {
		return "RoutineItemDTO [eName=" + eName + ", bNames=" + bNames + "]";
	}
}
