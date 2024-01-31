package tutorial.wednesdaytutoring.lesson2;

public class Main
{
    public static void main(String[] args)
    {
        int myNumber = 9;
        System.out.println(myNumber + 5);

        // myNumber = myNumber + 5;
        myNumber += 5;
        System.out.println(myNumber);

        // + addition
        // - subtraction
        // * multiplication
        // / division
        // % modulus

        int mySecondNumber = 6;

        System.out.println(myNumber % mySecondNumber);

        String myFirstName = "Kaspar";
        String myLastName = "Winston";

        System.out.println("Hello " + myFirstName);
    }
}
