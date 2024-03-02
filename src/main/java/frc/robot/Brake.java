// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

/** Climber brake */
public class Brake
{
  private Solenoid brake = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.CLIMBER_BRAKE);

  private boolean move_left = false, move_right = false;

  public void setLeft(boolean move)
  {
    move_left = move;
    update();
  }

  public void setRight(boolean move)
  {
    move_right = move;
    update();
  }

  private void update()
  {
    brake.set(move_left || move_right);
  }
}
