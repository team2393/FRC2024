// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Intake */
public class Intake extends SubsystemBase
{
  // Pneumatic cylinder to push intake down/out
  private final Solenoid in_out;
  // Spinner to pull game piece in
  private final CANSparkMax spinner;
  // Sensor that detects a captured game piece
  private final DigitalInput sensor;
  // NT entry: Do we have a game piece?
  private final NetworkTableEntry have_gamepiece_entry;
  // Simulation display elements
  private final MechanismLigament2d intake_upper, intake_lower;

  // Should intake right now be open (down, out) or closed (up, in)?
  private boolean open = false;

  public Intake()
  {
    // TODO We want REVPH, but only CTREPCM supports simulation...
    in_out = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.INTAKE_IN_OUT);

    spinner = new CANSparkMax(RobotMap.INTAKE_SPINNER, MotorType.kBrushless);
    spinner.restoreFactoryDefaults();
    spinner.clearFaults();
    spinner.setIdleMode(IdleMode.kCoast);
    // Dampen the acceleration
    spinner.setOpenLoopRampRate(0.5);

    sensor = new DigitalInput(RobotMap.FEED_SENSOR);

    have_gamepiece_entry = SmartDashboard.getEntry("Gamepiece");

    // Somewhat like this:
    //             \
    //              \
    //  === bumper ===

    // Size of mechanism: 0.8m wide, 1m high
    Mechanism2d mechanism = new Mechanism2d(0.8, 1.0, new Color8Bit(Color.kLightGray));
    // Static base of the robot with bumper
    mechanism.getRoot("back", 0, 0.1)
        .append(new MechanismLigament2d("bumper", 0.8, 0, 20, new Color8Bit(Color.kBlue)));
    // Intake over the front, two parts to simulate moving out/in
    intake_upper = mechanism.getRoot("front", 0.75, 0.2)
        .append(new MechanismLigament2d("intake_upper", 0.4, 135, 10, new Color8Bit(Color.kRed)));
    intake_lower = mechanism.getRoot("front", 0.75, 0.2)
        .append(new MechanismLigament2d("intake_lower", 0.0, -45, 10, new Color8Bit(Color.kRed)));
    // Make available on dashboard
    SmartDashboard.putData("Intake", mechanism);
  }

  @Override
  public void periodic()
  {
    // Have we detected a game piece?
    // Digital inputs have internal pull-up,
    // so with nothing connected they will read 'true'.
    // The game piece sensor should connect the input to ground,
    // so then it would read 'false'
    boolean have_gamepiece = ! sensor.get();
    // Show if we have detected a game piece
    have_gamepiece_entry.setBoolean(have_gamepiece);

    // Automatically close intake when we have a game piece
    if (have_gamepiece)
      open = false;

    // Update solenoid to open or close the intake
    in_out.set(open);

    // Run spinner when open
    // TODO Find good voltage for pulling in
    if (open)
      spinner.setVoltage(3);
    else
      spinner.setVoltage(0);

    // Update simulated intake mechanism
    if (open)
    {
      intake_upper.setLength(0.2);
      intake_lower.setLength(0.2);
    }
    else
    {
      intake_upper.setLength(0.4);
      intake_lower.setLength(0.0);
    }
  }

  /** @return Command to open the intake */
  public Command getOpenCommand()
  {
    return new InstantCommand(() ->  open = true, this);
  }

  /** @return Command to close the intake */
  public Command getCloseCommand()
  {
    return new InstantCommand(() ->  open = false, this);
  }

  /** @return Command to toggle the intake */
  public Command getToggleCommand()
  {
    return new InstantCommand(() ->  open = !open, this);
  }
}
