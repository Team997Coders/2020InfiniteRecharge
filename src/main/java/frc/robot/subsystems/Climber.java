/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Climber extends SubsystemBase {
  private Solenoid climberPiston;
  private CANSparkMax climberMotor;
  private CANEncoder climberEncoder;

  private Climber() {
    climberMotor = new CANSparkMax(Constants.Ports.climberMotorPort, MotorType.kBrushless);
    climberPiston = new Solenoid(Constants.Ports.climberPistonPort);

    climberEncoder = climberMotor.getEncoder();

    climberMotor.setSmartCurrentLimit(70);
    climberMotor.setIdleMode(IdleMode.kBrake);
  }

  public void ExtendPiston(){
    climberPiston.set(false);
  }

  public void RetractPiston(){
    climberPiston.set(true);
  }

  public void setBrake() {
    climberMotor.setIdleMode(IdleMode.kBrake);
  }

  public void setCoast() {
    climberMotor.setIdleMode(IdleMode.kCoast);
  }

  public void setSpeed(double speed){
    climberMotor.set(speed);
  }

  public double getEncoder() {
    return climberEncoder.getPosition();
  }

  @Override
  public void periodic() {
    updateSmartDashboard();
  }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Climber/Encoder value", getEncoder());

    if (Robot.verbose) {
      // put less essential datas here.
    }
  }

  public static Climber instance;
  public static Climber getInstance(){
    if (instance == null){
      instance = new Climber();
    }
    return instance;
  }
}
