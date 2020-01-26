package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class Intake implements Subsystem {

  private VictorSPX mIntakeMotor;

  public Intake() {
    mIntakeMotor = new VictorSPX(Constants.Ports.intakeMotor);
    mIntakeMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void set(double s) {
    mIntakeMotor.set(ControlMode.PercentOutput, s);
  }

  private static Intake instance;
  public static Intake getInstance() { if (instance == null) { instance = new Intake(); } return instance; }

}