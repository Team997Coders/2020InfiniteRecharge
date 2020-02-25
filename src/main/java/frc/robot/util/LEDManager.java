package frc.robot.util;

import org.team997coders.spartanlib.limelight.LimeLight;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants;

public class LEDManager {

  private AddressableLED mLeds;
  private AddressableLEDBuffer m_buf;
  private static int delay = 0;

  private LEDManager() {
    mLeds = new AddressableLED(Constants.Ports.LEDPORT);
    m_buf = new AddressableLEDBuffer(Constants.Values.LED_COUNT);
    mLeds.setLength(m_buf.getLength());

    clear();

    mLeds.start();
  }

  public void clear() {
    // clear the string (set to black)
    for (int i = 0; i < m_buf.getLength(); i++) {
      m_buf.setRGB(i, 0, 0, 0);
    }

    mLeds.setData(m_buf);
  }

  public void setColor(int h, int s, int v) {
    for (int i = 0; i < m_buf.getLength(); i++) {
      m_buf.setHSV(i, h, s, v);
    }
  }

  public void setColorIndex(int index, int h, int s, int v) {
    m_buf.setHSV(index, h, s, v);
    mLeds.setData(m_buf);
  }

  public void setColorToAlliance() {
    boolean blueAlliance = DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue ? true : false;
    if (blueAlliance) {
      setColor(120, 255, 255); // blue
    } else {
      setColor(255, 255, 255); // red
    }
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
      int middle = (int)(Constants.Values.LED_WIDTH / 2);
      if (LimeLight.getInstance().getDouble(LimeLight.LED_MODE, 0) == 0) {
        // limelight LED is off, need to turn it on to look for the target
        LimeLight.getInstance().setDouble(LimeLight.LED_MODE, 3.0);
      }
      int deltax = Math.min(0,(int) (0.1 + LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0) / res));
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
              for (int i=Math.min(0,(center - deltax)); i < center; i++){
                setColorIndex(i, 30, 255, 255); // yellow
              }
            } else {
              for (int i=center+1; i < Math.max((center+deltax),Constants.Values.LED_WIDTH); i++){
                setColorIndex(i, 30, 255, 255); // yellow
              }
            }
          }
        }
      } else {
        // set leds to red.
        setColor(255, 255, 255);
      }
      delay = 0;
    }
    delay++;

    clear();
  }

  private static LEDManager instance;

  public static LEDManager getInstance() {
    if (instance == null)
      instance = new LEDManager();
    return instance;
  }

}