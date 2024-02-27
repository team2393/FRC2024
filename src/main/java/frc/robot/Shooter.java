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
  private static double TURNS_PER_REV = 10/7.5;
  private CANSparkMax spinner, secondary;
  private PIDController pid = new PIDController(0.0, 0.0, 0.0);
  private SimpleMotorFeedforward ff = new SimpleMotorFeedforward(0.6, 0.13);

  // Speed at which spinner should run right now
  private double setpoint = 0;

  // Desired speed when shooting (otherwise zero)
  private NetworkTableEntry nt_desired_speed, nt_speed, nt_at_desired_speed;

  public Shooter()
  {
    spinner = new CANSparkMax(RobotMap.SHOOTER, MotorType.kBrushless);
    spinner.restoreFactoryDefaults();
    spinner.clearFaults();
    spinner.setIdleMode(IdleMode.kCoast);
    spinner.setOpenLoopRampRate(0.5);
    spinner.setInverted(true);
    // Lessen the built-in averaging to get faster respone?
    // See https://www.chiefdelphi.com/t/psa-default-neo-sparkmax-velocity-readings-are-still-bad-for-flywheels
    // TODO spinner.getEncoder().setAverageDepth(2);

    secondary = new CANSparkMax(RobotMap.SHOOTER2, MotorType.kBrushless);
    secondary.restoreFactoryDefaults();
    secondary.clearFaults();
    secondary.setIdleMode(IdleMode.kCoast);
    secondary.setOpenLoopRampRate(0.5);
    secondary.follow(spinner, true);

    nt_desired_speed = SmartDashboard.getEntry("Shooter Setpoint");
    nt_desired_speed.setDefaultDouble(40);
    nt_speed = SmartDashboard.getEntry("Shooter Speed");
    nt_at_desired_speed = SmartDashboard.getEntry("Shooter At Speed");
  }

  public void configure(double ks, double kv, double P, double I, double D)
  {
    ff = new SimpleMotorFeedforward(ks, kv);
    pid.setPID(P, I, D);
  }

  public void run(boolean do_run)
  {
    if (do_run)
      setpoint = nt_desired_speed.getDouble(500);
    else
      setpoint = 0;
  }

  /** @return Position in rotations */
  public double getTurns()
  {
    return spinner.getEncoder().getPosition() * TURNS_PER_REV;
  }

  /** @return Speed in rotations per second */
  public double getSpeed()
  {
    return spinner.getEncoder().getVelocity() * TURNS_PER_REV / 60.0;
  }

  public boolean atDesiredSpeed()
  {
    // "At speed": within 100 RPS of desired speed?
    return Math.abs(setpoint - getSpeed()) < 2;
  }

  @Override
  public void periodic()
  {
    double speed = getSpeed();
    nt_speed.setNumber(speed);
    if (setpoint <= 0)
    {
      nt_at_desired_speed.setBoolean(false);
      spinner.setVoltage(0);
    }
    else
    {
      nt_at_desired_speed.setBoolean(atDesiredSpeed());
  
      // Use feed forward and PID to compute the required voltage
      double voltage = ff.calculate(setpoint) + pid.calculate(speed, setpoint);
      spinner.setVoltage(voltage);
    }
  }
}
