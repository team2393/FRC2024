// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.tools;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** Command that applies a bunch of settings */
public class ApplySettingsCommand extends Command
{
  private final List<String> settings = new ArrayList<>();
  private final List<Double> values = new ArrayList<>();

  public ApplySettingsCommand(final String name)
  {
    setName(name);
  }

  public void add(final String name, final double value)
  {
    settings.add(name);
    values.add(value);
  }

  @Override
  public boolean runsWhenDisabled()
  {
    return true;
  }

  @Override
  public void initialize()
  {
    for (int i=0; i<settings.size(); ++i)
      SmartDashboard.putNumber(settings.get(i), values.get(i));
  }

  @Override
  public boolean isFinished()
  {
    return true;
  }
}
