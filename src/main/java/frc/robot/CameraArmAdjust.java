// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers.LimelightTarget_Fiducial;
import frc.swervebot.CenterOnAprilTag;

/** Command that adjusts arm angle from april tag */
public class CameraArmAdjust extends Command
{
  final private ShooterArm arm;

  // Map of 'distance' to arm angle
  // We use the 'ty' angle of the april tag within the camera image
  // to estimate the distance to the target.
  // This map holds certain 'ty' readings and the appropriate arm angle,
  // and can then interpolate between them.
  static final private InterpolatingDoubleTreeMap map = new InterpolatingDoubleTreeMap();
  static
  {
    //       ty,  arm angle
    map.put(-0.03, 55.0);
    map.put(-6.0, 50.0);
    map.put(-10.0, 45.0);
    map.put(-13.75, 40.0);
    map.put(-16.0, 36.27);
    map.put(-18.75, 33.75);
    map.put(-20.5, 31.5);
    map.put(-21.5, 29.99);
    map.put(-23.0, 28.0);
    map.put(-23.75, 26.5);

    // map.put(-10.0, 45.0);
    // map.put(-15.0, 39.0);
    // map.put(-20.0, 32.0);
    // map.put(-25.0, 25.0);
  }

  public CameraArmAdjust(ShooterArm arm)
  {
    this.arm = arm;
  }

  @Override
  public void execute()
  {
    LimelightTarget_Fiducial tag = CenterOnAprilTag.findSuitableTag();
    if (tag == null)
      return;      
      
    double ty = tag.ty;
    double angle = map.get(ty);
    // System.out.format("TY %6.2f -> Arm angle %6.2f\n", ty, angle);
    arm.setAngle(angle);
      
    SmartDashboard.putNumber("Shooter Setpoint", 55);
  }
}
