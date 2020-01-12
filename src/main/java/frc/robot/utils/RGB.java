/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utils;

/**
 * Add your docs here.
 */
public class RGB {
    public double red, green, blue;

    public RGB(double r, double g, double b) {
        this.red = r;
        this.blue = b;
        this.green = g;
        //System.out.println("Created new RGB with R: " + this.red + ", G: " + this.green + ", B: " + this.blue);
    } 
}
