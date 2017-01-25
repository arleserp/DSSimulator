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
public interface FileExperimentDAOInterface {
    public void createNew(String filename, int agentsNumber, int channelsNumber, int eppsteinEvolutions);

    public void loadExp(String filename, FunctionComputingInterface F, AgentProgram ap, Language languaje, String reportsFileOutput);
}
