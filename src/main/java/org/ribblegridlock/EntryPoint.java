package org.ribblegridlock;

import java.util.Random;

public class EntryPoint extends Thread {

    private final int creationRate;
    private final Road road;
    private final int id;
    public int totalCreated;

    private Main.DESTINATIONS[] weightedDestinations = {
            Main.DESTINATIONS.UNIVERSITY,
            Main.DESTINATIONS.STATION,
            Main.DESTINATIONS.STATION,
            Main.DESTINATIONS.SHOPPING_CENTRE,
            Main.DESTINATIONS.SHOPPING_CENTRE,
            Main.DESTINATIONS.SHOPPING_CENTRE,
            Main.DESTINATIONS.INDUSTRIAL_PARK,
            Main.DESTINATIONS.INDUSTRIAL_PARK,
            Main.DESTINATIONS.INDUSTRIAL_PARK,
            Main.DESTINATIONS.INDUSTRIAL_PARK
    };

    public EntryPoint(int id, int creationRate, Road road)
    {
        this.id = id;
        this.creationRate = creationRate;
        this.road = road;
        this.totalCreated = 0;
    }

    public void run() {
        while(!Clock.getInstance().isFinished())
        {

            try {
                sleep(3600 / creationRate * (1000 / Config.getInstance().getSpeedFactor()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!road.isFull())
            {
                Clock clock = Clock.getInstance();
                int randomDestination = new Random().nextInt(weightedDestinations.length);
                Main.DESTINATIONS destination = weightedDestinations[randomDestination];
                Vehicle newVehicle = new Vehicle(destination, clock.getCurrentTime());
                road.enqueue(newVehicle);
                totalCreated++;
            }
        }
    }
}

