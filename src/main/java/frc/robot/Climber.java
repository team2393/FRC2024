// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Climing arm */
public class Climber extends SubsystemBase
{
  // Voltage used to run motor
  private final double MOTOR_VOLTAGE = 3.0;

  // Calibration of arm extension
  private final double METERS_PER_TURN = 0.0001;

  // Maximum 'up' extension
  private final double MAX_EXTENSION = 2.0;

  // Motor to move climber up (forward) or down (reverse)
  private final CANSparkMax climber;
  // Motor's encoder
  private RelativeEncoder encoder;

  // Bottom limit switch
  // Digital inputs are pulled high, so nothing connected -> 'true'.
  // Switch is wired to ground, so 'false' means 'hit bottom switch'
  private final DigitalInput at_bottom;

  // NT entry: Height of extension
  private final NetworkTableEntry climber_height;

  // Climber states
  private enum State { STOP, UP, DOWN };

  // Current climber state
  private State state = State.STOP;

  // Simulation display elements
  private final MechanismLigament2d extension;

  public Climber()
  {
    climber = new CANSparkMax(RobotMap.CLIMBER, MotorType.kBrushless);
    climber.restoreFactoryDefaults();
    climber.clearFaults();
    climber.setIdleMode(IdleMode.kBrake);
    climber.setOpenLoopRampRate(0.5);

    encoder = climber.getEncoder();

    at_bottom = new DigitalInput(RobotMap.CLIMBER_AT_BOTTOM);

    climber_height = SmartDashboard.getEntry("Climber");

    // Somewhat like this:
    //  _
    // | |
    //   |
    //   |
    //  === bumper ===
    // front  ... back

    // Size of mechanism: 0.8m wide, 1m high
    Mechanism2d mechanism = new Mechanism2d(0.8, 1.0, new Color8Bit(Color.kLightGray));
    // Static base of the robot with bumper
    mechanism.getRoot("front", 0, 0.1)
        .append(new MechanismLigament2d("bumper", 0.8, 0, 20, new Color8Bit(Color.kBlue)));
    // Arm over the front
    MechanismLigament2d bottom = mechanism.getRoot("base", 0.1, 0.1)
        .append(new MechanismLigament2d("bottom", 0.4, 90, 15, new Color8Bit(Color.kRed)));
    extension = bottom
        .append(new MechanismLigament2d("extension", 0.05, 0, 10, new Color8Bit(Color.kRed)));
    MechanismLigament2d hook = extension.append(new MechanismLigament2d("hook", 0.1, 90, 5, new Color8Bit(Color.kGreen)));
    hook.append(new MechanismLigament2d("hook2", 0.05, 90, 5, new Color8Bit(Color.kGreen)));
    // Make available on dashboard
    SmartDashboard.putData("Climber", mechanism);
  }

  /** @return Is arm at bottom? */
  public boolean isAtBottom()
  {
    return !at_bottom.get();
  }

  @Override
  public void periodic()
  {
    // Get and show height
    double height = encoder.getPosition() * METERS_PER_TURN;
    climber_height.setNumber(height);
    extension.setLength(height);

    double voltage;
    if (state == State.UP)
    { // Move up unless we hit the maximum height
      if (height >= MAX_EXTENSION)
        voltage = 0;
      else
        voltage = MOTOR_VOLTAGE;
    }
    else if (state == State.DOWN)
    { // Move down unless we hit the bottom
      if (isAtBottom())
      {
        voltage = 0;
        // Reset position. Bottom is defined as "0 meters"
        encoder.setPosition(0.0);
        height = 0;
      }
      else
        voltage = -MOTOR_VOLTAGE;
    }
    else
      voltage = 0;
    
    climber.setVoltage(voltage);

    if (RobotBase.isSimulation())
    { // Simulate change in motor position. 0.001 simply "looked good"
      height += voltage * 0.001;
      encoder.setPosition(height / METERS_PER_TURN);
    }
  }

  /** @return Command to move up */
  public Command getUpCommand()
  {
    return new StartEndCommand(() -> state = State.UP,
                               () -> state = State.STOP,
                               this);
  }

  /** @return Command to move down */
  public Command getDownCommand()
  {
    return new StartEndCommand(() -> state = State.DOWN,
                               () -> state = State.STOP,
                               this);
  }

  /** @return Command to move down until at bottom */
  public Command getHomeCommand()
  {
    return new FunctionalCommand(() -> {},
                                 () -> state = State.DOWN,
                                 (interrupted) -> state = State.STOP,
                                 () -> isAtBottom(),
                                 this);
  }
}
