package frc.robot.util;

public class RGB {

    private int color;

    public RGB(int color) {
        this.color = color;
    }

    public int getRed() {
        return (color & 0xff0000) >> 16;
    }

    public int getGreen() {
        return (color & 0x00ff00) >> 8;
    }

    public int getBlue() {
        return (color & 0x0000ff);
    }

    public void changeBrightness(int amount) {
        amount += 128;
        if (amount > 255) amount = 255;
        if (amount < 0) amount = 0; 
        int hex = (amount << 16) & (amount << 8) & amount;
        color &= hex;
    }

}