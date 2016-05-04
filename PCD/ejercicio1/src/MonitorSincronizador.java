import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.*;

/**
 * Created by Jorge Gallego Madrid on 19/04/2016
 */
public class MonitorSincronizador {

    private Condition[] waiting;
    private boolean[] printed, bufferAvailable;
    private ReentrantLock lock;

    public MonitorSincronizador(){
        lock = new ReentrantLock();
        printed = new boolean[Main.MULT_THREAD_NUMBER];
        bufferAvailable = new boolean[Main.MULT_THREAD_NUMBER];
        waiting = new Condition[Main.MULT_THREAD_NUMBER + 1]; // Hay que tener en cuenta el mezclador

        for (int i = 0; i < Main.MULT_THREAD_NUMBER; i++) {
            printed[i] = false;
            bufferAvailable[i] = false;
        }
        for (int i = 0; i < 4; i++) {
            waiting[i] = lock.newCondition();
        }
    }


    public void empiezaImprimir (int threadNum){
        lock.lock();
        try{
            while (!printed[threadNum-1]){
                waiting[threadNum].await();
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void finImprimir (int threadNum){
        lock.lock();
        try{
            printed[threadNum] = true;
            waiting[threadNum+1].signal();
        } finally {
            lock.unlock();
        }

    }

    public void goMixed(int threadNum){
        lock.lock();
        try{
            bufferAvailable[threadNum] = true;
            waiting[threadNum].signal();
            while (bufferAvailable[threadNum]){
                waiting[3].await();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

    public void wantMixed(int threadNum){
        lock.lock();
        try{
            while(!bufferAvailable[threadNum]){
                waiting[threadNum].await();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

    public void doneMixed(int threadNum){
        lock.lock();
        try {
            bufferAvailable[threadNum] = false;
            waiting[3].signal();
        }finally{
            lock.unlock();
        }
    }
}
