/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import org.team997coders.spartanlib.math.MathUtils;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.DriveTrain;

public class ArcadeDrive extends CommandBase {

  double left, right;
  public ArcadeDrive() {
    addRequirements(DriveTrain.getInstance());
  }

  @Override
  public void initialize() {
    // DriveTrain.getInstance().playOrchestra("Megalovania.chrp");
    DriveTrain.getInstance().stopOrchestra();
  }

  @Override
  public void execute() {
    double forward = -OI.getInstance().gamepad1.getRawAxis(1);
    double turn = OI.getInstance().gamepad1.getRawAxis(4);

    forward = MathUtils.deadband(forward, 0.1);
    turn = MathUtils.deadband(turn, 0.1);

    forward *= 0.7;
    if (turn < 0) turn = -Math.pow(turn, 2);
    else turn = Math.pow(turn, 2);

    left = forward + turn;
    right = forward - turn;
    
    // DriveTrain.getInstance().setMotors(left, right);
    DriveTrain.getInstance().simpleAccelControl(left, right);
  }

  @Override
  public void end(boolean interrupted) {
    DriveTrain.getInstance().setMotors(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
