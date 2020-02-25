/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

/**
 * Add your docs here.
 */
public enum CRGB {
    /**
     * Color map: https://github.com/FastLED/FastLED/wiki/Pixel-reference Values are
     * in RGB hex values
     */
    WHITE(0xffffff),
    BLACK(0x0), 
    RED(0xff0000), 
    GREEN(0x008000), 
    BLUE(0x0000ff), 
    LIME(0x00ff00), 
    YELLOW(0xffff00);

    private int value;

    private CRGB(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getRed() {
        return (value & 0xff0000) >> 16;
    }

    public int getGreen() {
        return (value & 0x00ff00) >> 8;
    }

    public int getBlue() {
        return (value & 0x0000ff);
    }
}
