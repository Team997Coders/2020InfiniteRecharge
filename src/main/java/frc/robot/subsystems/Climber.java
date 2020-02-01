/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  private Solenoid climberPiston;
  private CANSparkMax climberMotor;
  private VictorSPX crawlerMotor;

  private Climber() {
    climberMotor = new CANSparkMax(Constants.Ports.climberMotorPort, MotorType.kBrushless);
    climberPiston = new Solenoid(Constants.Ports.climberPistonPort);
    crawlerMotor = new VictorSPX(Constants.Ports.crawlerMotorPort);
  }
  public void ExtendPiston(){
    climberPiston.set(true);
  }
  public void RetractPiston(){
    climberPiston.set(false);
  }
  public void setSpeed(double speed){
  climberMotor.set(speed);
}
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void crawler(double setSpep){
    crawlerMotor.set(ControlMode.PercentOutput, setSpep);
  }

  public static Climber instance;
  public static Climber getInstance(){
    if (instance == null){
      instance = new Climber();
    }
    return instance;
  }
}
