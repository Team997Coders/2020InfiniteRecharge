/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {
  VictorSPX upperConveyorMotor1;
  VictorSPX upperConveyorMotor2;
  VictorSPX lowerConveyorMotor1;
  VictorSPX lowerConveyorMotor2;
  AnalogInput frontIRsensor, backIRsensor;
  
  
  private Hopper() {
    upperConveyorMotor1 = new VictorSPX(Constants.Ports.upperHopperMotor1);
    upperConveyorMotor2 = new VictorSPX(Constants.Ports.upperHopperMotor2); 
    lowerConveyorMotor1 = new VictorSPX(Constants.Ports.lowerHopperMotor1);
    lowerConveyorMotor2 = new VictorSPX(Constants.Ports.lowerHopperMotor2);
    frontIRsensor = new AnalogInput(Constants.Ports.hopperfrontIR);
    backIRsensor = new AnalogInput(Constants.Ports.hopperbackIR);

    upperConveyorMotor2.follow(upperConveyorMotor1);
    lowerConveyorMotor2.follow(lowerConveyorMotor1);
  }

  public void setUpperSpeed(double setSpeep){
    upperConveyorMotor1.set(ControlMode.PercentOutput, setSpeep);
  }

  public void setLowerSpeed(double setSpeep){
    lowerConveyorMotor1.set(ControlMode.PercentOutput, setSpeep);
  }



  public void updateSmartDashboard(){
    SmartDashboard.putNumber("frontIRsensor", frontIRsensor.getVoltage());
    SmartDashboard.putNumber("backIRsensor", backIRsensor.getVoltage());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateSmartDashboard();
  }
  private static Hopper instance;

  public static Hopper getInstance(){
    if (instance == null){instance = new Hopper();}
    return instance; 
  }
}
