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

    BufferedImage image;

    public ImageLoader(String path) {
        try {
            image = ImageIO.read(new File(path));
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
        RGB[][] arr = new RGB[image.getWidth()][image.getHeight()];
        for(int i = 0; i < image.getHeight(); i++) {
            for(int j = 0; j < image.getWidth(); j++) {
                arr[j][i] = new RGB(image.getRGB(j, i));
            }
        }
        return arr;
    }

}
