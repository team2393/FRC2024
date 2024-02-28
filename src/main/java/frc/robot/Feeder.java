// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Feeder */
public class Feeder extends SubsystemBase
{
  private CANSparkMax feeder;
  private boolean running = false;
  private NetworkTableEntry voltage;

  // Sensor that detects a captured game piece
  private final DigitalInput sensor;

  // NT entry: Do we have a game piece?
  private final NetworkTableEntry have_gamepiece_entry;

  public Feeder()
  {
    feeder = new CANSparkMax(RobotMap.FEEDER, MotorType.kBrushless);
    feeder.restoreFactoryDefaults();
    feeder.clearFaults();
    feeder.setIdleMode(IdleMode.kBrake);
    feeder.setOpenLoopRampRate(0.5);

    voltage = SmartDashboard.getEntry("Feeder Voltage");
    voltage.setDefaultDouble(2.0);

    sensor = new DigitalInput(RobotMap.FEED_SENSOR);

    have_gamepiece_entry = SmartDashboard.getEntry("Gamepiece");
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
    return sensor.get();
  }

  @Override
  public void periodic()
  {
    // Show if we have detected a game piece
    have_gamepiece_entry.setBoolean(haveGamePiece());

    if (running)
      feeder.setVoltage(voltage.getDouble(4));
    else
      feeder.setVoltage(0);
  }
}
