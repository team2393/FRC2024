// Copyright (c) FIRST team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.swervelib.SwerveOI;

/** Operator Interface: Which stick or button does what? */
public class OperatorInterface extends SwerveOI
{
  public static Trigger toggleIntake()
  {
    return joystick.a();
  }

  public static Trigger reverseIntake()
  {
    return joystick.x();
  }

  public static Trigger fire()
  {
    return joystick.y();
  }

  public static Trigger leftClimberUp()
  {
    return joystick.leftBumper();
  }

  public static Trigger leftClimberDown()
  {
    return joystick.leftTrigger();
  }
  
  public static Trigger rightClimberUp()
  {
    return joystick.rightBumper();
  }

  public static Trigger rightClimberDown()
  {
    return joystick.rightTrigger();
  }
}
