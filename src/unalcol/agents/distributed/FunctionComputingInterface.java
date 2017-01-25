/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.distributed;

import java.util.Hashtable;

/**
 *
 * @author Arles Rodriguez
 */
public interface FunctionComputingInterface {
    //Implements a function F 
    Object Compute(Hashtable new_i, Hashtable inf_i, Hashtable recv_i, Hashtable out_i, String channel, String pid);
}
