// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj2.command.Command;
import frc.tools.CommandRobotBase;

/** Robot for testing the intake */
public class IntakeTestRobot extends CommandRobotBase
{
  private final PneumaticHub hub = new PneumaticHub();
  private final Intake intake = new Intake();
  private final Feeder feeder = new Feeder();
  private final Command open_intake = new OpenIntakeCommand(intake, feeder);
  
  public IntakeTestRobot()
  {
    hub.enableCompressorAnalog(85, 120);
    OperatorInterface.toggleIntake().toggleOnTrue(open_intake);
  }
}
