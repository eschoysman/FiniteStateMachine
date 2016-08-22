package db;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import beans.Address;
import beans.Person;

public class FakeRepository {

	public static final List<Person> pers = new CopyOnWriteArrayList<Person>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Person("Marco", "Polo", 34, new Address("via della Seta, 1", "Venezia")));
			add(new Person("Leonardo", "Da Vinci", 63, new Address("piazza Vinci, 5", "Vinci")));
			add(new Person("Cristoforo", "Colombo", 53, new Address("via America, 3", "Genova")));
			add(new Person("Pippo", "Jr", 2, null));
		}
	};
	
	public static boolean add(Person p) {
		return pers.add(p);
	}

}
