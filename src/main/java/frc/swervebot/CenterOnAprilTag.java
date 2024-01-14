// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervebot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.swervelib.SwerveDrivetrain;

/** Command that swerves left/right to center on an april tag */
public class CenterOnAprilTag extends Command
{
  final private SwerveDrivetrain drivetrain;
  final private NetworkTableEntry nt_valid, nt_yaw;

  public CenterOnAprilTag(SwerveDrivetrain drivetrain)
  {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);

    NetworkTable nt = NetworkTableInstance.getDefault().getTable("photonvision");
    nt = nt.getSubTable("HD_Pro_Webcam_C920");
    nt_valid = nt.getEntry("hasTarget");
    nt_yaw = nt.getEntry("targetYaw");
  }

  @Override
  public void execute()
  {
    boolean valid = nt_valid.getBoolean(false);
    if (!valid)
      return;
    
    double yaw = nt_yaw.getDouble(0.0);
    // TODO Drive such that we center on the target
    System.out.println("Yaw to target: " + yaw);
  }

  @Override
  public boolean isFinished()
  {
    // TODO Are we done?
    return false;
  }

  @Override
  public void end(boolean interrupted)
  {
    // TODO Anything to do when done?
  }
}
