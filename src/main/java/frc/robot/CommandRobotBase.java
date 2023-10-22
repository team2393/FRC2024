// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/** Base class for a robot that uses Commands */
public class CommandRobotBase extends TimedRobot
{
  /** Initialize robot.
   * 
   *  <p>Overriding code should call `super.robotInit()`
   *  to show the class name.
   * 
   *  <p>{@inheritDoc}}
   */
  @Override
  public void robotInit()
  {
    // Display the actual class name of the derived class
    // so we can see what's on the RoboRIO in the drive station console
    System.out.println("************************************");
    System.out.println("**  " + getClass().getName());
    System.out.println("************************************");

    // Show currently running commands on dashboard
    SmartDashboard.putData(CommandScheduler.getInstance());
  }

  /** Run command scheduler.
   * 
   *  <p>Overriding code should call `super.robotPeriodic()`.
   * 
   *  <p>{@inheritDoc}}
   */
  @Override
  public void robotPeriodic()
  {
    // Support commmand framework
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledPeriodic()
  {
    // Define as empty to avoid
    // "Default disabledPeriodic() method... Override me!"
  }
}
