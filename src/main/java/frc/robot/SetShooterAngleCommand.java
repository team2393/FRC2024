package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

/* Command to set angle of shooter arm */
public class SetShooterAngleCommand extends Command
{
    private ShooterArm arm;
    private double angle;

    private boolean isDone;

    public SetShooterAngleCommand(ShooterArm arm, double angle)
    {
        this.arm = arm;
        this.angle = angle;

        addRequirements(arm);
    }

    @Override
    public void initialize()
    {
        isDone = false;
        arm.setAngle(angle);
    }

    @Override
    public void execute()
    {
        if (arm.atDesiredAngle()) isDone = true;
    }

    @Override
    public boolean isFinished()
    {
        return isDone;
    }

    @Override
    public void end(boolean interrupted)
    {
        // do nothing
    }
}
