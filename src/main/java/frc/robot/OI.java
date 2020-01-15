/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Add your docs here.
 */
public class OI {

    
    private static XboxController gamepad1;


    public OI() {
        gamepad1 = new XboxController(0);
        
    }

    public Boolean getRightBumper() {
        return gamepad1.getBumper(Hand.kRight);
    }
    
    public Boolean getLeftBumper() {
        return gamepad1.getBumper(Hand.kLeft);
    }

    private static OI instance;
    public static OI getInstance() { return instance == null ? instance = new OI() : instance; }
}
