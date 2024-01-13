// Copyright (c) FIRST team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.demo;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.parts.RotationEncoder;
import frc.parts.SparkMini;
import frc.tools.CommandRobotBase;

/** Demo of 'intake' angle control */
public class IntakeAngleDemoRobot extends CommandRobotBase
{
    private XboxController joystick  = new XboxController(0);
    private SparkMini motor = new SparkMini(0);
    private RotationEncoder encoder = new RotationEncoder(0, -13.75);

    private final double MAX = 9;

    PIDController pid = new PIDController(0.5, 0, 0.005);
    // Consider adding   kg * Math.cos(angle)   as in ArmFeedforward
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        SmartDashboard.setDefaultNumber("Setpoint", 0);
        SmartDashboard.putData(pid);
    }
    @Override
    public void robotPeriodic()
    {
        super.robotPeriodic();        
        SmartDashboard.putNumber("Angle", encoder.getHeading().getDegrees());
    }
    @Override
    public void teleopPeriodic()
    {
        double setpoint = 90;
        if (joystick.getXButton())
            setpoint = 0;
        else if (joystick.getYButton())
            setpoint = 45;
        double readback = encoder.getHeading().getDegrees();
      
        // PID control
        double voltage = pid.calculate(readback, setpoint);
        voltage = MathUtil.clamp(voltage, -MAX, MAX);  
        motor.setVoltage(voltage);
    }

    @Override
    public void autonomousPeriodic()
    {
        double setpoint = SmartDashboard.getNumber("Setpoint", 0.0);
        double readback = encoder.getHeading().getDegrees();
      
        // PID control
        double voltage = pid.calculate(readback, setpoint);
        voltage = MathUtil.clamp(voltage, -MAX, MAX);        
        motor.setVoltage(voltage);
    }
}
