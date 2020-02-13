package frc.robot;

import org.team997coders.spartanlib.motion.pathfollower.PathData;
import org.team997coders.spartanlib.motion.pathfollower.PathGeneratedData;
import org.team997coders.spartanlib.motion.pathfollower.PathPreloadedData;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import frc.robot.util.Gains;

public final class Constants {


  public static boolean eps(double a, double b, double eps) {
    return Math.abs(a - b) < eps;
  }
  
  public static class Ports {

    public static final int

      SHOOTER_MOTOR_1 = 8,
      SHOOTER_MOTOR_2 = 9,

      INTAKE_MOTOR = 7,
      INTAKE_SOLENOID = 3,

      //Hopper things
      HOPPER_MOTOR_TOP = 6,
      HOPPER_MOTOR_BOTTOM = 5,

      INTAKE_IR = 0,
      SHOOTER_IR = 1,

      //Drivetrain things
      motorFrontLeft = 1,
      motorBackLeft = 2,
      motorFrontRight = 3,
      motorBackRight = 4,
      ultrasonicChannel = 0,

      //climber things
      climberMotorPort = 10,
      climberPistonPort = 0;
      
  }

  public static class Values {     

    public static final double
        ACCELERATION = 2, // Percentage / Seconds
        voltageToFeet = (12 * 0.0098), //9.8mV per inch with a 5V input.

        shooterOutput = 1.0, // TODO: replace with actual

        VISION_TURNING_P = 0.028,
        VISION_TURNING_I = 0.01,
        VISION_TURNING_D = 0.06,
        VISION_TOLERANCE = 1,
        VISION_TIMEOUT = 2000, //in ms

        INTAKE_IN = 1,
        INTAKE_EJECT = -0.5,
        INTAKE_EXTEND_DELAY = 0.3, // seconds

        HOPPER_HANDOFF_DELAY = 0.25,
        HOPPER_HANDOFF_ROLL_TIME = 0.4,
        HOPPER_INTAKE_SPEED = 0.4,
        HOPPER_STREAM_SPEED = 0.75,
        HOPPER_EJECT_SPEED = -0.4,
        HOPPER_INTAKE_IR_DELAY = 0, // ms
        HOPPER_SHOOTER_IR_DELAY = 20, //ms

        SHOOTER_GEARING = 3.0 / 4.0,
        SHOOTER_RPM = 3700,

        CLIMBER_UP = 0.75,
        CLIMBER_DOWN = -0.6,

        TRACK_WIDTH = 2.1,

        // To Seconds, To RPM Motorside, To RPM Wheelside, To Circumference, To Feet
        DRIVE_VEL_2_FEET = 10 * (1.0 / 2048.0)
          * (9.0 / 70.0) * (4.875 * Math.PI) * (1.0 / 12.0);

    public static final Gains

      DRIVE_VELOCITY_GAINS = new Gains(0.6, 0.012, 6.8, 1023.0 / 21500.0),
      SHOOTER_VELOCITY_GAINS = new Gains(0.001, 0, 0.005, (1.0 / (4060.0 * (22.0 / 18.0) * 0.5)));

  }

  public static class Paths {

    public static final PathData
    
      SHOOT_TO_PICKUP = new PathPreloadedData("ShootToPickup"),
      PICKUP_3 = new PathPreloadedData("Pickup3"),
      TRENCH_PIVOT = new PathPreloadedData("TrenchPivot"),
      PIVOT_TO_SHOOT = new PathPreloadedData("PivotToShoot"),
      ALT_SHOOT_TO_PICKUP = new PathPreloadedData("AltShootToPickup"),
      START_TO_ALT_SHOOT = new PathPreloadedData("StartToAltShoot"),
      GENERATOR_TEST = new PathGeneratedData(
        "GeneratorTest",
        new TrajectoryConfig(5 / 3.281, 1.2192),
        new Pose2d(5.432, -0.705, new Rotation2d(3.048, 0.0)),
        new Pose2d(7.842, -0.705, new Rotation2d(2.523, 0.0))
      );

  }
}
