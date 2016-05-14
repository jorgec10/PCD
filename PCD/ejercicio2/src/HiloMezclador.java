import messagepassing.Channel;
import messagepassing.Selector;

/**
 * Created by Jorge Gallego Madrid on 09/05/2016.
 */
public class HiloMezclador extends Thread {
    
    private Channel begin;
    private Channel mix2, mix3, mix5;
    private Selector sel;
    private int[] mixedBuffer;
    
    public HiloMezclador (Channel begin, Channel mix2, Channel mix3, Channel mix5){
        this.mixedBuffer = new int[3];

        this.begin = begin;
        this.mix2 = mix2;
        this.mix3 = mix3;
        this.mix5 = mix5;

        this.sel = new Selector();

        this.sel.addSelectable(mix2, false);
        this.sel.addSelectable(mix3, false);
        this.sel.addSelectable(mix5, false);
    }


    private int minimoMixed(int a, int b, int c){
        if ((a==99999) && (b==99999) && (c==99999)) return -1;
        else {
            if (a<=b && a<=c)
                return 0;
            else if (b<=a && b<=c)
                return 1;
            else
                return 2;
        }
    }

    @Override
    public void run() {
        begin.receive();
        System.out.println("Mezcla: ");
        int min;

        mixedBuffer[0] = (int) mix2.receive();
        mixedBuffer[1] = (int) mix3.receive();
        mixedBuffer[2] = (int) mix5.receive();

        min = minimoMixed(mixedBuffer[0], mixedBuffer[1], mixedBuffer[2]);

        while(min != -1){
            System.out.print(mixedBuffer[min] + " ");

            mix2.setGuardValue(min == 0);
            mix3.setGuardValue(min == 1);
            mix5.setGuardValue(min == 2);

            int caseNum = sel.selectOrBlock();

            switch (caseNum){
                case 1:
                    mixedBuffer[0] = (int) mix2.receive();
                    break;
                case 2:
                    mixedBuffer[1] = (int) mix3.receive();
                    break;
                case 3:
                    mixedBuffer[2] = (int) mix5.receive();
                    break;
            }

            min = minimoMixed(mixedBuffer[0], mixedBuffer[1], mixedBuffer[2]);

        }
    }
}
