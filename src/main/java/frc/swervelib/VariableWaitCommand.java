// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervelib;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** Like 'WaitCommand', but with configurable delay
 *
 *  By default, it uses the "AutoDelay" dashboard entry,
 *  but can be used with different dashboard enty.
 */
public class VariableWaitCommand extends Command
{
  public Timer timer = new Timer();
  private NetworkTableEntry nt_entry;

  public VariableWaitCommand()
  {
    this("AutoDelay");
  }

  public VariableWaitCommand(String delay_name)
  {
    nt_entry = SmartDashboard.getEntry(delay_name);
    // If entry doesn't exist, create it and set to 0.0 seconds
    nt_entry.setDefaultDouble(0.0);
  }

  @Override
  public void initialize()
  {
    timer.restart();
    System.out.println(nt_entry.getName() + " = " + nt_entry.getDouble(0.0) + " seconds");
  }

  @Override
  public void end(boolean interrupted)
  {
    timer.stop();
  }

  @Override
  public boolean isFinished()
  {
    return timer.hasElapsed(nt_entry.getDouble(0.0));
  }

  @Override
  public boolean runsWhenDisabled()
  {
    return true;
  }
}
