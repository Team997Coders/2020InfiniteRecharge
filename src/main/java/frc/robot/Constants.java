package frc.robot;

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
      hopperbackIR = 2,

      //Drivetrain things
      motorFrontLeft = 0,
      motorFrontRight = 1,
      motorBackLeft = 2,
      motorBackRight = 3,
      ultrasonicChannel = 1,

      //climber things
      climberMotorPort = 10,
      climberPistonPort = 0,

      buttonA = 1,
      ButtonB = 2,
      buttonX = 3,
      buttonY = 4;
      
  }

  public static class Values {

    public static final int 
        voltageToFeet = 1;

    public static final double
        shooterOutput = 1.0, // TODO: replace with actual

        visionTurningP = 0,
        visionTurningI = 0,
        visionTurningD = 0,
        visionTolerance = 0.5,
        visionTimeout = 1000; //in ms
  }

}
