package ejercicio2;

import messagepassing.Channel;
import messagepassing.MailBox;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class Main {

    public static final int MAX_NUM = 10000;
    public static final int SHARED_BUFFER_SIZE = 10;
    public static final int MULT_THREAD_NUMBER = 3;

    public static int[] sharedBuffer;                   // Buffer donde se depositan los números
    public static int[] mixedBuffer;                    // Buffer para mezclar los múltiplos

    public static void main(String[] args) {


        mixedBuffer = new int[MULT_THREAD_NUMBER];      // Creamos el array para mezclar los multiplos


        MailBox gen = new MailBox();
        MailBox col = new MailBox();
        MailBox recvNum2 = new MailBox();
        MailBox sendNum2 = new MailBox();
        MailBox recvNum3 = new MailBox();
        MailBox sendNum3 = new MailBox();
        MailBox recvNum5 = new MailBox();
        MailBox sendNum5 = new MailBox();

        Channel printMult3 = new Channel();
        Channel printMult5 = new Channel();
        Channel mixMult2 = new Channel();
        Channel mixMult3 = new Channel();
        Channel mixMult5 = new Channel();
        Channel startMixPrint = new Channel();

        Thread mixer = new HiloMezclador(startMixPrint, mixMult2, mixMult3, mixMult5);
        Thread recolector = new HiloRecolectorBasura(col);
        Thread generator = new HiloGeneradorNum(gen);
        Thread mult2 = new HiloMultiplosDos(recvNum2, sendNum2, printMult3, mixMult2);
        Thread mult3 = new HiloMultiplosTres(recvNum3, sendNum3, printMult3, printMult5, mixMult3);
        Thread mult5 = new HiloMultiplosCinco(recvNum5, sendNum5, printMult5, mixMult5, startMixPrint);
        Thread control = new HiloControlador(gen, col, recvNum2, sendNum2, recvNum3, sendNum3, recvNum5, sendNum5);

        control.start();
        generator.start();
        mult2.start();
        mult3.start();
        mult5.start();
        recolector.start();
        mixer.start();

        try {
            control.join();
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
