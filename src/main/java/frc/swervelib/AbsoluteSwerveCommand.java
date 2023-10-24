// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervelib;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;

/** Command for human to drive robot relative to field odometry */
public class AbsoluteSwerveCommand extends Command
{
  private final SwerveDrivetrain drivetrain;

  /** @param drivetrain */
  public AbsoluteSwerveCommand(SwerveDrivetrain drivetrain)
  {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  public void execute()
  {
    // Speed vector (vx, vy) meant to be in field coordinates,
    // vx going "up" from the origin of the field along X
    // For the blue side, this means "forward" is away from the
    // driver station and "right" is to the right
    double vx, vy;
    int pov = SwerveOI.joystick.getHID().getPOV();
    if (pov >= 0)
    {
      double angle = Math.toRadians(-pov);
      vx = SwerveDrivetrain.CRAWL_SPEED * Math.cos(angle);
      vy = SwerveDrivetrain.CRAWL_SPEED * Math.sin(angle);
    }
    else
    {
      vx = SwerveOI.getForwardSpeed();
      vy = SwerveOI.getLeftSpeed();
    }

    // When at the red side of the field,
    // rotate by 180 deg so that "forward" is again
    // away from the driver and "right" moves to the right
    if (DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red)
    {
      vx = -vx;
      vy = -vy;
    }
    // If robot also points 'up', we could use (vx, vy) as given,
    // but generally we need to rotate (vx, vy) backwards from the current heading
    // of the robot to see how the robot needs to move:
    Rotation2d correction = drivetrain.getHeading().unaryMinus();
    Translation2d absoluteDirection = new Translation2d(vx, vy).rotateBy(correction);

    // ^^^ correct the correction by fraction of angular speed,
    // https://www.chiefdelphi.com/t/field-relative-swervedrive-drift-even-with-simulated-perfect-modules

    // Swerve robot in 'absoluteDirection', while rotating as requested
    double vr = SwerveOI.getRotationSpeed();
    drivetrain.swerve(absoluteDirection.getX(), absoluteDirection.getY(), vr, SwerveDrivetrain.CENTER);
  }
}
