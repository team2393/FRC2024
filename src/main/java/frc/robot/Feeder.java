// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Feeder */
public class Feeder extends SubsystemBase
{
  private CANSparkMax feeder;
  private boolean running = false;
  private NetworkTableEntry voltage;

  public Feeder()
  {
    feeder = new CANSparkMax(RobotMap.FEEDER, MotorType.kBrushless);
    feeder.restoreFactoryDefaults();
    feeder.clearFaults();
    feeder.setIdleMode(IdleMode.kBrake);
    feeder.setOpenLoopRampRate(0.5);

    voltage = SmartDashboard.getEntry("Feeder Voltage");
    voltage.setDefaultDouble(4.0);
  }

  public void run(boolean do_run)
  {
    running = do_run;
  }

  @Override
  public void periodic()
  {
    if (running)
      feeder.setVoltage(voltage.getDouble(4));
    else
      feeder.setVoltage(0);
  }
}
