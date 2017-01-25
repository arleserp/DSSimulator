/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.distributed;

import unalcol.agents.AgentProgram;

/**
 *
 * @author Arles Rodriguez
 */
public class ExperimentFacadeGenerate {

    public static void main(String[] argv) {
        /**
         * This is for create all an experiment uncomment depending what you
         * need.
         */
        FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerMinImpl();
        //Parameters are filename for graph, nodes, edges and iterations.
        exp.createNew("min1000agents", 50, 150, 1000);
        // FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceRoutingImpl();
        // exp.createNew("testing_routing", 50, 100, 1);
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerSortingImpl();
        //exp.createNew("testing_sorting", 50, 100, 1);
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerAvgImpl();
        //exp.createNew("testing_average", 9, 100, 1);
    }
}
