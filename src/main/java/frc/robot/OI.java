/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.fasterxml.jackson.databind.ext.DOMDeserializer.DocumentDeserializer;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Add your docs here.
 */
public class OI {
    public static double axisPos;
    private XboxController gamepad1;

    public OI() {
        gamepad1 = new XboxController(0);

         
        gamepad1.getBumper(Hand.kRight);
        gamepad1.getBumper(Hand.kLeft);
    }

    public double getAxis(int portNum) {
        axisPos = gamepad1.getRawAxis(portNum);
        if (Math.abs(axisPos) <= 0.05) {
            axisPos = 0;
        } 
        return axisPos;
    }

    public boolean intakeIn() {
        return gamepad1.getBumper(Hand.kRight);
    }
    
    public boolean intakeOut() {
        return gamepad1.getBumper(Hand.kLeft);
    }

}
