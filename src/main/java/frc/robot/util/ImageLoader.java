/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import java.io.FileReader;
import java.util.Scanner;

public class ImageLoader {

    public ImageLoader () {
        FileReader reader;
        Scanner scanner;

        try {
            reader = new FileReader("/home/lvuser/deploy/images/TestGrid.bmp");
            scanner = new Scanner(reader);

            while (scanner.hasNext()) {
                String data = scanner.next();
                
                for (Byte item : data.getBytes("UTF8")) {
                    System.out.println();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
