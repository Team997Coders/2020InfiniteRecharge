package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class ShooterStream extends CommandBase {

  private double mTarget = 3700.0;

  public ShooterStream(double speed) {
    mTarget = speed;

    addRequirements(Shooter.getInstance());
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    //Shooter.getInstance().SetYeeterPercent(1);
    Shooter.getInstance().setRPM(mTarget);
    if (eps(Shooter.getInstance().getRPMs(), mTarget, 80) && Hopper.getInstance().mBallCount > 0) {
      Hopper.getInstance().setSpeed(Constants.Values.HOPPER_STREAM_SPEED);
    } else {
      Hopper.getInstance().setSpeed(0.0);
    }
  }

  public boolean eps(double a, double b, double eps) {
    return Math.abs(a - b) < eps;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().GoodStop();
    Hopper.getInstance().setSpeed(0.0);
  }

}
