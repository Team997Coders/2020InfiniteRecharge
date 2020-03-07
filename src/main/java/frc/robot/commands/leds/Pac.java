/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.leds;

import org.team997coders.spartanlib.math.MathUtils;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.LEDManager;

public class Pac extends CommandBase {
    boolean[][] mGrid = new boolean[7][5];
    boolean alive;
    int pacX, pacY, ghostX, ghostY, score, ipf, sWait;
    long delay;

    public Pac() {
        addRequirements(LEDManager.getInstance());
    }

    @Override
    public void initialize() {
        alive = true;
        pacX = 3;
        pacY = 1;
        ghostX = 5;
        ghostY = 1;
        score = 0;
        delay = 0;
        ipf = 10;
        sWait = 100;

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 5; y++) {
                if (x == 0 || x == 6 || y == 0 || y == 4) {
                    mGrid[x][y] = false;
                } else {
                    mGrid[x][y] = true;
                }
            }
        }
        mGrid[2][2] = false;
        mGrid[3][2] = false;
        mGrid[4][2] = false;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (sWait != 0) {
            sWait--;

            // Clears screen
            LEDManager.getInstance().clear();

            // ): - Mouth
            LEDManager.getInstance().setColorCoordinate(0, 4, 255, 0, 0);
            LEDManager.getInstance().setColorCoordinate(1, 3, 255, 0, 0);
            LEDManager.getInstance().setColorCoordinate(2, 3, 255, 0, 0);
            LEDManager.getInstance().setColorCoordinate(3, 3, 255, 0, 0);
            LEDManager.getInstance().setColorCoordinate(4, 3, 255, 0, 0);
            LEDManager.getInstance().setColorCoordinate(5, 3, 255, 0, 0);
            LEDManager.getInstance().setColorCoordinate(6, 4, 255, 0, 0);

            // ): - Eyes
            LEDManager.getInstance().setColorCoordinate(2, 0, 255, 0, 0);
            LEDManager.getInstance().setColorCoordinate(4, 0, 255, 0, 0);

            // Draws the screen
            LEDManager.getInstance().writeLeds();

            return;
        }

        // Adds to the iteration count
        delay++;

        // Delays the screen display
        if (delay % ipf == 0) {

            // Find joystick movement
            double changeY = OI.getInstance().getGamepad1Axis(5); // Y
            double changeX = OI.getInstance().getGamepad1Axis(4); // X

            double ph1 = OI.getInstance().getGamepad1Axis(1); // Y
            double ph2 = OI.getInstance().getGamepad1Axis(0); // X

            // Decide which to use
            if (Math.abs(ph1) > Math.abs(changeY)) {
                changeY = ph1;
            }
            if (Math.abs(ph2) > Math.abs(changeX)) {
                changeX = ph2;
            }

            // Sets the correct direction they want to go
            if (Math.abs(changeY) > Math.abs(changeX)) {
                changeX = 0;
            } else {
                changeY = 0;
            }

            // Changes the position if the grid spot is open
            if (mGrid[pacX][pacY + (int) Math.ceil(changeY)]) {
                pacY += (int) Math.ceil(changeY);
            }
            if (mGrid[pacX + (int) Math.ceil(changeX)][pacY]) {
                pacX += (int) Math.ceil(changeX);
            }

            // Makes the ghost move
            if (ghostX == 1) {
                if (ghostY == 3) {
                    ghostX += 1;
                } else {
                    ghostY += 1;
                }
            } else if (ghostX == 5) {
                if (ghostY == 1) {
                    ghostX -= 1;
                } else {
                    ghostY -= 1;
                }
            } else if (ghostY == 1) {
                if (ghostX == 1) {
                    ghostY += 1;
                } else {
                    ghostX -= 1;
                }
            } else if (ghostY == 3) {
                if (ghostX == 5) {
                    ghostY -= 1;
                } else {
                    ghostX += 1;
                }
            }

            // Death
            if (pacX == ghostX && pacY == ghostY) {
                pacX = 3;
                pacY = 1;
                ghostX = 5;
                ghostY = 1;
                sWait = 100;
            }

            // Clears screen
            LEDManager.getInstance().clear();

            // Draws the board
            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 5; y++) {
                    if (!mGrid[x][y]) {
                        LEDManager.getInstance().setColorCoordinate(x, y, 0, 0, 255);
                    }
                }
            }

            // Draws the characters
            LEDManager.getInstance().setColorCoordinate(pacX, pacY, 255, 255, 0); // Pacman
            LEDManager.getInstance().setColorCoordinate(ghostX, ghostY, 255, 0, 0); // Ghost

            // Draws the screen
            LEDManager.getInstance().writeLeds();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
        public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
