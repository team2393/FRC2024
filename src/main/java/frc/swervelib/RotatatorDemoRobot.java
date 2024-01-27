// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervelib;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tools.CommandRobotBase;

/** Robot for testing rotation motors
 *
 *  Verify that positive voltage runs motor counterclockwise as viewed from above.
 *  Calibrate angle sensor, positive is CCW.
 *  Zero angle when module points forward.
 *  Tune PID in auto to meet desired angle.
 */
public class RotatatorDemoRobot extends CommandRobotBase
{
  private final RotatorBase rotator;
  private final XboxController joystick = new XboxController(0);

  public RotatatorDemoRobot(RotatorBase rotator)
  {
    this.rotator = rotator;
    SmartDashboard.setDefaultNumber("Period", 5.0);
    SmartDashboard.setDefaultNumber("Setpoint1", 0.0);
    SmartDashboard.setDefaultNumber("Setpoint2", 90.0);
  }

  @Override
  public void teleopPeriodic()
  {
    if (joystick.getAButton())
      rotator.setAngle(Math.toDegrees(Math.atan2(-joystick.getLeftX(),
                                                 -joystick.getLeftY())));
    else
    {
      double voltage = 6.0 * joystick.getLeftX();
      SmartDashboard.putNumber("voltage", voltage);
      rotator.setVoltage(voltage);
    }
  }

  @Override
  public void autonomousPeriodic()
  {
    double setpoint = ((System.currentTimeMillis() / (int)(SmartDashboard.getNumber("Period", 5.0)*1000)) % 2 == 1)
                    ? SmartDashboard.getNumber("Setpoint1", 0.0)
                    : SmartDashboard.getNumber("Setpoint2", 90.0);
    rotator.setAngle(setpoint);
    SmartDashboard.putNumber("Setpoint", setpoint);
  }
}
