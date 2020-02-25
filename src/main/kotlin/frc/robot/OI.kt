package frc.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.robot.commands.climber.ClimberMove
import frc.robot.commands.drivetrain.TargetVision
import frc.robot.commands.hopper.HopperMove
import frc.robot.commands.intake.IntakeMove
import frc.robot.commands.shooter.ShooterBasic
import frc.robot.subsystems.Hopper
import frc.robot.subsystems.Intake

object OI {

  val mGamepad1 = XboxController(0)
  val mGamepad2 = XboxController(1)

  val mA = JoystickButton(mGamepad1, XboxController.Button.kA.value).apply {

  }
  val mB = JoystickButton(mGamepad1, XboxController.Button.kB.value).apply {
    whileHeld(TargetVision())
  }
  val mX = JoystickButton(mGamepad1, XboxController.Button.kX.value).apply {

  }
  val mY = JoystickButton(mGamepad1, XboxController.Button.kY.value).apply {

  }
  val mRightBumper = JoystickButton(mGamepad1, XboxController.Button.kBumperRight.value).apply {
    whileHeld(IntakeMove(Constants.Values.INTAKE_IN, true))
  }
  val mLeftBumper = JoystickButton(mGamepad1, XboxController.Button.kBumperLeft.value).apply {
    whileHeld(IntakeMove(Constants.Values.INTAKE_EJECT, false))
  }
  val mStart = JoystickButton(mGamepad1, XboxController.Button.kStart.value).apply {
    whenPressed(Runnable { Intake.togglePiston() })
  }

  val mA2 = JoystickButton(mGamepad2, XboxController.Button.kA.value).apply {
    whileHeld(ClimberMove(Constants.Values.CLIMBER_UP))
  }
  val mB2 = JoystickButton(mGamepad2, XboxController.Button.kB.value).apply {
    whileHeld(ClimberMove(Constants.Values.CLIMBER_DOWN))
  }
  val mX2 = JoystickButton(mGamepad2, XboxController.Button.kX.value).apply {
    whileHeld(HopperMove(Constants.Values.HOPPER_EJECT_SPEED))
  }
  val mY2 = JoystickButton(mGamepad2, XboxController.Button.kY.value).apply {
    whileHeld(HopperMove(Constants.Values.HOPPER_INTAKE_SPEED))
  }
  val mRightBumper2 = JoystickButton(mGamepad2, XboxController.Button.kBumperRight.value).apply {
    whileHeld(ShooterBasic(1.0))
  }
  val mLeftBumper2 = JoystickButton(mGamepad2, XboxController.Button.kBumperLeft.value).apply {
    whileHeld(ShooterBasic(0.66))
  }

}