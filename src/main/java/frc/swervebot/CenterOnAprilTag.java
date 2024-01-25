// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swervebot;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.swervelib.SwerveDrivetrain;

/** Command that swerves left/right to center on an april tag */
public class CenterOnAprilTag extends Command
{
  final private SwerveDrivetrain drivetrain;
  private final PhotonCamera camera;
  
  public CenterOnAprilTag(SwerveDrivetrain drivetrain)
  {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);

    camera = new PhotonCamera("HD_Pro_Webcam_C920");

    SmartDashboard.setDefaultNumber("CameraP", 0.1);
    SmartDashboard.setDefaultNumber("CameraMax", 0.3);
  }

  @Override
  public void execute()
  {
    // Query camera for target info, pick the largest (=closest) one as the best
    PhotonPipelineResult capture = camera.getLatestResult();
    double timestamp = capture.getTimestampSeconds();
    double now = Timer.getFPGATimestamp();

    PhotonTrackedTarget best = null;
    for (PhotonTrackedTarget target : capture.getTargets())
      if (best == null  ||  target.getArea() > best.getArea())
        best = target;

    if (best == null)
    {
      System.out.println("No target");
      return;
    }

    // TODO Drive such that we center on the target
    double yaw = best.getYaw();

    System.out.format("Now %.2f  Timestamp %.2f  Target %2d  Yaw %.1f\n",
                      now, timestamp, best.getFiducialId(), yaw);

    // Ignore small angles
    if (Math.abs(yaw) < 1)
      yaw = 0;
    // Negative yaw angle means target is to the left.
    // We need to serve in +Y direction to get closer.
    // Use prop. gain of 1, limit speed
    double P = SmartDashboard.getNumber("CameraP", 0.1);
    double max = SmartDashboard.getNumber("CameraMax", 0.3);
    double vy = MathUtil.clamp(-P*yaw, -max, max);
    drivetrain.swerve(0, vy, 0);
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
    drivetrain.swerve(0, 0, 0);
  }
}
