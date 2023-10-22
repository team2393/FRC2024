// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervelib;

import edu.wpi.first.wpilibj2.command.InstantCommand;

/** Command to reset drivetrain to all zero */
public class ResetPositionCommand extends InstantCommand
{
  public ResetPositionCommand(SwerveDrivetrain drivetrain)
  {
    super(drivetrain::reset);
    setName("ResetPosition");
  }

  @Override
  public boolean runsWhenDisabled()
  {
    return true;
  }
}
