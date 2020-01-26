package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

/**
 * Use WhileHeld
 */
public class IntakeMove extends CommandBase {

  private double mSpeed = 0.0;
  private boolean mEnableAutoLoader = false;

  public IntakeMove(double speed, boolean enableAutoLoader) {
    mSpeed = speed;
    mEnableAutoLoader = enableAutoLoader;

    addRequirements(Intake.getInstance());
  }

  @Override
  public void initialize() {
    if (mEnableAutoLoader) Robot.autoLoadHopper = true;
  }

  @Override
  public void execute() {
    if (mEnableAutoLoader) {
      if (!Hopper.getInstance().getShooterBall()) Intake.getInstance().setPercent(mSpeed);
      else Intake.getInstance().setPercent(0.0);
    } else {
      Intake.getInstance().setPercent(mSpeed);
    }
  }

  @Override
  public void end(boolean interrupted) {
    Intake.getInstance().setPercent(0.0);
    Robot.autoLoadHopper = false;
  }

}