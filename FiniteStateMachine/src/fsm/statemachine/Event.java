package fsm.statemachine;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Event other = (Event) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [event=" + event + "]";
	}
	
}
