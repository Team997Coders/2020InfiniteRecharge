/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.*;

import com.revrobotics.ColorSensorV3;

public class Colorsensor extends SubsystemBase {
  /**
   * Creates a new Colorsensor.
   */

  double colorTolerance = 10;
  ColorSensorV3 colorSensor1;

  public Colorsensor() {
    colorSensor1 = new ColorSensorV3(Port.kOnboard);
  }

  public double compareRGB(RGB a, RGB b) {
    return ((Math.abs(a.red - b.red) + Math.abs(a.green - b.green) + Math.abs(a.blue - b.blue)) / 3);
  }

  public void UpdateSmartDashboard() {
    final Color a = colorSensor1.getColor();
    //System.out.println("HA");
    SmartDashboard.putNumber("ColorSensor/Red", a.red * 255);
    SmartDashboard.putNumber("ColorSensor/Green", a.green * 255);
    SmartDashboard.putNumber("ColorSensor/Blue", a.blue * 255);

    compareTargets();
  }

  public void compareTargets() {
    Color color = colorSensor1.getColor();
    RGB currentView = new RGB(color.red * 255, color.green * 255, color.blue * 255);
    double[] targets = {
      compareRGB(currentView, Constants.Colors.blueTarget),
      compareRGB(currentView, Constants.Colors.greenTarget),
      compareRGB(currentView, Constants.Colors.redTarget),
      compareRGB(currentView, Constants.Colors.yellowTarget)
    };
    int min = 0;
    for (int i = 1; i < targets.length; i++) {
      if(targets[min] > targets[i]) { min = i; }
    }

    String[] targetNames = {
      "blue",
      "green",
      "red",
      "yellow",
    };

    SmartDashboard.putString("ColorSensor/Seen Target", targetNames[min]);
    SmartDashboard.putNumber("ColorSensor/Blue Target Error", targets[0]);
    SmartDashboard.putNumber("ColorSensor/Green Target Error", targets[1]);
    SmartDashboard.putNumber("ColorSensor/Red Target Error", targets[2]);
    SmartDashboard.putNumber("ColorSensor/Yellow Target Error", targets[3]);
  }

    @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
