// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.demo;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.CommandRobotBase;
import frc.robot.OperatorInterface;

/** Base class for a robot that uses Commands */
public class MotorDemoRobot extends CommandRobotBase
{
    private final TalonFX motor = new TalonFX(1);

    @Override
    public void teleopPeriodic()
    {
        motor.setVoltage(OperatorInterface.joystick.getRightY()*12.0);
    }
}
