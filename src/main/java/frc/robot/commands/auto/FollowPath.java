package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.pathfollower.PathManager;
import frc.robot.pathfollower.TrajectorySupplier;
import frc.robot.subsystems.DriveTrain;

public class FollowPath extends CommandBase {

  private TrajectorySupplier supplier;
  private Trajectory trajectory;

  private double startTime = 0.0;
  private double currentTime = 0.0;

  public FollowPath(TrajectorySupplier supplier) {
    this.supplier = supplier;

    addRequirements(DriveTrain.getInstance());
  }

  @Override
  public void initialize() {
    startTime = (double)System.currentTimeMillis() / (double)1000;

    trajectory = supplier.GetTrajectory();
    if (trajectory == null) this.cancel();
  }

  @Override
  public void execute() {
    currentTime = (System.currentTimeMillis() / (double)1000) - startTime;

    Trajectory.State currentState = trajectory.sample(currentTime);
    DifferentialDriveWheelSpeeds speeds = PathManager.getDriveSpeeds(currentState);

    DriveTrain.getInstance().setVelocity(
      speeds.leftMetersPerSecond / Constants.Values.DRIVE_VEL_2_FEET,
      speeds.rightMetersPerSecond / Constants.Values.DRIVE_VEL_2_FEET
    );
  }

  @Override
  public boolean isFinished() {
    if (trajectory.getTotalTimeSeconds() <= currentTime) {
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) System.out.println("Path '" + supplier.name + "' was interrupted");
    else System.out.println("Path '" + supplier.name + "' has finished");
  }

}