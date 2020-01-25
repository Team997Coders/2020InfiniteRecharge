package frc.robot;

public final class Constants {


    public static class Ports {
        public static final int
            motorFrontLeft = 0,
            motorFrontRight = 0,
            motorBackLeft = 0,
            motorBackRight = 0,
            ultraChannel = 0,


            SHOOTER_MOTOR_1 = 4,
            //SHOOTER_MOTOR_2 = 6,

            upperHopperMotor1 = 2, 
            upperHopperMotor2 = 3,
            lowerHopperMotor1 = 5,
            lowerHopperMotor2 = 6,
            hopperfrontIR = 0,
            hopperbackIR = 1,

            ButtonA = 1,
            ButtonB = 2,
            ButtonX = 3,
            ButtonY = 4;
    }

    public static class Values {
        public static final int 
            voltageToFeet = 1;


        public static final double
            autoDriveForwardp = 420,
            autoDriveForwardi = 69,
            autoDriveForwardd = 42.0,
            autoGyrop = 420,
            autoGyroi = 69,
            autoGyrod = 32.98,

            shooterOutput = 1300.0, // TODO: replace with actual
            shooterBeltSpeed = 0.8;

    }
}

