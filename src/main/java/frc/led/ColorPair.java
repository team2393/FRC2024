// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.led;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

/** Show two colors which roll around the ring */
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
