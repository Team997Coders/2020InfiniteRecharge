package frc.robot.util;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.Constants;

public class LEDManager {

  private AddressableLED mLeds;

  private LEDManager() {
    mLeds = new AddressableLED(9);
    mLeds.setLength(Constants.Values.LED_COUNT);

    setColor(0, 100, 100);
  }

  public void setColor(int h, int s, int v) {
    AddressableLEDBuffer buf = new AddressableLEDBuffer(Constants.Values.LED_COUNT);
    for (int i = 0; i < buf.getLength(); i++) {
      buf.setHSV(i, h, s, v);
    }

    mLeds.setData(buf);
    mLeds.start();
  }

  private static LEDManager instance;
  public static LEDManager getInstance() { if (instance == null) instance = new LEDManager(); return instance; } 

}