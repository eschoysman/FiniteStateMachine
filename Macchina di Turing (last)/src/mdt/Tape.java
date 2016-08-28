package mdt;

import static mdt.Machine.BLANK;

import java.util.LinkedList;

public class Tape {
	private LinkedList<Character> tape;
	private int current;
	public Tape(String input) {
		this.current = 0;
		tape = new LinkedList<>();
		for(int i=0; i<input.length();i++) {
			char c = input.charAt(i);
			if(c==' ') {
				tape.add(BLANK);
			}
			else {
				tape.add(c);
			}
		}

	}
	public void moveLeft() {
		if(current==0) {
			tape.add(0,BLANK);
			return;
		}
		if(current==tape.size()-1) {
			if(tape.get(current)==BLANK) {
				tape.removeLast();
			}
		}
		current--;
	}
	public void moveRight() {
		if(current==tape.size()-1) {
			tape.add(BLANK);
		}
		if(current==0) {
			if(tape.get(current)==BLANK) {
				tape.removeFirst();
				current--;
			}
		}
		current++;
	}
	public String getLeft() {
		return getLeft(Integer.MAX_VALUE);
	}
	public String getLeft(int n) {
		StringBuilder sb = new StringBuilder(BLANK);
		for(int i=Math.max(0,current-n); i<current;i++) {
			sb.append(tape.get(i));
		}
		return sb.toString();
	}
	public Character read() {
		return tape.get(current);
	}
	public String getRight() {
		return getRight(Integer.MAX_VALUE);
	}
	public String getRight(int n) {
		StringBuilder sb = new StringBuilder(BLANK);
		for(int i=current; i<Math.min(current+n,tape.size());i++) {
			sb.append(tape.get(i));
		}
		return sb.toString();
	}
	public void write(char c) {
		tape.set(current,c);
	}
	public String getValue() {
		StringBuilder sb = new StringBuilder();
		while(!read().equals(BLANK)) {
			sb.append(read());
			moveRight();
		}
		return sb.toString();
	}
	public String toString() {
		StringBuilder sb = new StringBuilder(BLANK);
		for(int i=0; i<tape.size();i++) {
			if(i==current) {
				sb.append("|"+tape.get(i)+"|");
			} else {
				sb.append(tape.get(i));
			}
		}
		return sb.toString();
	}
	// l'idea è quella di stampare sempre una stringa lunga 2n+1 caratteri, non necessariamente con la testina in mezzo
	public String toString(int n) {
		if(tape.size()<=2*n+1) {
			return toString();
		}
		return getLeft(n)+read()+getRight(n);
	}
}
