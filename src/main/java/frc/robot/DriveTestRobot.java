// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import frc.swervelib.DriverDemoRobot;

/** Robot for testing a single Driver */
public class DriveTestRobot extends DriverDemoRobot
{
    public DriveTestRobot()
    {
        super(new Driver(0, RobotMap.BACK_RIGHT_DRIVE));
    }
}
