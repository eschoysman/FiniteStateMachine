package fsm;

public class Event<E> {
	private static int generalId = 0;
	private int id;
	private E event;

	public Event(E event) {
		setId(generalId++);
		this.setEvent(event);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public E getEvent() {
		return event;
	}
	public void setEvent(E event) {
		this.event = event;
	}

}
