package org.ribblegridlock;

import java.util.Arrays;

public class Junction extends Thread {

    private Road[] incomingRoads;
    private Road[] outgoingRoads;
    private String id;
    private final int duration;

    public Junction(String id, Road[] incomingRoads, Road[] outgoingRoads, int duration)
    {
        this.id = id;
        this.incomingRoads = incomingRoads;
        this.outgoingRoads = outgoingRoads;
        this.duration = duration;
    }

    public void run() {
        while(!Clock.getInstance().isFinished())
        {
            for (Road road : incomingRoads) {

                int carsThrough = 0;

                if (road == null) continue;

                int startTime = Clock.getInstance().getCurrentTime();

                while (startTime + duration > Clock.getInstance().getCurrentTime()) {
                    try {
                        sleep(100 / Config.getInstance().getSpeedFactor());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (Clock.getInstance().isFinished()) break;
                    if (!road.isEmpty())
                    {
                        Vehicle vehicle = road.first();

                        Road destinationRoad = null;
                        for(Road outgoingRoad : outgoingRoads) {
                            if (outgoingRoad == null) continue;
                            for (Main.DESTINATIONS destination : outgoingRoad.destination())
                                if (destination == vehicle.getDestination()) {
                                    destinationRoad = outgoingRoad;
                                    break;
                                }
                        }

                        if (destinationRoad == null) try {
                            throw new Exception("No destination");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        if (!destinationRoad.isFull())
                        {
                            try {
                                road.dequeue();
                                destinationRoad.enqueue(vehicle);
                                carsThrough++;

                                try {
                                    sleep(5000 / Config.getInstance().getSpeedFactor());
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                if (Clock.getInstance().isFinished()) break;
                Logger.getInstance().Junction(id, carsThrough, road.size(), Arrays.asList(incomingRoads).indexOf(road));
            }
        }
    }
}
