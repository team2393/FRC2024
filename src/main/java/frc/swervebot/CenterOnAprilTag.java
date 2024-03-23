// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervebot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.swervelib.SwerveDrivetrain;

/** Command that swerves left/right to center on an april tag */
public class CenterOnAprilTag extends Command
{
  final private static String camera = "limelight-front";
  final private SwerveDrivetrain drivetrain;
  final private AprilTagFieldLayout tags;
  private boolean done = false;
  
  public CenterOnAprilTag(SwerveDrivetrain drivetrain)
  {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    
    tags = AprilTagFields.kDefaultField.loadAprilTagLayoutField();

    SmartDashboard.setDefaultNumber("CameraP", 0.05);
    SmartDashboard.setDefaultNumber("CameraMax", 1.0);
  }


  @Override
  public void execute()
  {
    if (! LimelightHelpers.getTV(camera))
      return;

    double yaw = LimelightHelpers.getTX(camera);
    
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
    LimelightHelpers.PoseEstimate estimate = LimelightHelpers.getBotPoseEstimate_wpiBlue(camera);
    drivetrain.updateLocationFromCamera(estimate.pose, estimate.timestampSeconds);

    // Get tag info
    int tag = (int) LimelightHelpers.getFiducialID(camera);
    Pose3d tagpose = tags.getTagPose(tag).orElse(null);
    System.out.println("Tag " + tag + " is at " + tagpose);
    // We could drive to a position specific to each tag.
    // If the tag is a speaker, move to 1 m in front of that tag.
    // If the tag is a pickup station, move to 0.5 m in front of that tag...
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
