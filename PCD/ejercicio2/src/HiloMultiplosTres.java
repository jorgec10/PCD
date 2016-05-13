import messagepassing.Channel;
import messagepassing.MailBox;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class HiloMultiplosTres extends Thread {

    private int[] inBuffer;
    private MailBox recvNum, sendNum;
    private Channel ch3, ch5, mix;
    private int cont;

    public HiloMultiplosTres(MailBox recvNum, MailBox sendNum, Channel ch3, Channel ch5, Channel mix){
        super("Mul3");
        this.inBuffer = new int[Main.MAX_NUM/3];
        this.recvNum = recvNum;
        this.sendNum = sendNum;
        this.ch3 = ch3;
        this.ch5 = ch5;
        this.mix = mix;
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

        ch3.receive();
        System.out.println("Múltiplos de 3: ");
        for (int i = 0; i < inBuffer.length - 1; i++) {
            System.out.print(inBuffer[i] + " ");
        }
        System.out.println(inBuffer[inBuffer.length-1]);
        ch5.send("Ya se pueden imprimir los multiplos de 5");

        for (int i = 0; i < inBuffer.length; i++) {
            mix.send(inBuffer[i]);
        }

        mix.send(-1);

    }
}
