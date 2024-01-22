// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervebot;

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
      SequentialCommandGroup auto = new SequenceWithStart("BBMSPS", 1.51, 2.38, 180);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectAbsoluteTrajectoryCommand(drivetrain, 1.51, 2.38, 180));
      // Move out (back), then over to front of target
      Trajectory path = createTrajectory(true, 1.51, 2.38,   0,
                                               3.50, 2.38,  90,
                                               2.70, 3.50,  90,
                                               2.44, 5.64, 120);
      auto.addCommands(drivetrain.createTrajectoryCommand(path, 180));
      auto.addCommands(new PrintCommand("Shoot!"));
      auto.addCommands(new WaitCommand(2));
      // Pickup another ring form right behind
      auto.addCommands(new PrintCommand("Open intake"));
      auto.addCommands(new RotateToHeadingCommand(drivetrain, 0));
      Trajectory path2 = createTrajectory(true, 2.44, 5.64, 0,
                                                3.60, 5.64, 0);
      auto.addCommands(drivetrain.createTrajectoryCommand(path2, 0));
      auto.addCommands(new PrintCommand("Close intake"));
      auto.addCommands(new RotateToHeadingCommand(drivetrain, 180));
      // Move forward to target and shoot
      Trajectory path3 = createTrajectory(true, 3.60, 5.64, 180,
                                                2.44, 5.64, 180);
      auto.addCommands(drivetrain.createTrajectoryCommand(path3, 180));
      auto.addCommands(new PrintCommand("Shoot!"));
      auto.addCommands(new WaitCommand(2));
      auto.addCommands(new PrintCommand("Done."));
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

    return autos;
  }
}
