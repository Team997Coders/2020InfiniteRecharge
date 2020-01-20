/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Shooter implements Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private CANSparkMax yeeter1, yeeter2;
  private CANEncoder yeeterEncoder;
  private CANPIDController yeeterPIDController;
  private Shooter() {
    yeeter1 = new CANSparkMax(Constants.Ports.SHOOTER_MOTOR_1, MotorType.kBrushless);
    yeeter2 = new CANSparkMax(Constants.Ports.SHOOTER_MOTOR_2, MotorType.kBrushless);

    yeeterEncoder = yeeter1.getEncoder(EncoderType.kHallSensor, 42);

    yeeterPIDController = yeeter1.getPIDController(); //TODO: set up constants

    yeeter1.restoreFactoryDefaults();
    yeeter2.restoreFactoryDefaults();

    yeeter2.follow(yeeter1);
  }

  private static Shooter instance;
  public static Shooter getInstance() {if(instance == null) instance = new Shooter(); return instance;}

  public void SetYeeterRPM(double yeeterRPMs) {
    yeeterPIDController.setSmartMotionMaxVelocity(yeeterRPMs, 0);
  }

  public double getRPMs() {
    return yeeterEncoder.getVelocity();
  }

}
