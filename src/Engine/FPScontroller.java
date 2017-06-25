package Engine;

/**
 * Created by George on 15/06/2017.
 */
public class FPScontroller {

    public int fps = 60;
    public long lastFps;
    public long lastFrame;
    private long variableYieldTime, lastTime;

    public FPScontroller() {
        lastFps = getTime();
    }

    public void updateFps(){
        if(getTime() - lastFps > 1000){

            //System.out.println(fps);
            fps = 0;
            lastFps += 1000;
        }else{
            fps++;
        }
    }

    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public int getDelta(){
        long time = getTime();
        int delta = (int)(time - lastFrame);
        lastFrame = time;
        return delta;
    }

    public void sync(){
        if (fps <= 0) return;

        long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
        // yieldTime + remainder micro & nano seconds if smaller than sleepTime
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000*1000));
        long overSleep = 0; // time the sync goes over by

        try {
            while (true) {
                long t = System.nanoTime() - lastTime;

                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                }else if (t < sleepTime) {
                    // burn the last few CPU cycles to ensure accuracy
                    Thread.yield();
                }else {
                    overSleep = t - sleepTime;
                    break; // exit while loop
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);

            // auto tune the time sync should yield
            if (overSleep > variableYieldTime) {
                // increase by 200 microseconds (1/5 a ms)
                variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
            }
            else if (overSleep < variableYieldTime - 200*1000) {
                // decrease by 2 microseconds
                variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
            }
        }
    }
}

