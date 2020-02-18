/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Leds extends SubsystemBase {
  /**
   * Creates a new Leds.
   */
  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;

  public Leds() {
    // PWM port 9
    // Must be a PWM header, not MXP or DIO
    m_led = new AddressableLED(Constants.Ports.LEDPORT);

    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(Constants.Ports.LEDCOUNT);
    m_led.setLength(m_ledBuffer.getLength());

    // clear the led string (set to black)
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, 0, 0, 0);
    }

    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();

    register();
  }

  public void clear() {
    // clear the led string (set to black)
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, 0, 0, 0);
    }
    m_led.setData(m_ledBuffer);
  }

  @Override
  public void periodic() {
  }

  private static Leds instance;

  public static Leds getInstance() {
    if (instance == null)
      instance = new Leds();
    return instance;
  }
}
