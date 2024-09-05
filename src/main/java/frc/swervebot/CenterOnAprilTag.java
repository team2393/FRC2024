// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervebot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.LimelightResults;
import frc.robot.LimelightHelpers.LimelightTarget_Fiducial;
import frc.swervelib.SwerveDrivetrain;

/** Command that swerves left/right to center on an april tag */
public class CenterOnAprilTag extends Command
{
  final private static String camera = "limelight-front";
  final private SwerveDrivetrain drivetrain;
  private boolean done = false;
  
  public CenterOnAprilTag(SwerveDrivetrain drivetrain)
  {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    
    SmartDashboard.setDefaultNumber("CameraP", 0.1);
    SmartDashboard.setDefaultNumber("CameraMax", 4.0);
  }

  public static LimelightTarget_Fiducial findSuitableTag()
  {
    // Does camera report anything?
    LimelightResults results = LimelightHelpers.getLatestResults(camera);
    if (! results.targetingResults.valid)
      return null;

    // Looks for info on tags 4 or 7 which are in the center of the 'speaker'
    for (LimelightTarget_Fiducial tag : results.targetingResults.targets_Fiducials)
      if (tag.fiducialID == 4  ||  tag.fiducialID == 7)
        return tag;

    return null;
  }

  @Override
  public void execute()
  {
    LimelightTarget_Fiducial tag = findSuitableTag();
    if (tag == null)
      return;
  
    double yaw = tag.tx;
    
    // Ignore small angles
    done = Math.abs(yaw) < 1;
    if (done)
      return;
    
    // Drive such that we center on the target
    // Negative yaw angle means target is to the left.
    // We need to serve in +Y direction to get closer.
    double P = SmartDashboard.getNumber("CameraP", 0.1);
    double max = SmartDashboard.getNumber("CameraMax", 0.3);
    double vr = MathUtil.clamp(-P*yaw, -max, max);
    drivetrain.swerve(0, 0, vr);

    // Instead of simply driving left/right,
    // we could try something more sophisticated...

    // Update estimated field position
    // LimelightHelpers.PoseEstimate estimate = LimelightHelpers.getBotPoseEstimate_wpiBlue(camera);
    // drivetrain.updateLocationFromCamera(estimate.pose, estimate.timestampSeconds);
  }

  @Override
  public boolean isFinished()
  {
    return done;
  }

  @Override
  public void end(boolean interrupted)
  {
    drivetrain.stop();
  }
}
