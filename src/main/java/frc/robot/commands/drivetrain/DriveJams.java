package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveJams extends CommandBase {

  public DriveJams() {
    addRequirements(DriveTrain.getInstance());
  }

  @Override
  public void execute() {
    DriveTrain.getInstance().playOrchestra("Megalovania.chrp");
  }

  @Override
  public boolean isFinished() {
    return true;
  }

}