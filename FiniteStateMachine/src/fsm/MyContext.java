package fsm;

import java.io.Serializable;

public class MyContext implements Serializable {

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
