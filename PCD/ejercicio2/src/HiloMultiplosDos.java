/**
 * Created by Jorge Gallego Madrid on 05/05/2016.
 */
public class HiloMultiplosDos extends Thread {

    private int[] inBuffer = new int[Main.MAX_NUM/2];

    @Override
    public void run() {
        int cont = 0;
        for (int i = 0; i < Main.MAX_NUM; i++) {
            if (Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE]%2 == 0) {
                inBuffer[cont] = Main.sharedBuffer[i % Main.SHARED_BUFFER_SIZE];
                cont++;
            }
        }
    }
}
