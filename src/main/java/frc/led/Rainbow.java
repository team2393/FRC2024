// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.led;

import edu.wpi.first.wpilibj2.command.Command;

/** lights up the whole ring which gradually morphs between colors of the rainbow */
public class Rainbow extends Command
{
  private final LEDRing ring;

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
    ring.clear();

    // red -> orange -> yellow
    for (int i = 0; i < 256; i++)
    {
      for (int j = 0; j < ring.N; j++)
      {
        ring.buffer.setRGB(j, 255, i, 0);
        ring.led.setData(ring.buffer);
      }

      try
      {
        Thread.sleep(3);
      }
      catch (Exception e)
      {
        // do nothing
      }
    }

    // yellow -> green
    for (int i = 255; i > 0; i--)
    {
      for (int j = 0; j < ring.N; j++)
      {
        ring.buffer.setRGB(j, i, 255, 0);
        ring.led.setData(ring.buffer);
      }

      try
      {
        Thread.sleep(3);
      }
      catch (Exception e)
      {
        // do nothing
      }
    }

    // green -> teal
    for (int i = 0; i < 256; i++)
    {
      for (int j = 0; j < ring.N; j++)
      {
        ring.buffer.setRGB(j, 0, 255, i);
        ring.led.setData(ring.buffer);
      }

      try
      {
        Thread.sleep(3);
      }
      catch (Exception e)
      {
        // do nothing
      }
    }

    // teal -> blue
    for (int i = 255; i > 0; i--)
    {
      for (int j = 0; j < ring.N; j++)
      {
        ring.buffer.setRGB(j, 0, i, 255);
        ring.led.setData(ring.buffer);
      }

      try
      {
        Thread.sleep(3);
      }
      catch (Exception e)
      {
        // do nothing
      }
    }

    // blue -> purple
    for (int i = 0; i < 256; i++)
    {
      for (int j = 0; j < ring.N; j++)
      {
        ring.buffer.setRGB(j, i, 0, 255);
        ring.led.setData(ring.buffer);
      }

      try
      {
        Thread.sleep(3);
      }
      catch (Exception e)
      {
        // do nothing
      }
    }

    // purple -> red
    for (int i = 255; i > 1; i--)
    {
      for (int j = 0; j < ring.N; j++)
      {
        ring.buffer.setRGB(j, 255, 0, i);
        ring.led.setData(ring.buffer);
      }

      try
      {
        Thread.sleep(3);
      }
      catch (Exception e)
      {
        // do nothing
      }
    }
  }  
}
