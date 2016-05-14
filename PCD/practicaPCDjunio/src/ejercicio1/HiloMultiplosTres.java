package ejercicio1;

/**
 * Created by Jorge Gallego Madrid on 19/04/2016
 */
public class HiloMultiplosTres extends Thread{

    private int[] inBuffer = new int[Main.MAX_NUM/3];

    private MonitorSincronizador monitor;

    public HiloMultiplosTres(MonitorSincronizador m){
        this.monitor=m;
    }

    @Override
    public void run() {
        // Calculamos los multiplos de los numeros que hay en el buffer compartido
        // y los depositamos en el buffer interno.
        int cont = 0;
        for (int i = 0; i < Main.MAX_NUM; i++) {
            try {
                Main.ready[1].acquire();
                Main.mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE]%3 == 0){
                inBuffer[cont] = Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE];
                cont++;
            }
            Main.mutex.release();
            Main.seen[1].release();
        }

        // Imprimimos los multiplos
        monitor.empiezaImprimir(1);
        System.out.println("Múltiplos de 3: ");
        for (int j = 0; j < inBuffer.length-1; j++) {
            System.out.print(inBuffer[j] + " ");
        }
        System.out.println(inBuffer[inBuffer.length-1]);

        Main.mixedBuffer[1] = inBuffer[0];
        monitor.finImprimir(1);

        for (int i = 1; i < cont; i++) {
            monitor.enviaNumero(1);
            Main.mixedBuffer[1] = inBuffer[i];
            monitor.numeroEnviado(1);
        }

        monitor.enviaNumero(1);
        Main.mixedBuffer[1] = 99999;
        monitor.numeroEnviado(1);

    }
}
