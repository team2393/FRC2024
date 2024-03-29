// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Intake */
public class Intake extends SubsystemBase
{
  // Pneumatic cylinder to push intake down/out
  private final Solenoid in_out;
  // Spinner to pull game piece in
  private final CANSparkMax spinner, spinner2;
  private final RelativeEncoder encoder;
  private final NetworkTableEntry nt_rpm;
  // Simulation display elements
  private final MechanismLigament2d pivot;

  // Should intake right now be open (down, out) or closed (up, in)?
  private boolean open = false;

  // Reverse intake spinner to eject game piece?
  private boolean reverse = false;

  public Intake()
  {
    // We want REVPH, but only CTREPCM supports simulation...
    in_out = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.INTAKE_IN_OUT);

    spinner = new CANSparkMax(RobotMap.INTAKE_SPINNER, MotorType.kBrushless);
    spinner.restoreFactoryDefaults();
    spinner.setSmartCurrentLimit(50, 50);
    spinner.clearFaults();
    spinner.setIdleMode(IdleMode.kCoast);
    // Dampen the acceleration
    spinner.setOpenLoopRampRate(1);

    encoder = spinner.getEncoder();

    spinner2 = new CANSparkMax(RobotMap.INTAKE_SPINNER_2, MotorType.kBrushless);
    spinner2.restoreFactoryDefaults();
    spinner2.setSmartCurrentLimit(50, 50);
    spinner2.clearFaults();
    spinner2.setIdleMode(IdleMode.kCoast);
    // Dampen the acceleration
    spinner2.setOpenLoopRampRate(1);
    spinner2.follow(spinner, true);

    nt_rpm = SmartDashboard.getEntry("Intake RPM");

    // Somewhat like this, rotating around '*'
    //           _____
    //             |
    //             *
    //  === bumper ===
    // front  ... back

    // Size of mechanism: 0.8m wide, 1m high
    Mechanism2d mechanism = new Mechanism2d(0.8, 1.0, new Color8Bit(Color.kLightGray));
    // Static base of the robot with bumper
    mechanism.getRoot("front", 0, 0.1)
        .append(new MechanismLigament2d("bumper", 0.8, 0, 20, new Color8Bit(Color.kBlue)));
    // Intake over the back, pivoting out/in
    pivot = mechanism.getRoot("back", 0.63, 0.12)
         .append(new MechanismLigament2d("pivot", 0.2, 90, 8, new Color8Bit(Color.kDarkMagenta)));
    pivot.append(new MechanismLigament2d("upper", 0.2, 90, 15, new Color8Bit(Color.kRed)));
    pivot.append(new MechanismLigament2d("lower", 0.15, -90, 15, new Color8Bit(Color.kRed)));
    // Make available on dashboard
    SmartDashboard.putData("Intake", mechanism);
  }

  /** Open intake and run intake spinner? */
  public void open(boolean do_open)
  {
    open = do_open;
  }

  /** Reverse intake spinner (overrides spinner's normal on/off behavior) */
  public void reverse(boolean do_reverse)
  {
    reverse = do_reverse;
  }

  @Override
  public void periodic()
  {
    // Update solenoid to open or close the intake
    in_out.set(open);

    nt_rpm.setNumber(encoder.getVelocity());

    // Reverse spinner on request, otherwise run spinner when open
    if (reverse)
      spinner.setVoltage(-9);
    else if (open)
      spinner.setVoltage(10);
    else
      spinner.setVoltage(0);

    // Update simulated intake mechanism
    double desired_angle = open ? 10 : 85;
    double angle = pivot.getAngle();
    // Slowly move to desired angle
    if (Math.abs(desired_angle - angle) < 2)
      angle = desired_angle;
    else
      angle += (desired_angle - angle) * 0.05;
    pivot.setAngle(angle);
  }
}
