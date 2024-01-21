package org.example;

public class Vehicle {

    private final Main.DESTINATIONS destination;
    private final int timeEntered;
    private int timeParked;

    public Vehicle(Main.DESTINATIONS destination, int timeEntered)
    {
        this.destination = destination;
        this.timeEntered = timeEntered;
    }

    public Main.DESTINATIONS getDestination()
    {
        return destination;
    }
    public int getTimeEntered() {return timeEntered; }
    public int getTimeParked() {return timeParked; }
    public void setTimeParked(int time) {
        timeParked = time;
    }
}
