package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

public class HopperAutoIndex extends CommandBase {

  private boolean lastInShooter = false;
  private boolean lastInIntake = false;

  private long 
    currentTime = -1, 
    intakeDelayTime = -1,
    intakeOutDelayTime = -1,
    shooterDelayTime = -1;

  @Override
  public void execute() {
    currentTime = System.currentTimeMillis();
    
    boolean intakeBall = Hopper.getInstance().getIntakeBall();
    boolean shooterBall = Hopper.getInstance().getShooterBall();

    SmartDashboard.putBoolean("Hopper/Used", Hopper.getInstance().autoIndexMoving);

    if ((!shooterBall && Robot.autoLoadHopper) && (intakeBall && !Hopper.getInstance().autoIndexMoving) && !lastInIntake) {
      CommandScheduler.getInstance().schedule(
        new WaitCommand(Constants.Values.HOPPER_HANDOFF_DELAY).andThen( // 0.2
        new HopperTimedMove(Constants.Values.HOPPER_HANDOFF_ROLL_TIME) // 0.13
      ));
      Hopper.getInstance().mBallCount++;
    }
    lastInIntake = Hopper.getInstance().getIntakeBall();

    /*if (Hopper.getInstance().getShooterBall() && !lastInShooter) {
      Hopper.getInstance().mBallCount--;
      if (Hopper.getInstance().mBallCount < 0) Hopper.getInstance().mBallCount = 0;
    }
    lastInShooter = Hopper.getInstance().getShooterBall();*/

    if (Hopper.getInstance().getShooterBall()) {
      if (intakeDelayTime == -1) {
        intakeOutDelayTime = -1;
        intakeDelayTime = currentTime;
      } else if (currentTime - intakeDelayTime >= Constants.Values.HOPPER_SHOOTER_IR_DELAY) {
        if (!lastInShooter) {
          if (Hopper.getInstance().mBallCount > 0) Hopper.getInstance().mBallCount--;
          lastInShooter = true;
        }
      }
    } else {
      if (intakeOutDelayTime == -1) {
        intakeDelayTime = -1;
        intakeOutDelayTime = currentTime;
      } else if (currentTime - intakeOutDelayTime >= Constants.Values.HOPPER_SHOOTER_IR_DELAY) {
        lastInShooter = false;
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) { System.out.println("HopperAutoIndex ended on cycle " + Robot.cycles); } else { System.out.println("HopperAutoIndex was interrupted on cycle " + Robot.cycles); }
  }

}