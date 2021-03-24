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

public class Intake extends SubsystemBase {

  private Solenoid intakePiston;
  private CANSparkMax mLeader;
  private CANEncoder encoder;

  private Intake() {
    mLeader = new CANSparkMax(Constants.Ports.INTAKE_MOTOR_1, MotorType.kBrushless);
    intakePiston = new Solenoid(Constants.Ports.INTAKE_SOLENOID);

    mLeader.restoreFactoryDefaults();
    
    encoder = mLeader.getEncoder();

    intakePiston.set(false);

    mLeader.setSmartCurrentLimit(50);
    mLeader.setIdleMode(IdleMode.kCoast);
    
    register();
  }

  public void setPercent(double percent) {
    mLeader.set(percent);
  }

  public double getRPM() {
    return encoder.getVelocity();
  }
 
  public void togglePiston() {
    intakePiston.set(!intakePiston.get());
  }

  public void setPiston(boolean extend) {
    intakePiston.set(extend);
  }

  public boolean getPiston() {
    return intakePiston.get();
  }

  @Override
  public void periodic() {
    updateSmartDashboard();
  }

  public void updateSmartDashboard() {
    // if you put anything here make sure to register the subsystem.
    
    SmartDashboard.putNumber("Intake/RPMs", getRPM());

    if (Robot.verbose) {
      // put non-essential data here.
    }
  }

  private static Intake instance;
  public static Intake getInstance() { if (instance == null) instance = new Intake(); return instance; }
}
