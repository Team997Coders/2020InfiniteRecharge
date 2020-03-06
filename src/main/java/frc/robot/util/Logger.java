/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import frc.robot.Robot;

public class Logger {

    private final String fileExtension = ".txt";
    private final String folderPath = "/home/lvuser/logfiles/";
    private String filePath = folderPath + "mostRecent" + fileExtension;

    private BufferedWriter writer = null;
    private BufferedReader reader = null;

    private Logger() {
        setFileName("mostRecent");
        clearFile();
    }

    /**
     * Change the name of the file to write to. Defaults to mostRecent.
     * @param name
     */
    public void setFileName(String name) {
        if (writer != null) {
            closeFileStream();
            filePath = folderPath + name + fileExtension;
            openFileStream();
        } else { filePath = folderPath + name + fileExtension; }
    }

    /**
     * Writes data to the log file.
     * @param data
     */
    public void write(String data) {
        if (data != null) {
            try {
                if (writer == null) {
                    File file = new File(filePath);
                    if (!file.exists()) file.createNewFile();
                    writer = new BufferedWriter(new FileWriter(file, true));
                    writer.write("T: " + Robot.getTimeSinceBoot() + "ms | cycle: " + Robot.cycles + " | " + data + "\n");
                    writer.flush();
                    writer.close();
                    writer = null;
                } else {
                    writer.write("T: " + Robot.getTimeSinceBoot() + "ms | cycle: " + Robot.cycles + " | " + data + "\n");
                    writer.flush();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Tried to write null data to a log file on cycle" + Robot.cycles);
        }
    }

    public void clearFile() {
        try {
            if (writer == null) {
                File file = new File(filePath);
                if (!file.exists()) file.createNewFile();
                writer = new BufferedWriter(new FileWriter(file));
                writer.write("");
                writer.flush();
                writer.close();
                writer = null;
            } else {
                closeFileStream();
                File file = new File(filePath);
                if (!file.exists()) file.createNewFile();
                writer = new BufferedWriter(new FileWriter(file));
                writer.write("");
                writer.flush();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFileStream() {
        try {
            writer.close();
            writer = null;
        } catch(Exception e) {}
    }

    /**
     * Use if you want to write a lot of stuff to the log file in quick succession.
     */
    public void openFileStream() {
        if (writer == null) {
            try {
                File file = new File(filePath);
                if (!file.exists()) file.createNewFile();
                writer = new BufferedWriter(new FileWriter(file, true));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Logger instance;
    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }
}
