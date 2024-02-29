// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.swervelib.RotatorBase;

/** Rotator using SparkMax with Neo motor, CANCoder for angle */
@SuppressWarnings("removal")
public class Rotator extends RotatorBase
{
  private final CANSparkMax motor;
  private final WPI_CANCoder encoder;

  /** Construct Rotator
   *  @param index Rotator index 0..3
   *  @param motor_id CAN ID of motor
   *  @param encoder_id CAN ID of encoder
   *  @param offset Offset from 'forward' in degrees
   */
  public Rotator(int index, int motor_id, int encoder_id, double offset)
  {
    super(index, offset, 0.13, 0.06, 0, 0.001, 6.0);

    motor = new CANSparkMax(motor_id, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
    motor.clearFaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setInverted(true);
    motor.setOpenLoopRampRate(0.25);

    encoder = new WPI_CANCoder(encoder_id, "rio");
    encoder.configFactoryDefault();
    encoder.clearStickyFaults();
    encoder.configAbsoluteSensorRange(AbsoluteSensorRange.Signed_PlusMinus180);
  }

  @Override
  public double getRawDegrees()
  {
    return encoder.getAbsolutePosition();
  }

  @Override
  public void setVoltage(double voltage)
  {
    motor.setVoltage(voltage);
  }
}
