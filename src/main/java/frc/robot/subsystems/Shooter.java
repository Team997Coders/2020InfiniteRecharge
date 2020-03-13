package frc.robot.subsystems;

// Imports 
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;

public class Shooter implements Subsystem {

  // Craete Hardwware Objects
  private CANSparkMax mMotor1, mMotor2;
  private CANPIDController mController;
  private CANEncoder mEncoder;

  private Shooter() {

    //create an instance of the mMotor1 and mMotor2 objects and set their ports and MotorType
    mMotor1 = new CANSparkMax(Constants.Ports.SHOOTER_MOTOR_1, MotorType.kBrushless);
    mMotor2 = new CANSparkMax(Constants.Ports.SHOOTER_MOTOR_2, MotorType.kBrushless);

    //Restore the Factory defaults to make sure that there are no pre-set settings
    mMotor1.restoreFactoryDefaults();
    mMotor2.restoreFactoryDefaults();

    //When not in use set the motors to coase
    mMotor1.setIdleMode(IdleMode.kCoast);
    mMotor2.setIdleMode(IdleMode.kCoast);

    //mMotor1.setInverted(true);
    //mMotor2.setInverted(true);

    //set mMotor2 to follow mMotor1
    mMotor2.follow(mMotor1);

    //get the mMotor1 encoder value
    mEncoder = mMotor1.getEncoder(); // get the value of the encoder on mMotor1
    mEncoder.setVelocityConversionFactor(Constants.Values.SHOOTER_GEARING); // convert the encoder value to a velocity based on the shooter gearing


    //PID controller
    mController = mMotor1.getPIDController(); // create the PIT controller
    mController.setP(Constants.Values.SHOOTER_VELOCITY_GAINS.kP); // set the Proportional value
    mController.setI(Constants.Values.SHOOTER_VELOCITY_GAINS.kI); // set the Integral value
    mController.setD(Constants.Values.SHOOTER_VELOCITY_GAINS.kD); // set the Derivative value
    mController.setFF(Constants.Values.SHOOTER_VELOCITY_GAINS.kF); // set the Fixed value

    register(); // save object references
  }

  //If there is no instance of the shooter create one
  private static Shooter instance;
  public static Shooter getInstance() {if(instance == null) instance = new Shooter(); return instance;}

  //Set the RPM of the shooter through the PID controller
  public void setRPM(double RPM) {
    mController.setReference(RPM, ControlType.kVelocity);
  }

  // Set the percent output of the lead shooter motor
  public void SetYeeterPercent(double perc) {
    mMotor1.set(perc);
  }

  // stop the shooter motor
  public void GoodStop() {
    mMotor1.set(0.0);
  }

 
  // get the RPM's of the shooter
  public double getRPMs() {
    return mEncoder.getVelocity(); // get the velocity from the encoder 
  }

  // get the needed ball velocity to make it into the top target given a supplied distance
  // TODO: Find The units for this
  public double getNeededBallVelocity(double distance) {
    return (1 / (((Math.tan(Constants.Values.SHOOTER_RELEASE_ANGLE * (Math.PI / 180))) / (-4.9 * distance)) + ((2.49 - Constants.Values.SHOOTER_RELEASE_HEIGHT) / (4.9 * distance * distance)))) * (1 / Math.cos(Constants.Values.SHOOTER_RELEASE_ANGLE * (Math.PI / 180)));
  }

  //From the motor velocity, calculate the velocity of the ball
  public double getBallSpeed() {
    return (getRPMs() / 60) * (Constants.Values.SHOOTER_CIRCUMFERENCE_CM / 100);
  }

  //update the smartdashboard
  public void updateSmartDashboard(){
    SmartDashboard.putNumber("Shooter/encoderspeed", getRPMs()); //put the encoder spped on the smart dashboard
    if (Robot.verbose) { // if verbose (debugging) turned on run the following
      SmartDashboard.putNumber("Shooter/Ball Ejection Speed", getBallSpeed()); // put the ball ejection spped on the smart dahsboard
    }
  }

  @Override
  public void periodic() {
    updateSmartDashboard(); // update the smart dashboard every cycle 
  }

}
