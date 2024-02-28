// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
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
  // private final SparkAbsoluteEncoder encoder = motor.getAbsoluteEncoder(Type.kDutyCycle);
  private final DutyCycleEncoder encoder = new DutyCycleEncoder(RobotMap.ARM_ENCODER);
  /** Zero degrees = arm horizontally out */
  private final static double ZERO_OFFSET = -111.0 - 17;
 
  // Rotate at a max speed of 45 deg/sec
  private final TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(45, 45);
  private ProfiledPIDController pid = new ProfiledPIDController(0.3, 0.03, 0.01, constraints);
  private double kg = 0.25;
  private NetworkTableEntry nt_angle, nt_desired_angle;
  
  public ShooterArm()
  {
    motor.restoreFactoryDefaults();
    motor.clearFaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setOpenLoopRampRate(0.5);
    motor.setInverted(true);

    pid.reset(getAngle());

    nt_angle = SmartDashboard.getEntry("Shooter Angle");
    nt_desired_angle = SmartDashboard.getEntry("Set Shooter Angle");
    nt_desired_angle.setDefaultDouble(50);
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

  public double getAngle()
  {
    return -encoder.getAbsolutePosition()*360 - ZERO_OFFSET;
  }

  public void setAngle(double degrees)
  {
    nt_desired_angle.setNumber(degrees);
  }

  @Override
  public void periodic()
  {
    final double angle = getAngle();
    nt_angle.setDouble(angle);

    double setpoint = MathUtil.clamp(nt_desired_angle.getDouble(50), 20, 60);

    double voltage = kg * Math.cos(Math.toRadians(angle))
                   + pid.calculate(angle, setpoint);
    motor.setVoltage(voltage);
  }
}
