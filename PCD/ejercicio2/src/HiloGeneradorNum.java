/**
 * Created by Jorge Gallego Madrid on 05/05/2016.
 */
public class HiloGeneradorNum extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < Main.MAX_NUM; i++) {

            Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE] = i+1;

        }
    }
}
