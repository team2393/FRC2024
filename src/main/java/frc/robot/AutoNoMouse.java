// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// This is a file to make sure that if I accidentally commit to main somehow, I dont wipe autonomouse. If I do push to the right branch I'll remove this and add it to AutoNoMouse.
package frc.robot;

import static frc.tools.AutoTools.createTrajectory;
import static frc.tools.AutoTools.followPathWeaver;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.swervelib.ResetPositionCommand;
import frc.swervelib.SelectAbsoluteTrajectoryCommand;
import frc.swervelib.SelectRelativeTrajectoryCommand;
import frc.swervelib.RotateToHeadingCommand;
import frc.swervelib.SwerveDrivetrain;
import frc.swervelib.SwerveToPositionCommand;
import frc.swervelib.VariableWaitCommand;
import frc.tools.SequenceWithStart;

/** Auto-no-mouse routines */
public class AutoNoMouse
{
  /** Create all our auto-no-mouse commands */
  public static List<Command> createAutoCommands(SwerveDrivetrain drivetrain)
  {
    // List of all autonomouse commands
    final List<Command> autos = new ArrayList<>();

    // Each auto is created within a { .. block .. } so we get local variables for 'path' and the like 
    {
      // Blue Bottom: Move out, Shoot, Pickup, Shoot
      SequentialCommandGroup auto = new SequenceWithStart("BBMSPS", 0.51, 2.38, 180);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectAbsoluteTrajectoryCommand(drivetrain, 0.51, 2.38, 180));
      // Move out (back), then over to front of target
      Trajectory path = createTrajectory(true, 0.51, 2.38,   0,
                                                                1.70, 3.50,  90,
                                                                1.44, 5.54, 120);
      auto.addCommands(drivetrain.createTrajectoryCommand(path, 180));
      auto.addCommands(new PrintCommand("Shoot!"));
      auto.addCommands(new WaitCommand(2));
      // Pickup another ring form right behind
      auto.addCommands(new PrintCommand("Open intake"));
      Trajectory path2 = createTrajectory(true, 1.44, 5.54, 0,
                                                2.60, 5.54, 0);
      auto.addCommands(drivetrain.createTrajectoryCommand(path2, 180));
      auto.addCommands(new PrintCommand("Close intake"));
      // Move forward to target and shoot
      Trajectory path3 = createTrajectory(true, 2.60, 5.54, 180,
                                                1.44, 5.54, 180);
      auto.addCommands(drivetrain.createTrajectoryCommand(path3, 180));
      auto.addCommands(new PrintCommand("Shoot!"));
      auto.addCommands(new WaitCommand(2));
      auto.addCommands(new PrintCommand("Done."));
      autos.add(auto);
    }

    // To 'mirror' a setup from the left/blue to the right/red side,
    // X turns into WIDTH_OF_FIELD - X, where WIDTH_OF_FIELD is about 16.52 meters
    // Y stays unchanged
    // Heading changes into 180 - heading
    //
    // Example: A pose      1.44, 5.54, 120 changes into
    //                16.52-1.44, 5.54, 180-120 = 
    //                     15.08, 5.54,  60
    {
      // Red Bottom: Move out, Shoot, Pickup, Shoot
      SequentialCommandGroup auto = new SequenceWithStart("RBMSPS", 16.01, 2.38, 0);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectAbsoluteTrajectoryCommand(drivetrain, 16.01, 2.38, 0));
      // Move out (back), then over to front of target
      Trajectory path = createTrajectory(true, 16.01, 2.38, 180,
                                               14.82, 3.50,  90,
                                               15.08, 5.54,  60);
      auto.addCommands(drivetrain.createTrajectoryCommand(path, 0));
      auto.addCommands(new PrintCommand("Shoot!"));
      auto.addCommands(new WaitCommand(2));
      // Pickup another ring form right behind
      auto.addCommands(new PrintCommand("Open intake"));
      Trajectory path2 = createTrajectory(true, 15.08, 5.54, 180,
                                                13.92, 5.54, 180);
      auto.addCommands(drivetrain.createTrajectoryCommand(path2, 0));
      auto.addCommands(new PrintCommand("Close intake"));
      // Move forward to target and shoot
      Trajectory path3 = createTrajectory(true, 13.92, 5.54, 0,
                                                15.08, 5.54, 0);
      auto.addCommands(drivetrain.createTrajectoryCommand(path3, 0));
      auto.addCommands(new PrintCommand("Shoot!"));
      auto.addCommands(new WaitCommand(2));
      auto.addCommands(new PrintCommand("Done."));
      autos.add(auto);
    }

    { // Drive forward 2.5 m using a (simple) trajectory
      // Can be used from Blue or Red, Top or Bottom
      SequentialCommandGroup auto = new SequentialCommandGroup();
      auto.setName("Forward 2.5m");
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectRelativeTrajectoryCommand(drivetrain));
      Trajectory path = createTrajectory(true, 0, 0, 0,
                                               2.50, 0, 0);
      auto.addCommands(drivetrain.createTrajectoryCommand(path, 0));
      autos.add(auto);
    }

    { // Drive forward 1.5 m using a (simple) trajectory
      // Can be used from Blue or Red, Top or Bottom
      SequentialCommandGroup auto = new SequentialCommandGroup();
      auto.setName("Forward 1.5m");
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectRelativeTrajectoryCommand(drivetrain));
      Trajectory path = createTrajectory(true, 0, 0, 0,
                                               1.50, 0, 0);
      auto.addCommands(drivetrain.createTrajectoryCommand(path, 0));
      autos.add(auto);
    }

    { // Drive a 1.5 square using SwerveToPositionCommand & RotateToHeadingCommand
      SequentialCommandGroup auto = new SequentialCommandGroup();
      auto.setName("1.5m Square");
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new ResetPositionCommand(drivetrain));

      auto.addCommands(new SwerveToPositionCommand(drivetrain, 1.5, 0.0));
      auto.addCommands(new RotateToHeadingCommand(drivetrain, 90));

      auto.addCommands(new SwerveToPositionCommand(drivetrain, 1.5, 1.5));
      auto.addCommands(new RotateToHeadingCommand(drivetrain, 180));

      auto.addCommands(new SwerveToPositionCommand(drivetrain, 0.0, 1.5));
      auto.addCommands(new RotateToHeadingCommand(drivetrain, -90));

      auto.addCommands(new SwerveToPositionCommand(drivetrain, 0.0, 0.0));
      auto.addCommands(new RotateToHeadingCommand(drivetrain, 0));

      autos.add(auto);
    }

    {
      autos.add(new SequenceWithStart("Circle", 1.91, 2.245, 0,
                                      new VariableWaitCommand(),
                                      new SelectAbsoluteTrajectoryCommand(drivetrain, 1.91, 2.245, 0),
                                      followPathWeaver(drivetrain, "Circle", 0.0)));
    }

    {
      // Blue Amplifier: Move Pickup Shoot
      SequentialCommandGroup auto = new SequenceWithStart("BAMPS", 1.5, 5.5, 180);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectAbsoluteTrajectoryCommand(drivetrain, 1.5, 5.5, 180));
      auto.addCommands(new PrintCommand("Open intake!"));


      // Move to top ring and get it.
      Trajectory path = createTrajectory(true, 1.92, 5.503, 0,
                                          2.5,  7, 0
                                                              );
      auto.addCommands(drivetrain.createTrajectoryCommand(path, 180));

      auto.addCommands(new PrintCommand("Close intake!"));


      // Move back to amp, shoot.
     Trajectory path2 = createTrajectory(true,  1.92, 5.503, 0,
                                                              1.4, 5.2, 0);

      auto.addCommands(drivetrain.createTrajectoryCommand(path2, 180));

      auto.addCommands(new PrintCommand("Shoot"));

      
       // Move from amp, pickup, shoot
      auto.addCommands(new PrintCommand("Open Intake"));

      Trajectory path3 = createTrajectory(true, 1.44, 5.54, 0,
                                                2.60, 5.54, 0);

      auto.addCommands(drivetrain.createTrajectoryCommand(path3, 180));

      auto.addCommands(new PrintCommand("Close Intake"));

      Trajectory path4 = createTrajectory(true, 2.5, 5.5, 180,
                                                                1.92, 5.5, 180,
                                                                  1.5, 5.5, 180);

      auto.addCommands(drivetrain.createTrajectoryCommand(path4, 180));

      auto.addCommands(new PrintCommand("Shoot"));
      
       // go to middle field ring, pickup
      Trajectory path5 = createTrajectory(true, 1.8, 5.5, 0,
                                                                1.89, 4.10, 0,
                                                                2.5, 4.10, 0);
    auto.addCommands(drivetrain.createTrajectoryCommand(path5, 180));

      auto.addCommands(new PrintCommand("Close Intake"));

      Trajectory path6 = createTrajectory(true, 1.5, 4.10, 0,
                                                                1.5, 5.5, 180);
      auto.addCommands(drivetrain.createTrajectoryCommand(path6, 180));

      auto.addCommands(new PrintCommand("Shoot!"));
      autos.add(auto);
    }

    return autos;
  }
}
