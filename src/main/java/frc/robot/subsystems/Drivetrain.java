package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Drivetrain implements Subsystem {
  private static Drivetrain instance = null;

  public static Solenoid yeeter;

  private Drivetrain() {
    yeeter = new Solenoid(0);

    register();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Drivetrain/Example Number", 420);
  }

  public static Drivetrain getInstance() {
    if (instance != null) instance = new Drivetrain();
    return instance;
  }

}