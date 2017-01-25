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
public class FunctionComputingInterfaceMinImpl implements FunctionComputingInterface {

    @Override
    public Object Compute(Hashtable new_i, Hashtable inf_i, Hashtable recv_i, Hashtable out_i, String ch, String pid) {
        Hashtable<String, Integer> aux;
        
        //aux = recv_i \infi U newi;
        aux = HashtableOperations.DifferenceSets(recv_i,
                HashtableOperations.JoinSets(new_i, inf_i));
        
        //calculates min
        int out = Math.min((int) HashtableOperations.Minimum(aux), (int) HashtableOperations.Minimum(inf_i));
        out_i.put(pid, out);
        return out;
    }

}
