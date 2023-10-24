// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervebot;

import frc.parts.RotationEncoder;
import frc.parts.SparkMini;
import frc.swervelib.RotatorBase;

/** SwerveBot Rotator */
public class Rotator extends RotatorBase
{
  private final SparkMini motor;
  private final RotationEncoder encoder;

  /** Construct Rotator
   *  @param index Rotator index 0..3
   *  @param offset Offset from 'forward' in degrees
   */
  public Rotator(int index, double offset)
  {
    super(index, offset, 0.1, 0.5, 0, 0.01, 9.0);
    motor = new SparkMini(index);
    encoder = new RotationEncoder(index, offset);
  }

  @Override
  public double getRawDegrees()
  {
    return encoder.getRawHeading();
  }

  @Override
  public void setVoltage(double voltage)
  {
    motor.setVoltage(voltage);
  }
}
