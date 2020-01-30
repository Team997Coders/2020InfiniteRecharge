package frc.robot.commands.auto;

import org.team997coders.spartanlib.limelight.LimeLight;
import org.team997coders.spartanlib.math.MathUtils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class AutoStreamUntilEmpty extends CommandBase {

  private double mTarget = 0.0;
  private long mLastUpdate;
  private boolean mDuringAuto = false;

  public AutoStreamUntilEmpty(double speed, boolean duringAuto) {
    mTarget = speed;
    mDuringAuto = duringAuto;
  }

  @Override
  public void initialize() {
    mLastUpdate = System.currentTimeMillis();
  }

  @Override
  public void execute() {
    double deltaT = System.currentTimeMillis() - mLastUpdate;
    double output = LimeLight.getInstance().getPIDOutput(deltaT);
    DriveTrain.getInstance().setMotors(-output, output);

    Shooter.getInstance().setRPM(mTarget);
    if (MathUtils.epsilon(Shooter.getInstance().getRPMs(), mTarget, 80)
      && Hopper.getInstance().mBallCount > 0
      && Math.abs(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0)) < 1) {
      Hopper.getInstance().setSpeed(Constants.Values.HOPPER_STREAM_SPEED);
    } else {
      Hopper.getInstance().setSpeed(0.0);
    }
  }

  @Override
  public boolean isFinished() {
    
    if (mDuringAuto && !DriverStation.getInstance().isAutonomous()) return true;

    return Hopper.getInstance().mBallCount <= 0;
  }

  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().GoodStop();
    Hopper.getInstance().setSpeed(0.0);
    DriveTrain.getInstance().setMotors(0.0, 0.0);
  }

}