/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClimberUp;
import frc.robot.commands.ClimberDown;

/**
 * Add your docs here.
 */
public class OI {
  Joystick gamepad1;
  Joystick gamepad2;
  JoystickButton buttonX, buttonY, buttonA;
    private OI(){
     gamepad1 = new Joystick(0);
     gamepad2 = new Joystick(1);
     buttonA = new JoystickButton(gamepad1, Constants.Ports.buttonA);
     buttonX = new JoystickButton(gamepad1, Constants.Ports.buttonX);
     buttonY = new JoystickButton(gamepad1, Constants.Ports.buttonY);

     buttonA.whenPressed(new ClimberUp());
     buttonX.whenPressed(new ClimberDown());

     
    }
  
   
    
  private static OI instance;
  public static OI getInstance() { return instance == null ? instance = new OI() : instance; }
}
