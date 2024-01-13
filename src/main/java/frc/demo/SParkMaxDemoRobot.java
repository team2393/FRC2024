// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.demo;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OperatorInterface;
import frc.tools.CommandRobotBase;

/** Motor Demo */
public class SParkMaxDemoRobot extends CommandRobotBase
{
    private final CANSparkMax motor = new CANSparkMax(0, MotorType.kBrushless);

    @Override
    public void robotInit()
    {
        super.robotInit();
        motor.restoreFactoryDefaults();
    }

    @Override
    public void robotPeriodic()
    {
        super.robotPeriodic();
        SmartDashboard.putNumber("Position", motor.getEncoder().getPosition());
        SmartDashboard.putNumber("Speed", motor.getEncoder().getVelocity());
    }    

    @Override
    public void teleopPeriodic()
    {
        motor.setVoltage(OperatorInterface.joystick.getRightY()*12.0);
    }
}
