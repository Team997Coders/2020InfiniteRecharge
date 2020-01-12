package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ArcadeDrive;

public class DriveTrain extends SubsystemBase {
  
  TalonFX frontLeft;
  TalonFX frontRight;
  TalonFX backLeft;
  TalonFX backRight;
  
  public DriveTrain() {
    frontLeft = new TalonFX(Constants.Ports.motorFrontLeft);
    frontRight = new TalonFX(Constants.Ports.motorFrontRight);
    backLeft = new TalonFX(Constants.Ports.motorBackLeft);
    backRight = new TalonFX(Constants.Ports.motorBackRight);

    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

    /**
     * TODO:
     * 1) Reverse one side
     * 2) Add current limiting
     * 3) Add methods to get encoder position and velocities (And maybe convert them to useful units)
     * 4) Put encoder readings on SmartDashboard
     */

    setDefaultCommand(new ArcadeDrive());
  }

  public void setMotors(double leftSpeed, double rightSpeed) {
    frontLeft.set(ControlMode.PercentOutput, leftSpeed);
    frontRight.set(ControlMode.PercentOutput, rightSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
