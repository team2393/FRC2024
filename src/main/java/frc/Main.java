// Copyright (c) FIRST team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc;

import edu.wpi.first.wpilibj.RobotBase;

/** Java 'Main'. Modify this file to select which robot to run */
public final class Main
{
  public static void main(String... args)
  {
    // RobotBase.startRobot(frc.tools.CommandRobotBase::new);
    // RobotBase.startRobot(frc.led.LEDRingDemoRobot::new);
    // RobotBase.startRobot(frc.demo.MotorDemoRobot::new);
    // RobotBase.startRobot(frc.swervebot.SwerveModuleDemoRobot::new);
    RobotBase.startRobot(frc.swervebot.SwerveBot::new);
  }
}
