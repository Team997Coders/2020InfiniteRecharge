package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFXPIDSetConfiguration;

import org.team997coders.spartanlib.helpers.PIDConstants;

import frc.robot.util.Gains;

public final class Constants {

  public static class Ports {

    public static final int

      SHOOTER_MOTOR_1 = 8,
      SHOOTER_MOTOR_2 = 9,

      //Hopper things
      upperHopperMotor1 = 4, 
      upperHopperMotor2 = 5,
      lowerHopperMotor1 = 6,
      lowerHopperMotor2 = 7,
      hopperfrontIR = 0,
      hopperbackIR = 1,

      //Drivetrain things
      motorFrontLeft = 0,
      motorFrontRight = 1,
      motorBackLeft = 2,
      motorBackRight = 3,
      ultrasonicChannel = 0,

      //climber things
      climberMotorPort = 10,
      climberPistonPort = 0,

      buttonA = 1,
      buttonB = 2,
      buttonX = 3,
      buttonY = 4;
      
  }

  public static class Values {     

    public static final double
        voltageToFeet = (12 * 0.0098), //9.8mV per inch with a 5V input.

        shooterOutput = 1.0, // TODO: replace with actual

        visionTurningP = 0,
        visionTurningI = 0,
        visionTurningD = 0,
        visionTolerance = 0.5,
        visionTimeout = 1000, //in ms

        // To Seconds, To RPM Motorside, To RPM Wheelside, To Circumference, To Feet
        DRIVE_VEL_2_FEET = 10 * ((double)1 / (double)2048)
          * ((double)9 / (double)70) * ((double)5 * Math.PI) * ((double)1 / (double)12);

    public static final Gains

      DRIVE_VELOCITY = new Gains(0.13, 0.0, 3.0, (double)1023 / (double)19990);
  }

  public static class PathFollower {

    public static final double

      TRACK_WIDTH = 2.1;

  }

}
