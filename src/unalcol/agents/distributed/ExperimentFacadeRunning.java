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
public class ExperimentFacadeRunning {

    public static void main(String[] argv) {
        
        String[] _percepts = {"Message", "Neighbors"};
        String[] _actions = {"Initialize", "Receive", "AsynchRound"};
        Language lang = new Language(_percepts, _actions);

        /**
         * This part is for loading and run an experiment previously created
         * Choose function F, Agent Program and Statistics filename Uncomment
         * according your needs.
         */
        FunctionComputingInterface F = new FunctionComputingInterfaceMinImpl();
        // FunctionComputingInterface F = new FunctionComputingInterfaceRoutingImpl();
        //FunctionComputingInterface F = new FunctionComputingInterfaceSortingImpl();
        //FunctionComputingInterface F = new FunctionComputingInterfaceAvgImpl();
        
        
        //Choose Agent Program
        double pf = 0;
        AgentProgram ap = new ProcessProgramRACrashesFailure(lang, pf);

        //Set filename for statistics 
        //String statsFilename = "sorting000005.csv" ;
        String statsFilename = "1000agentsMin.csv" ;
        //String statsFilename = "1000agentsAvg.csv" ;

        
        //Create Experiment
        FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerMinImpl();
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceRoutingImpl();
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerSortingImpl();
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerAvgImpl();
        exp.loadExp("min1000agents", F, ap, lang, statsFilename);
        //exp.loadExp("testing_routing", F, ap, lang, statsFilename);
        //exp.loadExp("testing_sorting", F, ap, lang, statsFilename);
       // exp.loadExp("testing_average", F, ap, lang, statsFilename);
    }
}
