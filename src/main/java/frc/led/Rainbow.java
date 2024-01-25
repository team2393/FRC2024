// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.led;

import edu.wpi.first.wpilibj2.command.Command;

/** lights up the whole ring which gradually morphs between colors of the rainbow */
public class Rainbow extends Command
{
  private final LEDRing ring;
  private int i = 0;
  private final int TARGET_INCREMENT = 5;
  private int target = TARGET_INCREMENT;
  private int stage = 0;

  public Rainbow(LEDRing ring)
  {
    this.ring = ring;
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
    if (stage == 5)
    {
      stage = 0;
    }

    // goes through colors in small chunks and preserves its current position in execute()
    for (/**/; i < target; i++)
    {
      // move on to next stage if finished with one RGB value
      if (i >= 255)
      {
        // reset if cycle is complete
        if (stage == 5)
        {
          // stage = 0;
        }
        else
        {
          stage++;
          i = 0;
          target = TARGET_INCREMENT;
        }
      }

      for (int j = 0; j < LEDRing.N; j++)
      {
        switch (stage)
        {
          // red -> orange -> yellow
          case 0:
            ring.buffer.setRGB(j, 255, i, 0);
            ring.led.setData(ring.buffer);
            break;

          // yellow -> green
          case 1:
            ring.buffer.setRGB(j, 255 - i, 255, 0);
            ring.led.setData(ring.buffer);
            break;
          
          // green -> teal
          case 2:
            ring.buffer.setRGB(j, 0, 255, i);
            ring.led.setData(ring.buffer);
            break;

          // teal -> blue
          case 3:
            ring.buffer.setRGB(j, 0, 255 - i, 255);
            ring.led.setData(ring.buffer);
            break;

          // blue -> purple
          case 4:
            ring.buffer.setRGB(j, i, 0, 255);
            ring.led.setData(ring.buffer);
            break;

          // purple -> red
          case 5:
            ring.buffer.setRGB(j, 255, 0, 255 - i);
            ring.led.setData(ring.buffer);
            break;
        }
      }
    }
    
    target += TARGET_INCREMENT;
  }  
}
