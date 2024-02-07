// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;

/** Command to close intake */
public class CloseIntakeCommand extends InstantCommand
{
  public CloseIntakeCommand(Intake intake, Feeder feeder)
  {
    super(() ->
    {
      intake.open(false);
      feeder.run(false);
    }, intake, feeder);
  }
}
