/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Intake extends SubsystemBase {
  private Solenoid intakePiston;
  private CANSparkMax mMotor;


  private Intake() {
    mMotor = new CANSparkMax(Constants.Ports.INTAKE_MOTOR, MotorType.kBrushless);
    intakePiston = new Solenoid(Constants.Ports.INTAKE_SOLENOID);

    intakePiston.set(false);

    mMotor.setSmartCurrentLimit(50);
    mMotor.setIdleMode(IdleMode.kCoast);
  }

  public void setPercent(double percent) {
    mMotor.set(percent);
  }
 
  public void togglePiston() {
    intakePiston.set(!intakePiston.get());
  }

  public void setPiston(boolean extend) {
    intakePiston.set(extend);
  }

  public boolean getPistonExtended() {
    return intakePiston.get();
  }

  @Override
  public void periodic() {
    updateSmartDashboard();
  }

  public void updateSmartDashboard() {
    // if you put anything here make sure to register the subsystem.

    if (Robot.verbose) {
      // put non-essential data here.
    }
  }

  private static Intake instance;
  public static Intake getInstance() { if (instance == null) instance = new Intake(); return instance; }
}
