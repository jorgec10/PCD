import messagepassing.Channel;
import messagepassing.MailBox;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class HiloMultiplosCinco extends Thread {

    private int[] inBuffer;
    private MailBox recvNum, sendNum;
    private Channel ch5, mix, mixPrint;
    private int cont;

    public HiloMultiplosCinco(MailBox recvNum, MailBox sendNum, Channel ch5, Channel mix, Channel mixPrint){
        super("Mul5");
        this.inBuffer = new int[Main.MAX_NUM/5];
        this.recvNum = recvNum;
        this.sendNum = sendNum;
        this.ch5 = ch5;
        this.mix = mix;
        this.mixPrint = mixPrint;
        this.cont = 0;

    }

    @Override
    public void run() {
        for (int i = 0; i < Main.MAX_NUM; i++) {
            sendNum.send(i%10);
            int received = (int) recvNum.receive();
            if (received%5 == 0) {
                inBuffer[cont] = received;
                cont++;
            }
        }

        ch5.receive();
        System.out.println("Múltiplos de 5: ");
        for (int i = 0; i < inBuffer.length - 1; i++) {
            System.out.print(inBuffer[i] + " ");
        }
        System.out.println(inBuffer[inBuffer.length-1]);


        mixPrint.send("Ya puede comenzar el mezclador");


        for (int i = 0; i < inBuffer.length; i++) {
            mix.send(inBuffer[i]);
        }

        mix.send(-1);

    }
}
