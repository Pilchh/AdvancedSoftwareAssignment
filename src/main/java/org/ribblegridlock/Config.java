package org.ribblegridlock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Config {
    private static Config instance = null;

    private int northEntry;
    private int eastEntry;
    private int southEntry;

    private int aJunction;
    private int bJunction;
    private int cJunction;
    private int dJunction;

    private final int speedFactor = 10;

    public int getNorth() { return northEntry; }
    public int getEast() { return eastEntry; }
    public int getSouth() { return southEntry; }

    public int getA() { return aJunction; }
    public int getB() { return bJunction; }
    public int getC() { return cJunction; }
    public int getD() { return dJunction; }

    public int getSpeedFactor() { return speedFactor; }

    public Config(String file) {
        try {
            File fileObject = new File("configs/" + file);
            Scanner reader = new Scanner(fileObject);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();

                if (data.contains("JUNCTIONS")) {
                    continue;
                }

                if (data.contains("North")) {
                    String[] values = data.split(" ");
                    northEntry = Integer.parseInt(values[1]);
                    continue;
                }

                if (data.contains("East")) {
                    String[] values = data.split(" ");
                    eastEntry = Integer.parseInt(values[1]);
                    continue;
                }

                if (data.contains("south")) {
                    String[] values = data.split(" ");
                    southEntry = Integer.parseInt(values[1]);
                    continue;
                }

                if (data.contains("A")) {
                    String[] values = data.split(" ");
                    aJunction = Integer.parseInt(values[1]);
                    continue;
                }

                if (data.contains("B")) {
                    String[] values = data.split(" ");
                    bJunction = Integer.parseInt(values[1]);
                    continue;
                }

                if (data.contains("C")) {
                    String[] values = data.split(" ");
                    cJunction = Integer.parseInt(values[1]);
                    continue;
                }

                if (data.contains("D")) {
                    String[] values = data.split(" ");
                    dJunction = Integer.parseInt(values[1]);
                }
            }

            reader.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println(Arrays.toString(exception.getStackTrace()));
        }
    }


    public static synchronized Config getInstance()
    {
        if (instance == null)
        {
            System.out.println("Configuration loaded: " + Main.config);
            instance = new Config(Main.config);
            Logger.getInstance().Config(Main.config);
        }

        return instance;
    }
}
