// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import frc.tools.CommandRobotBase;

/** Robot for testing the climber
 * 
 *  - Test that 'up' indeed moves up
 *  - Determine good MOTOR_VOLTAGE
 *  - Determine METERS_PER_TURN
 *  - Determine MAX_EXTENSION
 *  - Check motor stops moving down when hitting bottom switch
 *  - Check that 'height' zeroes when hitting bottom
 *  - Check motor stops moving up when reaching MAX_EXTENSION
 *  - Check homing
 *  - Make bottom switch fail-safe? Disconnected -> motor stops?
 */
public class ClimberTestRobot extends CommandRobotBase
{
  private final Climber climber = new Climber();

  public ClimberTestRobot()
  {
    OperatorInterface.joystick.b().whileTrue(climber.getUpCommand());
    OperatorInterface.joystick.a().whileTrue(climber.getDownCommand());
  }

  @Override
  public void autonomousInit()
  {
    climber.getHomeCommand().schedule();
  }
}
