// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.swervelib.AbsoluteSwerveCommand;
import frc.swervelib.RelativeSwerveCommand;
import frc.swervelib.ResetHeadingCommand;
import frc.swervelib.SwerveDrivetrain;
import frc.swervelib.SwerveOI;
import frc.tools.AutoTools;
import frc.tools.CommandRobotBase;

/** The robot */
public class Robot extends CommandRobotBase
{
  private final RobotDrivetrain drivetrain = new RobotDrivetrain();
  private final Command relswerve = new RelativeSwerveCommand(drivetrain);
  private final Command absswerve = new AbsoluteSwerveCommand(drivetrain);
  
  private final PneumaticHub hub = new PneumaticHub();
  private final Intake intake = new Intake();
  private final Feeder feeder = new Feeder();
  private final Command open_intake = new OpenIntakeCommand(intake, feeder);

  private final Shooter shooter = new Shooter();
  private final Command shoot = new ShootCommand(feeder, shooter);
  private final ShooterArm shooter_arm = new ShooterArm();

  private final SendableChooser<Command> autos = new SendableChooser<>();

  @Override
  public void robotInit()
  {
    super.robotInit();

    // Speed settings:
    // SwerveDrivetrain controls maximum enforced by swerve(vx, vy, vr) and SwerveOI
    SwerveDrivetrain.MAX_METERS_PER_SEC = 1.5;
    SwerveDrivetrain.MAX_ROTATION_DEG_PER_SEC = 90;
    // Slew limiters for interactive moves
    SwerveOI.forward_slew = new SlewRateLimiter(1.5);
    SwerveOI.side_slew = new SlewRateLimiter(1.5);
    SwerveOI.rotation_slew = new SlewRateLimiter(90);
    // Maximum speed requested in autonomous moves (can't exceed MAX_METERS_PER_SEC)
    AutoTools.config = new TrajectoryConfig(1.5, 1.5);

    hub.enableCompressorAnalog(85, 120);

    OperatorInterface.reset();
    OperatorInterface.toggleIntake().toggleOnTrue(open_intake);
    OperatorInterface.fire().onTrue(shoot);

    autos.setDefaultOption("Nothing", new PrintCommand("Do nothing"));

    for (Command auto : AutoNoMouse.createAutoCommands(drivetrain))
      autos.addOption(auto.getName(), auto);
    SmartDashboard.putData(autos);
  }

  @Override
  public void robotPeriodic()
  {
    super.robotPeriodic();
    SmartDashboard.putNumber("Pressure", hub.getPressure(0));
  }
  
  @Override
  public void disabledPeriodic()
  {
    AutoTools.indicateStart(drivetrain, autos.getSelected());
  }  

  @Override
  public void teleopInit()
  {
    // Bind buttons to commands
    OperatorInterface.selectRelative().onTrue(relswerve);
    OperatorInterface.selectAbsolute().onTrue(absswerve);
    OperatorInterface.resetHeading().onTrue(new ResetHeadingCommand(drivetrain));

    // Start relative mode
    relswerve.schedule();
  }

  @Override
  public void autonomousInit()
  {
    autos.getSelected().schedule();
  }
}
