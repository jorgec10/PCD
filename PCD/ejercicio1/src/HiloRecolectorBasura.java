/**
 * Created by Jorge Gallego Madrid on 19/04/2016
 */
public class HiloRecolectorBasura extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < Main.MAX_NUM; i++) {
            try {
                for (int j = 0; j < Main.MULT_THREAD_NUMBER; j++) {
                    Main.seen[j].acquire();
                }
                Main.mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE] = -1;
            Main.available.release();
            Main.mutex.release();
        }
    }
}
