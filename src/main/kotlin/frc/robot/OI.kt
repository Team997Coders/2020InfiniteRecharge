package frc.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton

object OI {

  val mGamepad1 = XboxController(0)
  val mGamepad2 = XboxController(1)

  val mA = JoystickButton(mGamepad1, XboxController.Button.kA.value).apply {

  }
  val mB = JoystickButton(mGamepad1, XboxController.Button.kB.value).apply {

  }
  val mX = JoystickButton(mGamepad1, XboxController.Button.kX.value).apply {

  }
  val mY = JoystickButton(mGamepad1, XboxController.Button.kY.value).apply {

  }
  val mRightBumper = JoystickButton(mGamepad1, XboxController.Button.kBumperRight.value).apply {

  }
  val mLeftBumper = JoystickButton(mGamepad1, XboxController.Button.kBumperLeft.value).apply {

  }

  val mA2 = JoystickButton(mGamepad2, XboxController.Button.kA.value).apply {

  }
  val mB2 = JoystickButton(mGamepad2, XboxController.Button.kB.value).apply {

  }
  val mX2 = JoystickButton(mGamepad2, XboxController.Button.kX.value).apply {

  }
  val mY2 = JoystickButton(mGamepad2, XboxController.Button.kY.value).apply {

  }
  val mRightBumper2 = JoystickButton(mGamepad2, XboxController.Button.kBumperRight.value).apply {

  }
  val mLeftBumper2 = JoystickButton(mGamepad2, XboxController.Button.kBumperLeft.value).apply {

  }

}