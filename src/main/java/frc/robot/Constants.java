package frc.robot;

import frc.robot.util.Gains;

public final class Constants {

  public static boolean eps(double a, double b, double eps) {
    return Math.abs(a - b) < eps;
  }
  
  public static class Ports {

    public static final int

      SHOOTER_MOTOR_1 = 8,
      SHOOTER_MOTOR_2 = 9,

      INTAKE_MOTOR = 10,

      //Hopper things
      HOPPER_MOTOR_TOP = 4,
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
      climberPistonPort = 0,
      crawlerMotorPort = 30; //TODO: replace with actual
  }

  public static class Values {     

    public static final double

        voltageToFeet = (12 * 0.0098), //9.8mV per inch with a 5V input.

        shooterOutput = 1.0, // TODO: replace with actual

        VISION_TURNING_P = 0.028,
        VISION_TURNING_I = 0.01,
        VISION_TURNING_D = 0.06,
        VISION_TOLERANCE = 1,
        VISION_TIMEOUT = 2000, //in ms

        INTAKE_IN = 0.4,
        INTAKE_EJECT = -0.6,

        HOPPER_HANDOFF_DELAY = 0.25,
        HOPPER_HANDOFF_ROLL_TIME = 0.4,
        HOPPER_INTAKE_SPEED = 0.4,
        HOPPER_STREAM_SPEED = 0.75,
        HOPPER_EJECT_SPEED = -0.4,

        SHOOTER_GEARING = 3.0 / 4.0,
        SHOOTER_RPM = 3700,

        CLIMBER_UP = 0.75,
        CLIMBER_DOWN = -0.6,

        // To Seconds, To RPM Motorside, To RPM Wheelside, To Circumference, To Feet
        DRIVE_VEL_2_FEET = 10 * ((double)1 / (double)2048)
          * ((double)9 / (double)70) * ((double)5 * Math.PI) * ((double)1 / (double)12);

    public static final Gains

      DRIVE_VELOCITY_GAINS = new Gains(0.13, 0.0, 3.0, (double)1023 / (double)19990),
      SHOOTER_VELOCITY_GAINS = new Gains(0.25, 0.001, 20, 1 / 500);

  }

  public static class PathFollower {

    public static final double

      TRACK_WIDTH = 2.1;

    public static final String[] PATHS = {
      "GoToPickup",
      "GoToShootPos",
      "Pickup3",
      "TrenchPivot"
    };

  }
}
