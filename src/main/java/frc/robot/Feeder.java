// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Feeder */
public class Feeder extends SubsystemBase
{
  private CANSparkMax feeder;
  private boolean running = false;
  private double voltage = 1.5;

  // private NetworkTableEntry voltage;
  private Debouncer debouncer = new Debouncer(0.1, DebounceType.kBoth);

  // Sensor that detects a captured game piece
  private final DigitalInput sensor;

  // NT entry: Do we have a game piece?
  private final NetworkTableEntry have_gamepiece_entry;
  private final NetworkTableEntry have_gamepiece_entry_debounced;

  public Feeder()
  {
    feeder = new CANSparkMax(RobotMap.FEEDER, MotorType.kBrushless);
    feeder.restoreFactoryDefaults();
    feeder.setSmartCurrentLimit(60, 60);
    feeder.clearFaults();
    feeder.setIdleMode(IdleMode.kBrake);
    feeder.setOpenLoopRampRate(0.5);

    // voltage = SmartDashboard.getEntry("Feeder Voltage");
    // voltage.setDefaultDouble(1.5);

    sensor = new DigitalInput(RobotMap.FEED_SENSOR);

    have_gamepiece_entry = SmartDashboard.getEntry("Gamepiece");
    have_gamepiece_entry_debounced = SmartDashboard.getEntry("Gamepiece (Debounced)");
  }

  public void run(boolean do_run, double voltage)
  {
    running = do_run;
    this.voltage = voltage;
  }

  public void run(boolean do_run)
  {
    running = do_run;
  }

  public boolean haveGamePiece()
  {
    // Have we detected a game piece?
    // Digital inputs have internal pull-up,
    // so with nothing connected they will read 'true'.
    // The game piece sensor should connect the input to ground,
    // so then it would read 'false'
    return !debouncer.calculate(sensor.get());
  }

  @Override
  public void periodic()
  {
    // Show if we have detected a game piece
    have_gamepiece_entry.setBoolean(haveGamePiece());
    have_gamepiece_entry_debounced.setBoolean(haveGamePiece());


    if (running)
      feeder.setVoltage(voltage);
    else
      feeder.setVoltage(0);
  }
}
