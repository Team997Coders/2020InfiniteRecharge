package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;

public class MoveHopperTimed extends CommandBase {

  private double time = 0.0;
  private double start = 0.0;

  public MoveHopperTimed(double time) {
    this.time = time;
    Hopper.used = true;
  }

  @Override
  public void initialize() {
    start = System.currentTimeMillis() / 1000.0;
  }

  @Override
  public void execute() {
    Hopper.getInstance().setUpperSpeed(0.4);
  }

  @Override
  public boolean isFinished() {
    if ((System.currentTimeMillis() / 1000.0) - start > time) {
      Hopper.used = false;
      return true;
    }
    if (!Hopper.getInstance().shooterIRsensor.get()) {
      Hopper.used = false;
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Hopper.getInstance().setUpperSpeed(0.0);
    if (interrupted) System.out.println("Wtf");
  }

}