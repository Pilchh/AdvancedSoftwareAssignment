package org.ribblegridlock;

public class CarPark extends Thread {

    private final String id;
    private final Road road;
    private int capacity;
    private int parkedCount;

    private Vehicle[] parkedCars;

    public CarPark(String id, int capacity, Road road)
    {
        this.id = id;
        this.road = road;
        this.capacity = capacity;
        this.parkedCount = 0;
        this.parkedCars = new Vehicle[capacity];
    }

    public void run() {
        while(!Clock.getInstance().isFinished())
        {
            try {
                sleep(12000 / Config.getInstance().getSpeedFactor());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!road.isEmpty() && parkedCount < capacity)
            {
                try {
                    Vehicle vehicle = road.dequeue();
                    Clock clock = Clock.getInstance();
                    vehicle.setTimeParked(clock.getCurrentTime());
                    parkedCars[parkedCount] = vehicle;
                    parkedCount++;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public int remainingSpaces() {
        return capacity - parkedCount;
    }

    public int totalParked()
    {
        return parkedCount;
    }
    public int calculateAverageTime()
    {
        int totalVehicles = 0;
        int totalTime = 0;
        for (Vehicle vehicle : parkedCars)
        {
            if (vehicle == null) continue;
            totalTime += vehicle.getTimeParked() - vehicle.getTimeEntered();
            totalVehicles++;
        }

        return (int) (totalTime / totalVehicles);
    }
}
