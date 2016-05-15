package ejercicio1;

/**
 * Created by Jorge Gallego Madrid on 19/04/2016
 */class HiloMultiplosCinco extends Thread{

    private int[] inBuffer = new int[Main.MAX_NUM/5];

    private MonitorSincronizador monitor;

    HiloMultiplosCinco(MonitorSincronizador m){
        this.monitor=m;
    }

    @Override
    public void run() {
        // Calculamos los multiplos de los numeros que hay en el buffer compartido
        // y los depositamos en el buffer interno.
        int cont = 0;
        for (int i = 0; i < Main.MAX_NUM; i++) {
            try {
                Main.ready[2].acquire();
                Main.mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE]%5 == 0){
                inBuffer[cont] = Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE];
                cont++;
            }
            Main.mutex.release();
            Main.seen[2].release();
        }

        try {
            Main.sem5.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Múltiplos de 5: ");
        for (int j = 0; j < inBuffer.length-1; j++) {
            System.out.print(inBuffer[j] + " ");
        }
        System.out.println(inBuffer[inBuffer.length-1]);

        Main.mixedBuffer[2] = inBuffer[0];
        Main.beginMix.release();

        for (int i = 1; i < cont; i++) {
            monitor.enviaNumero(2);
            Main.mixedBuffer[2] = inBuffer[i];
            monitor.numeroEnviado(2);
        }

        monitor.enviaNumero(2);
        Main.mixedBuffer[2] = 99999;
        monitor.numeroEnviado(2);
    }
}
