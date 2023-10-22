// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.parts;

import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.PWM;

/** REV SparkMini motor controller */
public class SparkMini extends PWMMotorController
{
  /** Construct motor controller for REV SparkMini
   *  @param channel PMW channel (0-9) on RoboRIO into which SparkMini is plugged
   */
  public SparkMini(final int channel)
  {
    super("SparkMini", channel);
    // WPILib provides basic PWMMotorController,
    // and when you display the "Type Hierarchy" it lists
    // several specializations, including "Spark",
    // but nothing for the slightly different "Spark Mini".
    // Maybe that's because the "Spark Mini" is meant for FTC,
    // it's not approved for FRC?
    //
    // Spark Mini manual describes PWM range of 500 .. 2500 us,
    // but trying 0.500 ms as lower end resulted in runtime error
    // "HAL: The PWM Scale Factors are out of range".
    // In addition, a setting of -1 for speed got converted to +1,
    // never reaching full reverse speed.
    // A min of 0.503 seems to avoid these problems
    // and set(-1) gets full reverse speed.
    // Adjusted max accordingly to (2.500 - 0.003) = 2.497.
    // Previous API uses millisecs:
    // m_pwm.setBounds(2.497, 1.52, 1.50, 1.48, 0.503);
    m_pwm.setBoundsMicroseconds(2497, 1520, 1500, 1480, 503);
    m_pwm.setPeriodMultiplier(PWM.PeriodMultiplier.k1X);
    m_pwm.setSpeed(0.0);
    m_pwm.setZeroLatch();
    m_pwm.enableDeadbandElimination(false);
  }
}
