package mdt;

import java.util.function.Consumer;

public enum Move {
	L(t->t.moveLeft()),
	S(t->{}),
	R(t->t.moveRight());
	
	private Consumer<Tape> move;

	private Move(Consumer<Tape> move) {
		this.move = move;
	}

	public void move(Tape tape) {
		move.accept(tape);
	}
}
