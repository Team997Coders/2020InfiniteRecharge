package frc.robot.util;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.Constants;

public class LEDManager {

  private AddressableLED mLeds;
  private AddressableLEDBuffer m_buf;

  private LEDManager() {
    mLeds = new AddressableLED(Constants.Ports.LEDPORT);
    m_buf = new AddressableLEDBuffer(Constants.Values.LED_COUNT);
    mLeds.setLength(m_buf.getLength());

    // clear the string (set to black)
    for (int i = 0; i < m_buf.getLength(); i++) {
      m_buf.setRGB(i, 0, 0, 0);
    }

    mLeds.setData(m_buf);
    mLeds.start();
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

  private static LEDManager instance;

  public static LEDManager getInstance() {
    if (instance == null)
      instance = new LEDManager();
    return instance;
  }

}