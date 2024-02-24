// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tools.CommandRobotBase;

/** Shooter Arm Test
 * 
 *  - Manually turn arm, check displayed angle, configure zero offset
 *  - In autonomous, find suitable kg for motor to "hold" arm against gravity
 *  - Find suitable P, I, D to move between two angle setpoints
 *  - Hardcode kg, P, I, D in ShooterArm
 */
public class ShooterArmTestRobot extends CommandRobotBase
{
  private ShooterArm arm = new ShooterArm();

  @Override
  public void robotInit()
  {
    super.robotInit();

    SmartDashboard.setDefaultNumber("Setpoint1", 0);
    SmartDashboard.setDefaultNumber("Setpoint2", 500);
    SmartDashboard.setDefaultNumber("Period", 5.0);
    SmartDashboard.setDefaultNumber("kg", 0.0);
    SmartDashboard.setDefaultNumber("P", 0.0);
    SmartDashboard.setDefaultNumber("I", 0.0);
    SmartDashboard.setDefaultNumber("D", 0.0);
  }

  @Override
  public void autonomousPeriodic()
  {
    // Every "Period", toggle between "Setpoint1" and "Setpoint2"
    double setpoint = ((System.currentTimeMillis() / (int)(SmartDashboard.getNumber("Period", 5.0)*1000)) % 2 == 1)
                    ? SmartDashboard.getNumber("Setpoint1", 0.0)
                    : SmartDashboard.getNumber("Setpoint2", 500.0);
    SmartDashboard.putNumber("Shooter Setpoint", setpoint);

    arm.configure(SmartDashboard.getNumber("kg", 0),
                      SmartDashboard.getNumber("P", 0),
                      SmartDashboard.getNumber("I", 0),
                      SmartDashboard.getNumber("D", 0));
    arm.setAngle(setpoint);
  }
}
