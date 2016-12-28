package test;

import java.util.function.Consumer;

import fsm.statemachine.IState;
import fsm.statemachine.StateMachine2;
import fsm.statemachine.StateMachineFunction;
import fsm.statemachine.exceptions.StateMachineException;
import fsm.statemachine.exceptions.TransitionException;
import fsm.statemachine.impl.RESULT;
import fsm.statemachine.impl.State;
import fsm.statemachine.impl.TRANSITION_ERROR_HANDLING;
import fsm.statemachine.impl.Transition;

public class FsmMain {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws StateMachineException {

		StateMachineFunction<MyContext,MyEvent> stateEnterAction = (f,e,t,c)->System.out.println("Enter state \""+t.getName()+"\"");
		Consumer<MyContext> stateExecuteAction = c->System.out.println("Execute state action on context: \""+c+"\"");
		StateMachineFunction<MyContext,MyEvent> stateExitAction = (f,e,t,c)->System.out.println("Exit state \""+f.getName()+"\"");
		StateMachineFunction<MyContext,MyEvent> transitionEnterAction = (f,e,t,c)->System.out.println("\tEnter transition from \""+f.getName()+"\" to \""+t.getName()+"\" on event \""+e.getEvent()+"\"");
		StateMachineFunction<MyContext,MyEvent> transitionExecuteAction = (f,e,t,c)->{c.setContesto(c.getContesto()+"ooo"); System.out.println("\tExecute transition from \""+f.getName()+"\" to \""+t.getName()+"\" on event \""+e.getEvent()+"\"");};
		StateMachineFunction<MyContext,MyEvent> transitionExitAction = (f,e,t,c)->System.out.println("\tExit transition from \""+f.getName()+"\" to \""+t.getName()+"\" on event \""+e.getEvent()+"\"");
		StateMachineFunction<MyContext,MyEvent> transitionExitAction2 = (f,e,t,c)->{throw new TransitionException("prova");};
		
		StateMachine2<MyContext,MyEvent> sm = new StateMachine2<MyContext,MyEvent>();
		IState<MyContext, MyEvent> s0 = sm.addStartState(new State("s0").setActions(stateEnterAction,stateExecuteAction,stateExitAction));
		IState<MyContext, MyEvent> s1 = sm.addStartState(new State("s1").setActions(stateEnterAction,stateExecuteAction,stateExitAction));
		IState<MyContext, MyEvent> s2 = sm.addStartState(new State("s2").setActions(stateEnterAction,stateExecuteAction,stateExitAction));
		
		sm.addEvents(MyEvent.values());

		sm.addTransition(new Transition(s0,MyEvent.VAI,s1,transitionEnterAction,transitionExecuteAction,transitionExitAction,null));
		sm.addTransition(new Transition(s1,MyEvent.VAI,s2).setActions(transitionEnterAction,transitionExecuteAction,transitionExitAction2));
		sm.addTransition(new Transition(s2,MyEvent.VAI,s0,transitionEnterAction,transitionExecuteAction,transitionExitAction,null));

		sm.setTransitionErrorHandler(TRANSITION_ERROR_HANDLING.ROLLBACK);
		
		MyContext context = new MyContext("Pippo");

		while(!sm.isFinalState() && sm.getTransitionResult()!=RESULT.ERROR) {
			sm.executeState(context);
			System.out.println("Current state: \""+sm.getCurrentState().getName()+"\"");
			sm.fire(MyEvent.VAI,context);
			System.out.println("Context: "+context);
		}
		System.out.println("\nCurrent state: \""+sm.getCurrentState().getName()+"\"");
		System.out.println("Context: "+context);
		
	}

}
