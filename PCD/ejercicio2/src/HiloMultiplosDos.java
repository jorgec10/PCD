import messagepassing.Channel;
import messagepassing.MailBox;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class HiloMultiplosDos extends Thread {

    private int[] inBuffer;
    private MailBox recvNum, sendNum;
    private Channel ch3, mix;
    private int cont;

    public HiloMultiplosDos(MailBox recvNum, MailBox sendNum, Channel ch3, Channel mix){
        super("Mul2");
        this.inBuffer = new int[Main.MAX_NUM/2];
        this.recvNum = recvNum;
        this.sendNum = sendNum;
        this.ch3=ch3;
        this.mix=mix;
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

        System.out.println("Múltiplos de 2: ");
        for (int i = 0; i < inBuffer.length - 1; i++) {
            System.out.print(inBuffer[i] + " ");
        }
        System.out.println(inBuffer[inBuffer.length-1]);
        ch3.send("Ya se pueden imprimir los múltiplos de 3");

        for (int i = 0; i < inBuffer.length; i++) {
            mix.send(inBuffer[i]);
        }

        mix.send(-1);
    }
}