// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.swervebot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.CommandRobotBase;
import frc.swervelib.AbsoluteSwerveCommand;
import frc.swervelib.RelativeSwerveCommand;
import frc.swervelib.SwerveOI;
import frc.swervelib.SwerveToPositionCommand;
import frc.swervelib.VariableWaitCommand;

/** ServeModule demo: All modules run at same speed and heading*/
public class SwerveBot extends CommandRobotBase
{
  private final SwervebotDrivetrain drivetrain = new SwervebotDrivetrain();
  private final Command relswerve = new RelativeSwerveCommand(drivetrain);
  private final Command absswerve = new AbsoluteSwerveCommand(drivetrain);
  
  private final Command auto = new SequentialCommandGroup(
    new VariableWaitCommand(),
    new SwerveToPositionCommand(drivetrain, 0, 0));
  
  @Override
  public void teleopInit()
  {
    // Bind buttons to commands
    SwerveOI.selectRelative().onTrue(relswerve);
    SwerveOI.selectAbsolute().onTrue(absswerve);
    // Start relative mode
    relswerve.schedule();
  }

  @Override
  public void autonomousInit()
  {
    auto.schedule();
  }
}
