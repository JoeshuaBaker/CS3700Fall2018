import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Unit1Exercise {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Person> people = Arrays.asList(
				new Person("Charles", "Dickens", 60),
				new Person("Lewis", "Carroll", 42),
				new Person("Thomas", "Carlyle", 51),
				new Person("Charlotte", "Bronte", 45),
				new Person("Matthew", "Arnolds", 39));
		
		
		//sort with anonymous instance of comparator interface
		//INTERFACE IMPLEMENTATION
		/*
		Collections.sort(people,  new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		*/
		//LAMBDA IMPLEMENTATION
		Collections.sort(people, (Person p1, Person p2) -> p1.getLastName().compareTo(p2.getLastName()));
		
		//implement condition interface anonymously in-line
		/* INTERFACE IMPLEMENTATION
		printConditionally(people, new Condition() {
			@Override
			public boolean test(Person p) {
				return p.getLastName().startsWith("C");
			}
		});
		*/
		//LAMBDA IMPLEMENTATION
		printConditionally(people, p -> p.getLastName().startsWith("C"));
		printConditionally(people, p -> p.getFirstName().startsWith("C"));
		
	}
	
	private static void printConditionally(List<Person> people, Condition condition) {
		for(Person p : people) {
			if(condition.test(p)) {
				System.out.println(p);
			}
		}
	}

}

@FunctionalInterface
interface Condition {
	boolean test(Person p);
}
