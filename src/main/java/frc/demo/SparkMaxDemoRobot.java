// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.demo;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OperatorInterface;
import frc.robot.RobotMap;
import frc.tools.CommandRobotBase;

/** Motor Demo */
public class SparkMaxDemoRobot extends CommandRobotBase
{
    private final CANSparkMax motor = new CANSparkMax(RobotMap.FRONT_LEFT_ROTATE, MotorType.kBrushless);
    private RelativeEncoder encoder;

    @Override
    public void robotInit()
    {
        super.robotInit();
        motor.restoreFactoryDefaults();
        motor.clearFaults();
        motor.setOpenLoopRampRate(1);
        encoder = motor.getEncoder();
    }

    @Override
    public void robotPeriodic()
    {
        super.robotPeriodic();
        SmartDashboard.putNumber("Position", encoder.getPosition());
        SmartDashboard.putNumber("Speed", encoder.getVelocity());
    }    

    @Override
    public void teleopPeriodic()
    {
        double voltage = OperatorInterface.joystick.getRightX() * 12;
        SmartDashboard.putNumber("Voltage", voltage);
        motor.setVoltage(voltage);
    }

    @Override
    public void autonomousPeriodic()
    {
        double voltage = ((System.currentTimeMillis() / 3000) % 2) * 6.0;
        SmartDashboard.putNumber("Voltage", voltage);
        motor.setVoltage(voltage);
    }
}
