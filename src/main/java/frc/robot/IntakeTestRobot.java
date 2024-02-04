// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import frc.tools.CommandRobotBase;

/** Robot for testing the intake */
public class IntakeTestRobot extends CommandRobotBase
{
  private final Intake intake = new Intake();

  public IntakeTestRobot()
  {
    OperatorInterface.toggleIntake().onTrue(intake.getToggleCommand());
  }
}
