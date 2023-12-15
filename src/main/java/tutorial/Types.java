package tutorial;

@SuppressWarnings("all")
public class Types
{
    public static void main(String[] args)
    {
        // Computers can store different types of data.
        // The following are offered in Java,
        // and under slightly different names also in C, C++, LabView, Python, ...
        
        // The simplest data type is just a single bit
        // that's either "on" or "off", 0 or 1.
        // The type is called boolean in honor of George Bool.
        boolean learning_java = true;
        
        // They can store the result of "Boolean Expressions",
        // i.e. logical expressions
        boolean is_ten_a_positive_number =  10 > 0;
        
        // They're typically used in "if" statements.
        if (learning_java)
            System.out.println("I'm learning Java!");

        if (is_ten_a_positive_number)
            System.out.println("10 appears to be larger than 0, just as I expected.");
        
        // You can use "Boolean Expressions" without assigning them to a variable.
        // Note that == is used to check if something is the same as something else,
        // while a single = is used to assign a value to a variable.
        if (1 + 1  == 2)
            System.out.println("My computer can compute 1 + 1, I'm so proud!");
        else
            System.out.println("Houston, we have a problem...");
        
        
        // The data type used most often is integer,
        // which holds whole number like 0, 2, 3, 3343.
        // Also negative numbers like -23.
        // Since it's used that often, it's just called "int"
        int counter = 0;
        System.out.println("I've seen " + counter + " unicorns");
        // .. assume you spot a unicorn ..
        counter = counter + 1;
        System.out.println("I've seen " + counter + " unicorn");
        
        // Integer means whole numbers:
        System.out.println("3 divided by 2 is " + (3 / 2));
        System.out.println(".. with a remainder of " + (3 % 2));

        // Integers can be quite large, but there is a maximum
        // value. It's around 2 billion.
        // If you try to add 1 to that largest number,
        // the value wraps around to the largest negative integer value.
        int next = counter + 1;
        while (counter < next)
        {
            ++counter;
            ++next;
        }
        System.out.println("The largest int is " + counter);
        System.out.println("If I try to get the next number, the result is " + next);
        
        // There are data types byte, short, long which are similar
        // to int but for smaller respectively larger value ranges.
        // We'll get to them once we actually need them.
        
        
        // For math operations, you typically need a floating point variable
        // that can hold not just 1 or 2 but also 1.5.
        // When typing float numbers, you add "f" to the end.
        System.out.println("3.0f divided by 2.0f is " + (3.0f / 2.0f));
        
        // There is a "float" data type, because that's what was used to store
        // floating point numbers way back, but it's not very accurate.
        float f1 = 99999.8f;
        float f2 = 99999.65f;
        // This should compute to 199999.45, but will print .44
        System.out.println(f1 + f2);
        if (f1 + f2  ==  199999.45f)
            System.out.println("What are you talking about, looks good to me?!");
        else
            System.out.println("Houston, we really do have a problem...");
        
        // The double type is twice as accurate as float,
        // and that's what everybody uses.
        // To type a double number, just include a "." dot.
        System.out.println("3.0 divided by 2.0 is " + (3.0 / 2.0));
        
        // Compared to float, double gives more accurate results.
        double a = 99999.8;
        double b = 99999.65;
        System.out.println(a + b);
        if (a + b  ==  199999.45)
            System.out.println("I like double better than float");
        
        // The char data type holds a single character.
        // To provide a single character, you put it in single quotes:
        char c = 'A';
        System.out.println("The first letter of the alphabet is " + c);
        
        // In the real world, you typically don't need a single character
        // but a whole string of chars, which are held by the type String
        // which we already used in the first few programs:
        String name = "Fred";
        System.out.println("Nice to meet you, " + name);
        
        // Note that boolean, int, byte, short, long, float, double, char
        // all have lower case names, while String starts with an Upper case.
        // boolean, int, .. are "primitive" data types. They tend to fit into
        // "register" of the computer, and can often be handled in just one
        // basic machine code instruction.
        // String is a "class". Each String object actually consists of many chars,
        // plus an int for the string length. A String has several methods:
        System.out.println("Length of the name: " + name.length());
        System.out.println("First character of the name: " + name.charAt(0));
        System.out.println("Name in all UPPERCASE: " + name.toUpperCase());
        
        // You can use "+" to add two Strings, but that's the exception.
        // You can generally not add objects with "+",
        // and it's very important to remember that you cannot compare objects with "=="
        // since that would only compare if it's the exact same object.
        // If you want to check that two objects are equal in value,
        // you use their equals() method.
        
        String other_name = "ed";
        other_name = "Fr" + other_name;
        System.out.println(other_name);
        
        if (name == other_name)
            System.out.println("Fred == Fred");
        else
            System.out.println("name is not == other_name, even though both contain the value Fred");
        
        if (name.equals(other_name))
            System.out.println("name is equal to other_name because both contain the value Fred");
    }
}
