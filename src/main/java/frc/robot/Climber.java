// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.function.BooleanConsumer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Solenoid;
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
  private final double METERS_PER_TURN = 0.4 / 80.360054;

  // Maximum 'up' extension
  private final double MAX_EXTENSION = 0.4;

  private final BooleanConsumer set_brake;

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

  /** @param left Left, or right climber?
   *  @param set_brake Function to call with am-I-moving state of this climber
   */
  public Climber(boolean left, BooleanConsumer set_brake)
  {
    this.set_brake = set_brake;
    climber = new CANSparkMax(left ? RobotMap.CLIMBER : RobotMap.CLIMBER2, MotorType.kBrushless);
    climber.restoreFactoryDefaults();
    climber.clearFaults();
    climber.setIdleMode(IdleMode.kBrake);
    climber.setOpenLoopRampRate(0.5);
    climber.setInverted(left);

    encoder = climber.getEncoder();

    at_bottom = new DigitalInput(left ? RobotMap.CLIMBER_AT_BOTTOM : RobotMap.CLIMBER_AT_BOTTOM2);

    climber_height = SmartDashboard.getEntry(left ? "Left Climber Height" : "Right Climber Height");
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
    
    // For left climber which controls the brake,
    // release brake while moving climber up/down
    set_brake.accept(voltage != 0);
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
