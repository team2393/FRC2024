// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervebot;

import static java.lang.Math.cos;
import static java.lang.Math.PI;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.led.ColorPair;
import frc.led.Comet;
import frc.led.LEDRing;
import frc.swervelib.AbsoluteSwerveCommand;
import frc.swervelib.RelativeSwerveCommand;
import frc.swervelib.SwerveDrivetrain;
import frc.swervelib.SwerveOI;
import frc.tools.AutoTools;
import frc.tools.CommandRobotBase;

/** Swerve demo robot */
public class SwerveBot extends CommandRobotBase
{
  private final SwervebotDrivetrain drivetrain = new SwervebotDrivetrain();
  private final Command relswerve = new RelativeSwerveCommand(drivetrain);
  private final Command absswerve = new AbsoluteSwerveCommand(drivetrain);
  
  private final DemoMechanism gadget = new DemoMechanism();
  private final Command gadgetcommand = gadget.run(() ->
  { 
    double sec = Timer.getFPGATimestamp();
    // Vary height from 0.3 to 0.7m every 4 seconds
    gadget.setLift(0.5 + 0.2*cos(2*PI*sec/4.0));
    // Vary angle +-45 deg every 6 seconds
    gadget.setArm(45.0*cos(2*PI*sec/6.0));

    gadget.poke(  (int)sec % 2 == 0  );
  });

  private final LEDRing ring = new LEDRing();

  private final SendableChooser<Command> autos = new SendableChooser<>();

  @Override
  public void robotInit()
  {
    super.robotInit();

    SwerveDrivetrain.MAX_METERS_PER_SEC = 1.5;
    SwerveDrivetrain.MAX_ROTATION_DEG_PER_SEC = 45;
    SwerveOI.forward_slew = new SlewRateLimiter(1.5);
    SwerveOI.side_slew = new SlewRateLimiter(1.5);
    SwerveOI.rotation_slew = new SlewRateLimiter(90);

    SwerveOI.reset();

    // ring.setDefaultCommand(
    //   new RepeatCommand(
    //     new SequentialCommandGroup(
    //       new ParallelRaceGroup(new WaitCommand(10), new Comet(ring)),
    //       new ParallelRaceGroup(new WaitCommand(10), new ColorPair(ring, Color.kDarkGreen, Color.kDarkGoldenrod))
    //     )));

    ring.setDefaultCommand(
      new Comet(ring).withTimeout(10)
                     .andThen(
                       new ColorPair(ring, Color.kDarkGreen, Color.kDarkGoldenrod).withTimeout(10)
                             ).repeatedly()
                          );

    // autos.setDefaultOption("Nothing", Commands.print("Do Nothing"));
    autos.setDefaultOption("Nothing", new PrintCommand("Do nothing"));

    for (Command auto : AutoNoMouse.createAutoCommands(drivetrain))
      autos.addOption(auto.getName(), auto);
    SmartDashboard.putData(autos);
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
    SwerveOI.selectRelative().onTrue(relswerve);
    SwerveOI.selectAbsolute().onTrue(absswerve);
    // Instead of 'binding' commands in ..Init(),
    // could add this to ..Periodic():
    //   if (SwerveOI.selectRelative().getAsBoolean())
    //     relswerve.schedule();

    // Start relative mode
    relswerve.schedule();

    gadgetcommand.schedule();
  }

  @Override
  public void autonomousInit()
  {
    autos.getSelected().schedule();
  }
}
