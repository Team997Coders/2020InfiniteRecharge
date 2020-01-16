package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ArcadeDrive;

public class DriveTrain extends SubsystemBase {
  
  private TalonFX frontLeft;
  private TalonFX frontRight;
  private TalonFX backLeft;
  private TalonFX backRight;

  private AHRS gyro;

  private AnalogInput ultrasonic;

  private DriveTrain() {

    SupplyCurrentLimitConfiguration currentLimitConfig = new SupplyCurrentLimitConfiguration(true, 40, 50, 0.1);
    setDefaultCommand(new ArcadeDrive());

    ultrasonic = new AnalogInput(Constants.Ports.ultraChannel);
    gyro = new AHRS();

    frontLeft = new TalonFX(Constants.Ports.motorFrontLeft);
    frontRight = new TalonFX(Constants.Ports.motorFrontRight);
    backLeft = new TalonFX(Constants.Ports.motorBackLeft);
    backRight = new TalonFX(Constants.Ports.motorBackRight);

    frontLeft.configSupplyCurrentLimit(currentLimitConfig, 10);
    frontRight.configSupplyCurrentLimit(currentLimitConfig, 10);
    backLeft.configSupplyCurrentLimit(currentLimitConfig, 10);
    backRight.configSupplyCurrentLimit(currentLimitConfig, 10);

    frontLeft.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    frontLeft.setSelectedSensorPosition(0, 0, 10);
    frontRight.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    frontRight.setSelectedSensorPosition(0, 0, 10);

    backLeft.follow(frontLeft);
    backRight.follow(frontRight);
  }

  public void setMotors(double leftSpeed, double rightSpeed) {
    frontLeft.set(ControlMode.PercentOutput, -leftSpeed);
    frontRight.set(ControlMode.PercentOutput, rightSpeed);
  }

  public double getLeftSensor() {
    return frontLeft.getSelectedSensorPosition(0);
  }

  public double getRightSensor() {
    return frontRight.getSelectedSensorPosition(0);
  }

  public double getGyroAngle() {
    return gyro.getAngle();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Drivetrain/Right Motors Position", getRightSensor());
    SmartDashboard.putNumber("Drivetrain/Left Motors Position", getLeftSensor());

    SmartDashboard.putNumber("Drivetrain/Left Motor Velocity", frontLeft.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("Drivetrain/Right Motors Velocity", frontRight.getSelectedSensorVelocity(0));
    
    SmartDashboard.putNumber("Drivetrain/Front Left Motor Temperature", frontLeft.getTemperature());
    SmartDashboard.putNumber("Drivetrain/Front Right Motor Temperature", frontLeft.getTemperature());
    SmartDashboard.putNumber("Drivetrain/Back Left Motor Temperature", frontLeft.getTemperature());
    SmartDashboard.putNumber("Drivetrain/Back Right Motor Temperature", frontLeft.getTemperature());

    SmartDashboard.putNumber("DriveTrain/Gyro", getGyroAngle());
    SmartDashboard.putNumber("DriveTrain/Ultrasonic", ultrasonic.getVoltage());
  }

  private static DriveTrain instance;
  public static DriveTrain getInstance() { return instance == null ? instance = new DriveTrain() : instance; }
  
}