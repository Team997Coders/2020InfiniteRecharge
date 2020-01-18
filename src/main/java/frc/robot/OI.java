/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.StopSucc;
import frc.robot.commands.Succ;

/**
 * Add your docs here.
 */
public class OI {
    
    private Joystick gamepad2;
    private JoystickButton rightBumper2, leftBumper2;

    public OI() {
        gamepad2 = new Joystick(4);
        
        rightBumper2 = new JoystickButton(gamepad2, 5);
        leftBumper2 = new JoystickButton(gamepad2, 6);

        rightBumper2.whenPressed(new Succ());
        leftBumper2.whenPressed(new StopSucc());
    }

    private static OI instance;
    public static OI getInstance() { return instance == null ? instance = new OI() : instance; }
}
