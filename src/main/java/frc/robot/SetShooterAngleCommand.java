package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

/* Command to set angle of shooter arm */
public class SetShooterAngleCommand extends Command
{
    private ShooterArm arm;
    private double angle;

    public SetShooterAngleCommand(ShooterArm arm, double angle)
    {
        this.arm = arm;
        this.angle = angle;

        addRequirements(arm);
    }

    @Override
    public void initialize()
    {
        arm.setAngle(angle);
    }

    @Override
    public void execute()
    {
        // do nothing
    }

    @Override
    public boolean isFinished()
    {
        return arm.atDesiredAngle();
    }

    @Override
    public void end(boolean interrupted)
    {
        // do nothing
    }
}
