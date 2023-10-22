package frc.robot.led;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.CommandRobotBase;

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
