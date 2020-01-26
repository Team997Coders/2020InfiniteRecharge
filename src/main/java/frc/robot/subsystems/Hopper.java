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
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {

  public static boolean used = false;

  public int mBallCount = 0;

  VictorSPX upperConveyorMotor1;
  VictorSPX upperConveyorMotor2;
  public DigitalInput frontIRsensor, shooterIRsensor;
  
  private Hopper() {
    upperConveyorMotor1 = new VictorSPX(Constants.Ports.upperHopperMotor1);
    upperConveyorMotor2 = new VictorSPX(Constants.Ports.upperHopperMotor2);
    frontIRsensor = new DigitalInput(Constants.Ports.hopperfrontIR);
    shooterIRsensor = new DigitalInput(Constants.Ports.hopperbackIR);

    upperConveyorMotor2.follow(upperConveyorMotor1);

    upperConveyorMotor1.setNeutralMode(NeutralMode.Brake);
    upperConveyorMotor2.setNeutralMode(NeutralMode.Brake);

    register();
  }

  public void setUpperSpeed(double setSpeep){
    upperConveyorMotor1.set(ControlMode.PercentOutput, setSpeep);
  }

  public void updateSmartDashboard(){
    SmartDashboard.putBoolean("Hopper/Intake IR Sensor", !frontIRsensor.get());
    SmartDashboard.putBoolean("Hopper/Shooter IR Sensor", !shooterIRsensor.get());
    SmartDashboard.putNumber("Hopper/Ball Count", mBallCount);
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
