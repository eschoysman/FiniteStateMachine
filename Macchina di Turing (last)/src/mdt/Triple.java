package mdt;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString@EqualsAndHashCode@AllArgsConstructor
public class Triple<A,B,C> {
	
	public final A a;
	public final B b;
	public final C c;
	
}
