// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;

import frc.swervelib.SwerveDrivetrain;
import frc.swervelib.SwerveModule;

/** Robot Drivetrain */
@SuppressWarnings("removal")
public class RobotDrivetrain extends SwerveDrivetrain
{
 private final Pigeon2 gyro = new Pigeon2(0);

  public RobotDrivetrain()
  {
    super(0.5906, // old numbers: width = 0.535, length = 0.542
          0.5906,
          new SwerveModule(new Rotator(0, RobotMap.FRONT_LEFT_ROTATE,  RobotMap.FRONT_LEFT_ANGLE, 218.9),
                           new Driver (0, RobotMap.FRONT_LEFT_DRIVE)),
          new SwerveModule(new Rotator(1, RobotMap.FRONT_RIGHT_ROTATE, RobotMap.FRONT_RIGHT_ANGLE, -173.1),
                           new Driver (1, RobotMap.FRONT_RIGHT_DRIVE)),
          new SwerveModule(new Rotator(2, RobotMap.BACK_RIGHT_ROTATE,  RobotMap.BACK_RIGHT_ANGLE, -159.01),
                           new Driver (2, RobotMap.BACK_RIGHT_DRIVE)),
          new SwerveModule(new Rotator(3, RobotMap.BACK_LEFT_ROTATE,   RobotMap.BACK_LEFT_ANGLE, 2),
                           new Driver (3, RobotMap.BACK_LEFT_DRIVE))
          );
  }

  public double getRawHeading()
  {
   return gyro.getYaw();
  }

  public double getPitch()
  {
    return gyro.getPitch();
  }

  public double getRoll()
  {
    return gyro.getRoll();
  }
}
