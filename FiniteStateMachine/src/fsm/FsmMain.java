package fsm;

import java.util.function.Consumer;

import fsm.statemachine.Event;
import fsm.statemachine.State;
import fsm.statemachine.StateMachine;
import fsm.statemachine.StateMachine.RESULT;
import fsm.statemachine.StateMachine.TRANSITION_ERROR_HANDLING;
import fsm.statemachine.StateMachineFunction;
import fsm.statemachine.exceptions.StateMachineException;
import fsm.statemachine.exceptions.TransitionException;

public class FsmMain {

	public static void main(String[] args) throws StateMachineException {

		StateMachineFunction<MyContext,String> stateEnterAction = (f,e,t,c)->System.out.println("Enter state \""+t.getName()+"\"");
		Consumer<MyContext> stateExecuteAction = c->System.out.println("Execute state action on context: \""+c+"\"");
		StateMachineFunction<MyContext,String> stateExitAction = (f,e,t,c)->System.out.println("Exit state \""+f.getName()+"\"");
		StateMachineFunction<MyContext,String> transitionEnterAction = (f,e,t,c)->System.out.println("\tEnter transition from \""+f.getName()+"\" to \""+t.getName()+"\" on event \""+e.getEvent()+"\"");
		StateMachineFunction<MyContext,String> transitionExecuteAction = (f,e,t,c)->{c.setContesto(c.getContesto()+"ooo"); System.out.println("\tExecute transition from \""+f.getName()+"\" to \""+t.getName()+"\" on event \""+e.getEvent()+"\"");};
		StateMachineFunction<MyContext,String> transitionExitAction = (f,e,t,c)->System.out.println("\tExit transition from \""+f.getName()+"\" to \""+t.getName()+"\" on event \""+e.getEvent()+"\"");
		StateMachineFunction<MyContext,String> transitionExitAction2 = (f,e,t,c)->{throw new TransitionException("prova");};
		
		StateMachine<MyContext,String> sm = new StateMachine<MyContext,String>();
		State<MyContext, String> s0 = sm.addStartState("s0").setActions(stateEnterAction,stateExecuteAction,stateExitAction);
		State<MyContext, String> s1 = sm.addState("s1").setActions(stateEnterAction,stateExecuteAction,stateExitAction);
		State<MyContext, String> s2 = sm.addState("s2").setFinal(true).setActions(stateEnterAction,stateExecuteAction,stateExitAction);
		
		Event<String> vai = sm.addEvent("vai");

		sm.addTransition(s0,vai,s1,transitionEnterAction,transitionExecuteAction,transitionExitAction);
		sm.addTransition(s1,vai,s2,transitionEnterAction,transitionExecuteAction,transitionExitAction2);
		sm.addTransition(s2,vai,s0,transitionEnterAction,transitionExecuteAction,transitionExitAction);

		sm.setTransitionErrorHandler(TRANSITION_ERROR_HANDLING.IGNORE);
		
		MyContext context = new MyContext("Pippo");

		while(!sm.isFinalState() && sm.getTransitionResult()!=RESULT.ERROR) {
			sm.executeState(context);
			System.out.println("Current state: \""+sm.getCurrentState().getName()+"\"");
			sm.fire("vai",context);
		}
		System.out.println("\nCurrent state: \""+sm.getCurrentState().getName()+"\"");
		System.out.println(context);
		
	}

}