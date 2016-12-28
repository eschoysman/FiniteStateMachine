package test;

import fsm.statemachine.Context;

public class MyContext implements Context {

	private String contesto;

	public MyContext(String contesto) {
		super();
		this.contesto = contesto;
	}

	public String getContesto() {
		return contesto;
	}

	public void setContesto(String contesto) {
		this.contesto = contesto;
	}
	
	public String toString() {
		return contesto;
	}
	
}
