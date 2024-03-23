// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** Command that adjusts arm angle from april tag */
public class CameraArmAdjust extends Command
{
  final private static String camera = "limelight-front";
  final private ShooterArm arm;

  // Map of 'distance' to arm angle
  // We use the 'ty' angle of the april tag within the camera image
  // to estimate the distance to the target.
  // This map holds certain 'ty' readings and the appropriate arm angle,
  // and can then interpolate between them.
  static final private InterpolatingDoubleTreeMap map = new InterpolatingDoubleTreeMap();
  static
  {
    // TODO: Drive robot to several 'ty' and find a good arm angle for each
    //       ty,  arm angle
    map.put( -0.60, 55.0);
    map.put(-10.0, 45.0);
    map.put(-15.0, 39.0);
    map.put(-20.0, 32.0);
    map.put(-25.0, 25.0);
  }

  public CameraArmAdjust(ShooterArm arm)
  {
    this.arm = arm;
      
  }


  @Override
  public void execute()
  {
    if (! LimelightHelpers.getTV(camera))
      return;      
      
      double ty = LimelightHelpers.getTY(camera);
      double angle = map.get(ty);
      // System.out.format("TY %6.2f -> Arm angle %6.2f\n", ty, angle);
      arm.setAngle(angle);
      
      SmartDashboard.putNumber("Shooter Setpoint", angle < 40 ? 55 : 50);
  }
}
