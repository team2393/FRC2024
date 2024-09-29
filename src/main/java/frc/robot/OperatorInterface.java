// Copyright (c) FIRST team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.proto.Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.swervelib.SwerveOI;

/** Operator Interface: Which stick or button does what? */
public class OperatorInterface extends SwerveOI
{
  public static Trigger toggleIntake()
  {
    return joystick.a();
  }

  public static boolean reverseIntake()
  {
    return buttons.getRawButton(9);
  }

  public static Trigger fire()
  {
    return joystick.y();
  }

  public static boolean leftClimberUp()
  {
    return buttons.getRawButton(8);
  }

  public static boolean leftClimberDown()
  {
    return buttons.getRawButton(5);
  }
  
  public static boolean rightClimberUp()
  {
    return buttons.getRawButton(6);
  }

  public static boolean rightClimberDown()
  {
    return buttons.getRawButton(10);
  }

  public static boolean bumperShoot()
  {
    return buttons.getRawButton(7);
  }

  public static double centerOnAprilTagJoe()
  {
    return joystick.getLeftTriggerAxis();
  }

  public static boolean autoCam()
  {
    return buttons.getRawButton(1);
  }

  public static Trigger resetHeading()
  {
    return joystick.rightBumper();
  }
}
