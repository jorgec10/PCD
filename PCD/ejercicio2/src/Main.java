/**
 * Created by Jorge Gallego Madrid on 05/05/2016.
 */
public class Main {

    public static final int MAX_NUM = 10000;
    public static final int SHARED_BUFFER_SIZE = 10;
    public static final int MULT_THREAD_NUMBER = 3;

    public static int[] sharedBuffer;                   // Buffer donde se depositan los números
    public static int[] mixedBuffer;                    // Buffer para mezclar los múltiplos

    public static void main(String[] args) {

        sharedBuffer = new int[SHARED_BUFFER_SIZE];     // Creamos el array compartido
        mixedBuffer = new int[MULT_THREAD_NUMBER];      // Creamos el array para mezclar los multiplos

        Thread mixer = new HiloMezclador();
        Thread recolector = new HiloRecolectorBasura();
        Thread generator = new HiloGeneradorNum();
        Thread mult2 = new HiloMultiplosDos();
        Thread mult3 = new HiloMultiplosTres();
        Thread mult5 = new HiloMultiplosCinco();

        generator.start();
        mult2.start();
        mult3.start();
        mult5.start();
        recolector.start();
        mixer.start();

        try {
            generator.join();
            mult2.join();
            mult3.join();
            mult5.join();
            recolector.join();
            mixer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
