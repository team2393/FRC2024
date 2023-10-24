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
