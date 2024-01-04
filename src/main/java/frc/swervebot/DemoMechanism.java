// Copyright (c) FIRST Team 2393 and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.swervebot;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Demo for a mechanism that can be displayed on the simulation GUI:
 * 
 *  NetworkTables -> Smart Dashboard -> DemoMechanism
 */
public class DemoMechanism extends SubsystemBase
{
    private final MechanismLigament2d lift, arm;

    public DemoMechanism()
    {
        // Somewhat like this:
        //
        //         *---arm---
        //         ||
        //        lift
        //         ||
        //       center
        //  === bumper ===

        // Size of mechanism: 0.8m wide, 1m high
        Mechanism2d mechanism = new Mechanism2d(0.8, 1.0, new Color8Bit(Color.kLightGray));
        // Static base of the robot with bumper
        mechanism.getRoot("edge", 0, 0.01)
                 .append(new MechanismLigament2d("bumper", 0.8, 0, 20, new Color8Bit(Color.kBlue)));

        // Lift is based at the center
        MechanismRoot2d center = mechanism.getRoot("center", 0.4, 0.01);
        // Lift goes straight  "up", 90 deg, with varying height = length
        lift = center.append(new MechanismLigament2d("lift", 0.5, 90, 10, new Color8Bit(Color.kYellow)));
        // Arm starts at end of lift and goes "forward".
        // Horizontal arm == 0 deg means -90 from direction of lift that's pointing up
        arm = lift.append(new MechanismLigament2d("arm", 0.2, -90, 5, new Color8Bit(Color.kRed)));

        // Make available on dashboard
        SmartDashboard.putData("DemoMechanism", mechanism);

        setLift(0.5);
        setArm(0);
    }

    /** Move lift up/down */
    public void setLift(double height_meters)
    {
        // Height of lift = length of simulated ligament
        lift.setLength(height_meters);
    }

    public void setArm(double degrees)
    {
        // Horizontal arm, 0 degrees are -90 from the lift that's pointing straight up
        arm.setAngle(-90 + degrees);
    }

}
