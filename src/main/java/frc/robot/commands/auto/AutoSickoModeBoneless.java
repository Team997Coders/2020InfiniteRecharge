package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.FollowPath;

public class AutoSickoModeBoneless extends SequentialCommandGroup {

  public AutoSickoModeBoneless() {
    addCommands(
      new FollowPath("Pickup3", false),
      new FollowPath("TrenchPivot", true)
    );
  }

}