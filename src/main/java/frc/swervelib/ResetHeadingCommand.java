// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervelib;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/** Command to reset gyro heading */
public class ResetHeadingCommand extends InstantCommand
{
  /** @param drivetrain */
  public ResetHeadingCommand(SwerveDrivetrain drivetrain)
  {
    super(() ->
    {
      if (DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red)
        drivetrain.resetHeading(180);
      else
        drivetrain.resetHeading(0);
    });
    // Do NOT require drivetrain
    // Just reset heading then continue with active drive command
  }
}
