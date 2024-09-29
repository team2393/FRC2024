// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.demo;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OperatorInterface;
import frc.tools.CommandRobotBase;

/** Motor Demo
 *  
 *  Runs motor with the more recent Phoennix 6 API
 *  while still accessing the older Pigeon with Phoenix v5 API.
 */
public class MotorPhoenix6DemoRobot extends CommandRobotBase
{
    private final TalonFX motor = new TalonFX(3);

    private final PigeonIMU gyro = new PigeonIMU(0);

    @Override
    public void robotInit()
    {
        super.robotInit();
        motor.clearStickyFaults();
        TalonFXConfiguration config = new TalonFXConfiguration()
            .withOpenLoopRamps(new OpenLoopRampsConfigs().withVoltageOpenLoopRampPeriod(0.3));
        motor.getConfigurator().apply(config);
        
        motor.setNeutralMode(NeutralModeValue.Brake);
    }

    @Override
    public void robotPeriodic()
    {
        super.robotPeriodic();
        SmartDashboard.putNumber("Position", motor.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("Speed", motor.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber("Heading", gyro.getFusedHeading());
    }    

    @Override
    public void teleopPeriodic()
    {
        motor.setVoltage(OperatorInterface.joystick.getRightY()*12.0);
    }
}
