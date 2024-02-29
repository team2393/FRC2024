// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

/** Command to shoot a game piece */
public class ShootCommand extends Command
{
  private Feeder feeder;
  private Shooter shooter;

  private enum State
  {
    // Wait for shooter to reach desired speed
    SPINUP,
    // Shoot
    SHOOT
  };
  private State state;
  private Timer timer = new Timer();

  public ShootCommand(Feeder feeder, Shooter shooter)
  {
    this.feeder = feeder;
    this.shooter = shooter;
    addRequirements(feeder, shooter);
  }

  @Override
  public void initialize()
  {
    state = State.SPINUP;
    timer.restart();
    // Ask shooter to get to desired speed, but don't feed a game piece, yet
    shooter.run(true);
    feeder.run(false);
  }

  @Override
  public void execute()
  {
    if (state == State.SPINUP)
    { // Move on when at desired speed or after timeout.
      if (shooter.atDesiredSpeed()  ||  timer.hasElapsed(5))
      {
        state = State.SHOOT;
        timer.restart();
      }
    }

    if (state == State.SHOOT)
    { // Keep shooter running and feed a game piece
      feeder.run(true);
    }
  }

  @Override
  public boolean isFinished()
  {
    // After ... seconds, assume the game piece has been emitted
    return state == State.SHOOT  &&  timer.hasElapsed(2);
  }

  @Override
  public void end(boolean interrupted)
  {
    feeder.run(false);
    shooter.run(false);
  }
}
