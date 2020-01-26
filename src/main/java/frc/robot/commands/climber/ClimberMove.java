package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberMove extends CommandBase {

  private double mSpeed;

  public ClimberMove(double speed) {
    mSpeed = speed;
  }

  @Override
  public void initialize() {
    Climber.getInstance().RetractPiston();
  }

  @Override
  public void execute() {
    Climber.getInstance().setSpeed(mSpeed);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Climber.getInstance().setSpeed(0.0);
    Climber.getInstance().ExtendPiston();
  }

}