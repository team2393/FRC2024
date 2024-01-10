// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.led;

import edu.wpi.first.wpilibj.util.Color;
import frc.tools.CommandRobotBase;

public class LEDRingDemoRobot extends CommandRobotBase
{
  private final LEDRing ring = new LEDRing();

  @Override
  public void robotInit()
  {
    super.robotInit();
    // ring.setDefaultCommand(new Comet(ring));
    ring.setDefaultCommand(new ColorPair(ring, Color.kDarkGreen, Color.kDarkGoldenrod));
  }
}
