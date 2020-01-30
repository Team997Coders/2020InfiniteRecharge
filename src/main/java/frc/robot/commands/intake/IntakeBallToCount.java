package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

public class IntakeBallToCount extends CommandBase {

  private int mBallCount = 0;

  /**
   * Use this command to intake until you have a certain amount of balls
   * 
   * @param mBallCount
   */
  public IntakeBallToCount(int ballCount) {
    mBallCount = Hopper.getInstance().mBallCount + ballCount;
  }

  @Override
  public void initialize() {
    Robot.autoLoadHopper = true;
  }

  @Override
  public void execute() {
    if (!Hopper.getInstance().getShooterBall()) Intake.getInstance().setPercent(Constants.Values.INTAKE_IN);
    else Intake.getInstance().setPercent(0.0);
  }

  @Override
  public boolean isFinished() {
    if (Hopper.getInstance().mBallCount >= mBallCount && !Hopper.getInstance().autoIndexMoving) return true;
    else return false;
  }

  @Override
  public void end(boolean interrupted) {
    Hopper.getInstance().setSpeed(0.0);
  }

}