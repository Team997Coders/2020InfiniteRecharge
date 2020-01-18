package frc.robot;

public final class Constants {
  
  public static class Ports {

    public static final int

      SHOOTER_MOTOR_1 = 5,
      SHOOTER_MOTOR_2 = 6,

      //Hopper things
      upperHopperMotor1 = 0, 
      upperHopperMotor2 = 1,
      lowerHopperMotor1 = 0,
      lowerHopperMotor2 = 1,
      hopperfrontIR = 0,
      hopperbackIR = 1,

      //Drivetrain things
      motorFrontLeft = 0,
      motorFrontRight = 0,
      motorBackLeft = 0,
      motorBackRight = 0,
      ultraChannel = 0,

      ButtonA = 0,
      ButtonB = 1;
  }

  public static class Values {

    public static final int 
      voltageToFeet = 1;

    public static final double
      shooterOutput = 1.0; // TODO: replace with actual
  }

}
