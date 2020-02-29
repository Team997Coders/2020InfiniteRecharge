/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.images;

import java.io.File;
import javax.imageio.*;

import frc.robot.util.RGB;

import java.awt.image.BufferedImage;

public class ImageLoader {

    private BufferedImage image;
    private RGB[][] imageArray;

    public ImageLoader(String path) {
        try {
            image = ImageIO.read(new File(path));
            imageArray = new RGB[image.getWidth()][image.getHeight()];
            
            for(int i = 0; i < image.getHeight(); i++) {
                for(int j = 0; j < image.getWidth(); j++) {
                    imageArray[j][i] = new RGB(image.getRGB(j, i));
                }
            }

        } catch(Exception e) {
            image = null;
        }
    }

    /**
     * Check in case the filepath was bad.
     */
    public boolean hasImage() {
        if (image != null) return true;
        return false; 
    }

    public RGB[][] getColorArray() {
        return imageArray;
    }

    public void changeBrightness(int amount) {
        if (hasImage()) {
            for(int i = 0; i < image.getHeight(); i++) {
                for(int j = 0; j < image.getWidth(); j++) {
                    System.out.println("Changed [" + imageArray[j][i].getRed() + " " + imageArray[j][i].getGreen() + " " + imageArray[j][i].getBlue() + "] to ");
                    imageArray[j][i].changeBrightness(amount);
                    System.out.print("[ " + imageArray[j][i].getRed() + " " + imageArray[j][i].getGreen() + " " + imageArray[j][i].getBlue() + "]");
                }
            }
        }   
    }

}
