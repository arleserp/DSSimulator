/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.distributed;

import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Arles Rodriguez
 */
public class NetworkMessageBuffer {

    Hashtable<String, LinkedBlockingQueue> mbuffer;
    static final int MAXQUEUE = 5; //Max input buffer size by process

    private static class Holder {

        static final NetworkMessageBuffer INSTANCE = new NetworkMessageBuffer();
    }

    private NetworkMessageBuffer() {
        mbuffer = new Hashtable<String, LinkedBlockingQueue>();
    }

    public static NetworkMessageBuffer getInstance() {
        return Holder.INSTANCE;
    }

    public void createBuffer(String pid){
        mbuffer.put(pid, new LinkedBlockingQueue());
    }
    
    public void putMessage(String pid, String[] msg) {
        /*while (mbuffer.get(pid).size() == MAXQUEUE) {
            wait();
        }*/
        mbuffer.get(pid).add(msg);
        //System.out.println("put message");
        //notify();
    }

    // Called by Consumer
    public String[] getMessage(String pid) {
        //notify();
        //while (mbuffer.get(pid).isEmpty()) {
        //    wait();//By executing wait() from a synchronized block, a thread gives up its hold on the lock and goes to sleep.
        //}
        //System.out.println("getMessage");
        String[] nmsg = (String[]) (mbuffer.get(pid).poll());
        return nmsg;
    }

}
