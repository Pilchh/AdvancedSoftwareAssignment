package org.ribblegridlock;

public class Clock extends Thread
{
    private static Clock instance = null;
    private int currentTime;

    private boolean isFinished;
    public Clock()
    {
        currentTime = 0;
        this.isFinished = false;
    }

    public void run() {
        for(int i = 0; i < 3600; i++)
        {
            try {
                sleep(1000 / Config.getInstance().getSpeedFactor());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentTime += 1;
        }

        this.isFinished = true;
    }
    public boolean isFinished() { return this.isFinished; }
    public int getCurrentTime()
    {
        return currentTime;
    }

    public static synchronized Clock getInstance()
    {
        if (instance == null)
        {
            instance = new Clock();
            instance.start();
        }

        return instance;
    }

}
