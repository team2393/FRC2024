// Copyright (c) FIRST team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Robot (Hardware) Map: What's connected where? */
public class RobotMap
{
  // TODO Power Distribution Info
  // Add someting similar to the comment in
  // https://github.com/team2393/FRC2022/blob/main/src/main/java/frc/robot/RobotMap.java
  //
  //
  // 21  -  RoboRIO, 10 Amp
  //
  // which shows the outputs of the power distribution panel
  // and how they are used: What motor/device/.., what type of fuse.
  // See https://docs.wpilib.org/en/latest/docs/controls-overviews/control-system-hardware.html
  // for the potential types of power distribution panels.

  // CAN IDs
  public static final int FRONT_LEFT_DRIVE = 1;
  public static final int FRONT_RIGHT_DRIVE = 2;
  public static final int BACK_RIGHT_DRIVE = 3;
  public static final int BACK_LEFT_DRIVE = 4;

  public static final int FRONT_LEFT_ROTATE = 5;
  public static final int FRONT_RIGHT_ROTATE = 6;
  public static final int BACK_RIGHT_ROTATE = 7;
  public static final int BACK_LEFT_ROTATE = 8;

  public static final int INTAKE_SPINNER = 9;

  public static final int FEEDER = 10;

  public static final int SHOOTER = 11;

  // Pneumatic control module channels
  public static final int INTAKE_IN_OUT = 0;

  // Digital I/O
  public static final int FEED_SENSOR = 0;
  public static final int SHOOTER_SENSOR = 1;

  // PWM outputs
  public static final int LED_STRIP = 0;
}
