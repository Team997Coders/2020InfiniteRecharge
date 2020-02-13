package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.util.TimedTrigger;

/**
 * Use WhileHeld
 */
public class IntakeMove extends CommandBase {

  private double mSpeed = 0.0;
  private boolean mEnableAutoLoader = false;
  private TimedTrigger mIntakeTrigger;

  public IntakeMove(double speed, boolean enableAutoLoader) {
    mSpeed = speed;
    mEnableAutoLoader = enableAutoLoader;

    mIntakeTrigger = new TimedTrigger(Constants.Values.INTAKE_EXTEND_DELAY);

    addRequirements(Intake.getInstance());
  }

  @Override
  public void initialize() {
    if (mEnableAutoLoader)
      Robot.autoLoadHopper = true;

    mIntakeTrigger.trigger();
    Intake.getInstance().setPiston(true);
  }

  @Override
  public void execute() {
    if (mIntakeTrigger.get(false)) {
      if (mEnableAutoLoader) {
        if (!Hopper.getInstance().getShooterBall())
          Intake.getInstance().setPercent(mSpeed);
        else
          Intake.getInstance().setPercent(0.0);
      } else {
        Intake.getInstance().setPercent(mSpeed);
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    Intake.getInstance().setPercent(0.0);
    Robot.autoLoadHopper = false;
    Intake.getInstance().setPiston(false);
  }

}