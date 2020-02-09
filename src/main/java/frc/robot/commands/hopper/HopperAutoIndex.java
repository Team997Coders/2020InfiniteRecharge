package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;

public class HopperAutoIndex extends CommandBase {

  private boolean lastInShooter = false;

  @Override
  public void execute() {
    boolean intakeBall = Hopper.getInstance().getIntakeBall();
    boolean shooterBall = Hopper.getInstance().getShooterBall();

    SmartDashboard.putBoolean("Hopper/Used", Hopper.getInstance().autoIndexMoving);

    if ((!shooterBall && Robot.autoLoadHopper) && (intakeBall && !Hopper.getInstance().autoIndexMoving)) {
      CommandScheduler.getInstance().schedule(
        new WaitCommand(Constants.Values.HOPPER_HANDOFF_DELAY).andThen( // 0.2
        new HopperTimedMove(Constants.Values.HOPPER_HANDOFF_ROLL_TIME) // 0.13
      ));
      Hopper.getInstance().mBallCount++;
    }

    if (Hopper.getInstance().getShooterBall() && !lastInShooter) {
      Hopper.getInstance().mBallCount--;
    }
    lastInShooter = Hopper.getInstance().getShooterBall();
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("Well that's not supposed to happen");
  }

}