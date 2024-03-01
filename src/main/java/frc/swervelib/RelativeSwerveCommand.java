// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervelib;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;

/** Command for human to drive robot in direction in which it's currently pointed
 *
 *  'Forward' means go whereever the nose of the robot points
 */
public class RelativeSwerveCommand extends Command
{
  private final SwerveDrivetrain drivetrain;
  // TODO private final SelectCenter center;

  public RelativeSwerveCommand(SwerveDrivetrain drivetrain)
  {
    this(drivetrain, false);
  }

  /** @param drivetrain
   *  @param select_center Support selectable rotation center?
   */
  public RelativeSwerveCommand(SwerveDrivetrain drivetrain, boolean select_center)
  {
    this.drivetrain = drivetrain;
    // if (select_center)
    //   center = new SelectCenter(drivetrain);
    // else
    //   center = null;
    addRequirements(drivetrain);
  }

  public void execute()
  {
    Translation2d axis = // TODO center == null
                       //?
                        SwerveDrivetrain.CENTER
                       //: center.determineCenter();
                       ;

    int pov = SwerveOI.joystick.getHID().getPOV();
    if (pov >= 0)
    {
      double angle = Math.toRadians(-pov);
      double vx = SwerveDrivetrain.CRAWL_SPEED * Math.cos(angle);
      double vy = SwerveDrivetrain.CRAWL_SPEED * Math.sin(angle);
      drivetrain.swerve(vx, vy, 0);
    }
    else
      drivetrain.swerve(SwerveOI.getForwardSpeed(),
                        SwerveOI.getLeftSpeed(),
                        Math.toRadians(SwerveOI.getRotationSpeed()),
                        axis);
  }

  @Override
  public void end(boolean interrupted)
  {
    drivetrain.stop();
  }
}
