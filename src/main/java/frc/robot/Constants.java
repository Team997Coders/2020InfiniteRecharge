package frc.robot;

import java.util.HashMap;

import org.team997coders.spartanlib.motion.pathfollower.data.PathData;
import org.team997coders.spartanlib.motion.pathfollower.data.PathSwervePreloadedData;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.robot.util.Gains;

public final class Constants {


  public static boolean eps(double a, double b, double eps) {
    return Math.abs(a - b) < eps;
  }
  
  public static class Ports {

    public static final int

      SHOOTER_MOTOR_1 = 8,
      SHOOTER_MOTOR_2 = 9,

      INTAKE_MOTOR_1 = 7,
      INTAKE_MOTOR_2 = 11,
      INTAKE_SOLENOID = 3,

      //Hopper things
      HOPPER_MOTOR_TOP = 6,
      HOPPER_MOTOR_BOTTOM = 5,

      INTAKE_IR = 0,
      SHOOTER_IR = 1,
      OVERFLOW_IR = 2,

      //Drivetrain things
      ultrasonicChannel = 4,

      //climber things
      climberMotorPort = 10,
      climberPistonPort = 2,

      //LEDs
      LEDPORT = 9,
      LEDCOUNT = 35,

      __end_of_ports__ = 0;
      
    public static final int[]

      AZIMUTH_PORTS = {
        12, 13, 14, 15
      },

      DRIVE_PORTS = {
        1, 2, 3, 4
      },

      MODULE_ENCODERS = {
        0, 1, 2, 3
      };
  }

  public static class Values {     

    public static final double

        WHEEL_BASE = 22.0,
        TRACK_WIDTH = 12.0,

        VOLTAGE_TO_FEET = (12 * 0.0098), //9.8mV per inch with a 5V input. For ultrasonic.

        VISION_ANGLE_TOLERANCE = 1.5,

        VISION_DRIVE_P = 0.000025,
        VISION_DRIVE_I = 0.00006,
        VISION_DRIVE_D = 0.00003,

        ACCELERATION = 2.5, // Percentage / Seconds

        VISION_TURNING_P = 0.025, //0.04
        VISION_TURNING_I = 0.06,
        VISION_TURNING_D = 0.07,
        VISION_TOLERANCE = 1.5,
        VISION_TIMEOUT = 2000, //in ms

        VISION_LIMELIGHT_HEIGHT = 40, //Height (inches) up from the ground of the center of the limelight. 
        VISION_LIMELIGHT_ANGLE = 30,//Math.atan(2.5/1.75) * (180 / Math.PI), //angle the limelight is tilted at. In degrees up from the floor.

        INTAKE_IN = 0.6, //0.75 // percent speed to intake
        INTAKE_EJECT = -0.5, // percent speed to outtake
        INTAKE_EXTEND_DELAY = 0.2, // seconds

        HOPPER_HANDOFF_DELAY = 0.0, //0.13  // 0.13 * (speed / 0.75)
        HOPPER_HANDOFF_ROLL_TIME = 0.68,
        HOPPER_INTAKE_SPEED = 0.4,
        HOPPER_STREAM_SPEED = 1,
        HOPPER_EJECT_SPEED = -0.4,
        HOPPER_INTAKE_IR_DELAY = 0, // ms
        HOPPER_SHOOTER_IR_DELAY = 20, //ms

        SHOOTER_GEARING = 3.0 / 4.0,
        SHOOTER_RPM = 3700,
        SHOOTER_CIRCUMFERENCE_CM = (10.16 * Math.PI), // cm
        SHOOTER_RELEASE_ANGLE = 80, // degrees up from horizontal
        SHOOTER_RELEASE_HEIGHT = 1.0612121212, // meters up from ground

        CLIMBER_UP = 1,
        CLIMBER_DOWN = -1,
        CLIMBER_P = 0,
        CLIMBER_I = 0,
        CLIMBER_D = 0,

        // To Seconds, To RPM Motorside, To RPM Wheelside, To Circumference, To Feet
        DRIVE_VEL_2_FEET = 10 * (1.0 / 2048.0)
          * (9.0 / 70.0) * (4.875 * Math.PI) * (1.0 / 12.0),

        //PathWeaver Stuff (meters)
        MAX_VEL = 3.048, // m/sec
        MAX_ACCEL = 1.2192, // m/sec^2
        W_BASE = 0.64; // m

    public static final double[]

      MODULE_ZEROS = {
        0.0, 0.0, 0.0, 0.0
      };

    public static final Gains

      DRIVE_VELOCITY_GAINS = new Gains(0.6, 0.012, 6.8, 1023.0 / 21500.0),
      SHOOTER_VELOCITY_GAINS = new Gains(0.001, 0, 0.005, (1.0 / (4060.0 * (22.0 / 18.0) * 0.5)));

    public static final Gains[]

      DRIVE_GAINS = {
        new Gains(0.0, 0.0, 0.0, 0.0),
        new Gains(0.0, 0.0, 0.0, 0.0),
        new Gains(0.0, 0.0, 0.0, 0.0),
        new Gains(0.0, 0.0, 0.0, 0.0)
      },

      AZIMUTH_GAINS = {
        new Gains(0.01, 0.0, 0.0, 0.0),
        new Gains(0.01, 0.0, 0.0, 0.0),
        new Gains(0.01, 0.0, 0.0, 0.0),
        new Gains(0.01, 0.0, 0.0, 0.0)
      };
      
    public static final int

      LED_COUNT = 35,
      LED_WIDTH = 7,
      LED_ROWS = 5;

  }

  public static class PathFollower {

    public static final double

      TRACK_WIDTH = 2.1;

    public static final String[] PATHS = {
      "GoToPickup",
      "GoToShootPos",
      "Pickup3",
      "TrenchPivot",
      "Slalom"
    };

    public static final HashMap<String, PathData> PATH_DATA = new HashMap<String, PathData>() {{
      put("GoToPickup", new PathSwervePreloadedData("GoToPickup",
        new Rotation2d(0.0), new Rotation2d(0.0)));
      put("GoToShootPos", new PathSwervePreloadedData("GoToShootPos",
        new Rotation2d(0.0), new Rotation2d(0.0)));
      put("Pickup3", new PathSwervePreloadedData("Pickup3",
        new Rotation2d(0.0), new Rotation2d(0.0)));
      put("TrenchPivot", new PathSwervePreloadedData("TrenchPivot",
        new Rotation2d(0.0), new Rotation2d(0.0)));
      put("DriveForward", new PathSwervePreloadedData("DriveForward",
        new Rotation2d(0.0), new Rotation2d(0.0)));  
      put("BarrelRacing", new PathSwervePreloadedData("BarrelRacing",
        new Rotation2d(0.0), new Rotation2d(0.0)));
      put("Slalom", new PathSwervePreloadedData("Slalom",
        new Rotation2d(0.0), new Rotation2d(0.0)));
      put("Bounce", new PathSwervePreloadedData("Bounce",
        new Rotation2d(0.0), new Rotation2d(0.0)));
    }};

  }
}
