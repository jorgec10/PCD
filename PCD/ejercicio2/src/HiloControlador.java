import messagepassing.MailBox;
import messagepassing.Selector;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class HiloControlador extends Thread{

    private MailBox gen, col;
    private Selector sel;


    public HiloControlador(MailBox gen, MailBox col){
        this.gen = gen;
        this.col = col;

        this.sel = new Selector();
        sel.addSelectable(this.gen, false);
        sel.addSelectable(this.col, false);



    }

    @Override
    public void run() {
        int [] sharedBuffer = new int[Main.SHARED_BUFFER_SIZE];

        int emptySlotsBuff = 10;
        int numSlot = 0;
        int cleaner = 0;
        int received;
        int [] slotToMult = new int[10];

        for (int i = 0; i < 10; i++) {

            gen.setGuardValue(emptySlotsBuff > 0);
            gen.setGuardValue(slotToMult[cleaner] == 0);

            switch (sel.selectOrBlock()){
                case 1:
                    received = (int) gen.receive();
                    sharedBuffer[numSlot] = received;
                    emptySlotsBuff--;

                    if(numSlot+1 == 10) numSlot = 0;
                    else numSlot++;
                    break;
                case 2:
                    received = (int) col.receive();
                    sharedBuffer[received] = 0;
                    slotToMult[cleaner] = 3;
                    emptySlotsBuff++;

                    if(cleaner+1 == 10) cleaner = 0;
                    else cleaner++;
                    break;
            }

        }



    }
}
