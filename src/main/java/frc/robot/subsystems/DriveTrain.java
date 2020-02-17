package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.kauailabs.navx.frc.AHRS;

import org.team997coders.spartanlib.math.MathUtils;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import org.team997coders.spartanlib.limelight.LimeLight;

public class DriveTrain implements Subsystem {

  private double lastLeft = 0.0, lastRight = 0.0;
  private double maxSpeedForward = 0.6, maxSpeedReverse = -0.6;

  private TalonFX frontLeft;
  private TalonFX frontRight;
  private TalonFX backLeft;
  private TalonFX backRight;

  private Orchestra orch;

  private AHRS imu;

  private AnalogInput ultrasonic;

  private DriveTrain() {

    // Try setting the Threshold Limit to 0 to hard cap the current
    SupplyCurrentLimitConfiguration currentLimitConfig = new SupplyCurrentLimitConfiguration(true, 40, 0, 0);
    // StatorCurrentLimitConfiguration statorLimit = new StatorCurrentLimitConfiguration(true)

    ultrasonic = new AnalogInput(Constants.Ports.ultrasonicChannel);
    imu = new AHRS(Port.kUSB);

    orch = new Orchestra();

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

    register();
  }

  public void setMotors(double leftSpeed, double rightSpeed) {

    frontLeft.set(ControlMode.PercentOutput, leftSpeed);
    frontRight.set(ControlMode.PercentOutput, rightSpeed);

    SmartDashboard.putNumber("DriveTrain/left speddd", leftSpeed);
    SmartDashboard.putNumber("DriveTrain/right speddd", rightSpeed);
  }

  public void setVelocity(double leftFeetPerSecond, double rightFeetPerSecond) {
    frontLeft.set(ControlMode.Velocity, leftFeetPerSecond / Constants.Values.DRIVE_VEL_2_FEET);
    frontRight.set(ControlMode.Velocity, rightFeetPerSecond / Constants.Values.DRIVE_VEL_2_FEET);
  }

  public void simpleAccelControl(double left, double right) {
    double maxAdjust = Constants.Values.ACCELERATION * Robot.getDeltaT();
    if (Math.abs(left - lastLeft) > maxAdjust) {
      int sign = (int)(Math.abs(left - lastLeft) / (left - lastLeft));
      left = lastLeft + (maxAdjust * sign);
    }
    if (Math.abs(right - lastRight) > maxAdjust) {
      int sign = (int)(Math.abs(right - lastRight) / (right - lastRight));
      right = lastRight + (maxAdjust * sign);
    }

    left = MathUtils.clamp(left, maxSpeedReverse, maxSpeedForward);
    right = MathUtils.clamp(right, maxSpeedReverse, maxSpeedForward);

    frontLeft.set(ControlMode.PercentOutput, left);
    frontRight.set(ControlMode.PercentOutput, right);

    lastLeft = left;
    lastRight = right;

    lastLeft = MathUtils.clamp(lastLeft, maxSpeedReverse, maxSpeedForward);
    lastRight = MathUtils.clamp(lastRight, maxSpeedReverse, maxSpeedForward);
  }

  public void stopOrchestra() {
    orch.clearInstruments();
    if (orch.isPlaying()) orch.stop();
  }

  public void playOrchestra(String song) {
    orch.addInstrument(frontLeft);
    orch.addInstrument(frontRight);
    orch.addInstrument(backLeft);
    orch.addInstrument(backRight);
    orch.loadMusic("Megalovania.chrp");
    orch.play();
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

  public double getLeftFeet() {
    return getFeet(frontLeft);
  }

  public double getFeet(TalonFX m) {
    return m.getSelectedSensorPosition(0) * (Constants.Values.DRIVE_VEL_2_FEET / 10.0);
  }

  public void resetEncoders() {
    frontLeft.setSelectedSensorPosition(0, 0, 10);
    frontRight.setSelectedSensorPosition(0, 0, 10);
  }

  @Override
  public void periodic() {
    updateSmartDashboard();
  }

  public void updateSmartDashboard() {
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
    SmartDashboard.putNumber("DriveTrain/Ultrasonic Distance (ft)", ultrasonic.getVoltage() / Constants.Values.VOLTAGE_TO_FEET);

    if (Robot.verbose) {
      SmartDashboard.putNumber("LimeLight/Target Distance (in)", (98.25 - Constants.Values.VISION_LIMELIGHT_HEIGHT) / (Math.tan( (Constants.Values.VISION_LIMELIGHT_ANGLE + LimeLight.getInstance().getDouble(LimeLight.TARGET_Y, 0)) * (Math.PI / 180) )));
      SmartDashboard.putNumber("LimeLight/AngleToTarget", (Constants.Values.VISION_LIMELIGHT_ANGLE + LimeLight.getInstance().getDouble(LimeLight.TARGET_Y, 0)));
          
      SmartDashboard.putNumber("Limelight/hasTarget", LimeLight.getInstance().getDouble(LimeLight.TARGET_VISIBLE, 0));
      SmartDashboard.putNumber("Limelight/targetX", LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0));
      SmartDashboard.putNumber("Limelight/targetY", LimeLight.getInstance().getDouble(LimeLight.TARGET_Y, 0));
    }
  }

  private double currentAugment(double wants, TalonFX motor) {
    final double LIM = 35;
    final double GAIN_MOD = 1.0;
    final double MAX_AUGMENT = 0.4; // Max amount of augmentation per second

    double currentError = motor.getSupplyCurrent() - LIM;
    wants -= currentError * GAIN_MOD;

    return MathUtils.clamp(wants, wants - MAX_AUGMENT * Robot.getDeltaT(), wants + MAX_AUGMENT * Robot.getDeltaT());
  }

  public void setCoast() {
    frontLeft.setNeutralMode(NeutralMode.Coast);
    frontRight.setNeutralMode(NeutralMode.Coast);
    backLeft.setNeutralMode(NeutralMode.Coast);
    backRight.setNeutralMode(NeutralMode.Coast);
  }

  public void setBrake() {
    frontLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    backLeft.setNeutralMode(NeutralMode.Brake);
    backRight.setNeutralMode(NeutralMode.Brake);
  }

  private static DriveTrain instance;

  public static DriveTrain getInstance() {
    if (instance == null) {
      instance = new DriveTrain();
    }
    return instance;
  }
}