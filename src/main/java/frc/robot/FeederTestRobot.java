// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import frc.tools.CommandRobotBase;

/** Robot for testing the feeder */
public class FeederTestRobot extends CommandRobotBase
{
  private Feeder feeder = new Feeder();
    
  @Override
  public void teleopPeriodic()
  {
    // Find a good voltage for the "Feeder Voltage"
    // and hardcode that into the Feeder

    // See https://www.chiefdelphi.com/t/commandscheduler-loop-overruns-one-potential-cause/453901
    // feeder.run(OperatorInterface.joystick.a().getAsBoolean());
    feeder.run(OperatorInterface.joystick.getHID().getAButton());
  }
}
