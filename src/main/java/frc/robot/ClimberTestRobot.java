// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  private final Brake brake = new Brake();
  private final Climber climber = new Climber(true, brake::setLeft);
  private final Climber climber2 = new Climber(false, brake::setRight);

  public ClimberTestRobot()
  {
    if (OperatorInterface.leftClimberUp())
    {
      climber.getUpCommand().schedule();
    }
    if (OperatorInterface.rightClimberUp())
    {
      climber2.getUpCommand().schedule();
    }
    if (OperatorInterface.leftClimberDown())
    {
      climber.getDownCommand().schedule();
    }
    if (OperatorInterface.rightClimberDown())
    {
      climber2.getDownCommand().schedule();
    }
  }

  @Override
  public void robotPeriodic()
  {
    super.robotPeriodic();
    SmartDashboard.putBoolean("at bottom", climber.isAtBottom());
    SmartDashboard.putBoolean("at bottom2", climber2.isAtBottom());
  }

  @Override
  public void autonomousInit()
  {
    climber.getHomeCommand().schedule();
    climber2.getHomeCommand().schedule();
  }
}
