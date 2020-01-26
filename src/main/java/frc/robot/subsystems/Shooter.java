/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Shooter implements Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private TalonSRX yeeter;
  private Shooter() {
    yeeter = new TalonSRX(Constants.Ports.SHOOTER_MOTOR_1);
    yeeter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    yeeter.setNeutralMode(NeutralMode.Coast);

    //yeeterPIDController = yeeter1.getPIDController(); //TODO: set up constants

    //yeeter1.restoreFactoryDefaults();
    //yeeter2.restoreFactoryDefaults();

    //yeeter2.follow(yeeter1);

    register();
  }

  private static Shooter instance;
  public static Shooter getInstance() {if(instance == null) instance = new Shooter(); return instance;}

  public void SetYeeterRPM(double yeeterRPMs) {
    double rpmToInternal = 1 / 0.1465;
    yeeter.set(ControlMode.Velocity, yeeterRPMs * rpmToInternal);
  }

  public void SetYeeterPercent(double perc) {
    yeeter.set(ControlMode.PercentOutput, perc);
  }

  public void GoodStop() {
    yeeter.set(ControlMode.Disabled, 0);
  }

  public void updateSmartDashboard(){
    SmartDashboard.putNumber("Shooter/encoderspeed", getRPMs());
    System.out.println("Shooter Sped: " + getRPMs());
  }
  public double getRPMs() {
    double internalCountToRpm = 0.1465;
    double revsPerSecondMotorSide = internalCountToRpm * yeeter.getSelectedSensorVelocity();// / (1024 * 4); //(motor.getSelectedSensorVelocity(0) / (1024 * 4)) * 10;
    return revsPerSecondMotorSide;
  }

  @Override
  public void periodic() {
    updateSmartDashboard();
  }

}
