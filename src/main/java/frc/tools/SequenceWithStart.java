// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.tools;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** Command group with start position */
public class SequenceWithStart extends SequentialCommandGroup
{
  private final Pose2d start;

  /** @param name Name of command
   *  @param x X pos in meters
   *  @param y Y pos in meters
   *  @param heading Final heading in degrees
   *  @param commands Commands
   */
  public SequenceWithStart(String name, double x, double y, double heading, Command ... commands)
  {
    setName(name);
    start = new Pose2d(x, y, Rotation2d.fromDegrees(heading));
    addCommands(commands);
  }

  public Pose2d getStart()
  {
    return start;
  }
}
