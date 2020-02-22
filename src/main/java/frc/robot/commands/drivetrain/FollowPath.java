package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team997coders.spartanlib.motion.pathfollower.PathManager;

import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class FollowPath extends CommandBase {

  private String name;
  private Trajectory trajectory = null;

  private Trajectory.State lastState;

  private double startTime = 0.0;
  private double currentTime = 0.0;
  private boolean reverse = false;

  public FollowPath(String name, boolean reverse) {
    this.name = name;
    this.reverse = reverse;

    SmartDashboard.putNumber("Paht" + name, 0);

    addRequirements(DriveTrain.getInstance());
  }

  @Override
  public void initialize() {
    startTime = (double) System.currentTimeMillis() / (double) 1000;

    SmartDashboard.putString("Path '" + name + "' has Started", "");

    // Get the trajectory here so you can initalize on startup and still allow it process the data
    trajectory = PathManager.getInstance().getTrajectory(name);

    // Populate the first state before running
    lastState = trajectory.sample(0.0);
  }

  @Override
  public void execute() {

    // Don't follow the path if you either don't have the path yet or something went wrong
    if (trajectory == null) return;

    currentTime = (System.currentTimeMillis() / (double) 1000) - startTime;

    Trajectory.State currentState = trajectory.sample(currentTime);
    Pose2d currentPose = currentState.poseMeters;
    Pose2d lastPose = lastState.poseMeters;
    double deltaTime = currentState.timeSeconds - lastState.timeSeconds;
    Translation2d deltaTranslation = currentPose.getTranslation().minus(lastPose.getTranslation());

    Translation2d speed = new Translation2d(0.0, 0.0);
    
    // If the deltaTime is less than 0.0 then something went very wrong. In case it is 0, I don't want to divide by 0
    if (deltaTime > 0) speed = deltaTranslation.div(deltaTime);
    else System.out.println("The big bad has happened");

    double xOffset = Constants.Values.TRACK_WIDTH / 2;
    double yOffset = Constants.Values.WHEEL_BASE / 2;

    SwerveDriveKinematics kine = new SwerveDriveKinematics(
      new Translation2d(xOffset, yOffset),
      new Translation2d(-xOffset, yOffset),
      new Translation2d(-xOffset, -yOffset),
      new Translation2d(xOffset, -yOffset)
    );
    SwerveModuleState[] moduleStates = kine.toSwerveModuleStates(
      new ChassisSpeeds(speed.getX(), speed.getY(), 0.0)
    );

    DriveTrain.getInstance().setSwerveInput(DriveTrain.getInstance().toSwerveMixerData(moduleStates));

    // This is for the TankDrive. Of course we could use it but it would defeat the purpose of having a swerve.
    /*DifferentialDriveWheelSpeeds speeds = PathManager.getDriveSpeeds(currentState);

    if (!reverse) {
      DriveTrain.getInstance().setVelocity(PathManager.m2f(speeds.leftMetersPerSecond),
          PathManager.m2f(speeds.rightMetersPerSecond));
    } else {
      DriveTrain.getInstance().setVelocity(-PathManager.m2f(speeds.rightMetersPerSecond),
          -PathManager.m2f(speeds.leftMetersPerSecond));
    }*/
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
    SmartDashboard.putString("Path '" + name + "' was interrupted", "");
    else
    SmartDashboard.putString("Path '" + name + "' has finished", "");

    DriveTrain.getInstance().setMotors(0.0, 0.0);
  }

}