package ejercicio1;

/**
 * Created by Jorge Gallego Madrid on 19/04/2016
 */
class HiloMezclador extends Thread {

    private MonitorSincronizador monitor;

    HiloMezclador (MonitorSincronizador m){
        this.monitor = m;
    }

    private int minimoMixed(int a, int b, int c){
        if ((a==99999) && (b==99999) && (c==99999)) return -1;
        else {
            if (a<=b && a<=c)
                return 0;
            else if (b<=a && b<=c)
                return 1;
            else
                return 2;
        }
    }

    @Override
    public void run() {
        try {
            Main.beginMix.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Secuencia final: ");
        int posicion = minimoMixed(Main.mixedBuffer[0], Main.mixedBuffer[1], Main.mixedBuffer[2]);
        while (posicion!=-1) {
            System.out.print(Main.mixedBuffer[posicion] + " ");
            monitor.posicionLiberada(posicion);
            posicion = minimoMixed(Main.mixedBuffer[0], Main.mixedBuffer[1], Main.mixedBuffer[2]);
        }
    }
}
