package ejercicio1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.*;

/**
 * Created by Jorge Gallego Madrid on 19/04/2016
 */
class MonitorSincronizador {

    private Condition[] waiting;
    private boolean[] bufferAvailable;
    private ReentrantLock lock;

    MonitorSincronizador(){
        lock = new ReentrantLock();
        bufferAvailable = new boolean[Main.MULT_THREAD_NUMBER];
        waiting = new Condition[Main.MULT_THREAD_NUMBER + 1]; // Hay que tener en cuenta el mezclador

        for (int i = 0; i < Main.MULT_THREAD_NUMBER; i++) {
            bufferAvailable[i] = false;
        }
        for (int i = 0; i < 4; i++) {
            waiting[i] = lock.newCondition();
        }
    }

    void posicionLiberada(int threadNum){
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

    void enviaNumero(int threadNum){
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

    void numeroEnviado(int threadNum){
        lock.lock();
        try {
            bufferAvailable[threadNum] = false;
            waiting[3].signal();
        }finally{
            lock.unlock();
        }
    }
}
