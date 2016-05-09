import messagepassing.CommunicationScheme;

/**
 * Created by Jorge Gallego Madrid on 05/05/2016.
 */

public class HiloGeneradorNum extends Thread {

    private CommunicationScheme cs, cd;

    public HiloGeneradorNum (CommunicationScheme cs, CommunicationScheme cd){
        this.cs = cs;
        this.cd = cd;
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.MAX_NUM; i++) {
            cs.send("Peticion para generar numero");
            cd.receive();
            Main.sharedBuffer[i%Main.SHARED_BUFFER_SIZE] = i+1;
            System.out.println("Generado: " + (i+1) + " en: " + i%Main.SHARED_BUFFER_SIZE);
            cs.send("Numero generado e insertado");
        }
    }
}
