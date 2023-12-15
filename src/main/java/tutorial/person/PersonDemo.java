package tutorial.person;

// Demo of persons with name and age
public class PersonDemo
{
    public static void main(String[] args)
    {
        Person guy = new Person("Freddy", 21);
        Person gal = new Person("Jenny", 17);

        if (guy.hasEyes())
            System.out.println(guy.name + " can see!");

        if (gal.hasEyes())
            System.out.println(gal.name + " can see!");
        
        if (Person.hasEyes())
            System.out.println("Every person has eyes!");

        // System.out.println(guy.name + " is " + guy.getAge() + " years old");
        show(guy);
        show(gal);

        System.out.println("Happy Birthday to " + gal.name + " and " + gal.name + "!");
        gal.celebrateBirthday();
        guy.celebrateBirthday();

        show(guy);
        show(gal);


        Person that_guy = new VainPerson("Jim", 46);
        System.out.println("We happen to know he's 46, but..");
        show(that_guy);
    }

    private static void show(Person person)
    {
        System.out.println(person.name + " is " + person.getAge() + " years old");
    }
}
