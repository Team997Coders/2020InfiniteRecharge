package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.shooter.StopShooting;

public class OI {
  private double axisPos;
  private  Joystick gamepad1, gamepad2;
  private  JoystickButton buttonA2, buttonB2;

  private OI() {
    gamepad1 = new Joystick(0);
    gamepad2 = new Joystick(1);

    buttonA2 = new JoystickButton(gamepad2, Constants.Ports.ButtonA);
    buttonB2 = new JoystickButton(gamepad2, Constants.Ports.ButtonB);

    buttonA2.whenPressed(new Shoot());
    buttonB2.whenPressed(new StopShooting());
  }

  public double getGamepad1Axis(int portNum) {
    axisPos = gamepad1.getRawAxis(portNum);
    if (Math.abs(axisPos) <= 0.05) {
      axisPos = 0;
    }
    return axisPos;
  }

  private static OI instance;
  public static OI getInstance() {if(instance == null) instance = new OI(); return instance;}

}

