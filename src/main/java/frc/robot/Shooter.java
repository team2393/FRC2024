// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Shooter: Dual spinners that eject game piece */
public class Shooter extends SubsystemBase
{
  private CANSparkMax spinner, secondary;
  // Use SpinnerDemoRobot to determine PID and FF settings
  private PIDController pid = new PIDController(0.0, 0.0, 0.0);
  private SimpleMotorFeedforward ff = new SimpleMotorFeedforward(0.0, 0.0);

  // Speed at which spinner should run right now
  private double setpoint = 0;

  // Desired speed when shooting (otherwise zero)
  private NetworkTableEntry desired_speed;

  public Shooter()
  {
    spinner = new CANSparkMax(RobotMap.SHOOTER, MotorType.kBrushless);
    spinner.restoreFactoryDefaults();
    spinner.clearFaults();
    spinner.setIdleMode(IdleMode.kCoast);
    spinner.setOpenLoopRampRate(0.5);

    secondary = new CANSparkMax(RobotMap.SHOOTER, MotorType.kBrushless);
    secondary.restoreFactoryDefaults();
    secondary.clearFaults();
    secondary.setIdleMode(IdleMode.kCoast);
    secondary.setOpenLoopRampRate(0.5);
    secondary.follow(spinner, false);

    desired_speed = SmartDashboard.getEntry("Shooter Setpoint");
    desired_speed.setDefaultDouble(500);
  }

  public void run(boolean do_run)
  {
    if (do_run)
      setpoint = desired_speed.getDouble(500);
    else
      setpoint = 0;
  }

  /** @return Speed in rotations per second */
  public double getSpeed()
  {
    // TODO Calibrate to get speed of spinner wheel in case there's
    // gering between motor and spinner?
    return spinner.getEncoder().getVelocity() / 60.0;
  }

  public boolean atDesiredSpeed()
  {
    // "At speed": within 100 RPS of desired speed?
    return Math.abs(setpoint - getSpeed()) < 100;
  }

  @Override
  public void periodic()
  {
    if (setpoint <= 0)
      spinner.setVoltage(0);
    else
    {
      double speed = getSpeed();
      SmartDashboard.putNumber("Shooter", speed);
  
      // Use feed forward and PID to compute the required voltage
      double voltage = ff.calculate(setpoint) + pid.calculate(speed, setpoint);
      spinner.setVoltage(voltage);
    }
  }
}
