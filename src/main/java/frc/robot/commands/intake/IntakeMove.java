package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

/**
 * Use WhileHeld
 */
public class IntakeMove extends CommandBase {

  private double mSpeed = 0.0;

  public IntakeMove(double speed) {
    mSpeed = speed;
  }

  @Override
  public void execute() {
    Intake.getInstance().setPercent(mSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    Intake.getInstance().setPercent(0.0);
  }

}