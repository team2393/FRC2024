// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervelib;

import edu.wpi.first.wpilibj2.command.RunCommand;

/** Command for stopping all motors */
public class StopCommand extends RunCommand
{
  public StopCommand(SwerveDrivetrain drivetrain)
  {
    super(drivetrain::stop, drivetrain);
  }
}
