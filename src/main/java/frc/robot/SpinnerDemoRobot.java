// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tools.CommandRobotBase;

/** (Dual) Spinner Demo */
public class SpinnerDemoRobot extends CommandRobotBase
{
  private CANSparkMax spinner, secondary;
  private PIDController pid;

  @Override
  public void robotInit()
  {
    super.robotInit();

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

    SmartDashboard.setDefaultNumber("Voltage", 0);
    SmartDashboard.setDefaultNumber("Setpoint1", 0);
    SmartDashboard.setDefaultNumber("Setpoint2", 500);
    SmartDashboard.setDefaultNumber("Period", 5.0);
    SmartDashboard.setDefaultNumber("ks", 0.0);
    SmartDashboard.setDefaultNumber("kv", 0.0);

    pid = new PIDController(0, 0, 0);
    SmartDashboard.putData("PID", pid);
  }

  /** @return Speed in rotations per second */
  private double getSpeed()
  {
    return spinner.getEncoder().getVelocity() / 60.0;
  }

  @Override
  public void robotPeriodic()
  {
    SmartDashboard.putNumber("Revs", spinner.getEncoder().getPosition());
    SmartDashboard.putNumber("RPS", getSpeed());
  }

  @Override
  public void teleopPeriodic()
  {
    // Use voltage entered in dashboard
    spinner.setVoltage(SmartDashboard.getNumber("Voltage", 0));
  }

  @Override
  public void autonomousPeriodic()
  {
    // Very "Period", toggle between "Setpoint1" and "Setpoint2"
    double setpoint = ((System.currentTimeMillis() / (int)(SmartDashboard.getNumber("Period", 5.0)*1000)) % 2 == 1)
                    ? SmartDashboard.getNumber("Setpoint1", 0.0)
                    : SmartDashboard.getNumber("Setpoint2", 500.0);

    // Use feed forward and PID to compute the required voltage
    SimpleMotorFeedforward ff = new SimpleMotorFeedforward(SmartDashboard.getNumber("ks", 0),
                                                           SmartDashboard.getNumber("kv", 0));
    double voltage = ff.calculate(setpoint) + pid.calculate(getSpeed(), setpoint);

    SmartDashboard.putNumber("Voltage", voltage);
    spinner.setVoltage(voltage);
  }
}
