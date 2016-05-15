package ejercicio1;

/**
 * Created by Jorge Gallego Madrid on 19/04/2016
 */
class HiloMultiplosDos extends Thread{

    private int[] inBuffer = new int[Main.MAX_NUM/2];

    private MonitorSincronizador monitor;

    HiloMultiplosDos(MonitorSincronizador m){
        this.monitor=m;
    }

    @Override
    public void run() {
        // Calculamos los multiplos de los numeros que hay en el buffer compartido
        // y los depositamos en el buffer interno.
        int cont = 0;
        for (int i = 0; i < Main.MAX_NUM; i++) {
            try {
                Main.ready[0].acquire();
                Main.mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE]%2 == 0){
                inBuffer[cont] = Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE];
                cont++;
            }
            Main.mutex.release();
            Main.seen[0].release();
        }

        System.out.println("Múltiplos de 2: ");
        for (int j = 0; j < inBuffer.length-1; j++) {
            System.out.print(inBuffer[j] + " ");
        }
        System.out.println(inBuffer[inBuffer.length-1]);

        Main.mixedBuffer[0] = inBuffer[0];
        Main.sem3.release();

        for (int i = 1; i < cont; i++) {
            monitor.enviaNumero(0);
            Main.mixedBuffer[0] = inBuffer[i];
            monitor.numeroEnviado(0);
        }

        monitor.enviaNumero(0);
        Main.mixedBuffer[0] = 99999;
        monitor.numeroEnviado(0);


    }
}
