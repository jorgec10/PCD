import messagepassing.MailBox;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class HiloMultiplosDos extends Thread {

    private int[] inBuffer;
    private MailBox recvNum, sendNum;
    private int cont;

    public HiloMultiplosDos(MailBox recvNum, MailBox sendNum){
        super("Mul2");
        this.inBuffer = new int[Main.MAX_NUM/2];
        this.recvNum = recvNum;
        this.sendNum = sendNum;
        this.cont = 0;

    }

    @Override
    public void run() {
        for (int i = 0; i < Main.MAX_NUM; i++) {
            sendNum.send(i%10);
            int received = (int) recvNum.receive();
            if (received%2 == 0) {
                inBuffer[cont] = received;
                cont++;
            }
        }
        System.out.println("Hola");
    }
}