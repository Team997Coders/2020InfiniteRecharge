package frc.robot.commands.drivetrain;

import org.team997coders.spartanlib.motion.pathfollower.PathManager;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class FollowPath extends CommandBase {

  private String name;
  private Trajectory trajectory = null;

  private double startTime = 0.0;
  private double currentTime = 0.0;
  private boolean reverse = false;

  public FollowPath(String name, boolean reverse) {
    this.name = name;
    this.reverse = reverse;

    addRequirements(DriveTrain.getInstance());
  }

  @Override
  public void initialize() {
    startTime = (double) System.currentTimeMillis() / (double) 1000;

    trajectory = PathManager.getInstance().getTrajectory(name);
  }

  @Override
  public void execute() {

    if (trajectory == null) return;

    currentTime = (System.currentTimeMillis() / (double) 1000) - startTime;

    Trajectory.State currentState = trajectory.sample(currentTime);
    DifferentialDriveWheelSpeeds speeds = PathManager.getDriveSpeeds(Constants.Values.TRACK_WIDTH, currentState);

    if (!reverse) {
      DriveTrain.getInstance().setVelocity(PathManager.m2f(speeds.leftMetersPerSecond),
          PathManager.m2f(speeds.rightMetersPerSecond));
    } else {
      DriveTrain.getInstance().setVelocity(-PathManager.m2f(speeds.rightMetersPerSecond),
          -PathManager.m2f(speeds.leftMetersPerSecond));
    }
  }

  @Override
  public boolean isFinished() {

    if (trajectory == null) return true;

    if (trajectory.getTotalTimeSeconds() <= currentTime) {
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted)
    System.out.println("Path '" + name + "' was interrupted");
    else
    System.out.println("Path '" + name + "' has finished");

    DriveTrain.getInstance().setMotors(0.0, 0.0);
  }

}