// Copyright (c) FIRST team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.demo;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tools.CommandRobotBase;

/** Demo of speed control of a spinner */
public class SpinnerSpeedDemo extends CommandRobotBase  
{
    private WPI_TalonFX motor = new WPI_TalonFX(1);
    private XboxController joystick  = new XboxController(0);

    // 163988/12 = 13665.66, 136333/10 = 13633.3
    private final double TICKS_PER_REV = 163988 / 12.0;
    private final double MAX = 9;

    PIDController pid = new PIDController(0, 0, 0);

    @Override
    public void robotInit()
    {
        super.robotInit();
        SmartDashboard.setDefaultNumber("Setpoint", 0);
        SmartDashboard.setDefaultNumber("ks", 0.28);
        SmartDashboard.setDefaultNumber("kv", 0.74);
        SmartDashboard.putData(pid);
    }

    @Override
    public void robotPeriodic()
    {
        super.robotPeriodic();
        SmartDashboard.putNumber("position",motor.getSelectedSensorPosition()/TICKS_PER_REV);
        SmartDashboard.putNumber("speed",motor.getSelectedSensorVelocity()/TICKS_PER_REV * 10);
        SmartDashboard.putNumber("voltage", motor.getMotorOutputVoltage());
    }

    @Override
    public void teleopPeriodic()
    {
        motor.setVoltage(9* joystick.getLeftY());       
    }

    @Override
    public void autonomousPeriodic()
    {
        double setpoint = SmartDashboard.getNumber("Setpoint", 0.0);
        double readback = motor.getSelectedSensorVelocity()/TICKS_PER_REV * 10;
     
        SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(
              SmartDashboard.getNumber("ks", 0.28),
              SmartDashboard.getNumber("kv", 0.74));
        double voltage = feedforward.calculate(setpoint) + pid.calculate(readback, setpoint);
        voltage = MathUtil.clamp(voltage, -MAX, MAX);
        motor.setVoltage(voltage);
    }
}
