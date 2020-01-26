/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.shooter.Shoot;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.commands.UpperConveyorMotor;

public class OI {
  
  private  Joystick gamepad1, gamepad2;
  private  JoystickButton buttonA2, buttonB2, buttonY2, bumperRight, bumperLeft;
    private OI() {
    //public OI() {
    gamepad1 = new Joystick(0);
    gamepad2 = new Joystick(1);

    buttonA2 = new JoystickButton(gamepad1, Constants.Ports.ButtonA);
    buttonB2 = new JoystickButton(gamepad1, Constants.Ports.ButtonB);
    buttonY2 = new JoystickButton(gamepad1, Constants.Ports.ButtonY);
    bumperLeft = new JoystickButton(gamepad1, XboxController.Button.kBumperLeft.value);
    bumperRight = new JoystickButton(gamepad1, XboxController.Button.kBumperRight.value);

    buttonA2.whileHeld(new Shoot());
    //buttonB2.whenPressed(new StopShooting());
    buttonB2.whileHeld(new UpperConveyorMotor(-Constants.Values.shooterBeltSpeed));
    buttonY2.whileHeld(new UpperConveyorMotor(Constants.Values.shooterBeltSpeed));

    bumperRight.whileHeld(new InstantCommand(() -> {
        if (Hopper.getInstance().shooterIRsensor.get()) Intake.getInstance().set(0.4);
        else Intake.getInstance().set(0.0);
        Robot.autoLoadHopper = true;
    }, Intake.getInstance()));
    bumperRight.whenInactive(new InstantCommand(()-> {
      Intake.getInstance().set(0.0);
      Robot.autoLoadHopper = false;
    }), true);
    
    bumperLeft.whileHeld(new InstantCommand(() -> {
      Intake.getInstance().set(-0.4);
    }, Intake.getInstance()));
    bumperLeft.whenInactive(new InstantCommand(()->Intake.getInstance().set(0.0)), true);
  }

  private static OI instance;
  public static OI getInstance() {if(instance == null) instance = new OI(); return instance;}
}
