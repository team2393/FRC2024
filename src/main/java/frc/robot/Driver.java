// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.swervelib.DriverBase;

/** Driver using SparkMax */
public class Driver extends DriverBase
{
  private final static int REV_PER_METER = 1;

  private final CANSparkMax motor;

  /** @param index Driver index 0..3
   *  @param motor_id CAN ID of motor
   */
  public Driver(int index, int motor_id)
  {
    super(index, 0.5, 2.24, 0, 0.4, 0.1);
  
    motor = new CANSparkMax(motor_id, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
    motor.clearFaults();
    motor.setIdleMode(IdleMode.kBrake);
    // Dampen the acceleration
    motor.setOpenLoopRampRate(0.1);
  }
  
  protected double getRawPosition()
  {
    // Convert revolutions into meter
    return motor.getEncoder().getPosition() / REV_PER_METER;
  }

  protected double getRealSpeed()
  {
    // Convert revolution per minute into m/s
    return motor.getEncoder().getVelocity() * 60.0 / REV_PER_METER;
  }

  public void setVoltage(double voltage)
  {
    motor.setVoltage(voltage);
  }
}
