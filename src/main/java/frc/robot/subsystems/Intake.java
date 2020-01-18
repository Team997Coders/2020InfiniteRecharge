/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  private CANSparkMax destromos;
  private Intake() {
    destromos = new CANSparkMax(Constants.Ports.destromos, MotorType.kBrushless);
  }

  public void IntakePercent(double percent) {
    destromos.set(percent);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private static Intake instance;
  public static Intake getInstance() { return instance == null ? instance = new Intake() : instance; }
}
