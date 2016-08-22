package beans;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Person {

	private String nome;
	private String cognome;
	private int age;
	private Address address;
	
}
