package frc.robot.pathfollower;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * @deprecated This path manager is limited to 1 thread and I don't like it as much
 *  as the other one. Use the SpartanLib version.
 */
public class PathManager {

  private static DifferentialDriveKinematics diffDriveKin;
  private static HashMap<String, Trajectory> trajectories;
  private static Lock hashLock;
  private static Thread runner;

  static {
    diffDriveKin = new DifferentialDriveKinematics(f2m(Constants.PathFollower.TRACK_WIDTH));

    hashLock = new ReentrantLock();

    trajectories = new HashMap<String, Trajectory>();

    runner = new Thread(() -> {
      double completed = 0;
      for (String path : Constants.PathFollower.PATHS) {
        Trajectory t = loadPath(path);
        addPath(path, t);
        completed++;
      }
    });
    System.out.println("Started Loading Paths");
    runner.start();
  }

  public static Trajectory loadPath(String name) {
    int retryCount = 0;
    Path filePath = Paths.get("/home/lvuser/deploy/paths/output/" + name + ".wpilib.json");
    while (true) {
      try {
        Trajectory tra = TrajectoryUtil.fromPathweaverJson(filePath);
        if (tra != null) {
          return tra;
        } else {
          return null;
        }

      } catch (Exception e) {
        e.printStackTrace();
      } finally {

        if (retryCount > 2) {
          return null;
        } else {
          retryCount++;
        }

      }
    }
  }

  private static void addPath(String n, Trajectory t) {
    hashLock.lock();
    trajectories.put(n, t);
    hashLock.unlock();
  }

  public static Trajectory getPath(String name) {
    Trajectory t = null;
    hashLock.lock();
    t = trajectories.get(name);
    hashLock.unlock();
    return t;
  }

  public static DifferentialDriveWheelSpeeds getDriveSpeeds(Trajectory.State currentState) {
    return diffDriveKin.toWheelSpeeds(new ChassisSpeeds(currentState.velocityMetersPerSecond, 0,
        currentState.curvatureRadPerMeter * currentState.velocityMetersPerSecond));
  }

  public static double f2m(double feet) {
    return feet / 3.281;
  }

  public static double m2f(double meters) {
    return meters * 3.281;
  }

}