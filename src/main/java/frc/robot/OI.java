/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.intake.IntakeMove;

/**
 * Add your docs here.
 */
public class OI {

  private XboxController gamepad2;
  private JoystickButton rightBumper2, leftBumper2;

  public OI() {
    gamepad2 = new XboxController(1);

    rightBumper2 = new JoystickButton(gamepad2, XboxController.Button.kBumperRight.value);
    leftBumper2 = new JoystickButton(gamepad2, XboxController.Button.kBumperLeft.value);

    rightBumper2.whileHeld(new IntakeMove(Constants.Values.INTAKE_IN));
    leftBumper2.whileHeld(new IntakeMove(Constants.Values.INTAKE_EJECT));
  }

  private static OI instance;

  public static OI getInstance() {
    return instance == null ? instance = new OI() : instance;
  }
}
