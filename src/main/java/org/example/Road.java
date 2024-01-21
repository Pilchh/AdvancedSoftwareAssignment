package org.example;

import java.util.concurrent.Semaphore;

public class Road {

    // Mutex = 1 - Only allows one thread to access the critical region
    private final Semaphore mutex = new Semaphore(1);

    // Max size of the queue
    private final int maxSize;
    // Array to store the queue
    private final Vehicle[] queue;
    // Pointer to the first element in queue
    private int first;
    // Pointer to last element in queue
    private int last;
    // Current size of the queue
    private int currentSize;

    private Main.DESTINATIONS[] destination;

    public Road(int size, Main.DESTINATIONS[] destination)
    {
        this.maxSize = size;
        queue = new Vehicle[size];
        first = 0;
        last = -1;
        currentSize = 0;
        this.destination = destination;
    }

    public void enqueue(Vehicle vehicle)
    {
        /*
            THREAD SAFETY
            - Use semaphores to ensure mutual exclusion
              and check for available space.
        */

        try {
            // Try to take the mutex
            mutex.acquire();
        } catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }

        /*
            CRITICAL REGION
            - Perform the enqueue action
        */

        // If queue is already full, don't add new vehicle
        if (isFull())
        {
            return;
        }

        // Reset pointer if at end of array
        if (last == maxSize - 1)
        {
            last = -1;
        }

        // Add new vehicle and update pointer
        last += 1;
        queue[last] = vehicle;

        // Update size of queue
        currentSize += 1;

        // Release semaphore
        mutex.release();
    }

    public Vehicle dequeue() throws Exception {

        /*
            THREAD SAFETY
            - Use semaphores to ensure mutual exclusion
              and check for available items.
        */

        try {
            // Try to take the mutex
            mutex.acquire();

        } catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }

        /*
            CRITICAL REGION
            - Perform the dequeue action
        */

        // If queue is empty, throw exception
        if (isEmpty())
        {
            throw new Exception("Queue is empty");
        }

        // Get first vehicle and move pointer
        Vehicle vehicle = queue[first];
        first += 1;

        // Cycle pointer if at the end of queue
        if (first == maxSize)
        {
            first = 0;
        }

        // Update the queue size
        currentSize -= 1;

        mutex.release();

        // Return vehicle
        return vehicle;
    }

    public boolean isEmpty()
    {
        return currentSize == 0;
    }

    public boolean isFull()
    {
        return currentSize == maxSize ;
    }

    public Vehicle first() { return queue[first]; }

    public int size() { return currentSize; }

    public Main.DESTINATIONS[] destination() { return destination; }
}
