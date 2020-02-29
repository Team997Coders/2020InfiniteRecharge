package frc.robot.commands.drivetrain;

import org.team997coders.spartanlib.motion.linear.TrapezoidalMotion;
import org.team997coders.spartanlib.motion.pathfollower.PathManager;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class DriveDistance extends CommandBase {

  private double mStartTime = 0.0;
  private TrapezoidalMotion mMotion;

  public DriveDistance(double feet) {
    mMotion = new TrapezoidalMotion(PathManager.m2f(1.2192), PathManager.m2f(3.048));
  }

  @Override
  public void initialize() {
    mMotion.initMotion(0.0);
    mStartTime = Robot.getCurrentSeconds();
  }

  @Override
  public void execute() {
    double currentTime = Robot.getCurrentSeconds();
    double feet = mMotion.getPosition(currentTime - mStartTime);

    DriveTrain.getInstance().setPosition(feet, feet);
  }

}