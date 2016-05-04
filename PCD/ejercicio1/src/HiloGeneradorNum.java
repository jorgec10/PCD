/**
 * Created by Jorge Gallego Madrid on 27/04/2016
 */
public class HiloGeneradorNum extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < Main.MAX_NUM; i++) {
            try {
                Main.available.acquire();
                Main.mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE] = i+1;
            Main.mutex.release();
            for (int j = 0; j < Main.MULT_THREAD_NUMBER; j++) {
                Main.ready[j].release();
            }
        }
    }
}
