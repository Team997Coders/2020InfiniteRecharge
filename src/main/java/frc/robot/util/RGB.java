package frc.robot.util;

import org.team997coders.spartanlib.math.MathUtils;

public class RGB {

    private int color, red, green, blue;

    public RGB(int color) {
        this.color = color;
        red = (color & 0xff0000) >> 16;
        green = (color & 0x00ff00) >> 8;
        blue = (color & 0x0000ff);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void changeBrightness(int amount) {
        red += amount;
        green += amount;
        blue += amount;
        clampColor();
    }

    private void clampColor() {
        MathUtils.clamp(red, 0, 255);
        MathUtils.clamp(green, 0, 255);
        MathUtils.clamp(blue, 0, 255);
    }

}