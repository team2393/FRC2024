// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tools.CommandRobotBase;

/** Shooter Test
 * 
 *  - Manually turn spinner while idle to
 *    calibrate Shooter.TURNS_PER_REV
 *  - In autonomous, find suitable ks & kv
 *  - Zero ks & kv, then find suitable I & D
 *  - Set both ks, kv and I, D
 */
public class ShooterTestRobot extends CommandRobotBase
{
  private Shooter shooter = new Shooter();

  @Override
  public void robotInit()
  {
    super.robotInit();

    SmartDashboard.setDefaultNumber("Setpoint1", 20);
    SmartDashboard.setDefaultNumber("Setpoint2", 30);
    SmartDashboard.setDefaultNumber("Period", 5.0);
    SmartDashboard.setDefaultNumber("ks", 0.6);
    SmartDashboard.setDefaultNumber("kv", 0.11);
    SmartDashboard.setDefaultNumber("P", 0.0);
    SmartDashboard.setDefaultNumber("I", 0.1);
    SmartDashboard.setDefaultNumber("D", 0.0);
    SmartDashboard.setDefaultNumber("Max I", 5.0);
  }

  @Override
  public void robotPeriodic()
  {
      super.robotPeriodic();
      SmartDashboard.putNumber("Turns", shooter.getTurns());
  }

  @Override
  public void disabledInit()
  {
    shooter.run(false);
  }

  @Override
  public void autonomousPeriodic()
  {
    // Every "Period", toggle between "Setpoint1" and "Setpoint2"
    double setpoint = ((System.currentTimeMillis() / (int)(SmartDashboard.getNumber("Period", 5.0)*1000)) % 2 == 1)
                    ? SmartDashboard.getNumber("Setpoint1", 0.0)
                    : SmartDashboard.getNumber("Setpoint2", 500.0);
    SmartDashboard.putNumber("Shooter Setpoint", setpoint);

    shooter.configure(SmartDashboard.getNumber("ks", 0),
                      SmartDashboard.getNumber("kv", 0),
                      SmartDashboard.getNumber("P", 0),
                      SmartDashboard.getNumber("I", 0),
                      SmartDashboard.getNumber("D", 0),
                      SmartDashboard.getNumber("Max I",0));
    shooter.run(true);
  }
}
