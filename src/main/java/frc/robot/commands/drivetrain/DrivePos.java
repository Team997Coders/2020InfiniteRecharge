package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DrivePos extends CommandBase {

  private double mPosition = 0.0;

  public DrivePos(double pos) {
    mPosition = pos;

    addRequirements(DriveTrain.getInstance());
  }

  @Override
  public void initialize() {
    DriveTrain.getInstance().resetEncoders();

    DriveTrain.getInstance().setPosition(mPosition, mPosition);
  }

  @Override
  public void end(boolean interrupted) {
    DriveTrain.getInstance().disableMotors();
  }

}