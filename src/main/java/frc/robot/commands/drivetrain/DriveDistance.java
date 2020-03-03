package frc.robot.commands.drivetrain;

import org.team997coders.spartanlib.motion.linear.TrapezoidalMotion;
import org.team997coders.spartanlib.motion.pathfollower.PathManager;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class DriveDistance extends CommandBase {

  private double mStartTime = Double.NaN;
  private TrapezoidalMotion mMotion;

  public DriveDistance(double feet) {
    mMotion = new TrapezoidalMotion(PathManager.m2f(1.2192), PathManager.m2f(3.048));
  }

  @Override
  public void initialize() {
    mMotion.initMotion(0.0);
  }

  @Override
  public void execute() {
    if (!Double.isFinite(mStartTime)) mStartTime = Robot.getCurrentSeconds();
    double currentTime = Robot.getCurrentSeconds();
    SmartDashboard.putNumber("DriveTrain/current Time", currentTime - mStartTime);
    double feet = mMotion.getPosition(currentTime - mStartTime);

    DriveTrain.getInstance().setPosition(feet, feet);

    SmartDashboard.putNumber("DriveTrain/Target Feet", feet);
  }

  @Override
  public void end(boolean interrupted) {
    DriveTrain.getInstance().disableMotors();
  }

}