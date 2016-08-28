package mdt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString@EqualsAndHashCode
public class State {
	
	private String name;
	private boolean isStart = false;
	private boolean isFinal = false;
	
	public State(String name) {
		this(name,StateType.NORMAL);
		isStart = isFinal = false;
	}
	public State(String name, StateType...types) {
		this.name = name;
		for(StateType type : types) {
			switch (type) {
				case START: isStart = true; break;
				case FINAL: isFinal = true; break;
				case NORMAL:
				default: isStart = isFinal = false; break;
			}
		}
	}
	
}
