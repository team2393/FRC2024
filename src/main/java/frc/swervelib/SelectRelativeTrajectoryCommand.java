// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervelib;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/** Configure drivetrain to run trajectories relative to current position when command starts */
public class SelectRelativeTrajectoryCommand extends InstantCommand
{
  /** @param drivetrain Drivetrain to use */
  public SelectRelativeTrajectoryCommand(SwerveDrivetrain drivetrain)
  {
    super(() ->
    {
      Pose2d current_pose = drivetrain.getPose();
      drivetrain.setTrajectoryOrigin(current_pose);
      System.out.println("Set trajectory origin to " + current_pose);
    });
  }
}
