package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class DriveTrain implements Subsystem {

  public double P;
  public double I;
  public double D;
  
  private TalonFX frontLeft;
  private TalonFX frontRight;
  private TalonFX backLeft;
  private TalonFX backRight;

  private AHRS imu;

  private AnalogInput ultrasonic;

  private DriveTrain() {

    P = Constants.Values.VISION_TURNING_P;
    I = Constants.Values.VISION_TURNING_I;
    D = Constants.Values.VISION_TURNING_D;

    SupplyCurrentLimitConfiguration currentLimitConfig = new SupplyCurrentLimitConfiguration(true, 40, 50, 0.1);

    ultrasonic = new AnalogInput(Constants.Ports.ultrasonicChannel);
    imu = new AHRS(Port.kUSB);

    frontLeft = new TalonFX(Constants.Ports.motorFrontLeft);
    frontRight = new TalonFX(Constants.Ports.motorFrontRight);
    backLeft = new TalonFX(Constants.Ports.motorBackLeft);
    backRight = new TalonFX(Constants.Ports.motorBackRight);

    frontLeft.configFactoryDefault(10);
    frontRight.configFactoryDefault(10);
    backLeft.configFactoryDefault(10);
    backRight.configFactoryDefault(10);

    frontLeft.setInverted(true);
    backLeft.setInverted(true);

    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

    frontLeft.configSupplyCurrentLimit(currentLimitConfig, 10);
    frontRight.configSupplyCurrentLimit(currentLimitConfig, 10);
    backLeft.configSupplyCurrentLimit(currentLimitConfig, 10);
    backRight.configSupplyCurrentLimit(currentLimitConfig, 10);

    frontLeft.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    frontLeft.setSelectedSensorPosition(0, 0, 10);
    frontRight.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    frontRight.setSelectedSensorPosition(0, 0, 10);

    frontLeft.config_kP(0, Constants.Values.DRIVE_VELOCITY_GAINS.kP);
    frontLeft.config_kI(0, Constants.Values.DRIVE_VELOCITY_GAINS.kI);
    frontLeft.config_kD(0, Constants.Values.DRIVE_VELOCITY_GAINS.kD);
    frontLeft.config_kF(0, Constants.Values.DRIVE_VELOCITY_GAINS.kF);
    
    frontRight.config_kP(0, Constants.Values.DRIVE_VELOCITY_GAINS.kP);
    frontRight.config_kI(0, Constants.Values.DRIVE_VELOCITY_GAINS.kI);
    frontRight.config_kD(0, Constants.Values.DRIVE_VELOCITY_GAINS.kD);
    frontRight.config_kF(0, Constants.Values.DRIVE_VELOCITY_GAINS.kF);

    frontLeft.setNeutralMode(NeutralMode.Brake);
    backLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    backRight.setNeutralMode(NeutralMode.Brake);
  }

  /*public void updatePID() {
    P = SmartDashboard.getNumber("DriveTrain/P", 0);
    I = SmartDashboard.getNumber("DriveTrain/I", 0);
    D = SmartDashboard.getNumber("DriveTrain/D", 0);

  }*/

  public void putCurrentPID() {
    SmartDashboard.putNumber("DriveTrain/P", Constants.Values.VISION_TURNING_P);
    SmartDashboard.putNumber("DriveTrain/I", Constants.Values.VISION_TURNING_I);
    SmartDashboard.putNumber("DriveTrain/D", Constants.Values.VISION_TURNING_D);

    //SmartDashboard.setPersistent("DriveTrain/P");
    //SmartDashboard.setPersistent("DriveTrain/I");
    //SmartDashboard.setPersistent("DriveTrain/D");

  }

  public void setMotors(double leftSpeed, double rightSpeed) {
    frontLeft.set(ControlMode.PercentOutput, leftSpeed);
    frontRight.set(ControlMode.PercentOutput, rightSpeed);
  }

  public void setVelocity(double leftFeetPerSecond, double rightFeetPerSecond) {
    //SmartDashboard.putNumber(, value)
    frontLeft.set(ControlMode.Velocity, leftFeetPerSecond / Constants.Values.DRIVE_VEL_2_FEET);
    frontRight.set(ControlMode.Velocity, rightFeetPerSecond / Constants.Values.DRIVE_VEL_2_FEET);
  }

  public double getLeftSensor() {
    return frontLeft.getSelectedSensorPosition(0);
  }

  public double getRightSensor() {
    return frontRight.getSelectedSensorPosition(0);
  }

  public double getGyroAngle() {
    return imu.getAngle();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("DriveTrain/Right Motors Position", getRightSensor());
    SmartDashboard.putNumber("DriveTrain/Left Motors Position", getLeftSensor());

    SmartDashboard.putNumber("DriveTrain/Left Motor Velocity", frontLeft.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("DriveTrain/Right Motors Velocity", frontRight.getSelectedSensorVelocity(0));
    
    SmartDashboard.putNumber("DriveTrain/Front Left Motor Temperature", frontLeft.getTemperature());
    SmartDashboard.putNumber("DriveTrain/Front Right Motor Temperature", frontRight.getTemperature());
    SmartDashboard.putNumber("DriveTrain/Back Left Motor Temperature", backLeft.getTemperature());
    SmartDashboard.putNumber("DriveTrain/Back Right Motor Temperature", backRight.getTemperature());

    SmartDashboard.putNumber("DriveTrain/Left Feet", getFeet(frontLeft));
    SmartDashboard.putNumber("DriveTrain/Right Feet", getFeet(frontRight));

    SmartDashboard.putNumber("DriveTrain/Left Error", frontLeft.getClosedLoopError());
    SmartDashboard.putNumber("DriveTrain/Right Error", frontRight.getClosedLoopError());

    SmartDashboard.putNumber("DriveTrain/Gyro", getGyroAngle());
    SmartDashboard.putNumber("DriveTrain/Ultrasonic", ultrasonic.getVoltage() / Constants.Values.voltageToFeet); //displays feet from target.
  }

  private static DriveTrain instance;

  public double getFeet(TalonFX m) {
    return m.getSelectedSensorPosition(0) * (Constants.Values.DRIVE_VEL_2_FEET / 10.0);
  }

  public void resetEncoders() {
    frontLeft.setSelectedSensorPosition(0, 0, 10);
    frontRight.setSelectedSensorPosition(0, 0, 10);
  }

  public static DriveTrain getInstance() {
    if (instance == null) { 
      instance = new DriveTrain();
      //System.out.println("Inited========================================================================");
    }
    return instance;
  }
}