package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ArcadeDrive;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class DriveTrain extends SubsystemBase {

  private TalonFX frontLeft;
  private TalonFX frontRight;
  private TalonFX backLeft;
  private TalonFX backRight;

  private AHRS imu;

  private AnalogInput ultrasonic;

  private DriveTrain() {

    SupplyCurrentLimitConfiguration currentLimitConfig = new SupplyCurrentLimitConfiguration(true, 40, 50, 0.1);

    ultrasonic = new AnalogInput(Constants.Ports.ultraChannel);
    imu = new AHRS(Port.kUSB);// TODO: but something in here

    frontLeft = new TalonFX(Constants.Ports.motorFrontLeft);
    frontRight = new TalonFX(Constants.Ports.motorFrontRight);
    backLeft = new TalonFX(Constants.Ports.motorBackLeft);
    backRight = new TalonFX(Constants.Ports.motorBackRight);

    frontLeft.configFactoryDefault(10);
    frontRight.configFactoryDefault(10);
    backLeft.configFactoryDefault(10);
    backRight.configFactoryDefault(10);

    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

    frontLeft.setInverted(true);
    backLeft.setInverted(true);


    // frontLeft.setInverted(false);
    // frontRight.setInverted(true);
    // backLeft.setInverted(false);
    // backRight.setInverted(true);

    frontLeft.config_kP(0, 0.2);
    frontLeft.config_kI(0, 0);
    frontLeft.config_kD(0, 0);
    frontLeft.config_kF(0, 0);
    frontRight.config_kP(0, 0.2);
    frontRight.config_kI(0, 0);
    frontRight.config_kD(0, 0);
    frontRight.config_kF(0, 0);

    frontLeft.configSupplyCurrentLimit(currentLimitConfig, 10);
    frontRight.configSupplyCurrentLimit(currentLimitConfig, 10);
    backLeft.configSupplyCurrentLimit(currentLimitConfig, 10);
    backRight.configSupplyCurrentLimit(currentLimitConfig, 10);

    frontLeft.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    frontLeft.setSelectedSensorPosition(0, 0, 10);
    frontRight.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    frontRight.setSelectedSensorPosition(0, 0, 10);

  }

  public void setMotors(double leftSpeed, double rightSpeed) {
    frontLeft.set(ControlMode.PercentOutput, leftSpeed);
    frontRight.set(ControlMode.PercentOutput, rightSpeed);
  }

  public double getLeftSensor() {
    return frontLeft.getSelectedSensorPosition(0);
  }

  public double getRightSensor() {
    return frontRight.getSelectedSensorPosition(0);
  }

  public boolean getVelocityZero(){

    final boolean leftZero = (frontLeft.getSelectedSensorVelocity(0) == 0);
    final boolean rightZero = (frontRight.getSelectedSensorVelocity(0) == 0);

    return (leftZero && rightZero);
    
  }
  public double getGyroAngle() {
    return imu.getAngle();
  }

  public void resetGyroAngle() {
    imu.reset();
  }

  public void resetEncoders() {

    frontLeft.setSelectedSensorPosition(0, 0, 10);
    frontRight.setSelectedSensorPosition(0, 0, 10);
  }

  public void setPosition(double leftPostion, double rightPosition) {
    System.out.println("Doing the other thing-777777====" + leftPostion + "" + rightPosition);

    frontLeft.set(ControlMode.Position, leftPostion);
    frontRight.set(ControlMode.Position, rightPosition);
  }

  public double calcualteEncoderTicksFromInches(double inches) {
    //double ticks = (inches / (Math.PI * 5)) * (50 / 9) * 2048;
    double ticks = inches * 1000;
    return ticks;

  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("left Position Target", getLeftSensor());
    SmartDashboard.putNumber("right Position Target", getRightSensor());
    SmartDashboard.putNumber("Drivetrain/Right Motors Position", getRightSensor());
    SmartDashboard.putNumber("Drivetrain/Left Motors Position", getLeftSensor());

    SmartDashboard.putNumber("Drivetrain/Left Motor Velocity", frontLeft.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("Drivetrain/Right Motors Velocity", frontRight.getSelectedSensorVelocity(0));

    SmartDashboard.putNumber("Drivetrain/Front Left Motor Temperature", frontLeft.getTemperature());
    SmartDashboard.putNumber("Drivetrain/Front Right Motor Temperature", frontLeft.getTemperature());
    SmartDashboard.putNumber("Drivetrain/Back Left Motor Temperature", frontLeft.getTemperature());
    SmartDashboard.putNumber("Drivetrain/Back Right Motor Temperature", frontLeft.getTemperature());

    SmartDashboard.putNumber("DriveTrain/Gyro", getGyroAngle());
    SmartDashboard.putNumber("DriveTrain/Ultrasonic", ultrasonic.getVoltage() * Constants.Values.voltageToFeet);
  }

  private static DriveTrain instance;

  public static DriveTrain getInstance() {
    return instance == null ? instance = new DriveTrain() : instance;
  }

}