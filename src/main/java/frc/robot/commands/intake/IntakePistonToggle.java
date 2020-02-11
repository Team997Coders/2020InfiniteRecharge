package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakePistonToggle extends CommandBase {

  @Override
  public void execute() {
    Intake.getInstance().togglePiston();
  }

  @Override
  public boolean isFinished() {
    return true;
  }

}