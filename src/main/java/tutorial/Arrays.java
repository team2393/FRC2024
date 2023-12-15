package tutorial;

import java.util.ArrayList;
import java.util.List;

import tutorial.person.Person;

public class Arrays
{
    public static void main(String[] args)
    {
        Person one_person = new Person("Fred", 21);
        System.out.println(one_person.name);
        
        // Arrays keep more than one thing of a type
        Person[] the_smiths = new Person[4];
        // Creating a new Whatever[count] array creates just the array,
        // need to then set the [elements]:
        the_smiths[0] = new Person("Dad", 47);
        the_smiths[1] = new Person("Mum", 48);
        the_smiths[2] = new Person("Daughter", 17);
        the_smiths[3] = new Person("Son", 14);

        // May also combine creating the array and setting the elements:
        Person[] the_millers = new Person[]
        {
            new Person("Grandpa", 78),
            new Person("Grandma", 75),
            new Person("Husband", 52),
            new Person("Wife", 50),
            new Person("Son", 21)
        };
        
        // Access specfic element
        System.out.println(the_millers[1].name);

        // Loop over all elements by index
        for (int i=0; i<the_smiths.length; ++i)
            System.out.println("Element " + i + " is " + the_smiths[i].name);

        // If index doesn't matter, this is shorter
        for (Person person : the_millers)
            System.out.println(person.name);
        
        // Note that arrays have a fixed size.
        // "the_smiths[4]" does not exist.
        // If you don't know the size ahead of time,
        // or it might change later on,
        // if you want to delete entries from the array etc,
        // an "ArrayList" is a helper type that uses an array internally,
        // but will automatically re-create that array when we add more elements
        List<Person> people = new ArrayList<>();
        people.add(new Person("Grandpa", 78));
        people.add(new Person("Grandma", 75));
        people.add(new Person("Daughter", 17));
        people.remove(1);
        System.out.println("There are " + people.size() + " people");
        for (Person person : people)
            System.out.println(person.name);
    }
}
