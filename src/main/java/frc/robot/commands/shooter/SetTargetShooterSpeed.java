package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SetTargetShooterSpeed extends CommandBase {

  private double rpm = 0.0, tolerance = Double.NaN;

  public SetTargetShooterSpeed(double rpm) {
    this.rpm = rpm;
    addRequirements(Shooter.getInstance());
  }

  public SetTargetShooterSpeed(double rpm, double tolerance) {
    this.rpm = rpm; this.tolerance = tolerance;
    addRequirements(Shooter.getInstance());
  }

  @Override
  public void initialize() {
    Shooter.getInstance().setRPM(rpm);
  }

  @Override
  public boolean isFinished() {
    if (Double.isFinite(tolerance)) {
      if (Shooter.getInstance().getRPMError() > tolerance) return true;
    }
    return false;
  }

}