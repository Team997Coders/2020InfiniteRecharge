package frc.robot.commands.drivetrain;

import org.team997coders.spartanlib.helpers.SwerveMixerData;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.DriveTrain;

public class SwerveMixer extends CommandBase {

  public SwerveMixer() {
    addRequirements(DriveTrain.getInstance());
  }

  @Override
  public void initialize() {
    DriveTrain.getInstance().resetGyro();
  }

  @Override
  public void execute() {
    double f = -OI.getInstance().getGamepad1Axis(1);
    double s = OI.getInstance().getGamepad1Axis(0);
    double r = OI.getInstance().getGamepad1Axis(4);

    double theta = 0; //-DriveTrain.getInstance().getGyroAngle();
    while (theta < 0) theta += 360;
    while (theta >= 360) theta -= 360;

    SwerveMixerData dat = DriveTrain.getInstance().getSwerveData(f, s, r, theta);
    DriveTrain.getInstance().setSwerveInput(dat);
  }

}