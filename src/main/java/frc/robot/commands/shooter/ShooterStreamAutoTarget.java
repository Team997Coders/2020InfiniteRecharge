package frc.robot.commands.shooter;

import org.team997coders.spartanlib.limelight.LimeLight;
import org.team997coders.spartanlib.math.MathUtils;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class ShooterStreamAutoTarget extends CommandBase {

  private double mTarget = 0.0;
  private long mLastUpdate;

  public ShooterStreamAutoTarget(double speed) {
    mTarget = speed;
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
    if (MathUtils.epsilon(Shooter.getInstance().getRPMs(true), mTarget, 80)
      && Hopper.getInstance().mBallCount > 0
      && Math.abs(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0)) < Constants.Values.VISION_TOLERANCE) {
      Hopper.getInstance().setSpeed(Constants.Values.HOPPER_STREAM_SPEED);
    } else {
      Hopper.getInstance().setSpeed(0.0);
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().GoodStop();
    Hopper.getInstance().setSpeed(0.0);
    DriveTrain.getInstance().setMotors(0.0, 0.0);
  }

}