// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
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
  private final CANSparkMax spinner, secondary;
  private final RelativeEncoder encoder;
  private final PIDController pid = new PIDController(0.0, 0.0, 0.0);
  private SimpleMotorFeedforward ff = new SimpleMotorFeedforward(0.6, 0.13);

  // Should spinner run right now?
  private boolean run = false;

  // Is spinner at desired speed?
  private boolean at_desired_speed = false;

  // Desired speed when shooting (otherwise zero)
  private NetworkTableEntry nt_desired_speed, nt_speed, nt_at_desired_speed, nt_always_on;

  public Shooter()
  {
    spinner = new CANSparkMax(RobotMap.SHOOTER, MotorType.kBrushless);
    spinner.restoreFactoryDefaults();
    spinner.clearFaults();
    spinner.setIdleMode(IdleMode.kCoast);
    spinner.setOpenLoopRampRate(0.5);
    spinner.setInverted(true);
    encoder = spinner.getEncoder();
    // TODO Lessen the built-in averaging to get faster respone?
    // See https://www.chiefdelphi.com/t/psa-default-neo-sparkmax-velocity-readings-are-still-bad-for-flywheels
    // encoder.setMeasurementPeriod(16);
    // encoder.setAverageDepth(2);

    secondary = new CANSparkMax(RobotMap.SHOOTER2, MotorType.kBrushless);
    secondary.restoreFactoryDefaults();
    secondary.clearFaults();
    secondary.setIdleMode(IdleMode.kCoast);
    secondary.setOpenLoopRampRate(0.5);
    secondary.follow(spinner, true);

    // Speed control mostly uses 'I' term.
    // Its default range is -1..1, but we seal with larger RPS ranges
    // TODO Find good value
    pid.setIntegratorRange(-100, 100);

    nt_desired_speed = SmartDashboard.getEntry("Shooter Setpoint");
    nt_desired_speed.setDefaultDouble(40);
    nt_speed = SmartDashboard.getEntry("Shooter Speed");
    nt_at_desired_speed = SmartDashboard.getEntry("Shooter At Speed");
    nt_always_on = SmartDashboard.getEntry("Shooter On");
    nt_always_on.setDefaultBoolean(false);
  }

  public void configure(double ks, double kv, double P, double I, double D)
  {
    ff = new SimpleMotorFeedforward(ks, kv);
    pid.setPID(P, I, D);
  }

  public void run(boolean do_run)
  {
    run = do_run;
  }

  /** @return Position in rotations */
  public double getTurns()
  {
    return encoder.getPosition() * TURNS_PER_REV;
  }

  /** @return Speed in rotations per second */
  public double getSpeed()
  {
    return encoder.getVelocity() * TURNS_PER_REV / 60.0;
  }

  public boolean atDesiredSpeed()
  {
    return at_desired_speed;
  }

  @Override
  public void periodic()
  {
    double speed = getSpeed();
    nt_speed.setNumber(speed);
    if (run  ||  nt_always_on.getBoolean(false))
    {
      double setpoint = nt_desired_speed.getDouble(500);
      // "At speed": Fast enough
      at_desired_speed = speed >= setpoint;

      // Use feed forward and PID to compute the required voltage
      double voltage = ff.calculate(setpoint) + pid.calculate(speed, setpoint);
      spinner.setVoltage(voltage);
    }
    else
    {
      at_desired_speed = false;
      spinner.setVoltage(0);
    }
    nt_at_desired_speed.setBoolean(atDesiredSpeed());
  }
}
