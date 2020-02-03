/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Hopper implements Subsystem {

  public boolean autoIndexMoving = false;
  public int mBallCount = 0;

  private DigitalInput mIntakeIR, mShooterIR;
  private TalonSRX mMotor1, mMotor2;
  
  private Hopper() {
    mMotor1 = new TalonSRX(Constants.Ports.HOPPER_MOTOR_TOP);
    mMotor2 = new TalonSRX(Constants.Ports.HOPPER_MOTOR_BOTTOM);
    mIntakeIR = new DigitalInput(Constants.Ports.INTAKE_IR);
    mShooterIR = new DigitalInput(Constants.Ports.SHOOTER_IR);

    mMotor1.configFactoryDefault(10);
    mMotor2.configFactoryDefault(10);

    mMotor1.setNeutralMode(NeutralMode.Brake);
    mMotor2.setNeutralMode(NeutralMode.Brake);

    mMotor2.setInverted(true);
    mMotor2.follow(mMotor1);

    register();
  }

  public void setSpeed(double speed){
    mMotor1.set(ControlMode.PercentOutput, speed);
  }

  public boolean getShooterBall() { return !mShooterIR.get(); }
  public boolean getIntakeBall() { return !mIntakeIR.get(); }

  public void updateSmartDashboard(){
    SmartDashboard.putBoolean("Hopper/Intake IR Sensor", getIntakeBall());
    SmartDashboard.putBoolean("Hopper/Shooter IR Sensor", getShooterBall());
    SmartDashboard.putNumber("Hopper/Ball Count", mBallCount);
  }

  @Override
  public void periodic() { updateSmartDashboard(); }

  private static Hopper instance;
  public static Hopper getInstance() { if (instance == null) instance = new Hopper(); return instance; }
}
