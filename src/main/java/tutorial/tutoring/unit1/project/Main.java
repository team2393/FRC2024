package tutorial.tutoring.unit1.project;

public class Main
{
    public static void main(String[] args)
    {
        // all your variables up here
        String name = "Kaspar Winston";
        int number1 = 1;
        int number2 = 5;
        boolean bool = true;

        // Hello, my name is <name>
        System.out.println("Hello, my name is " + name);

        // I have two numbers: 1, 5
        System.out.println("I have two numbers: " + number1 + ", " + number2);

        // My first number plus my second number equals 6
        System.out.println("My first number plus my second number equals" + (number1 + number2));

        // subtraction, division, modulus, etc

        if (bool)
        {
            System.out.println("My boolean is true!");
        }
        else
        {
            System.out.println("My boolean is not true!");
        }
    }
}
