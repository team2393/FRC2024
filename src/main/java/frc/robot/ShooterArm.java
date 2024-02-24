// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Shooter Arm: Motor to rotate shooter up/down
 * 
 *  REV Through Bore encoder connected to Spark Max via
 *  Absolute Encoder Adapter    REV-11-3326 
 */
public class ShooterArm extends SubsystemBase
{
  private CANSparkMax motor = new CANSparkMax(RobotMap.SHOOTER_ARM, MotorType.kBrushless);
  /** Through Bore Encoder to measure angle.
   *  'A'/'S' switch on side of encoder must be in 'A' position.
   *  Absolute readout can use (white, red, black) into DI,
   *  but we have it plugged into the motor's SparkMax
   */
  private final SparkAbsoluteEncoder encoder = motor.getAbsoluteEncoder(Type.kDutyCycle);

  // Rotate at a max speed of 45 deg/sec
  private final TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(45, 45);
  private ProfiledPIDController pid = new ProfiledPIDController(0, 0, 0, constraints);
  private double kg = 0.0;
  private NetworkTableEntry nt_angle;
  private double desired_angle = 0.0;
  
  public ShooterArm()
  {
    motor.restoreFactoryDefaults();
    motor.clearFaults();
    motor.setIdleMode(IdleMode.kCoast);
    motor.setOpenLoopRampRate(0.5);

    // Calibrate encoder for 0..360 degrees
    encoder.setPositionConversionFactor(360);
    // TODO Calibrate zero offset
    encoder.setZeroOffset(0.0);

    pid.reset(encoder.getPosition());

    nt_angle = SmartDashboard.getEntry("Shooter Angle");
  }

  /** @param kg Gravity compensation constant
   *  @param P  Proportional
   *  @param I  Integral
   *  @param D  Diff. gain
   */
  public void configure(double kg, double P, double I, double D)
  {
    this.kg = kg;
    pid.setPID(P, I, D);
  }

  public void setAngle(double degrees)
  {
    desired_angle = degrees;
  }

  @Override
  public void periodic()
  {
    final double angle = encoder.getPosition();
    nt_angle.setDouble(angle);

    double voltage = kg * Math.cos(Math.toRadians(angle))
                   + pid.calculate(angle, desired_angle);
    motor.setVoltage(voltage);
  }
}
