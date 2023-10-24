// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervebot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.swervelib.SwerveModule;
import frc.swervelib.SwerveOI;
import frc.tools.CommandRobotBase;

/** ServeModule demo: All modules run at same speed and heading */
public class SwerveModuleDemoRobot extends CommandRobotBase
{
  private final SwerveModule modules[] = 
  {
    new SwerveModule(new Rotator(0,  -16), new Driver(0)),
    new SwerveModule(new Rotator(1,   89), new Driver(1)),
    new SwerveModule(new Rotator(2, -161), new Driver(2)),
    new SwerveModule(new Rotator(3, -108), new Driver(3))
  };
  
  private double angle = 0.0;

  @Override
  public void teleopPeriodic()
  {
    final double MAX_SPEED = 0.5;
    double forward = -SwerveOI.joystick.getRightY();
    double right   = -SwerveOI.joystick.getRightX();
        
    double speed = -MAX_SPEED * SwerveOI.joystick.getLeftY();
    
    // Has angle stick been moved by significant amount from center?
    if (Math.hypot(forward, right) > 0.5)
      angle = Math.toDegrees(Math.atan2(right, forward));
    // else: leave angle unchanged
      
    SmartDashboard.putNumber("Forward", forward);
    SmartDashboard.putNumber("Right", right);
    SmartDashboard.putNumber("Angle", angle);
    for (SwerveModule module : modules)
      module.drive(angle, speed);
  }
}
