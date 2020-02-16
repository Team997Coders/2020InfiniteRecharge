package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;
import frc.robot.util.TimedTrigger;

public class ShootBadly extends CommandBase {

  private TimedTrigger mTrig1, mTrig2;

  public ShootBadly() {
    mTrig1 = new TimedTrigger(1.5);
    mTrig2 = new TimedTrigger(5);
  }

  @Override
  public void initialize() {
    mTrig1.trigger();
  }

  @Override
  public void execute() {
    Shooter.getInstance().SetYeeterPercent(1.0);
    if (mTrig1.get(false)) {
      mTrig2.trigger();
      Hopper.getInstance().setSpeed(1);
    }
  }

  @Override
  public boolean isFinished() {
    return mTrig2.get(false);
  }

  @Override
  public void end(boolean interrupted) {
    Hopper.getInstance().setSpeed(0.0);
    Shooter.getInstance().SetYeeterPercent(0.0);
  }

}