package frc.robot.led;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

public class ColorPair extends Command
{
  private final LEDRing ring;
  private final Color one, other;

  public ColorPair(LEDRing ring, Color one, Color other)
  {
    this.ring = ring;
    this.one = one;
    this.other = other;
    addRequirements(ring);
  }

  @Override
  public boolean runsWhenDisabled()
  {
    return true;
  }

  @Override
  public void execute()
  {
    // Step the 'active' LED every 200 ms
    int active = (int) ((System.currentTimeMillis()  / 200) % LEDRing.N);

    // Half of the LEDs use one color
    for (int i=0; i<LEDRing.N/2; ++i)
    {
      ring.buffer.setLED(active, one);
      active = (active+1) % LEDRing.N;
    }
    // .. and half the other color
    for (int i=0; i<LEDRing.N/2; ++i)
    {
      ring.buffer.setLED(active, other);
      active = (active+1) % LEDRing.N;
    }

    ring.led.setData(ring.buffer);
  }  
}
