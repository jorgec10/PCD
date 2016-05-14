package ejercicio2;

import messagepassing.MailBox;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class HiloRecolectorBasura extends Thread {
    private MailBox col;

    public HiloRecolectorBasura (MailBox col){
        super("Recolector");
        this.col = col;
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.MAX_NUM; i++) {
            col.send(i%10);
        }
    }
}
