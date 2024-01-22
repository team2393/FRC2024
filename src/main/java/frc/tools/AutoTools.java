// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.tools;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.swervelib.SwerveDrivetrain;

/** Auto-no-mouse routines */
public class AutoTools
{
  // Run at up to 1.5m/s, accelerate by 1.0ms per second
  public static TrajectoryConfig config = new TrajectoryConfig(1.5, 1);

  /** Create trajectory from points
   *
   *  <p>Given list of points must contain entries x, y, h,
   *  i.e., total length of x_y_h array must be a multiple of 3.
   *
   *  @param forward Are we driving forward along the trajectory?
   *                 (swerve bot might actually face any direction, this is about the trajectory!)
   *  @param x_y_z   Sequence of points { X, Y, Heading }
   */
  public static Trajectory createTrajectory(final boolean forward, final double... x_y_h)
  {
    if (x_y_h.length % 3 != 0)
      throw new IllegalArgumentException("List of { X, Y, Heading } contains " + x_y_h.length + " entries?!");

    List<Pose2d> waypoints = new ArrayList<>();
    for (int i = 0; i < x_y_h.length; i += 3)
      waypoints.add(new Pose2d(x_y_h[i], x_y_h[i + 1], Rotation2d.fromDegrees(x_y_h[i + 2])));

    config.setReversed(!forward);
    return TrajectoryGenerator.generateTrajectory(waypoints, config);
  }

  /** Create command that follows a PathWeaver path 
   *  @param drivetrain Drivetrain to use
   *  @param pathname Base name "XXX" for "deploy/output/XXX.wpilib.json"
   *  @param final_heading .. of robot
   *  @return Command that follows the path
   */
  public static Command followPathWeaver(SwerveDrivetrain drivetrain, String pathname, double final_heading)
  {
    Path file = Filesystem.getDeployDirectory().toPath().resolve("output").resolve(pathname + ".wpilib.json");
    try
    {
      Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(file);
      return drivetrain.createTrajectoryCommand(trajectory, final_heading);
    }
    catch (Exception ex)
    {
      System.err.println("Cannot load pathweaver file '" + file + "'");
      ex.printStackTrace();
    }
    return new PrintCommand("Error loading '" + pathname + "'");
  }

  /** To be called from `disabledPeriodic` to show start of selected auto option */
  public static void indicateStart(SwerveDrivetrain drivetrain, Command auto_option)
  {
    if (auto_option instanceof SequenceWithStart)
    {
      SequenceWithStart seq = (SequenceWithStart) auto_option;
      Pose2d start = seq.getStart();
      drivetrain.setOdometry(start.getX(), start.getY(), start.getRotation().getDegrees());
    }
  }
}
