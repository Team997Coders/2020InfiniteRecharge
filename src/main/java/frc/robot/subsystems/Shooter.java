package frc.robot.subsystems;

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

  private CANSparkMax mMotor1, mMotor2;
  private CANPIDController mController;
  private CANEncoder mEncoder;

  private Shooter() {
    mMotor1 = new CANSparkMax(Constants.Ports.SHOOTER_MOTOR_1, MotorType.kBrushless);
    mMotor2 = new CANSparkMax(Constants.Ports.SHOOTER_MOTOR_2, MotorType.kBrushless);

    mMotor1.restoreFactoryDefaults();
    mMotor2.restoreFactoryDefaults();

    mMotor1.setIdleMode(IdleMode.kCoast);
    mMotor2.setIdleMode(IdleMode.kCoast);

    //mMotor1.setInverted(true);
    //mMotor2.setInverted(true);

    mMotor2.follow(mMotor1);

    mEncoder = mMotor1.getEncoder();
    // mEncoder.setVelocityConversionFactor(Constants.Values.SHOOTER_GEARING);

    mController = mMotor1.getPIDController();
    mController.setP(Constants.Values.SHOOTER_VELOCITY_GAINS.kP);
    mController.setI(Constants.Values.SHOOTER_VELOCITY_GAINS.kI);
    mController.setD(Constants.Values.SHOOTER_VELOCITY_GAINS.kD);
    mController.setFF(Constants.Values.SHOOTER_VELOCITY_GAINS.kF);
    mController.setIMaxAccum(1, 1);

    register();
  }

  private static Shooter instance;
  public static Shooter getInstance() {if(instance == null) instance = new Shooter(); return instance;}

  public void setRPM(double RPM) {
    mController.setReference(RPM, ControlType.kVelocity);
  }

  public void SetYeeterPercent(double perc) {
    mMotor1.set(perc);
  }

  public void GoodStop() {
    mMotor1.set(0.0);
  }

  public void updateSmartDashboard(){
    SmartDashboard.putNumber("Shooter/encoderspeed", getRPMs(false));
    SmartDashboard.putNumber("Shooter/output", mMotor1.getAppliedOutput());
    SmartDashboard.putNumber("Shooter/current 1", mMotor1.getOutputCurrent());
    SmartDashboard.putNumber("Shooter/current 2", mMotor2.getOutputCurrent());
    if (Robot.verbose) {
      SmartDashboard.putNumber("Shooter/Ball Ejection Speed", getBallSpeed());
    }
  }

  public double getRPMs(boolean parsing) {
    if (parsing) return mEncoder.getVelocity() * Constants.Values.SHOOTER_GEARING;
    else return mEncoder.getVelocity();
  }

  public double getNeededBallVelocity(double distance) {
    return (1 / (((Math.tan(Constants.Values.SHOOTER_RELEASE_ANGLE * (Math.PI / 180))) / (-4.9 * distance)) + ((2.49 - Constants.Values.SHOOTER_RELEASE_HEIGHT) / (4.9 * distance * distance)))) * (1 / Math.cos(Constants.Values.SHOOTER_RELEASE_ANGLE * (Math.PI / 180)));
  }

  public double getBallSpeed() {
    return (getRPMs(false) / 60) * (Constants.Values.SHOOTER_CIRCUMFERENCE_CM / 100);
  }

  public double getControllerError(double target) {
    return target - getRPMs(false);
  }

  @Override
  public void periodic() {
    updateSmartDashboard();
  }

}
