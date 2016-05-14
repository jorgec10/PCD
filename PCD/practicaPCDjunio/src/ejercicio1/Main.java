package ejercicio1;

import java.util.concurrent.Semaphore;


/**
 * Created by Jorge Gallego Madrid on 19/04/2016
 */
public class Main {

    public static final int MAX_NUM = 10000;
    public static final int SHARED_BUFFER_SIZE = 10;
    public static final int MULT_THREAD_NUMBER = 3;

    public static Semaphore[] ready;                    // Da paso a los multiplos
    public static Semaphore[] seen;                     // Lleva la cuenta de los multiplos que ha cogido
    public static Semaphore mutex;                      // Exclusión mutua
    public static Semaphore available;                  // Controla si se llena el buffer de números

    public static int[] sharedBuffer;                   // Buffer donde se depositan los números
    public static int[] mixedBuffer;                    // Buffer para mezclar los múltiplos

    public static MonitorSincronizador monitor;


    public static void main(String[] args){
        ready = new Semaphore[MULT_THREAD_NUMBER];                       // Un semáforo para cada hilo de múltiplos
        seen = new Semaphore[MULT_THREAD_NUMBER];                        // Un semáforo para cada hilo de múltiplos
        for (int i = 0; i < MULT_THREAD_NUMBER; i++) {
            ready[i] = new Semaphore(0);                // Se inicializan a 0 los dos porque al principio no hay números
            seen[i] = new Semaphore(0);
        }
        mutex = new Semaphore(1);                       // Se inicializa a 1
        available = new Semaphore(10);                  // Se inicializa a 10, huecos disponibles

        sharedBuffer = new int[SHARED_BUFFER_SIZE];     // Creamos el array compartido
        mixedBuffer = new int[MULT_THREAD_NUMBER];      // Creamos el array para mezclar los multiplos

        monitor = new MonitorSincronizador();           // Creamos el monitor

        Thread mixer = new HiloMezclador(monitor);
        Thread recolector = new HiloRecolectorBasura();
        Thread generator = new HiloGeneradorNum();
        Thread mult2 = new HiloMultiplosDos(monitor);
        Thread mult3 = new HiloMultiplosTres(monitor);
        Thread mult5 = new HiloMultiplosCinco(monitor);

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
