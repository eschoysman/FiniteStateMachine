package fsm;

import fsm.exceptions.UnexpectedEventException;
import fsm.exceptions.UnexpectedTransitionException;

public class FSMMain {

	public static void main(String[] args) throws UnexpectedTransitionException, UnexpectedEventException {

		State<String,String> s0 = new State<String,String>() {
			@Override
			public void enter(State<String,String> from, Event<String> event, State<String,String> to, Context<String> context) {
				super.enter(from,event,to,context);
				System.out.println("Entro in "+getName());
			}
			@Override
			public void exit(State<String,String> from, Event<String> event, State<String,String> to, Context<String> context) {
				super.exit(from,event,to,context);
				System.out.println("Esco da "+getName());
			}
		};
//		s0.setEnter((f,e,t,c)->System.out.println("Entro in "+getName()));
		State<String,String> s1 = new State<String,String>() {
			@Override
			public void enter(State<String,String> from, Event<String> event, State<String,String> to, Context<String> context) {
				super.enter(from,event,to,context);
				System.out.println("Entro in "+getName());
			}
			@Override
			public void exit(State<String,String> from, Event<String> event, State<String,String> to, Context<String> context) {
				super.exit(from,event,to,context);
				System.out.println("Esco da "+getName());
			}
		};
		State<String,String> s2 = new State<String,String>() {
			@Override
			public void enter(State<String,String> from, Event<String> event, State<String,String> to, Context<String> context) {
				super.enter(from,event,to,context);
				System.out.println("Entro in "+getName());
			}
			@Override
			public void exit(State<String,String> from, Event<String> event, State<String,String> to, Context<String> context) {
				super.exit(from,event,to,context);
				System.out.println("Esco da "+getName());
			}
		};
		StateMachine<String,String> sm = new StateMachine<String,String>(s0);
		
		Event<String> event1 = new Event<String>("event1");
		Event<String> event2 = new Event<String>("event2");
		Event<String> event3 = new Event<String>("event3");
		Event<String> vai = new Event<String>("vai");
		
		Context<String> context = new Context<String>("Pippo");
		
		sm.transit(s0,vai,s1);
		sm.transit(s1,vai,s2);
		sm.transit(s2,vai,s0);
		
		while(true)
			sm.fire(vai,context);
//		sm.fire(event2,context);
		
	}

}
