package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

/* Command to set angle of shooter arm */
public class SetShooterAngleCommand extends Command
{
    private ShooterArm arm;
    private double angle;

    private enum State
    {
        // setting the angle
        SETTING,
        // done
        DONE
    };

    private State state;

    public SetShooterAngleCommand(ShooterArm arm, double angle)
    {
        this.arm = arm;
        this.angle = angle;

        addRequirements(arm);
    }

    @Override
    public void initialize()
    {
        state = State.SETTING;
        arm.setAngle(angle);
    }

    @Override
    public void execute()
    {
        // TODO: check that angle is reached (with error)
    }

    @Override
    public boolean isFinished()
    {
        return state == State.DONE;
    }
}
