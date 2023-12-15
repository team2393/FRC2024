package tutorial.person;

// A vain person with name and age
public class VainPerson extends Person
{
    // 'Constructor', method with same name as the class,
    // called via "new VainPerson(...)"
    public VainPerson(String the_name, int current_age)
    {
        // Call the constructor of the 'super' class, Person
        super(the_name, current_age);
    }

    // Replace getAge with our own version
    public int getAge()
    {   // Call the Person's getAge(), then return a lower age
        return super.getAge()-5;
    }
}
