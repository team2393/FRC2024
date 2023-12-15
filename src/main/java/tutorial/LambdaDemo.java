package tutorial;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class LambdaDemo
{
    public static void main(String[] args)
    {
        // WPILib uses basic Java data types and classes,
        // and we typically pass the current value of something
        // in, like here when we create a new Pose:
        double x = 1.4;
        double y = 3.7;
        double heading = 45.0;
        Pose2d position = new Pose2d(x, y, Rotation2d.fromDegrees(heading));
        
        // Sometimes, however, the WPILib classes don't need some value "right now".
        // Instead, they need a function or method that they can call later,
        // maybe more than once, as necessary.
        //
        // There are 3 commonly used types for this:
        // Runnable, Consumer<SomeType>, Supplier<SomeType>
        //
        // 1) 'Runnable' is a method without arguments and without returning anything,
        //    it simply 'runs'.
        //    Here we create a Runnable that prints something whenever it's called.
        //    The syntax is
        //                    () -> { What to do; }    
        Runnable example_runnable =  ()  -> {  System.out.println("I'm doing something...");  };

        // This is how a Runnable is invoked:
        System.out.println("We're running the Runnable...");
        example_runnable.run();

        // One example where WPILib uses a 'Runnable' is with the InstantCommand
        InstantCommand example_command = new InstantCommand( () ->
        {
            System.out.println("I'm doing something!");
            System.out.println(".. and I'm done, that's it for an InstantCommand");
        } );
        example_command.initialize();

        // 2) A 'Consumer<SomeType>' is a method that takes one parameter of type 'SomeType'
        //    and does something with it, without returning anything.
        //    Here we create a Consumer for a Pose2d, which prints out details of the pose.
        //    The syntax is
        //                   (that_parameter) -> { What to do; }
        Consumer<Pose2d> example_consumer = (pose) ->
        {
            System.out.println("Current robot position: X = " + pose.getX() +
                               " m, Y = " + pose.getY() +
                               " m, Heading = " + pose.getRotation().getDegrees() + " degrees");
        };

        // This is how a Consumer is invoked:
        System.out.println("We're invoking the consumer with the robot position...");
        example_consumer.accept(position);

        // 3) a 'Supplier<SomeType>' is a method that takes no paramters but returns
        //    a value of SomeType.
        //    Here we create a supplier that returns a Rotation2d.
        //     The syntax is
        //                   ()  ->  { Do whatever; return value_of_that_type; }
        Supplier<Rotation2d> example_supplier = () ->
        {
            // Creating a simulated robot heading of 40 .. 50 degrees
            double degrees = 40.0 + 10.0 * Math.random();
            return Rotation2d.fromDegrees(degrees);
        };

        // This is how a Supplier is invoked:
        System.out.println("We're invoking the supplier...");
        Rotation2d result = example_supplier.get();
        System.out.println("Result: " + result.getDegrees() + " degrees");

        // There are some more syntax variations.
        // For example, assume you have a DriveTrain drive_train with a method
        //   Rotation2d getHeading()
        // and you need a Supplier<Rotation2d>.
        //
        // Instead of using the syntax shown above,
        //   Supplier<Rotation2d> example_supplier = () -> { return drive_train.getHeading() };
        //
        // you can also use the shorter version
        //   Supplier<Rotation2d> example_supplier = () -> drive_train.getHeading();
        //
        // or directly pass the getHeading method which after all is a method that takes
        // no parameters and returns a Rotation2d, exactly what we need here, like so:
        //   Supplier<Rotation2d> example_supplier = drive_train::getHeading;

        // The RamseteCommand and SwerveControllerCommand which follow trajectories
        // use several of these:
        // A Supplier<Pose2d> which they call to ask where the robot is,
        // a Consumer<..> that they call with suggested wheel speeds or swerve module settings. 
    }   
}
