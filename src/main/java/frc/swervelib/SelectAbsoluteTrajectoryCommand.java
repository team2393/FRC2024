// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervelib;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/** Configure drivetrain to run trajectories in absolute field coordinates */
public class SelectAbsoluteTrajectoryCommand extends InstantCommand
{
  /** Switch trajectory follower to absolute field coords
   *  @param drivetrain Drivetrain to use
   */
  public SelectAbsoluteTrajectoryCommand(SwerveDrivetrain drivetrain)
  {
    super(() ->
    {
      Pose2d absolute = new Pose2d(0, 0, Rotation2d.fromDegrees(0));
      drivetrain.setTrajectoryOrigin(absolute);
    });
  }

  /** Switch trajectory follower to absolute field coords
   *  and set odometry to start position
   *  @param drivetrain Drivetrain to use
   *  @param x [m]
   *  @param y [m]
   *  @param heading [degrees]
   */
  public SelectAbsoluteTrajectoryCommand(SwerveDrivetrain drivetrain,
                                         double x, double y, double heading)
  {
    super(() ->
    {
      Pose2d absolute = new Pose2d(0, 0, Rotation2d.fromDegrees(0));
      drivetrain.setTrajectoryOrigin(absolute);
      drivetrain.setOdometry(x, y, heading);
    });
  }
}
