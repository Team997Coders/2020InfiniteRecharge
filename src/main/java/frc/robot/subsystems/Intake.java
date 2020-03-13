/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//imports 
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

  //Create hardware objects 
  private Solenoid intakePiston;
  private CANSparkMax mLeader, mFollower;
  private CANEncoder encoder;

  private Intake() {

    //crete instances of mLeader, mFollower, and the intakePiston and set their port and in the case of the motors, their motor type 
    mLeader = new CANSparkMax(Constants.Ports.INTAKE_MOTOR_1, MotorType.kBrushless);
    mFollower = new CANSparkMax(Constants.Ports.INTAKE_MOTOR_2, MotorType.kBrushless);
    intakePiston = new Solenoid(Constants.Ports.INTAKE_SOLENOID);

    //restore the facotry defaults for the motors so that there are no conflicing pre-set values
    mLeader.restoreFactoryDefaults();
    mFollower.restoreFactoryDefaults();

    // set mFolloer to follow mLeader
    mFollower.follow(mLeader, true);

    //set the encoder so that it gets its information from the mLeader encoder
    encoder = mLeader.getEncoder();

    //set the default state for the intake piston to be retracted 
    intakePiston.set(false);

    mLeader.setSmartCurrentLimit(50); // current limit on the intake motor is 50%
    mLeader.setIdleMode(IdleMode.kCoast); // when not in use set the mLeader motor to coast
    mFollower.setSmartCurrentLimit(50); // current limit on the intake motor is 50%
    mFollower.setIdleMode(IdleMode.kCoast); // when not in use set the mLeader motor to coast

    register(); // save object referenecs
  }

  // make an instance of the Intake if it dossn't exsist
  private static Intake instance;
  public static Intake getInstance() { if (instance == null) instance = new Intake(); return instance; }

  // set the percent output for the mLeader motor
  public void setPercent(double percent) {
    mLeader.set(percent);
  }

  // get the RPM of the mLeader motor through the encoder
  public double getRPM() {
    return encoder.getVelocity();
  }
 
  //Toggle the piston between the retracted and extended states 
  public void togglePiston() {
    intakePiston.set(!intakePiston.get()); // set the piston state to the opposite of its current state
  }

  // actuate the Pistion to a given state
  public void setPiston(boolean extend) {
    intakePiston.set(extend);
  }

  //get the current state of the Piston
  public boolean getPiston() {
    return intakePiston.get();
  }

  // Update the smart Dahsboard
  public void updateSmartDashboard() {
   
    
    SmartDashboard.putNumber("Intake/RPMs", getRPM()); // put the intake motor RPM's on the dashbaord

    if (Robot.verbose) { // if verbose (debugging) turned on run the below code
    
    }
  }

  @Override
  public void periodic() {
    updateSmartDashboard(); // update the smart dashboard on every cycle
  }

}