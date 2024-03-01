// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervelib;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

/** One serve module that can rotate and drive */
public class SwerveModule
{
  private final RotatorBase rotator;
  private final DriverBase driver;    

  public SwerveModule(RotatorBase rotator, DriverBase driver)
  {
    this.rotator = rotator;
    this.driver = driver;
  }

  /** Reset position of driver to zero */
  public void resetPosition()
  {
    driver.resetPosition();
  }

  /** @return Angle of rotator */
  public Rotation2d getAngle()
  {
    return rotator.getAngle();
  }

  /** @return Driver position */
  public SwerveModulePosition getPosition()
  {
    return new SwerveModulePosition(driver.getPosition(), rotator.getAngle());
  }

  /** Stop all motors */
  public void stop()
  {
    rotator.setVoltage(0);
    driver.setVoltage(0);
  }

  /** @param angle Module angle in degrees
   *  @param speed Module speed in meters per second
   */
  public void drive(double angle, double speed)
  {
     rotator.setAngle(angle);
     driver.setSpeed(speed);
  }
}
