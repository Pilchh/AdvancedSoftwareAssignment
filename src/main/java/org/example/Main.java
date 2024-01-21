package org.example;

public class Main {

    public enum DESTINATIONS {
        UNIVERSITY,
        STATION,
        SHOPPING_CENTRE,
        INDUSTRIAL_PARK
    }

    public static String config = "Scenario2.txt";

    public static void main(String[] args) {

        // Roads
        Road nEpJc = new Road(50, new Main.DESTINATIONS[] {DESTINATIONS.UNIVERSITY, DESTINATIONS.STATION, DESTINATIONS.SHOPPING_CENTRE, DESTINATIONS.INDUSTRIAL_PARK});
        Road eEpJb = new Road(30, new Main.DESTINATIONS[] {DESTINATIONS.UNIVERSITY, DESTINATIONS.STATION, DESTINATIONS.SHOPPING_CENTRE, DESTINATIONS.INDUSTRIAL_PARK});
        Road sEpJa = new Road(60, new Main.DESTINATIONS[] {DESTINATIONS.UNIVERSITY, DESTINATIONS.STATION, DESTINATIONS.SHOPPING_CENTRE, DESTINATIONS.INDUSTRIAL_PARK});

        Road jAIp = new Road(15, new Main.DESTINATIONS[] {DESTINATIONS.INDUSTRIAL_PARK});
        Road jAJB = new Road(7, new Main.DESTINATIONS[] {DESTINATIONS.UNIVERSITY, DESTINATIONS.STATION, DESTINATIONS.SHOPPING_CENTRE});
        Road jBJA = new Road(7, new Main.DESTINATIONS[] {DESTINATIONS.INDUSTRIAL_PARK});

        Road jBJC = new Road(10, new Main.DESTINATIONS[] {DESTINATIONS.UNIVERSITY, DESTINATIONS.STATION, DESTINATIONS.SHOPPING_CENTRE});
        Road jCJB = new Road(10, new Main.DESTINATIONS[] {DESTINATIONS.INDUSTRIAL_PARK});

        Road jCSc = new Road(7, new Main.DESTINATIONS[] {DESTINATIONS.SHOPPING_CENTRE});

        Road jCJD = new Road(10, new Main.DESTINATIONS[] {DESTINATIONS.UNIVERSITY, DESTINATIONS.STATION});

        Road jDU = new Road(15, new Main.DESTINATIONS[] {DESTINATIONS.UNIVERSITY});
        Road jDS = new Road(15, new Main.DESTINATIONS[] {DESTINATIONS.STATION});

        // Entry Points
        EntryPoint north = new EntryPoint(1, Config.getInstance().getNorth(), nEpJc);
        EntryPoint east = new EntryPoint(2, Config.getInstance().getEast(), eEpJb);
        EntryPoint south = new EntryPoint(3, Config.getInstance().getSouth(), sEpJa);

        // Junctions
        Junction a = new Junction("A", new Road[] {jBJA, null, sEpJa, null}, new Road[] {jAJB, null, null, jAIp}, Config.getInstance().getA());
        Junction b = new Junction("B", new Road[] {jCJB, eEpJb, jAJB, null}, new Road[] {jBJC, null, null, jBJA}, Config.getInstance().getB());
        Junction c = new Junction("C", new Road[] {null, nEpJc, jBJC, null}, new Road[] {jCJD, null, jCJB, jCSc}, Config.getInstance().getC());
        Junction d = new Junction("D", new Road[] {null, null, jCJD, null}, new Road[] {null, jDU, null, jDS}, Config.getInstance().getD());

        // Car Parks
        CarPark university = new CarPark("University", 100, jDU);
        CarPark station = new CarPark("Station", 150, jDS);
        CarPark shoppingCentre = new CarPark("Shopping Centre", 400, jCSc);
        CarPark industrialPark = new CarPark("Industrial Park", 1000, jAIp);

        //Start threads
        north.start();
        east.start();
        south.start();

        c.start();
        d.start();
        a.start();
        b.start();

        university.start();
        station.start();
        shoppingCentre.start();
        industrialPark.start();

        while (!Clock.getInstance().isFinished())
        {
            int currentTime = Clock.getInstance().getCurrentTime();
            if (currentTime % 600 < 5 || currentTime % 600 > 595)
            {
                String time = Logger.getInstance().secondsToTime(currentTime);

                System.out.println("\nTime: " + time);
                System.out.println("University: " + university.remainingSpaces());
                System.out.println("Shopping Centre: " + shoppingCentre.remainingSpaces());
                System.out.println("Station: " + station.remainingSpaces());
                System.out.println("Industrial Park: " + industrialPark.remainingSpaces());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("\nUniversity: " + university.totalParked() + " cars parked - average time: " + Logger.getInstance().secondsToTime(university.calculateAverageTime()));
        System.out.println("Shopping Centre: " + shoppingCentre.totalParked() + " cars parked - average time: " + Logger.getInstance().secondsToTime(shoppingCentre.calculateAverageTime()));
        System.out.println("Station: " + station.totalParked() + " cars parked - average time: " + Logger.getInstance().secondsToTime(station.calculateAverageTime()));
        System.out.println("Industrial Park: " + industrialPark.totalParked() + " cars parked - average time: " + Logger.getInstance().secondsToTime(industrialPark.calculateAverageTime()));

        int totalQueued = nEpJc.size() + eEpJb.size() + sEpJa.size() + jAIp.size() + jAJB.size() + jBJA.size() + jBJC.size() + jCJB.size() + jCSc.size() + jCJD.size() + jDU.size() + jDS.size();
        int totalParked = university.totalParked() + shoppingCentre.totalParked() + station.totalParked() + industrialPark.totalParked();
        int totalCreated = north.totalCreated + east.totalCreated + south.totalCreated;

        System.out.println("\nCars queued: " + totalQueued);
        System.out.println("Cars parked: " + totalParked);
        System.out.println("Cars parked & queued: " + (totalParked + totalQueued));
        System.out.println("Cars created: " + totalCreated);
    }
}

