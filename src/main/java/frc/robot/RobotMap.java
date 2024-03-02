// Copyright (c) FIRST team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Robot (Hardware) Map: What's connected where? */
public class RobotMap
{
  // CAN IDs
  // (IDs are specific to the device type, so a SparkMax and CANCoder could use the same ID...)

  // SparkMax drive motors
  public static final int FRONT_LEFT_DRIVE = 9;
  public static final int FRONT_RIGHT_DRIVE = 8;
  public static final int BACK_RIGHT_DRIVE = 2;
  public static final int BACK_LEFT_DRIVE = 3;

  // SparkMax rotator motors
  public static final int FRONT_LEFT_ROTATE = 7;
  public static final int FRONT_RIGHT_ROTATE = 10;
  public static final int BACK_RIGHT_ROTATE = 4;
  public static final int BACK_LEFT_ROTATE = 1;

  // CANCoder angle sensors
  public static final int FRONT_LEFT_ANGLE = 11;
  public static final int FRONT_RIGHT_ANGLE = 12;
  public static final int BACK_RIGHT_ANGLE = 6;
  public static final int BACK_LEFT_ANGLE = 5;

  // SparkMax motors
  public static final int INTAKE_SPINNER = 26; // AKA 6A
  public static final int INTAKE_SPINNER_2 = 11;
  public static final int FEEDER = 14;
  public static final int SHOOTER = 13;
  public static final int SHOOTER2 = 16;
  public static final int CLIMBER = 15;  // Left
  public static final int CLIMBER2 = 20; // Right
  public static final int SHOOTER_ARM = 18;

  // Pneumatic control module channels
  public static final int INTAKE_IN_OUT = 0;
  public static final int CLIMBER_BRAKE = 7;

  // Digital I/O
  public static final int FEED_SENSOR = 0;
  public static final int CLIMBER_AT_BOTTOM = 1;  // Left
  public static final int CLIMBER_AT_BOTTOM2 = 3; // Right
  public static final int ARM_ENCODER = 2; // shooter angle encoder

  // PWM outputs
  public static final int LED_STRIP = 0;
}
