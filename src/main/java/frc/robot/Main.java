// Copyright (c) FIRST team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/** Java 'Main'. Modify this file to select which robot to run */
public final class Main
{
  public static void main(String... args)
  {
    // RobotBase.startRobot(CommandRobotBase::new);
    // RobotBase.startRobot(frc.robot.led.LEDRingDemoRobot::new);
    RobotBase.startRobot(frc.robot.demo.MotorDemoRobot::new);
  }
}
