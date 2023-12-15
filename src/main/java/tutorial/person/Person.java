package tutorial.person;

// A person with name and age
public class Person
{
    // Anybody can get the 'name', but it's final, can't change it
    public final String name;
    
    // Age is 'private' to prevent outsiders from messing with it
    private int age;

    // 'Constructor', method with same name as the class,
    // called via "new Person(...)"
    public Person(String the_name, int current_age)
    {
        name = the_name;
        age = current_age;
    }

    // static because every person has eyes
    public static boolean hasEyes()
    {
        return true;
    }

    // 'getter' for reading the age
    // (but we can't directly 'set' the age)
    public int getAge()
    {
        return age;
    }

    // Celebrating a birthday makes you one year older
    public void celebrateBirthday()
    {
        age = age + 1;
    }

    // There's no way to get younger
}
