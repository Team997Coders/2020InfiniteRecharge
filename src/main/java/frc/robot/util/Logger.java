package frc.robot.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import frc.robot.Robot;

public class Logger {

    private String fileExtension = ".txt";
    private final String folderPath = "/home/lvuser/logfiles/";
    private String fileName = "mostRecent";
    private String filePath = folderPath + fileName + fileExtension;

    private BufferedWriter writer = null;

    private Logger() {
        setFileName("mostRecent");
        clearFile();
    }

    /**
     * Change the name of the file to write to. Defaults to mostRecent.
     * File extensions are specified with the setFileExtension method.
     * @param name
     */
    public void setFileName(String name) {
        fileName = name;
        reloadFile();
    }

    /**
     * If you really want a csv or something I guess this is an option.
     * You don't need to add the "." before the extension.
     * @param extension
     */
    public void setFileExtension(String extension) {
        fileExtension = "." + extension;
        reloadFile();
    }

    private void reloadFile() {
        if (writer != null) {
            closeFileStream();
            filePath = folderPath + fileName + fileExtension;
            openFileStream();
        } else { filePath = folderPath + fileName + fileExtension; }
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
                    writer.write("T: " + "0000000000".substring(("" + Robot.getTimeSinceBoot()).length()) + Robot.getTimeSinceBoot() + "ms | cycle: " + "0000000000".substring(("" + Robot.cycles).length()) + Robot.cycles + " | " + data + "\n");
                    writer.flush();
                    writer.close();
                    writer = null;
                } else {
                    writer.write("T: " + "0000000000".substring(("" + Robot.getTimeSinceBoot()).length()) + Robot.getTimeSinceBoot() + "ms | cycle: " + "0000000000".substring(("" + Robot.cycles).length()) + Robot.cycles + " | " + data + "\n");
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
     * Make sure to run closeFileStream() at some point if you use this.
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