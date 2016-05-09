import messagepassing.CommunicationScheme;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class Controlador extends Thread{

    private CommunicationScheme cs;

    public Controlador (CommunicationScheme cs){
        this.cs = cs;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10000; i++) {
            cs.send("Via libre al generador");
        }



    }
}
