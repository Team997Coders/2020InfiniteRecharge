/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.XboxController.Button;

/**
 * Add your docs here.
 */
public class OI {

    private static XboxController gamepad1;
  
     

    private OI() {

        gamepad1 = new XboxController(0);

    }
  
    public boolean getButtonX() { return gamepad1.getXButton(); }
    public boolean getButtonB() { return gamepad1.getBButton(); }
   
    
  private static OI instance;
  public static OI getInstance() { return instance == null ? instance = new OI() : instance; }
}
