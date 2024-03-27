// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// This is a file to make sure that if I accidentally commit to main somehow, I dont wipe autonomouse. If I do push to the right branch I'll remove this and add it to AutoNoMouse.
package frc.robot;

import static frc.tools.AutoTools.createTrajectory;
import static frc.tools.AutoTools.followPathWeaver;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.swervelib.ResetPositionCommand;
import frc.swervelib.SelectAbsoluteTrajectoryCommand;
import frc.swervelib.SelectRelativeTrajectoryCommand;
import frc.swervelib.RotateToHeadingCommand;
import frc.swervelib.SwerveDrivetrain;
import frc.swervelib.SwerveToPositionCommand;
import frc.swervelib.VariableWaitCommand;
import frc.tools.SequenceWithStart;

/** Auto-no-mouse routines */
public class AutoNoMouse
{
  // Use this as a reference when making the same autonomous but for the other team
  private static final double FIELD_WIDTH = 16.52;
  private static final double HEADING_OFFSET = 180; // Offset of mirror heading.
  /** Create all our auto-no-mouse commands */
  public static List<Command> createAutoCommands(SwerveDrivetrain drivetrain, Intake intake, Feeder feeder, Shooter shooter, ShooterArm shooter_arm)
  {
    // List of all autonomouse commands
    final List<Command> autos = new ArrayList<>();

    // Each auto is created within a { .. block .. } so we get local variables for 'path' and the like 

    { // Drive back 1 m using a trajectory
      // Can be used from anywhere
      SequentialCommandGroup auto = new SequentialCommandGroup();
      auto.setName("Backwards 1m");
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectRelativeTrajectoryCommand(drivetrain));
      Trajectory path = createTrajectory(true, 0, 0, 180,
                                              -1, 0, 180);
      auto.addCommands(drivetrain.followTrajectory(path, 0).asProxy());
      autos.add(auto);
    }

  /*  {
      // Blue Bottom: Move out, Shoot, Pickup, Shoot
      SequentialCommandGroup auto = new SequenceWithStart("BBMSPS", 0.51, 2.38, 180);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectAbsoluteTrajectoryCommand(drivetrain, 0.51, 2.38, 180));
      // Move out (back), then over to front of target
      Trajectory path = createTrajectory(true, 0.51, 2.38,   0,
                                               1.70, 3.50,  90,
                                               1.44, 5.54, 120);
      auto.addCommands(drivetrain.followTrajectory(path, 180).asProxy());
      auto.addCommands(new ShootCommand(feeder, shooter));
      // Pickup another ring from right behind
      Trajectory path2 = createTrajectory(true, 1.44, 5.54, 0,
                                                2.60, 5.54, 0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder),
        drivetrain.followTrajectory(path2, 180).asProxy()));
      
      // Move forward to target and shoot
      Trajectory path3 = createTrajectory(true, 2.60, 5.54, 180,
                                                1.44, 5.54, 180);
      // Ideally, intake closes on its own when it fetches the game piece.
      // Just in case, force it closed, but not right away since we might be
      // in the middle of pulling the game piece in.
      // So in parallel, a) drive forward,
      //                 b) wait a little and then close the intake
      auto.addCommands(new ParallelCommandGroup(
        drivetrain.followTrajectory(path3, 180).asProxy(),
        new WaitCommand(1).andThen(new CloseIntakeCommand(intake, feeder))));

      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new PrintCommand("Done."));
      autos.add(auto);
    }
*/
    // To 'mirror' a setup from the left/blue to the right/red side,
    // X turns into WIDTH_OF_FIELD - X, where WIDTH_OF_FIELD is about 16.52 meters
    // Y stays unchanged
    // Heading changes into 180 - heading
    //
    // Example: A pose      1.44, 5.54, 120 changes into
    //                16.52-1.44, 5.54, 180-120 = 
    //                     15.08, 5.54,  60
 /*    {
      // Red Bottom: Move out, Shoot, Pickup, Shoot
      SequentialCommandGroup auto = new SequenceWithStart("RBMSPS", 16.01, 2.38, 0);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectAbsoluteTrajectoryCommand(drivetrain, 16.01, 2.38, 0));
      // Move out (back), then over to front of target
      Trajectory path = createTrajectory(true, 16.01, 2.38, 180,
                                               14.82, 3.50,  90,
                                               15.08, 5.54,  60);
      auto.addCommands(drivetrain.followTrajectory(path, 0).asProxy());
      auto.addCommands(new ShootCommand(feeder, shooter));
      // Pickup another ring from right behind
      Trajectory path2 = createTrajectory(true, 15.08, 5.54, 180,
                                                13.92, 5.54, 180);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder),
        drivetrain.followTrajectory(path2, 0).asProxy()));

      // Move forward to target and shoot
      Trajectory path3 = createTrajectory(true, 13.92, 5.54, 0,
                                                15.08, 5.54, 0);
      auto.addCommands(new ParallelCommandGroup(
        drivetrain.followTrajectory(path3, 0).asProxy(),
        new WaitCommand(1).andThen(new CloseIntakeCommand(intake, feeder))));

      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new PrintCommand("Done."));
      autos.add(auto);
    }
*/
    // { // Drive a 1.5 square using SwerveToPositionCommand & RotateToHeadingCommand
    //   SequentialCommandGroup auto = new SequentialCommandGroup();
    //   auto.setName("1.5m Square");
    //   auto.addCommands(new VariableWaitCommand());
    //   auto.addCommands(new ResetPositionCommand(drivetrain));

    //   auto.addCommands(new SwerveToPositionCommand(drivetrain, 1.5, 0.0));
    //   auto.addCommands(new RotateToHeadingCommand(drivetrain, 90));

    //   auto.addCommands(new SwerveToPositionCommand(drivetrain, 1.5, 1.5));
    //   auto.addCommands(new RotateToHeadingCommand(drivetrain, 180));

    //   auto.addCommands(new SwerveToPositionCommand(drivetrain, 0.0, 1.5));
    //   auto.addCommands(new RotateToHeadingCommand(drivetrain, -90));

    //   auto.addCommands(new SwerveToPositionCommand(drivetrain, 0.0, 0.0));
    //   auto.addCommands(new RotateToHeadingCommand(drivetrain, 0));

    //   autos.add(auto);
    // }

    // {
    //   autos.add(new SequenceWithStart("Circle", 1.91, 2.245, 0,
    //                                   new VariableWaitCommand(),
    //                                   new SelectAbsoluteTrajectoryCommand(drivetrain, 1.91, 2.245, 0),
    //                                   followPathWeaver(drivetrain, "Circle", 0.0).asProxy()));
    // }
 
   /*  {
      // Blue Middle: Move Pickup Shoot
      SequentialCommandGroup auto = new SequenceWithStart("BMMPS", 1.5, 5.5, 180);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectAbsoluteTrajectoryCommand(drivetrain, 1.5, 5.5, 180));
      
      // Move to top ring and get it.
      Trajectory path = createTrajectory(true, 1.92, 5.503, 0,
                                               2.5,  7,     0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder),
        drivetrain.followTrajectory(path, 180).asProxy()));

      // Move back to amp, shoot.
      Trajectory path2 = createTrajectory(true,  1.92, 5.503, 0,
                                                 1.4,  5.2,   0);
      auto.addCommands(drivetrain.followTrajectory(path2, 180).asProxy());

      auto.addCommands(new ShootCommand(feeder, shooter));
      
       // Move from amp, pickup, shoot
       Trajectory path3 = createTrajectory(true, 1.44, 5.54, 0,
                                                 2.60, 5.54, 0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder),
        drivetrain.followTrajectory(path3, 180).asProxy()));

      Trajectory path4 = createTrajectory(true, 2.5,  5.5, 180,
                                                1.92, 5.5, 180,
                                                1.5,  5.5, 180);
      auto.addCommands(drivetrain.followTrajectory(path4, 180).asProxy());

      auto.addCommands(new ShootCommand(feeder, shooter));
      
      // go to middle field ring, pickup
      Trajectory path5 = createTrajectory(true, 1.8,  5.5,  0,
                                                1.89, 4.10, 0,
                                                2.5,  4.10, 0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder),
        drivetrain.followTrajectory(path5, 180).asProxy()));

      Trajectory path6 = createTrajectory(true, 1.83, 4.65, 0, 1.41, 5.5, 180);
      auto.addCommands(drivetrain.followTrajectory(path6, 180).asProxy());

      auto.addCommands(new ShootCommand(feeder, shooter));
      autos.add(auto);
    }  */
   
    /* {
      // Red Middle: Move Pickup Shoot
      SequentialCommandGroup auto = new SequenceWithStart("RMMPS", FIELD_WIDTH - 1.5, 5.5, 0);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SelectAbsoluteTrajectoryCommand(drivetrain, FIELD_WIDTH - 1.5, 5.5, 0));

      // Move to top ring and get it.
      Trajectory path = createTrajectory(true, FIELD_WIDTH - 1.92, 5.503, 180,
                                               FIELD_WIDTH - 2.50, 7.000, 180);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder),
        drivetrain.followTrajectory(path, 0).asProxy()));

      // Move back to amp, shoot.
     Trajectory path2 = createTrajectory(true, FIELD_WIDTH - 1.92, 5.503, 0,
                                               FIELD_WIDTH - 1.40, 5.200, 0);
      auto.addCommands(drivetrain.followTrajectory(path2, 0).asProxy());

      auto.addCommands(new ShootCommand(feeder, shooter));

       // Move from amp, pickup, shoot
       Trajectory path3 = createTrajectory(true, FIELD_WIDTH - 1.44, 5.54, 180,
                                                 FIELD_WIDTH - 2.60, 5.54, 180);
       auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder),
        drivetrain.followTrajectory(path3, 0).asProxy()));

      Trajectory path4 = createTrajectory(true, FIELD_WIDTH - 2.50, 5.5, 0,
                                                FIELD_WIDTH - 1.92, 5.5, 0,
                                                FIELD_WIDTH - 1.50, 5.5, 0);
      auto.addCommands(drivetrain.followTrajectory(path4, 0).asProxy());

      auto.addCommands(new ShootCommand(feeder, shooter));
      
       // go to middle field ring, pickup
      Trajectory path5 = createTrajectory(true, FIELD_WIDTH - 1.80, 5.50, 180,
                                                FIELD_WIDTH - 1.89, 4.10, 180,
                                                FIELD_WIDTH - 2.50, 4.10, 180);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder),
        drivetrain.followTrajectory(path5, 0).asProxy()));

      Trajectory path6 = createTrajectory(true, FIELD_WIDTH - 1.5, 4.10, 0,
                                                FIELD_WIDTH - 1.5, 5.50, 0);
      auto.addCommands(drivetrain.followTrajectory(path6, 0).asProxy());

      auto.addCommands(new ShootCommand(feeder, shooter));
      autos.add(auto);
    }
*/
    
    // {
    //   // Blue Speaker: Shoot Move Pickup Shoot
    //   SequentialCommandGroup auto = new SequenceWithStart("BlueSpeakerPlus1", 1.5, 5.5, 180);
    //   auto.addCommands(new VariableWaitCommand());
    //   auto.addCommands(new ShootCommand(feeder, shooter));

    //   // Pickup another ring from right behind
    //   Trajectory path2 = createTrajectory(true, 1.5, 5.5, 0,
    //                                             2.6, 5.5, 0);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(7),
    //     new WaitCommand(2).andThen(drivetrain.followTrajectory(path2, 180).asProxy())));
      
    //   // Move forward to target and shoot
    //   Trajectory path3 = createTrajectory(true, 2.6, 5.5, 180,
    //                                             1.5, 5.5, 180);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path3, 180).asProxy(),
    //     new WaitCommand(1).andThen(new CloseIntakeCommand(intake, feeder))));
                  
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   auto.addCommands(new PrintCommand("Done."));        
    //   autos.add(auto);
    // }

    {
      // Blue Speaker: Shoot Move Pickup Shoot (Chad edition)
      SequentialCommandGroup auto = new SequenceWithStart("BlueSpeakerPlus1 (Chad)", 1.5, 5.5, 180);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SetShooterAngleCommand(shooter_arm, 55).withTimeout(1));
      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new SetShooterAngleCommand(shooter_arm, 40).withTimeout(.1));

      // Pickup another ring from right behind
      Trajectory path2 = createTrajectory(true, 1.5, 5.5, 0,
                                                2.6, 5.5, 0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder).withTimeout(7),
        new WaitCommand(2).andThen(drivetrain.followTrajectory(path2, 180).asProxy())));

      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new PrintCommand("Done."));        
      autos.add(auto);
    }

    // {
    //   // Red Speaker: Shoot Move Pickup Shoot
    //   SequentialCommandGroup auto = new SequenceWithStart("RedSpeakerPlus1", FIELD_WIDTH - 1.5, 5.5, 0);
    //   auto.addCommands(new VariableWaitCommand());
    //   auto.addCommands(new ShootCommand(feeder, shooter));

    //   // Pickup another ring from right behind
    //   Trajectory path2 = createTrajectory(true, FIELD_WIDTH - 1.5, 5.5, 180,
    //                                             FIELD_WIDTH - 2.6, 5.5, 180);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(7),
    //     new WaitCommand(2).andThen(drivetrain.followTrajectory(path2, 0).asProxy())));
      
    //   // Move forward to target and shoot
    //   Trajectory path3 = createTrajectory(true, FIELD_WIDTH - 2.6, 5.5, 0,
    //                                             FIELD_WIDTH - 1.5, 5.5, 0);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path3, 0).asProxy(),
    //     new WaitCommand(1).andThen(new CloseIntakeCommand(intake, feeder))));
                  
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   auto.addCommands(new PrintCommand("Done."));        
    //   autos.add(auto);
    // }

    // {
    //   // Blue Amp: Shoot Move Pickup Shoot
    //   SequentialCommandGroup auto = new SequenceWithStart("BlueAmpPlus1", 0.86, 6.7, -120);
    //   auto.addCommands(new VariableWaitCommand());
    //   auto.addCommands(new ShootCommand(feeder, shooter));

    //   // Pickup another ring from right behind
    //   Trajectory path = createTrajectory(true, 0.86, 6.7, 0,
    //                                             2.5, 7, 0);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(7),
    //     new WaitCommand(1.5).andThen(drivetrain.followTrajectory(path, 180).asProxy())));
      
    //   // Move forward to target and shoot
    //   Trajectory path2 = createTrajectory(true, 2.6, 6.72, 180,
    //                                             0.86, 6.7, -120);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path2, -120).asProxy(),
    //     new WaitCommand(1).andThen(new CloseIntakeCommand(intake, feeder))));
                  
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   auto.addCommands(new PrintCommand("Done."));        
    //   autos.add(auto);
    // }

    // {
    //   // Red Amp: Shoot Move Pickup Shoot
    //   SequentialCommandGroup auto = new SequenceWithStart("RedAmpPlus1", FIELD_WIDTH - 0.86, 6.7, -60);
    //   auto.addCommands(new VariableWaitCommand());
    //   auto.addCommands(new ShootCommand(feeder, shooter));

    //   // Pickup another ring from right behind
    //   Trajectory path = createTrajectory(true, FIELD_WIDTH - 0.86, 6.7, 180,
    //                                            FIELD_WIDTH - 2.5, 7, 180);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(7),
    //     new WaitCommand(2).andThen(drivetrain.followTrajectory(path, 0).asProxy())));
      
    //   // Move forward to target and shoot
    //   Trajectory path2 = createTrajectory(true, FIELD_WIDTH - 2.6, 6.72, 0,
    //                                             FIELD_WIDTH - 0.86, 6.7, -60);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path2, -60).asProxy(),
    //     new WaitCommand(1).andThen(new CloseIntakeCommand(intake, feeder))));
                  
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   auto.addCommands(new PrintCommand("Done."));        
    //   autos.add(auto);
    // }

    {
      // Shoot
      SequentialCommandGroup auto = new SequenceWithStart("Shoot", 0, 0, 0);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new ShootCommand(feeder, shooter));

      auto.addCommands(new PrintCommand("Done."));        
      autos.add(auto);
    }
 
    // {
    //   // Blue Speaker: Shoot Move Pickup Shoot Pickup Shoot (Up)
    //   SequentialCommandGroup auto = new SequenceWithStart("BMSMPSPS (Up)", 1.5, 5.5, 180);
    //   auto.addCommands(new VariableWaitCommand());
    //   auto.addCommands(new ShootCommand(feeder, shooter));

    //   // Pickup another ring from right behind
    //   Trajectory path2 = createTrajectory(true, 1.5, 5.5, 0,
    //                                             2.6, 5.5, 0);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(7),
    //     new WaitCommand(.5).andThen(drivetrain.followTrajectory(path2, 180).asProxy())));
      
    //   // Move forward to target and shoot
    //   Trajectory path3 = createTrajectory(true, 2.6, 5.5, 180,
    //                                             1.5, 5.5, 180);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path3, 180).asProxy(),
    //     new WaitCommand(.5).andThen(new CloseIntakeCommand(intake, feeder))));
      
    //   // Pickup another ring from behind and to the side
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   Trajectory path4 = createTrajectory(true, 1.5, 5.5, 0,
    //                                             2.6, 6.72, 38.5);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(7),
    //     new WaitCommand(.5).andThen(drivetrain.followTrajectory(path4, -141.5).asProxy())));

    //   // Move back to target and shoot
    //   Trajectory path5 = createTrajectory(true, 2.6, 6.72, -141.5,
    //                                       1.5, 5.5, 180);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path5, 180).asProxy(),
    //     new WaitCommand(.5).andThen(new CloseIntakeCommand(intake, feeder))));
      
    //   // Shoot and done
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   auto.addCommands(new PrintCommand("Done."));
    //   autos.add(auto);
    // }

    // {
    //   // Blue Speaker: Shoot Move Pickup Shoot Pickup Shoot (Down)
    //   SequentialCommandGroup auto = new SequenceWithStart("BMSMPSPS (Down)", 1.5, 5.5, 180);
    //   auto.addCommands(new VariableWaitCommand());
    //   auto.addCommands(new ShootCommand(feeder, shooter));

    //   // Pickup another ring from right behind
    //   Trajectory path2 = createTrajectory(true, 1.5, 5.5, 0,
    //                                             2.6, 5.5, 0);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(7),
    //     new WaitCommand(.5).andThen(drivetrain.followTrajectory(path2, 180).asProxy())));
      
    //   // Move forward to target and shoot
    //   Trajectory path3 = createTrajectory(true, 2.6, 5.5, 180,
    //                                             1.5, 5.5, 180);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path3, 180).asProxy(),
    //     new WaitCommand(.5).andThen(new CloseIntakeCommand(intake, feeder))));
      
    //   // Pickup another ring from behind and to the side
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   Trajectory path4 = createTrajectory(true, 1.5, 5.5, 0,
    //                                             2.6, 4, 0);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(7),
    //     new WaitCommand(.5).andThen(drivetrain.followTrajectory(path4, 180).asProxy())));

    //   // Move back to target and shoot
    //   Trajectory path5 = createTrajectory(true, 2.6, 4, 180,
    //                                       1.5, 5.5, 180);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path5, 180).asProxy(),
    //     new WaitCommand(.5).andThen(new CloseIntakeCommand(intake, feeder))));
      
    //   // Shoot and done
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   auto.addCommands(new PrintCommand("Done."));
    //   autos.add(auto);
    // }

    {
      // Blue Speaker: Shoot Move Pickup Shoot Pickup Shoot (Chad edition)
      SequentialCommandGroup auto = new SequenceWithStart("BMSMPSPS (Chad)", 1.5, 5.5, 180);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SetShooterAngleCommand(shooter_arm, 55).withTimeout(1));
      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new SetShooterAngleCommand(shooter_arm, 40).withTimeout(.1));

      // Pickup another ring from right behind
      Trajectory path2 = createTrajectory(true, 1.5, 5.5, 0,
                                                2.6, 5.5, 0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder).withTimeout(7),
        new WaitCommand(.5).andThen(drivetrain.followTrajectory(path2, 180).asProxy())));

      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new SetShooterAngleCommand(shooter_arm, 35).withTimeout(.1));

      // Pickup another ring to the right
      Trajectory path3 = createTrajectory(true, 2.6, 5.5, 180,
                                                2.0, 6.15, 90,
                                                2.6, 6.8, 0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder).withTimeout(7),
        new WaitCommand(.5).andThen(drivetrain.followTrajectory(path3, -150.0).asProxy())));
      
      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new PrintCommand("Done."));
      autos.add(auto);
    }

    {
      // Blue Speaker: Legendary 4 note
      SequentialCommandGroup auto = new SequenceWithStart("BlueMiddle4Note", 1.5, 5.5, 180);
      auto.addCommands(new VariableWaitCommand());
      auto.addCommands(new SetShooterAngleCommand(shooter_arm, 55).withTimeout(1.5));
      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new SetShooterAngleCommand(shooter_arm, 42).withTimeout(.1));

      // Pickup another ring from right behind
      Trajectory path1 = createTrajectory(true, 1.5, 5.5, 0,
                                                2.6, 5.5, 0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder).withTimeout(7),
        new WaitCommand(.5).andThen(drivetrain.followTrajectory(path1, 180).asProxy())));

      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new SetShooterAngleCommand(shooter_arm, 40).withTimeout(.1));

      // Pickup another ring to the right
      Trajectory path2 = createTrajectory(true, 2.6, 5.5, 180,
                                                2.0, 6.15, 90,
                                                2.6, 6.6, 0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder).withTimeout(7),
        new WaitCommand(.5).andThen(drivetrain.followTrajectory(path2, -155.0).asProxy())));
        
      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new SetShooterAngleCommand(shooter_arm, 38).withTimeout(.1));
      
      // Pickup another ring to the far left
      Trajectory path3 = createTrajectory(true, 2.6, 6.8, 180,
                                                2.0, 5.45, -90,
                                                2.0, 4.8, -90,
                                                3.1, 4.8, 0);
      auto.addCommands(new ParallelCommandGroup(
        new OpenIntakeCommand(intake, feeder).withTimeout(7),
        new WaitCommand(.5).andThen(drivetrain.followTrajectory(path3, 177).asProxy())));

      // // Move forward a bit and shoot
      auto.addCommands(new SwerveToPositionCommand(drivetrain, 2.8, 4.8).asProxy());
      auto.addCommands(new RotateToHeadingCommand(drivetrain, 155).asProxy());
      auto.addCommands(new ShootCommand(feeder, shooter));
      auto.addCommands(new PrintCommand("Done."));
      autos.add(auto);
    }
  
    // {
    //   // Red Speaker: Shoot Move Pickup Shoot Pickup Shoot (Up)
    //   SequentialCommandGroup auto = new SequenceWithStart("RMSMPSPS (Up)", FIELD_WIDTH - 1.5, 5.5, HEADING_OFFSET - 180);
    //   auto.addCommands(new VariableWaitCommand());
    //   auto.addCommands(new ShootCommand(feeder, shooter));

    //   // Pickup another ring from right behind
    //   Trajectory path2 = createTrajectory(true, FIELD_WIDTH - 1.5, 5.5, HEADING_OFFSET - 0,
    //                                             FIELD_WIDTH - 2.6, 5.5, HEADING_OFFSET - 0);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(7),
    //     new WaitCommand(.5).andThen(drivetrain.followTrajectory(path2, HEADING_OFFSET - 180).asProxy())));
      
    //   // Move forward to target and shoot
    //   Trajectory path3 = createTrajectory(true, FIELD_WIDTH - 2.6, 5.5, HEADING_OFFSET - 180,
    //                                             FIELD_WIDTH - 1.5, 5.5, HEADING_OFFSET - 180);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path3, HEADING_OFFSET - 180).asProxy(),
    //     new WaitCommand(.5).andThen(new CloseIntakeCommand(intake, feeder))));
      
    //   // Pickup another ring from behind and to the side
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   Trajectory path4 = createTrajectory(true, FIELD_WIDTH - 1.5, 5.5, HEADING_OFFSET - 0,
    //                                             FIELD_WIDTH - 2.6, 6.72, HEADING_OFFSET - 38.5);
    //   auto.addCommands(new ParallelCommandGroup(
    //     new OpenIntakeCommand(intake, feeder).withTimeout(10),
    //     new WaitCommand(.5).andThen(drivetrain.followTrajectory(path4, HEADING_OFFSET - -141.5).asProxy())));

    //   // Move back to target and shoot
    //   Trajectory path5 = createTrajectory(true, FIELD_WIDTH - 2.6, 6.72, HEADING_OFFSET - -141.5,
    //                                       FIELD_WIDTH - 1.5, 5.5, HEADING_OFFSET - 180);
    //   // Ideally, intake closes on its own when it fetches the game piece.
    //   // Just in case, force it closed, but not right away since we might be
    //   // in the middle of pulling the game piece in.
    //   // So in parallel, a) drive forward,
    //   //                 b) wait a little and then close the intake
    //   auto.addCommands(new ParallelCommandGroup(
    //     drivetrain.followTrajectory(path5, HEADING_OFFSET - 180).asProxy(),
    //     new WaitCommand(.5).andThen(new CloseIntakeCommand(intake, feeder))));
      
    //   // Shoot and done
    //   auto.addCommands(new ShootCommand(feeder, shooter));
    //   auto.addCommands(new PrintCommand("Done."));
    //   autos.add(auto);
    // }

    
    return autos;
  }
}
