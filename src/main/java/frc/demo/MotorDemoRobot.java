// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.demo;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OperatorInterface;
import frc.robot.RobotMap;
import frc.tools.CommandRobotBase;

/** Motor Demo
 *  
 *  CTRE still offers the Phoenix v5 API like
 *  com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
 *  as well as a new v6 API like
 *  com.ctre.phoenix6.hardware.TalonFX
 * 
 *  The original Pigeon, however, is only supported
 *  with the v5 API, so best stay with that as long
 *  as we have some older hardware?
 */
@SuppressWarnings("removal")
public class MotorDemoRobot extends CommandRobotBase
{
    private final WPI_TalonFX motor = new WPI_TalonFX(RobotMap.FRONT_LEFT_DRIVE);

    @Override
    public void robotInit()
    {
        super.robotInit();
        motor.clearStickyFaults();
    }

    @Override
    public void robotPeriodic()
    {
        super.robotPeriodic();
        SmartDashboard.putNumber("Position", motor.getSelectedSensorPosition());
        SmartDashboard.putNumber("Speed", motor.getSelectedSensorVelocity());
    }    

    @Override
    public void teleopPeriodic()
    {
        motor.setVoltage(OperatorInterface.joystick.getRightY()*12.0);
    }
}
