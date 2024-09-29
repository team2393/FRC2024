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
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.swervebot.CenterOnAprilTag;
import frc.swervelib.AbsoluteSwerveCommand;
import frc.swervelib.RelativeSwerveCommand;
import frc.swervelib.ResetHeadingCommand;
import frc.swervelib.StopCommand;
import frc.swervelib.SwerveDrivetrain;
import frc.swervelib.SwerveOI;
import frc.tools.ApplySettingsCommand;
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
  private final Command reverse = new StartEndCommand(() -> intake.reverse(true),
                                                      () -> intake.reverse(false));

  private final Shooter shooter = new Shooter();
  private final Command shoot = new ShootCommand(feeder, shooter);
  private final ShooterArm shooter_arm = new ShooterArm();
  private final Command bumper_shoot = new StartEndCommand(() -> shooter_arm.setAngle(55),
                                                           () -> shoot.schedule());
  private final Command april_tag_shoot = new StartEndCommand(() -> shooter_arm.setAngle(55),
                                                           () -> shoot.schedule());

  private final Brake brake = new Brake();
  private final Climber climber = new Climber(true, brake::setLeft);
  private final Climber climber2 = new Climber(false, brake::setRight);
  private final Command leftUp =  climber.getUpCommand();
  private final Command rightUp =  climber2.getUpCommand();
  private final Command leftDown = climber.getDownCommand();
  private final Command rightDown = climber2.getDownCommand();
  private final Command cameraAdjust = new CameraArmAdjust(shooter_arm);

  private final Command resetHeading = new ResetHeadingCommand(drivetrain);

  private final SendableChooser<Command> autos = new SendableChooser<>();
  private CenterOnAprilTag center_on_tag = new CenterOnAprilTag(drivetrain);

  @Override
  public void robotInit()
  {
    super.robotInit();
    
    center_on_tag = new CenterOnAprilTag(drivetrain);

    // Speed settings:
    // SwerveDrivetrain controls maximum enforced by swerve(vx, vy, vr) and SwerveOI
    SwerveDrivetrain.MAX_METERS_PER_SEC = 3.37;
    SwerveDrivetrain.MAX_ROTATION_DEG_PER_SEC = 200;
    // Slew limiters for interactive moves
    SwerveOI.forward_slew = new SlewRateLimiter(4);
    SwerveOI.side_slew = new SlewRateLimiter(4);
    SwerveOI.rotation_slew = new SlewRateLimiter(360);
    // Maximum speed requested in autonomous moves (can't exceed MAX_METERS_PER_SEC)
    AutoTools.config = new TrajectoryConfig(3, 3);

    hub.enableCompressorAnalog(85, 120);

    OperatorInterface.reset();
    OperatorInterface.toggleIntake().toggleOnTrue(open_intake);
    OperatorInterface.fire().onTrue(shoot);
    OperatorInterface.resetHeading().onTrue(resetHeading);

    autos.setDefaultOption("Nothing", new PrintCommand("Do nothing"));

    for (Command auto : AutoNoMouse.createAutoCommands(drivetrain, intake, feeder, shooter, shooter_arm))
      autos.addOption(auto.getName(), auto);
    SmartDashboard.putData(autos);

    ApplySettingsCommand high = new ApplySettingsCommand("High");
    high.add("Set Shooter Angle", 55);
    high.add("Shooter Setpoint", 50);
    SmartDashboard.putData(high);
    
    ApplySettingsCommand low = new ApplySettingsCommand("Low");
    low.add("Set Shooter Angle", 35);
    low.add("Shooter Setpoint", 50);
    SmartDashboard.putData(low);

    ApplySettingsCommand pass = new ApplySettingsCommand("Pass");
    pass.add("Set Shooter Angle", 55);
    pass.add("Shooter Setpoint", 40);
    SmartDashboard.putData(pass);

    center_on_tag.findSuitableTag();
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
  public void disabledInit()
  {
    drivetrain.setDefaultCommand(new StopCommand(drivetrain));
  }

  @Override
  public void teleopInit()
  {
    // Bind buttons to commands
    // OperatorInterface.selectRelative().onTrue(relswerve);
    // OperatorInterface.selectAbsolute().onTrue(absswerve);
    // OperatorInterface.resetHeading().onTrue(new ResetHeadingCommand(drivetrain));

    shooter_arm.reset();

    // Start relative mode
    drivetrain.setDefaultCommand(relswerve);
  }



  @Override
  public void teleopPeriodic()
  {
    // climbers
    if (OperatorInterface.leftClimberUp()) leftUp.schedule(); else leftUp.cancel();
    if (OperatorInterface.rightClimberUp()) rightUp.schedule(); else rightUp.cancel();
    if (OperatorInterface.leftClimberDown()) leftDown.schedule(); else leftDown.cancel();
    if (OperatorInterface.rightClimberDown()) rightDown.schedule(); else rightDown.cancel();
    if(OperatorInterface.autoCam()) cameraAdjust.schedule(); else cameraAdjust.cancel();
    // eject
    if (OperatorInterface.reverseIntake()) reverse.schedule(); else reverse.cancel();
    if (OperatorInterface.centerOnAprilTagJoe() > .5) center_on_tag.schedule(); else center_on_tag.cancel();

    // shoot when up against speaker
    if (OperatorInterface.bumperShoot())
    {
      bumper_shoot.schedule();
    }

  }

  @Override
  public void autonomousInit()
  {
    autos.getSelected().schedule();
    climber.getHomeCommand().schedule();
    climber2.getHomeCommand().schedule();
    shooter_arm.reset();
  }
}
