package org.ribblegridlock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static Logger instance = null;

    private final String logFile;

    public Logger()
    {
        this.logFile = "log-" + System.currentTimeMillis() / 1000L + ".txt";
        try {
            File myObj = new File("logs/" + logFile);
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void Config(String configFile)
    {
        writeToFile("Configuration Loaded: " + configFile);
    }

    public void Junction(String id, int carsThrough, int carsWaiting, int direction)
    {
        String directionName = switch (direction) {
            case 0 -> "North";
            case 1 -> "East";
            case 2 -> "South";
            case 3 -> "West";
            default -> "Invalid Direction";
        };

        String output = "Junction " + id + ": " + carsThrough + " cars through from the " + directionName + ". " + carsWaiting + " cars waiting.";
        if (carsWaiting > 0 && carsThrough == 0)
        {
            output += " GRIDLOCK";
        }

        writeToFile(output);
    }

    private void writeToFile(String line)
    {
        try{
            FileWriter myWriter = new FileWriter("logs/" + logFile, true);

            int time = Clock.getInstance().getCurrentTime();
            int minutes = Math.floorDiv((time % 86400) % 3600, 60);
            int seconds = ((time % 86400) % 3600) % 60;

            myWriter.write("[" + minutes + "m " + seconds + "s" +"] " + line + "\n");
            myWriter.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static synchronized Logger getInstance()
    {
        if (instance == null)
        {
            instance = new Logger();
        }

        return instance;
    }

    public String secondsToTime(int time)
    {
        int minutes = Math.floorDiv((time % 86400) % 3600, 60);
        int seconds = ((time % 86400) % 3600) % 60;

        return minutes + "m " + seconds + "s";
    }
}
