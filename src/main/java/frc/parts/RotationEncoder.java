// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.parts;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;

/** Encoder for rotation based on AndyMark "MA3" absolute analog encoder
 *  
 *  <p>https://www.andymark.com/products/ma3-absolute-encoder-with-cable
 * 
 *  <p>The sensor is meant to be powered by 5 V, in which case it
 *  reports 0...5 V for 0..360 degrees. The output is otherwise
 *  "ratiometric to the power supply voltage".
 * 
 *  <p>While the sensor could be mounted such that 0 V = 0 degrees
 *  = "straight ahead", this is hard to accomplish in practice.
 *  We thus handle the offset from "straight ahead" in software.
 */
public class RotationEncoder
{
  /** Analog encoder that reads roughtly 0..5 V from RoboRIO input */
  private final AnalogInput encoder;

  /** Offset in degrees from "straight ahead" which we define as 0 degrees */
  private double offset;

  /** Construct a RotationEncoder to read angle of "MA3" absolute encoder
   *  @param channel Analog input channel (0-3) on RoboRIO into which MA3 is plugged
   *  @param zero_heading Initial zero offset in degrees, the difference between
   *                      0 degrees and what we actually read from the sensor
   *                      when pointed "straight ahead"
   */
  public RotationEncoder(final int channel, final double zero_heading)
  {
    encoder = new AnalogInput(channel);
    offset = zero_heading;
  }

  /** @return Heading 0..360 degrees, not zeroed */
  public double getRawHeading()
  {
    // Raw value should be 0..5V, scale relative to actual 5V rail
    final double raw = encoder.getVoltage() / RobotController.getVoltage5V();
    return raw * 360.0;
  }

  /** @return Zeroed heading -180..+180 degrees */
  public Rotation2d getHeading()
  {
    // Map to -180..180 degrees
    return Rotation2d.fromDegrees(Math.IEEEremainder(getRawHeading() - offset, 360.0));
  }
    
  /** Define zero offset from current heading
   *  @return Did that change our zero offset?
   */
  public boolean setZero()
  {
    return setZero(getRawHeading());
  }
    
  /** @param zero_heading Heading that should be declared 'zero'
   *  @return Did that change our zero offset?
   */
  public boolean setZero(final double zero_heading)
  {
    if (offset == zero_heading)
      return false;
    offset = zero_heading;
    return true;
  }
}
