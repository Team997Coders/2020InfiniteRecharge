/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.fasterxml.jackson.databind.ext.DOMDeserializer.DocumentDeserializer;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoDriveForward;
import frc.robot.subsystems.DriveTrain;
/**
 * Add your docs here.
 */
public class OI {
  public static double axisPos;
  private XboxController gamepad1;
  private JoystickButton rohanTest;
  private OI() {
    gamepad1 = new XboxController(0);
    rohanTest = new JoystickButton(gamepad1, XboxController.Button.kA.value);
    rohanTest.whenPressed(new AutoDriveForward(DriveTrain.getInstance().calcualteEncoderTicksFromInches(36.0), DriveTrain.getInstance().calcualteEncoderTicksFromInches(36.0), 0.5));
  }

  public double getAxis(int portNum) {
    axisPos = gamepad1.getRawAxis(portNum);
    if (Math.abs(axisPos) <= 0.05) {
      axisPos = 0;
    }
    return axisPos;
  }

  private static OI instance;
  public static OI getInstance() { return instance == null ? instance = new OI() : instance; }
}
