package frc.robot.pathfollower;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.wpilibj.trajectory.Trajectory;

public class TrajectorySupplier {

  private Trajectory trajectory;
  private boolean hasTrajectory;
  private Lock accessLock;

  public Path filePath;
  public String name;

  public TrajectorySupplier(String name) {
    filePath = Paths.get("/home/lvuser/deploy/paths/output/" + name + ".wpilib.json");
    accessLock = new ReentrantLock();
    this.name = name;
  }

  public Trajectory GetTrajectory() {
    accessLock.lock();
    Trajectory t = null;
    if (!hasTrajectory) {
      t = trajectory;
    }
    accessLock.unlock();
    return t;
  }

  void SetTrajectory(Trajectory t) {
    accessLock.lock();
    if (!hasTrajectory) {
      
      hasTrajectory = true;
      trajectory = t;

    }
    accessLock.unlock();
  }

}