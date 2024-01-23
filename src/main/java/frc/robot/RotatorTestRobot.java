// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import frc.swervelib.RotatatorDemoRobot;

/** Robot for testing a single Rotator */
public class RotatorTestRobot extends RotatatorDemoRobot
{
    public RotatorTestRobot()
    {
        super(new Rotator(0, RobotMap.FRONT_LEFT_ROTATE, RobotMap.FRONT_LEFT_ANGLE, 0.0));
    }
}
