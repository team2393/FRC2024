// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervebot;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.RobotBase;
import frc.swervelib.SwerveDrivetrain;
import frc.swervelib.SwerveModule;

/** SwerveDrivetrain with Rotator using SparkMini for rotation, Falcon to drive, older pigeon as gyro */
public class SwervebotDrivetrain extends SwerveDrivetrain
{
  private final PigeonIMU gyro = new PigeonIMU(0);

  public SwervebotDrivetrain()
  {
    super(0.393,
          0.416,
          new SwerveModule(new Rotator(0,  -16), new Driver(0)),
          new SwerveModule(new Rotator(1,   89), new Driver(1)),
          new SwerveModule(new Rotator(2, -161), new Driver(2)),
          new SwerveModule(new Rotator(3, -108), new Driver(3))
          );
  }

  public double getRawHeading()
  {
    return gyro.getFusedHeading();
  }

  public double getPitch()
  {
    if (RobotBase.isSimulation())
    {
      // Leftover example from 2023:
      // Coming from the right, headed left, simulate driving 'up' and then 'down' the charge station

      Pose2d pose = getPose();
      double x  = pose.getTranslation().getX();
      double heading = pose.getRotation().getDegrees();

      if (Math.abs(Math.IEEEremainder(heading - 180.0, 360)) < 10.0)
      { // From the right, go 'up'
        if (4.03 < x  &&  x < 4.9)
          return 30.0;
        // Then flat from 4.03 to 3.66, then down
        if (2.8  < x  &&   x < 3.66)
          return -30.0;
      }
    }
    return -gyro.getPitch();
  }

  public double getRoll()
  {
    return -gyro.getRoll();
  }
}
