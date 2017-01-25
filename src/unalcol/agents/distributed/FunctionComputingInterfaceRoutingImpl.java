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
public class FunctionComputingInterfaceRoutingImpl implements FunctionComputingInterface {

    /**
     * computation of routing tables
     *
     * @param new_i
     * @param inf_i
     * @param recv_i
     * @param out_i
     * @param ch
     * @param pid
     * @return
     */
    @Override
    public Object Compute(Hashtable new_i, Hashtable inf_i, Hashtable recv_i, Hashtable out_i, String ch, String pid) {
        Hashtable<String, Integer> aux;
        
        //This is for calculate routing tables, so we get the channel
        //aux = newh \infi U newi;
        aux = HashtableOperations.DifferenceSets(recv_i,
                HashtableOperations.JoinSets(new_i, inf_i));

        // outi[ch] = outi[ch] U aux  
        out_i.put(ch, HashtableOperations.JoinSets((Hashtable) out_i.get(ch), aux));
        return out_i;
    }

}
