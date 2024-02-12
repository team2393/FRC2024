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
    super(0.535, // for the new measure I got 0.5906 meters for both l and w
          0.542,
          new SwerveModule(new Rotator(0, RobotMap.FRONT_LEFT_ROTATE,  RobotMap.FRONT_LEFT_ANGLE,   -162.15 + 180),
                           new Driver (0, RobotMap.FRONT_LEFT_DRIVE)),
          new SwerveModule(new Rotator(1, RobotMap.FRONT_RIGHT_ROTATE, RobotMap.FRONT_RIGHT_ANGLE,  -176.6),
                           new Driver (1, RobotMap.FRONT_RIGHT_DRIVE)),
          new SwerveModule(new Rotator(2, RobotMap.BACK_RIGHT_ROTATE,  RobotMap.BACK_RIGHT_ANGLE,   42.978),
                           new Driver (2, RobotMap.BACK_RIGHT_DRIVE)),
          new SwerveModule(new Rotator(3, RobotMap.BACK_LEFT_ROTATE,   RobotMap.BACK_LEFT_ANGLE,    -173.583 + 180),
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
