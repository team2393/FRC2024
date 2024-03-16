// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervelib;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;

/** Command for swerving to a position */
public class SwerveToPositionCommand extends Command
{
  /** Proportional gain for distance control */
  private static final double P = 2.0;

  /** Max. speed */
  private static final double MAX_SPEED = 3.0;

  /** Max. acceleration */
  private static final double ACCEL = 3.0;

  private final SwerveDrivetrain drivetrain;
  private final double x, y;
  private double last_speed, distance, angle;

  /** @param drivetrain
   *  @param x Desired X position
   *  @param y Desired Y position
   */
  public SwerveToPositionCommand(SwerveDrivetrain drivetrain, double x, double y)
  {
    this.drivetrain = drivetrain;
    this.x = x;
    this.y = y;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize()
  {
    last_speed = 0.0;
    distance = 20.0;
    angle = 0.0;
  }

  // We generally rely on the trajectory tools to follow arbitrary paths.
  // In this case, 'initialize' could create a simple 2-point trajectory
  // from drivetrain.getPose() to the desired (x, y) and then
  // launch a drivetrain.followTrajectory(...).
  // However, such a trajectory computation fails when the distance
  // between the points is too small (~10 cm), and we want to support
  // even small moves with this command.
  public void execute()
  {
    // Compute direct line from where we are right now to the desired location
    Pose2d pose = drivetrain.getPose();
    double dx = x - pose.getX();
    double dy = y - pose.getY();
    
    // Distance, angle relative to the current robot heading
    distance = Math.hypot(dx, dy);
    angle = Math.toDegrees(Math.atan2(dy, dx)) - pose.getRotation().getDegrees();

    // Proportional control of speed based on distance
    double speed = Math.min(MAX_SPEED, P*distance);

    // Limit acceleration
    if (speed > last_speed)
      speed = Math.min(speed, last_speed + ACCEL * TimedRobot.kDefaultPeriod);
    last_speed = speed;

    drivetrain.drive(angle, speed);
  }

  @Override
  public boolean isFinished()
  {
    // Within 5 cm?
    return distance < 0.05;
  }

  @Override
  public void end(boolean interrupted)
  {
    drivetrain.stop();
  }
}
