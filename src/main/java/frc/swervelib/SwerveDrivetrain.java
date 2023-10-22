// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervelib;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

/** Swerve module drive train */
abstract public class SwerveDrivetrain extends SubsystemBase
{
  /** Center of robot */
  public static Translation2d CENTER = new Translation2d(0, 0);
  
  /** Maximum swerve speed of this drivetrain */
  public static double MAX_METERS_PER_SEC = 3.0;

  /** Speed when going slow (Joystick POV) */
  public static double CRAWL_SPEED = 0.15;

  /** Maximum rotational speed of this drivetrain */
  public static int MAX_ROTATION_DEG_PER_SEC = 90;

  /** Don't rotate swerve module unless speed is at least this
   *  to avoid spinning in place
   */
  public static double MINIMUM_SPEED_THRESHOLD = .05;

  /** Position on field */
  private final NetworkTableEntry nt_x = SmartDashboard.getEntry("X");
  private final NetworkTableEntry nt_y = SmartDashboard.getEntry("Y");
  private final NetworkTableEntry nt_heading = SmartDashboard.getEntry("Heading");
  private final NetworkTableEntry nt_set_pose = SmartDashboard.getEntry("SetPose");
  private final NetworkTableEntry nt_set_x = SmartDashboard.getEntry("SetX");
  private final NetworkTableEntry nt_set_y = SmartDashboard.getEntry("SetY");
  private final NetworkTableEntry nt_set_heading = SmartDashboard.getEntry("SetHeading");

  private final Field2d field = new Field2d();

  /** Trajectory follower P gains */
  private final NetworkTableEntry nt_xy_p = SmartDashboard.getEntry("Traj XY P");
  private final NetworkTableEntry nt_angle_p = SmartDashboard.getEntry("Traj Angle P");

  /** Rectangle where modules are on the corners */
  private final double width, length;

  /** Front left, front right, back right, back left module */
  private final SwerveModule[] modules;

  /** Zero offset for gyro in degrees */
  private double zero_heading = 0.0;

  /** Simulated gyro angle in degrees */
  private double simulated_heading = 0.0;

  /** Kinematics that translate chassis speed to module settings and vice versa */
  private final SwerveDriveKinematics kinematics;

  /** Position tracker */
  // private final SwerveDriveOdometry odometry;
  private final SwerveDrivePoseEstimator odometry;

  /** Origin used by {@link #createTrajectoryCommand} */
  private Pose2d trajectory_origin = new Pose2d();

  /** @param width Width of the rectangle where modules are on corners in meters
   *  @param length Length of that rectangle in meters
   *  @param modules Front left, front right, back right, back left module
   */
  public SwerveDrivetrain(double width, double length, SwerveModule[] modules)
  {
    this.width = width;
    this.length = length;
    this.modules = modules;
    
    kinematics = new SwerveDriveKinematics(new Translation2d( length / 2,  width / 2),
                                           new Translation2d( length / 2, -width / 2),
                                           new Translation2d(-length / 2, -width / 2),
                                           new Translation2d(-length / 2,  width / 2) );

    // odometry = new SwerveDriveOdometry(kinematics, new Rotation2d(), getPositions());
    // Default errors are 0.1 for state  vs. 0.9 for vision
    odometry = new SwerveDrivePoseEstimator(kinematics, new Rotation2d(), getPositions(), new Pose2d(),
                                            VecBuilder.fill(0.05, 0.05, 0.05),
                                            VecBuilder.fill(0.95, 0.95, 0.95));

    // Publish command to reset position
    SmartDashboard.putData(new ResetPositionCommand(this));

    // Publish field
    SmartDashboard.putData(field);

    // Gain settings
    nt_xy_p.setDefaultDouble(1.0);
    nt_angle_p.setDefaultDouble(5.0);

    // Entries for setting the pose (while disabled)
    nt_set_pose.setDefaultBoolean(false);
    nt_set_x.setDefaultDouble(0.0);
    nt_set_y.setDefaultDouble(0.0);
    nt_set_heading.setDefaultDouble(0.0);

    // When no other command uses the drivetrain, stay put with modules pointed to 0.0
    setDefaultCommand(new StayPutCommand(this, 0.0));
  }

  /** @return Width of the rectangle where modules are on corners in meters */
  public double getWidth()
  {
    return width;
  }

  /** @return Length of the rectangle where modules are on corners in meters */
  public double getLength()
  {
    return length;
  }

  /** Reset position tracker */
  public void reset()
  {
    zero_heading = getRawHeading();
    simulated_heading = 0.0;
    for (int i=0; i<modules.length; ++i)
      modules[i].resetPosition();
    odometry.resetPosition(getHeading(), getPositions(), new Pose2d());
    trajectory_origin = new Pose2d();
  }

  /** Set odometry to position
   *  @param x [m]
   *  @param y [m]
   *  @param heading [degrees]
   */
  public void setOdometry(double x, double y, double heading)
  {
    odometry.resetPosition(getHeading(), getPositions(), new Pose2d(x, y, Rotation2d.fromDegrees(heading)));
  }

  /** @return Heading of gyro in degrees, counter-clockwise, not corrected for zero heading */
  abstract public double getRawHeading();

  /** @return Pitch angle, i.e., angle of robot being "nose up" in degrees */
  abstract public double getPitch();

  /** @return Roll angle, i.e., angle of "left" side of robot being "up" in degrees */
  abstract public double getRoll();

  /** @return Heading of robot on field (relative to last "reset") */
  public Rotation2d getHeading()
  {
    if (RobotBase.isSimulation())
      return Rotation2d.fromDegrees(simulated_heading);
    return Rotation2d.fromDegrees(getRawHeading() - zero_heading);
  }

  /** @return Positions of the swerve modules */
  private SwerveModulePosition[] getPositions()
  {
    final SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];
    for (int i=0; i<modules.length; ++i)
      positions[i] = modules[i].getPosition();
    return positions;
  }

  /** @return Position of drivetrain on field (from odometry) */
  public Pose2d getPose()
  {
    // return odometry.getPoseMeters();
    return odometry.getEstimatedPosition();
  }

  /** @param robot_position Robot's position on field as estimated by camera
   *  @param timestamp Based on Timer.getFPGATimestamp()
  */
  public void updateLocationFromCamera(Pose2d robot_position, double timestamp)
  {
    odometry.addVisionMeasurement(robot_position, timestamp);
  }

  /** Lock modules in "diamond" pattern to prevent rolling */
  public void lock()
  {
    modules[0].drive(-45, 0.0);
    modules[1].drive(45, 0.0);
    modules[2].drive(-45, 0.0);
    modules[3].drive(45, 0.0);
  }

  /** Drive all modules with same angle and speed
   *  @param angle Swerve module angle in degrees
   *  @param speed Swerve module speed in meters per second
   */
  public void drive(double angle, double speed)
  {
    for (SwerveModule module : modules)
        module.drive(angle, speed);
  }

  /** Swerve, rotating around center
   *  @param vx Speed in 'X' (forward/back) direction [m/s]
   *  @param vy Speed in 'Y' (left/right) direction [m/s]
   *  @param vr Speed for rotation [rad/s]
   */
  public void swerve(double vx, double vy, double vr)
  {
    swerve(vx, vy, vr, CENTER);
  }

  /** Swerve
   *  @param vx Speed in 'X' (forward/back) direction [m/s]
   *  @param vy Speed in 'Y' (left/right) direction [m/s]
   *  @param vr Speed for rotation [rad/s]
   *  @param center Center of rotation
   */
  public void swerve(double vx, double vy, double vr, Translation2d center)
  {
    // Translate desired chassis movement to settings of the 4 swerve modules
    SwerveModuleState[] states = kinematics.toSwerveModuleStates(new ChassisSpeeds(vx, vy, vr), center);

    for (int i=0; i<modules.length; ++i)
    {
      // Optimize module rotation
      states[i] = SwerveModuleState.optimize(states[i], modules[i].getAngle());

      // Actually moving? Then rotate as requested
      if (Math.abs(states[i].speedMetersPerSecond) < MINIMUM_SPEED_THRESHOLD)
          states[i] = new SwerveModuleState(0, modules[i].getAngle());
    }

    SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_METERS_PER_SEC);

    for (int i=0; i<modules.length; ++i)
      modules[i].drive(states[i].angle.getDegrees(),
                       states[i].speedMetersPerSecond);
    
    if (RobotBase.isSimulation())
    {
      double adjusted_vr = Math.toDegrees(kinematics.toChassisSpeeds(states).omegaRadiansPerSecond);
      simulated_heading += adjusted_vr * TimedRobot.kDefaultPeriod;
    }
  }

  @Override
  public void periodic()
  {
    // Update and publish position
    odometry.update(getHeading(), getPositions());

    // While disabled, allow setting X, Y, Heading
    if (DriverStation.isDisabled())
    { // Pushing the "SetPose" button updates pose to entered values
      if (nt_set_pose.getBoolean(false))
      {
        setOdometry(nt_set_x.getDouble(0.0), nt_set_y.getDouble(0.0), nt_set_heading.getDouble(0.0));
        // Reset the button to "acknowledge" and only set position once
        nt_set_pose.setBoolean(false);
      }
    }

    Pose2d pose = getPose();
    nt_x.setDouble(pose.getX());
    nt_y.setDouble(pose.getY());
    nt_heading.setDouble(pose.getRotation().getDegrees());

    // Example for simulating different origin
    // pose = new Pose2d(pose.getTranslation().rotateBy(Rotation2d.fromDegrees(90))
    //                                        .plus(new Translation2d(6, 4)),
    //                   getHeading().plus(Rotation2d.fromDegrees(90)));

    field.setRobotPose(pose);
  }

  /** @param new_origin New origin for trajectory commands
   *  @see #createTrajectoryCommand
   */
  public void setTrajectoryOrigin(Pose2d new_origin)
  {
    trajectory_origin = new_origin;
  }

  /** @param trajectory Trajectory to follow
   *  @param end_angle Final heading angle
   *  @return Command that follows the trajectory
   */
  public Command createTrajectoryCommand(Trajectory trajectory, double end_angle)
  {
    return createTrajectoryCommand(trajectory, end_angle, true);
  }

  /** @param trajectory Trajectory to follow
   *  @param end_angle Final heading angle
   *  @param require_drivetrain Command should require drivetrain unless it's inside a proxy that already holds the drivetrain
   *  @return Command that follows the trajectory
   */
  public Command createTrajectoryCommand(Trajectory trajectory, double end_angle, boolean require_drivetrain)
  {
    // SwerveControllerCommand will basically send the speed at each point of the
    // trajectory to the serve modules, using many little helpers

    // Controllers that correct for the x, y and angle to match the trajectory
    // in case simply using the suggested wheel speed settings aren't
    // perfectly placing us on the trajectory
    PIDController x_pid = new PIDController(nt_xy_p.getDouble(0), 0, 0);
    PIDController y_pid = new PIDController(nt_xy_p.getDouble(0), 0, 0);
    // Angle controller is 'profiled', allowing up to 90 deg/sec (and 90 deg/sec/sec acceleration) 
    ProfiledPIDController angle_pid = new ProfiledPIDController(
      nt_angle_p.getDouble(0), 0, 0,
      new TrapezoidProfile.Constraints(Math.toRadians(90), Math.toRadians(90)));
    // ..and 'continuous' because angle wraps around
    angle_pid.enableContinuousInput(-Math.PI, Math.PI);

    // Called by SwerveControllerCommand to check where we are.
    // We return our position relative to a 'trajectory origin'
    // which starts out as 0, 0, 0 but may be updated
    Supplier<Pose2d> pose_getter = () -> getPose().relativeTo(trajectory_origin);

    // Called by SwerveControllerCommand to tell us what modules should do
    Consumer<SwerveModuleState[]> module_setter = states ->
    {
        for (int i=0; i<modules.length; ++i)
        {
          SwerveModuleState optimized = SwerveModuleState.optimize(states[i], modules[i].getAngle());
          modules[i].drive(optimized.angle.getDegrees(),
                           optimized.speedMetersPerSecond);
        }
        double vr = Math.toDegrees(kinematics.toChassisSpeeds(states).omegaRadiansPerSecond);
        simulated_heading += vr * TimedRobot.kDefaultPeriod;
    };

    // Called by SwerveControllerCommand to check at what angle we want to be
    Supplier<Rotation2d> desiredRotation = () -> Rotation2d.fromDegrees(end_angle);

    // Create command that follows the trajectory
    SwerveControllerCommand follower =  new SwerveControllerCommand(trajectory, pose_getter, kinematics,
                                                                    x_pid, y_pid, angle_pid,
                                                                    desiredRotation, module_setter);
    if (require_drivetrain)
      follower.addRequirements(this);
    return follower;
  }
}
