// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

/** Command to open intake and pick up game piece */
public class OpenIntakeCommand extends Command
{
  private Intake intake;
  private Feeder feeder;

  public OpenIntakeCommand(Intake intake, Feeder feeder)
  {
    this.intake = intake;
    this.feeder = feeder;
    addRequirements(intake, feeder);
  }

  @Override
  public void initialize()
  {
    intake.open(true);
    feeder.run(true);
  }

  @Override
  public boolean isFinished()
  {
    return feeder.haveGamePiece();
  }

  @Override
  public void end(boolean interrupted)
  {
    feeder.run(false);
    intake.open(false);
  }
}
