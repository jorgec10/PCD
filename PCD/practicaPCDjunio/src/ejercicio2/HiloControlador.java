package ejercicio2;

import messagepassing.MailBox;
import messagepassing.Selector;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
class HiloControlador extends Thread{

    private MailBox gen, col;
    private Selector sel;
    private MailBox recvNum2, recvNum3, recvNum5;
    private MailBox sendNum2, sendNum3, sendNum5;
    private int [] sharedBuffer;


    HiloControlador(MailBox gen, MailBox col, MailBox recvNum2, MailBox sendNum2, MailBox recvNum3, MailBox sendNum3, MailBox recvNum5, MailBox sendNum5){
        super("Controlador");
        this.gen = gen;
        this.col = col;

        this.sharedBuffer = new int[Main.SHARED_BUFFER_SIZE];

        this.recvNum2 = sendNum2;
        this.sendNum2 = recvNum2;
        this.recvNum3 = sendNum3;
        this.sendNum3 = recvNum3;
        this.recvNum5 = sendNum5;
        this.sendNum5 = recvNum5;

        this.sel = new Selector();

        this.sel.addSelectable(this.gen, false);
        this.sel.addSelectable(this.col, false);
        this.sel.addSelectable(this.recvNum2, false);
        this.sel.addSelectable(this.recvNum3, false);
        this.sel.addSelectable(this.recvNum5, false);
    }

    @Override
    public void run() {

        int emptySlotsBuff = 10;
        int numSlot = 0;
        int cleaner = 0;
        int received = 0;
        int [] slotToMult = new int[10];            // Array donde se lleva la cuenta de los hilos mult que quedan por usar el numero de esa posicion
        for (int i = 0; i < 10; i++) {
            slotToMult[i] = Main.MULT_THREAD_NUMBER;
        }
        int [] numAvailable = new int[Main.MULT_THREAD_NUMBER];           // Numero de enteros que le quedan por ver a cada hilo mult
        for (int i = 0; i < Main.MULT_THREAD_NUMBER; i++) {
            numAvailable[i] = 0;
        }

        for (int i = 0; i < 50000; i++) {

            gen.setGuardValue(emptySlotsBuff > 0);
            col.setGuardValue(slotToMult[cleaner] == 0);
            recvNum2.setGuardValue(numAvailable[0] > 0);
            recvNum3.setGuardValue(numAvailable[1] > 0);
            recvNum5.setGuardValue(numAvailable[2] > 0);

            int caseNum = sel.selectOrBlock();
            //System.out.println(caseNum);
            switch (caseNum){
                case 1:
                    received = (int) gen.receive();
                    sharedBuffer[numSlot] = received;
                    emptySlotsBuff--;
                    if(numSlot+1 == 10) numSlot = 0;
                    else numSlot++;

                    for (int j = 0; j < 3; j++) {
                        numAvailable[j]++;
                    }
                    break;
                case 2:
                    received = (int) col.receive();
                    sharedBuffer[received] = 0;
                    slotToMult[cleaner] = Main.MULT_THREAD_NUMBER;
                    emptySlotsBuff++;

                    if(cleaner+1 == 10) cleaner = 0;
                    else cleaner++;
                    break;
                case 3:
                    received = (int) recvNum2.receive();
                    sendNum2.send(sharedBuffer[received]);
                    slotToMult[received]--;
                    numAvailable[0]--;
                    break;
                case 4:
                    received = (int) recvNum3.receive();
                    sendNum3.send(sharedBuffer[received]);
                    slotToMult[received]--;
                    numAvailable[1]--;
                    break;
                case 5:
                    received = (int) recvNum5.receive();
                    sendNum5.send(sharedBuffer[received]);
                    slotToMult[received]--;
                    numAvailable[2]--;
                    break;
            }

        }






    }
}
