package frc.robot.commands.drivetrain

import com.ctre.phoenix.motorcontrol.NeutralMode
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.OI
import frc.robot.subsystems.Drivetrain

class ArcadeDrive: CommandBase() {

  init {
    addRequirements(Drivetrain)
  }

  override fun initialize() {
    Drivetrain.setNeutralMode(NeutralMode.Brake)
  }

  override fun execute() {

    System.out.println("Uhhh: " + OI.mGamepad1.getRawAxis(1))

    val f = Constants.deadband(-OI.mGamepad1.getRawAxis(1) * 0.7, 0.05)
    val t = Constants.deadband(OI.mGamepad1.getRawAxis(4) * 0.4, 0.05)

    val left = f + t
    val right = f - t

    Drivetrain.setMotors(left, right)
  }

  override fun isFinished(): Boolean {
    return false;
  }

  override fun end(interrupted: Boolean) {
    Drivetrain.setMotors(0.0, 0.0)
  }

}