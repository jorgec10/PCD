package ejercicio2;

import messagepassing.MailBox;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */

public class HiloGeneradorNum extends Thread {

    private MailBox gen;

    public HiloGeneradorNum (MailBox gen){
        super("Generador");
        this.gen = gen;
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.MAX_NUM; i++) {
            gen.send(i+1);
        }
    }
}
