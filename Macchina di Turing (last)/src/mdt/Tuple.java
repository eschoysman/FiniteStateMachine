package mdt;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString@EqualsAndHashCode@AllArgsConstructor
public class Tuple<A,B> {
	
	public final A a;
	public final B b;
	
}
