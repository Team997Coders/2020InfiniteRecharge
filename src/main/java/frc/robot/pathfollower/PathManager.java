package frc.robot.pathfollower;

import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.Constants;

public class PathManager {

  private static DifferentialDriveKinematics diffDriveKin;
  public static TrajectorySupplier[] suppliers;
  private static Thread runner;

  static {
    diffDriveKin = new DifferentialDriveKinematics(f2m(Constants.PathFollower.TRACK_WIDTH));

    suppliers = new TrajectorySupplier[] {
      new TrajectorySupplier("GoToPickup"),
      new TrajectorySupplier("GoToShootPos"),
      new TrajectorySupplier("Pickup3")
    };

    runner = new Thread(() -> LoadAllPaths());
    System.out.println("Started Loading Paths");
    runner.start();
  }

  public static void LoadAllPaths() {
    for (int i = 0; i < suppliers.length; i++) {
      LoadPath(suppliers[i]);
    }

    System.out.println("Loaded Paths");
  }

  public static void LoadPath(TrajectorySupplier sup) {
    int retryCount = 0;
    boolean success = false;
    while (!success) {
      try {

        Trajectory tra = TrajectoryUtil.fromPathweaverJson(sup.filePath);
        if (tra != null) {
          sup.SetTrajectory(tra);
          success = true;
        } else {
          System.out.println("Loading Path '" + sup.filePath.toString() + "' Failed");
        }

      } catch (Exception e) {

        System.out.println("Loading Path '" + sup.filePath.toString() + "' Failed");
        e.printStackTrace();

      } finally {

        if (retryCount > 2) {
          return;
        } else {
          retryCount++;
        }

      }
    }
  }

  public static TrajectorySupplier getSupplier(String name) {
    for (TrajectorySupplier t : suppliers) {
      if (name.equals(t.name)) return t;
    }
    return null;
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