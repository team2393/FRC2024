// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervelib;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Rotational part of a swerve module */
abstract public class RotatorBase extends SubsystemBase
{
  private final NetworkTableEntry nt_offset;
  private final NetworkTableEntry nt_ks;
  private final NetworkTableEntry nt_P;
  private final NetworkTableEntry nt_I;
  private final NetworkTableEntry nt_D;
  private final NetworkTableEntry nt_max;
  private final NetworkTableEntry nt_angle;
  private final PIDController pid = new PIDController(0,0,0);
  private double simulated_angle = 0.0;

  /** Construct Rotator
   *  @param index Rotator index 0..3
   *  @param offset Offset from 'forward' in degrees
   *  @param ks Static gain
   *  @param kp Proportional gain
   *  @param ki Integral gain
   *  @param kd Differential gain
   *  @param max Maximum (absolute) voltage
   */
  public RotatorBase(int index, double offset,
                     double ks, double kp, double ki, double kd, double max)
  {
    nt_offset = SmartDashboard.getEntry("Offset" + index);
    nt_angle = SmartDashboard.getEntry("Angle" + index);
    nt_ks = SmartDashboard.getEntry("Rotator ks");
    nt_P = SmartDashboard.getEntry("Rotator P");
    nt_I = SmartDashboard.getEntry("Rotator I");
    nt_D = SmartDashboard.getEntry("Rotator D");
    nt_max = SmartDashboard.getEntry("Rotator Max");

    pid.enableContinuousInput(-180, 180);

    nt_offset.setDefaultDouble(offset);
    nt_ks.setDefaultDouble(ks);
    nt_P.setDefaultDouble(kp);
    nt_I.setDefaultDouble(ki);
    nt_D.setDefaultDouble(kd);
    nt_max.setDefaultDouble(max);
  }

  /** @return Angle without any offset correction [degrees] */
  abstract public double getRawDegrees();

  /** @param voltage Voltage to motor for rotating the swerve module */
  abstract public void setVoltage(double voltage);

  /** @return Angle */
  public Rotation2d getAngle()
  {
    if (RobotBase.isSimulation())
      return Rotation2d.fromDegrees(simulated_angle);
    return Rotation2d.fromDegrees(Math.IEEEremainder(getRawDegrees() - nt_offset.getDouble(0.0), 360.0));
  }

  /** @param desired Desired angle of serve module in degrees */
  public void setAngle(double desired)
  {
    // PID control, with error normalized to -180..180
    double angle = getRawDegrees() - nt_offset.getDouble(0.0);
    double error = Math.IEEEremainder(desired - angle, 360.0);
   
    // Start with minimal static voltage to overcome friction
    double output = nt_ks.getDouble(0.0) * Math.signum(error);
    // Add PID correction
    pid.setPID(nt_P.getDouble(0.0), nt_I.getDouble(0.0), nt_D.getDouble(0.0));
    output += pid.calculate(angle, desired);

    // Clamp to +- max
    double max = nt_max.getDouble(1.0);
    output = MathUtil.clamp(output, -max, max);
    setVoltage(output);

    simulated_angle = desired;
  }
  
  @Override
  public void periodic()
  {
    nt_angle.setDouble(getAngle().getDegrees());
  }
}
