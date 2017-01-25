/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.distributed;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import unalcol.agents.Agent;
import unalcol.agents.AgentProgram;

/**
 *
 * @author Arles Rodriguez
 */
public class Process extends Agent implements Serializable {

    protected Hashtable<String, Object> properties = new Hashtable<String, Object>();
    LinkedBlockingQueue<Object> mbuffer = new LinkedBlockingQueue<>();
    private int round;

    public Process(AgentProgram _program) {
        super(_program);
        round = -1;
    }

    public void setAttribute(String key, Object value) {
        properties.put(key, value);
    }

    public Object getAttribute(String key) {
        return properties.get(key);
    }

    public boolean removeAttribute(String key) {
        if (properties.remove(key) != null) {
            return true;
        } else {
            return (false);
        }
    }

    public void putMessage(Object message) {
        mbuffer.add(message);
    }

    public Object getMessage(){
        return mbuffer.poll();
    }

    /**
     * @return the round round is defined as the number of iteration by
     * distributed Asynchronous Round-Based Computation Differently from
     * synchronous sys -tems, the rounds are not given for free in an
     * asynchronous system. Each process pi has to handle a local variable ri
     * which denotes its current round number. We first consider that, in each
     * round r it executes, a process sends a message to each of its neighbors,
     * and receives a message from each of them.
     */
    public int getRound() {
        return round;
    }

    /**
     * @param round the round to set
     */
    public void setRound(int round) {
        this.round = round;
    }

    public boolean hasMessages() {
        synchronized (mbuffer) {
            return !mbuffer.isEmpty();
        }
    }
    
    public void log(String msg){
        System.out.println("Agent: " + this.getAttribute("ID") + " : " + msg);
    }
}
