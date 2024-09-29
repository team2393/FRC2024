// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** Command to shoot a game piece */
public class SetShooterSpeedCommand extends Command
{
  private int shooterSpeed;

  public SetShooterSpeedCommand(int shooterSpeed)
  {
    this.shooterSpeed = shooterSpeed;
  }

  @Override
  public void initialize()
  {
    // do nothing
  }

  @Override
  public void execute()
  {
    SmartDashboard.putNumber("Shooter Setpoint", shooterSpeed);
  }

  @Override
  public boolean isFinished()
  {
    // After ... seconds, assume the game piece has been emitted
    return SmartDashboard.getEntry("Shooter Setpoint").getDouble(50) == shooterSpeed;
  }

  @Override
  public void end(boolean interrupted)
  {
    // do nothing
  }
}
