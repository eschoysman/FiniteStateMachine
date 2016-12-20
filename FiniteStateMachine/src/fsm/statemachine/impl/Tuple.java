package fsm.statemachine.impl;

import fsm.statemachine.Event;
import fsm.statemachine.IState;
import fsm.statemachine.Context;

public final class Tuple<C extends Context, E> {
	private IState<C, E> state;
	private Event<E> event;

	public Tuple(IState<C, E> state, Event<E> event) {
		this.state = state;
		this.event = event;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		@SuppressWarnings("unchecked")
		Tuple<C, E> other = (Tuple<C, E>) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

}
