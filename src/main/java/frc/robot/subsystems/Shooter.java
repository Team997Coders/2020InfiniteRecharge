package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class Shooter implements Subsystem {

  private CANSparkMax motor1, motor2;
  private CANEncoder encoder;
  private CANPIDController controller;

  private double targetRPM = 0.0;

  public Shooter() {
    motor1 = new CANSparkMax(Constants.Ports.SHOOTER_MOTOR_1, MotorType.kBrushless);
    motor2 = new CANSparkMax(Constants.Ports.SHOOTER_MOTOR_2, MotorType.kBrushless);
    
    motor1.restoreFactoryDefaults();
    motor2.restoreFactoryDefaults();

    motor2.follow(motor1);

    motor1.setSmartCurrentLimit(30, 50);
    motor2.setSmartCurrentLimit(30, 50);

    encoder = motor1.getEncoder();
    encoder.setPosition(0.0);
    encoder.setVelocityConversionFactor(Constants.Values.SHOOTER_GEARING_FACTOR);

    controller = motor1.getPIDController();
    controller.setP(Constants.Values.SHOOTER_CONTROL_P);
    controller.setI(0.0);
    controller.setD(0.0);
    controller.setFF(0.0);
  }

  public void setRPM(double rpm) {
    controller.setReference(rpm, ControlType.kVelocity, 0, rpm);
    targetRPM = rpm;
  }

  public double getCurrentRPM() { return encoder.getVelocity(); }
  public double getTargetRPM() { return targetRPM; }
  public double getRPMError() { return Math.abs(getCurrentRPM() - targetRPM); }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter/Current RPM", getCurrentRPM());
    SmartDashboard.putNumber("Shooter/Target RPM", targetRPM);
  }

  private static Shooter instance = null;
  public static Shooter getInstance() {
    if (instance == null) instance = new Shooter();
    return instance;
  }
}