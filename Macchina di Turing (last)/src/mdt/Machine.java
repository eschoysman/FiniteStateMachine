package mdt;

import static mdt.Esito.ACCEPT;
import static mdt.Esito.REJECT;
import static mdt.Esito.UNKNOWN;
import static mdt.StateType.FINAL;
import static mdt.StateType.START;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import lombok.Getter;
import lombok.Setter;

public class Machine {
	public static final char BLANK = '_';
	private State startState;
	private HashMap<String,State> statesMap;
	private LinkedHashMap<Tuple<State,Character>,Triple<Character,Move,State>> rules;	// LRU map?
	private @Getter Esito esito;
	private @Getter int steps;
	private @Getter@Setter String nome;
	public Machine() {
		this("Macchina di Turing");
	}
	public Machine(String nome) {
		reset();
		this.nome = nome;
	}
	public void reset() {
		this.rules = new LinkedHashMap<Tuple<State,Character>,Triple<Character,Move,State>>(16,0.75f,true);
		this.statesMap = new HashMap<String,State>();
		statesMap.put("start",startState=new State("start",START));
		statesMap.put("end",new State("end",FINAL));
		statesMap.put("end-accept",new State("end-accept",FINAL));
		statesMap.put("end-reject",new State("end-reject",FINAL));
		esito = UNKNOWN;
	}
	public void setStartState(String startState) {
		this.startState = statesMap.computeIfAbsent(startState,s->new State(s,START));
	}
	public void setFinalState(String... finalState) {
		for(String s : finalState) {
			statesMap.computeIfPresent(s,(fs,st)->{st.setFinal(true); return st;});
			statesMap.computeIfAbsent(s,fs->new State(fs,FINAL));
		}
	}
	public boolean addRule(String rule) {
		if(rule.startsWith(";") || rule.length()<9) return false;	// stringa minima accettata: "a 0 1 R b"
		rule = rule.trim();
		rule = rule.replaceAll(" [\\s]+"," ");	// tolgo gli spazi inutili
		String[] elems = rule.split(" ");
		if(elems.length<5) return false;
		statesMap.computeIfAbsent(elems[0],State::new);
		State from = statesMap.get(elems[0]);
		statesMap.computeIfAbsent(elems[4],State::new);
		State to = statesMap.get(elems[4]);
		Tuple<State,Character> left = new Tuple<State,Character>(from,elems[1].charAt(0));
		Triple<Character,Move,State> right = new Triple<>(elems[2].charAt(0),Move.valueOf(elems[3].substring(0,1)),to);
		rules.put(left,right);
		return true;
	}
	public String run(String input) {
		Tape tape = new Tape(input);
		State currentState = startState;
		Tuple<State,Character> currentTuple;
		Triple<Character,Move,State> action;
		System.out.println("State: "+currentState.getName());
		System.out.println(tape+"\n");
		while(!currentState.isFinal()) {
			currentTuple = new Tuple<State,Character>(currentState,tape.read());
			action = rules.get(currentTuple);
			if(action==null) {
				currentTuple = new Tuple<State,Character>(currentState,'*');
				action = rules.get(currentTuple);
				if(action==null) {
    				esito = REJECT;
    				System.err.println("Non esiste nessuna regola nello stato \""+currentState.getName()+"\" per il carattere '"+tape.read()+"'");
    				return tape.toString();
				}
			}
			if(action.a=='*') {
				action = new Triple<Character,Move,State>(tape.read(),action.b,action.c);
			}
			// prendo le operazioni e le faccio
			tape.write(action.a);
			action.b.move(tape);
			currentState = action.c;
			System.out.println("State: "+currentState.getName());
			System.out.println(tape+"\n");
			steps++;
		}
		if(currentState.getName().contains("reject")) {
			esito = REJECT;
		} else {
			esito = ACCEPT;
		}
		return tape.getValue();
	}
	public String printMachine() {
		StringBuilder sb = new StringBuilder("Nome: "+nome);
		sb.append("\nStart: "+startState.getName());
		sb.append("\nEnds:");
		statesMap.forEach((k,s)->{if(s.isFinal()) sb.append(" "+s.getName());});
		rules.forEach((k,v)->sb.append("\n"+k.a.getName()+" "+k.b+" "+v.a+" "+v.b+" "+v.c.getName()));
		return sb.toString();
	}
	public boolean save(String path) {
		try {
			@SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(path+".turing");
			fos.write(printMachine().getBytes());
			return true;
		}
		catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static Machine load(String path) {
		Machine out = new Machine();
		try {
			Iterator<String> iter = Files.readAllLines(Paths.get(path)).iterator();
			out.setNome(iter.next().replaceAll("Nome: ",""));
			out.setStartState(iter.next().replaceFirst("Start: ",""));
			out.setFinalState(iter.next().replaceFirst("Ends: ","").split(" "));
			iter.forEachRemaining(out::addRule);
			return out;
		}
		catch(IOException e) {
			e.printStackTrace();
			return out;
		}
	}
	
}
