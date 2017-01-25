/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.distributed;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author Arles Rodriguez
 */
public class FunctionComputingInterfaceSortingImpl implements FunctionComputingInterface {

    @Override
    public Object Compute(Hashtable new_i, Hashtable inf_i, Hashtable recv_i, Hashtable out_i, String ch, String pid) {
        Hashtable<String, Integer> aux;

        aux = HashtableOperations.DifferenceSets(recv_i,
                HashtableOperations.JoinSets(new_i, inf_i));

        ArrayList out = HashtableOperations.Sort(aux, inf_i);

        //This is a new output to the control board. 
        out_i.put(pid, out);
        return out;
    }

}
