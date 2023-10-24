package frc.led;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** 12-LED Neopixel Ring */
public class LEDRing extends SubsystemBase
{
  public static final int N = 12;

  public final AddressableLED led;

  public final AddressableLEDBuffer buffer;

  public LEDRing()
  {
    led = new AddressableLED(9);
    buffer = new AddressableLEDBuffer(N);
    led.setLength(N);
    led.start();
  }

  public void clear()
  {
    for (int i=0; i<N; ++i)
      buffer.setRGB(i, 0, 0, 0);
  }  
}
