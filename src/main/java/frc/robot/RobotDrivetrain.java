// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import frc.swervelib.SwerveDrivetrain;
import frc.swervelib.SwerveModule;

/** Robot Drivetrain */
public class RobotDrivetrain extends SwerveDrivetrain
{
// TODO  private final PigeonIMU gyro = new PigeonIMU(0);

  public RobotDrivetrain()
  {
    super(0.393,
          0.416,
          new SwerveModule(new Rotator(0, RobotMap.FRONT_LEFT_ROTATE,  RobotMap.FRONT_LEFT_ANGLE,   0.0),
                           new Driver (0, RobotMap.FRONT_LEFT_DRIVE)),
          new SwerveModule(new Rotator(1, RobotMap.FRONT_RIGHT_ROTATE, RobotMap.FRONT_RIGHT_ANGLE,  0.0),
                           new Driver (1, RobotMap.FRONT_RIGHT_DRIVE)),
          new SwerveModule(new Rotator(2, RobotMap.BACK_RIGHT_ROTATE,  RobotMap.BACK_RIGHT_ANGLE,   0.0),
                           new Driver (2, RobotMap.BACK_RIGHT_DRIVE)),
          new SwerveModule(new Rotator(3, RobotMap.BACK_LEFT_ROTATE,   RobotMap.BACK_LEFT_ANGLE,    0.0),
                           new Driver (3, RobotMap.BACK_LEFT_DRIVE))
          );
  }

  public double getRawHeading()
  {
    return 0.0;
 // TODO   return gyro.getFusedHeading();
  }

  public double getPitch()
  {
    return 0.0;
    // TODO  return -gyro.getPitch();
  }

  public double getRoll()
  {
    return 0.0;
    // TODO  return -gyro.getRoll();
  }
}
