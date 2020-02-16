package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Hopper;

public class DriveBadly extends CommandBase {

  private double mFeet = 0.0;

  public DriveBadly(double feet) {
    mFeet = feet;

    addRequirements(Hopper.getInstance(), DriveTrain.getInstance());
  }

  @Override
  public void initialize() {
    DriveTrain.getInstance().resetEncoders();
  }

  @Override
  public void execute() {
    DriveTrain.getInstance().setMotors(0.3, 0.3);
  }

  @Override
  public boolean isFinished() {
    if (DriveTrain.getInstance().getLeftFeet() >= mFeet) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void end(boolean interrupted) {
    DriveTrain.getInstance().setMotors(0.0, 0.0);
  }

}