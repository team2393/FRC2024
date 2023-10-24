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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swervelib.SelectRelativeTrajectoryCommand;
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
    final List<Command> autos = new ArrayList<>();

    { // Simply drive forward 1.5 m, can be used from Blue or Red, Top or Bottom
      SequentialCommandGroup auto = new SequentialCommandGroup();
      auto.setName("Forward 1.5m");
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectRelativeTrajectoryCommand(drivetrain));
      Trajectory path = createTrajectory(true, 0, 0, 0,
                                               1.50, 0, 0);
      auto.addCommands(drivetrain.createTrajectoryCommand(path, 0));
      autos.add(auto);
    }

    {
      autos.add(new VariableWaitCommand()
                .andThen(new SwerveToPositionCommand(drivetrain, 4, 4))
                .andThen(new SwerveToPositionCommand(drivetrain, 9, 3))
                .withName("4,4 <-> 9,3"));
    }

    {
      autos.add(new SequenceWithStart("Circle", 1.66, 4.47, 0,
                                      new VariableWaitCommand(),
                                      followPathWeaver(drivetrain, "Circle", 0.0)));
    }

    return autos;
  }
}
