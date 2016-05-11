import messagepassing.MailBox;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class HiloMultiplosTres extends Thread {

    private int[] inBuffer;
    private MailBox recvNum, sendNum;
    private int cont;

    public HiloMultiplosTres(MailBox recvNum, MailBox sendNum){
        super("Mul3");
        this.inBuffer = new int[Main.MAX_NUM/3];
        this.recvNum = recvNum;
        this.sendNum = sendNum;
        this.cont = 0;

    }

    @Override
    public void run() {
        for (int i = 0; i < Main.MAX_NUM; i++) {
            sendNum.send(i%10);
            int received = (int) recvNum.receive();
            if (received%3 == 0) {
                inBuffer[cont] = received;
                cont++;
            }
        }
    }
}
