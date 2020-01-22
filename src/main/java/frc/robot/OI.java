/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.shooter.StopShooting;
import frc.robot.commands.LowerConveyorMotor;
import frc.robot.commands.UpperConveyorMotor;


/**
 * Add your docs here.
 */
public class OI {
  
  private  Joystick gamepad1, gamepad2;
  private  JoystickButton buttonA2, buttonB2, buttonY2, buttonX2;
    private OI() {
    //public OI() {
    gamepad1 = new Joystick(0);
    gamepad2 = new Joystick(1);

    buttonA2 = new JoystickButton(gamepad2, Constants.Ports.ButtonA);
    buttonB2 = new JoystickButton(gamepad2, Constants.Ports.ButtonB);
    buttonX2 = new JoystickButton(gamepad2, Constants.Ports.ButtonX);
    buttonY2 = new JoystickButton(gamepad2, Constants.Ports.ButtonY);

    buttonA2.whileHeld(new Shoot());
    buttonB2.whenPressed(new StopShooting());
    buttonX2.whileHeld(new LowerConveyorMotor(0.3));
    buttonY2.whileHeld(new UpperConveyorMotor(0.3));
  }
  private static OI instance;
  public static OI getInstance() {if(instance == null) instance = new OI(); return instance;}
}
