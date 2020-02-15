package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;

public class HopperTimedMove extends CommandBase {

  private double 
    mTime = 0.0,
    mStart = 0.0;

  public HopperTimedMove(double time) {
    mTime = time;
    Hopper.getInstance().autoIndexMoving = true;
  }

  @Override
  public void initialize() {
    mStart = System.currentTimeMillis() / 1000.0;
  }

  @Override
  public void execute() {
    if (!Hopper.getInstance().getOverflowBall()) { Hopper.getInstance().setSpeed(0.4); }
  }

  @Override
  public boolean isFinished() {
    if ((System.currentTimeMillis() / 1000.0) - mStart > mTime) {
      Hopper.getInstance().autoIndexMoving = false;
      return true;
    }
    if (Hopper.getInstance().getShooterBall()) {
      Hopper.getInstance().autoIndexMoving = false;
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Hopper.getInstance().setSpeed(0.0);
    if (interrupted) System.out.println("HopperTimedMove interuppted on cycle " + Robot.cycles);
  }

}