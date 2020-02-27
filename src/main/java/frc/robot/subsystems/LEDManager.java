/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.team997coders.spartanlib.limelight.LimeLight;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.CRGB;
import frc.robot.util.RGB;

public class LEDManager extends SubsystemBase {

  private AddressableLED mLeds;
  private AddressableLEDBuffer m_buf;
  private static int delay = 0;

  private int[][] coordinateMap;
  private int step = (54 / (Constants.Values.LED_WIDTH - 1)); // Degrees per light;

  private LEDManager() {
    mLeds = new AddressableLED(Constants.Ports.LEDPORT);
    m_buf = new AddressableLEDBuffer(Constants.Values.LED_COUNT);
    mLeds.setLength(m_buf.getLength());

    coordinateMap = new int[Constants.Values.LED_WIDTH][Constants.Values.LED_ROWS];

    clear();

    mLeds.start();

    boolean invertCount = false; // Set to true if 1st LED in panel is on the right side.
    int currentIndex = 0; // Set to LED_WITDTH if invertCount is true.

    // Set up a reference grid so we don't have to deal with linearity.
    for (int i = Constants.Values.LED_ROWS - 1; i >= 0; i--) {
      for (int j = 0; j < Constants.Values.LED_WIDTH; j++) {
        coordinateMap[j][i] = currentIndex;
        if (invertCount) currentIndex--; else currentIndex++;
      }
      if (invertCount) currentIndex += (Constants.Values.LED_WIDTH + 1); else currentIndex += (Constants.Values.LED_WIDTH - 1);
      invertCount = !invertCount;
    }

    for (int i = 0; i < Constants.Values.LED_ROWS; i++) {
      for (int j = 0; j < Constants.Values.LED_WIDTH; j++) {
        System.out.println("Value at [" + j + ", " + i + "] = " + coordinateMap[j][i]);
      }
    }
  }

  public void clear() {
    // clear the string (set to black)
    for (int i = 0; i < m_buf.getLength(); i++) {
      m_buf.setRGB(i, 0, 0, 0);
    }
  }

  public void setColor(CRGB color) {
    for (int i = 0; i < m_buf.getLength(); i++) {
      m_buf.setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
    }
  }

  public void setColor(int r, int g, int b) {
    for (int i = 0; i < m_buf.getLength(); i++) {
      m_buf.setRGB(i, r, g, b);
    }
  }

  public void setColorIndex(int index, CRGB color) {
    m_buf.setRGB(index, color.getRed(), color.getGreen(), color.getBlue());
  }

  public void setColorIndex(int index, int r, int g, int b) {
    m_buf.setRGB(index, r, g, b);
  }

  public void setColorCoordinate(int x, int y, CRGB color) {
    setColorIndex(coordinateMap[x][y], color.getRed(), color.getGreen(), color.getBlue());
  }

  public void setColorCoordinate(int x, int y, int r, int g, int b) {
    setColorIndex(coordinateMap[x][y], r, g, b);
    //System.out.println("Set " + coordinateMap[x][y] + " to do color");
  }

  public void setColorArray(RGB[][] array) {
    setColorArray(array, 0, 0);
  }

  public void setColorArray(RGB[][] array, int xOffset, int yOffset) {
    for (int i = (0); i < (Constants.Values.LED_ROWS); i++) {
      for (int j = (0); j < (Constants.Values.LED_WIDTH); j++) {
        if ((j - xOffset ) >= 0 && (i - yOffset) >= 0 && (j - xOffset) < array.length && (i - yOffset) < array[0].length) {
          setColorIndex(coordinateMap[j][i], array[j - xOffset][i - yOffset].getRed(), array[j - xOffset][i - yOffset].getGreen(), array[j - xOffset][i - yOffset].getBlue());
        } else {
          setColorIndex(coordinateMap[j][i], CRGB.BLACK);
        } 
      }
    }
  }

  public void setColorToAlliance() {
    boolean blueAlliance = DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue ? true : false;
    if (blueAlliance) {
      setColor(CRGB.BLUE); // blue
    } else {
      setColor(CRGB.RED); // red
    }
  }

  public void writeLeds() {
    mLeds.setData(m_buf);
  }

  /**
   * A janky version of the original targeting method.
   */
  public void target() {

    if((int)LimeLight.getInstance().getDouble(LimeLight.TARGET_VISIBLE, 0) == 0) {
      setColor(127, 0, 0);
    } else if (Math.abs(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 100)) < 0.5) {
      setColor(0, 127, 0);
    } else {
      setColor(0, 0, 127);

      double error = LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 100);
      System.out.println(error);

      int sign = (int)(Math.abs(error) / error);

      //TODO replace constants with %step% and stuff
      //setColorCoordinate((int)((error / 9.0) + 3.0), 0, 255, 0, 0);
      setColorCoordinate((int)((error / (54 / (Constants.Values.LED_WIDTH - 1))) + Math.floor(Constants.Values.LED_WIDTH / 2)), 0, 127, 31, 0);
      setColorCoordinate((int)((sign * ((Math.abs(error) + 1.8) / (54 / (Constants.Values.LED_WIDTH - 1))) + Math.floor(Constants.Values.LED_WIDTH / 2))), 1, 127, 31, 0);
      setColorCoordinate((int)((sign * ((Math.abs(error) + 3.6) / (54 / (Constants.Values.LED_WIDTH - 1))) + Math.floor(Constants.Values.LED_WIDTH / 2))), 2, 127, 31, 0);
      setColorCoordinate((int)((sign * ((Math.abs(error) + 5.4) / (54 / (Constants.Values.LED_WIDTH - 1))) + Math.floor(Constants.Values.LED_WIDTH / 2))), 3, 127, 31, 0);
      setColorCoordinate((int)((sign * ((Math.abs(error) + 7.2) / (54 / (Constants.Values.LED_WIDTH - 1))) + Math.floor(Constants.Values.LED_WIDTH / 2))), 4, 127, 31, 0);
    }

    writeLeds();
  }

  /**
   * A simple targetting feedback using LEDs on the back of the robot. This
   * routine assumes that the leds are in a rectangular shape with an odd number
   * of rows and pixels (so there is a center).
   * 
   * Use this during the disabled portion of the match when we are setting up the
   * robot. Manually move the robot to make sure we are on target.
   * 
   * I have been working on the assumption of a 7 pixel wide by 5 rows high.
   * 
   * If we can't see the target then make the LEDs red. If we can see the target
   * but we are > 5degrees off, then switch the center led (vertical stripe) to
   * yellow and the remainder of the LEDs to green. Each led off of center
   * represents ~5 degrees off the target center (see tx below) [ Note: The actual
   * value per led is 30degrees/(num of leds). Note integer division (I only want
   * the integer portion) I am multipling the result by 2 to double the resolution
   * of the leds.]: 30 degrees / 7 leds => 4 degrees per led (integer div)
   * 
   * Straight from the Limelight manual: tv Whether the limelight has any valid
   * targets (0 or 1) tx Horizontal Offset From Crosshair To Target (LL1: -27
   * degrees to 27 degrees | LL2: -29.8 to 29.8 degrees) ty Vertical Offset From
   * Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to
   * 24.85 degrees)
   */

  public void targetReticle() {
    if (delay % 10 == 0) {
      int rows = Constants.Values.LED_ROWS;
      int res = 5; // degrees per led
      int middle = (int) (Constants.Values.LED_WIDTH / 2);
      if (LimeLight.getInstance().getDouble(LimeLight.LED_MODE, 0) != 3) {
        // limelight LED is off, need to turn it on to look for the target
        LimeLight.getInstance().setDouble(LimeLight.LED_MODE, 3.0);
      }
      int deltax = Math.min(0, (int) (0.1 + LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0) / res));
      boolean hasTarget = LimeLight.getInstance().hasTarget;
      clear(); // reset the string to black
      if (hasTarget) {
        // on target within +/- 1 led resolution
        // set middle led stripe green
        for (int row = 0; row < rows; row++) {
          int center = ((row * Constants.Values.LED_WIDTH) + middle);
          if (deltax == 0) {
            setColorIndex(center, 55, 255, 255); // green
          } else {
            setColorIndex(center, 30, 255, 255); // yellow
            // off-target
            if (deltax < 0) {
              for (int i = Math.min(0, (center - deltax)); i < center; i++) {
                setColorIndex(i, 30, 255, 255); // yellow
              }
            } else {
              for (int i = center + 1; i < Math.max((center + deltax), Constants.Values.LED_WIDTH); i++) {
                setColorIndex(i, 30, 255, 255); // yellow
              }
            }
          }
        }
      } else {
        // set leds to red.
        setColor(CRGB.RED);
      }
      delay = 0;
    }
    delay++;

    clear();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


  private static LEDManager instance;

  public static LEDManager getInstance() {
    if (instance == null)
      instance = new LEDManager();
    return instance;
  }
}
