// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervelib;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;

/** Command for rotating robot to a certain heading */
public class RotateToHeadingCommand extends Command
{
  /** Proportional gain for angle control */
  private static final double P = 10.0;

  /** Max. rotational speed [deg/sec] */
  private static final double MAX_SPEED = 90;

  /** Max. acceleration */
  private static final double ACCEL = 90;

  private final SwerveDrivetrain drivetrain;
  private final double heading;
  private double last_speed, angle;

  /** @param drivetrain
   *  @param x Desired X position
   *  @param y Desired Y position
   */
  public RotateToHeadingCommand(SwerveDrivetrain drivetrain, double heading)
  {
    this.drivetrain = drivetrain;
    this.heading = heading;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize()
  {
    last_speed = 0.0;
    angle = 0.0;
  }

  public void execute()
  {    
    // Proportional control of speed based on angle
    angle = heading - drivetrain.getPose().getRotation().getDegrees();
    double speed = MathUtil.clamp(P*angle, -MAX_SPEED, MAX_SPEED);

    // Limit acceleration
    if (Math.abs(speed) > Math.abs(last_speed))
      speed = Math.signum(speed) * Math.min(Math.abs(speed), Math.abs(last_speed) + ACCEL * TimedRobot.kDefaultPeriod);
    last_speed = speed;

    drivetrain.swerve(0, 0, Math.toRadians(speed));
  }

  @Override
  public boolean isFinished()
  {
    // Within .. degrees?
    return Math.abs(angle) < 1.5;
  }

  @Override
  public void end(boolean interrupted)
  {
    drivetrain.drive(0, 0.0);
  }
}
