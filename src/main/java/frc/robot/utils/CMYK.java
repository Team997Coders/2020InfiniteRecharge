/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shacyan by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utils;

/**
 * Add your docs here.
 */
public class CMYK {
    public double cyan, magenta, yellow, black;

    public CMYK(double c, double m, double y, double k) {
        this.cyan = c;
        this.yellow = y;
        this.magenta = m;
        this.black = k;
    }

    public RGB toRGB() {
        
        double r = 255 * ( 1 - cyan / 100 ) * ( 1 - black / 100 );
        double g = 255 * ( 1 - magenta / 100 ) * ( 1 - black / 100 );
        double b = 255 * ( 1 - yellow / 100 ) * ( 1 - black / 100 );
        return new RGB(r, g, b);

    }
}
