package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  @Override
  public void initialize() {
    Shooter.getInstance().setRPM(mTarget);
  }

  @Override
  public void execute() {
    SmartDashboard.putNumber("Shooter/Error", Shooter.getInstance().getControllerError(mTarget));
    /*if (eps(Shooter.getInstance().getRPMs(), mTarget, 80) && Hopper.getInstance().mBallCount > 0) {
      Hopper.getInstance().setSpeed(Constants.Values.HOPPER_STREAM_SPEED);
    } else {
      Hopper.getInstance().setSpeed(0.0);
    }*/
  }

  public boolean eps(double a, double b, double eps) {
    return Math.abs(a - b) < eps;
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().GoodStop();
    Hopper.getInstance().setSpeed(0.0);
  }

}
