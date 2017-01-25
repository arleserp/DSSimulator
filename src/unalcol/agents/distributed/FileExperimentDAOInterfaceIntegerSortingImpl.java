/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.distributed;

import edu.uci.ics.jung.algorithms.generators.random.EppsteinPowerLawGenerator;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import unalcol.agents.AgentProgram;
import unalcol.agents.distributed.testing.GenerateIntegerDataSet;
import java.util.Vector;

/**
 *
 * @author Arles Rodriguez
 */
public class FileExperimentDAOInterfaceIntegerSortingImpl implements FileExperimentDAOInterface {

    /**
     *
     * @param filename Filename for new experiment
     * @param agentsNumber Number of agents
     * @param channelsNumber Number of Channels
     * @param eppsteinEvolutions Number of steady state iterations for Eppstein
     * Algorithm
     */
    public void createNew(String filename, int agentsNumber, int channelsNumber, int eppsteinEvolutions) {
        //Definiendo acciones y percepciones
        String[] _percepts = {"Message", "Neighbors"};
        String[] _actions = {"Initialize", "Receive", "AsynchRound"};
        Language languaje = new Language(_percepts, _actions);
        AgentProgram ap = new ProcessProgramRA(languaje);

        //Dataset creation
        GenerateIntegerDataSet dataset = new GenerateIntegerDataSet(agentsNumber, channelsNumber, eppsteinEvolutions);
        dataset.saveFile(filename + ".dataset");

        //Network creation
        GraphCreator.VertexFactory v = new GraphCreator.VertexFactory(languaje, ap, dataset);
        Graph<String, String> g = new EppsteinPowerLawGenerator<>(new GraphCreator.GraphFactory(), v, new GraphCreator.EdgeFactory(), agentsNumber, channelsNumber, eppsteinEvolutions).create();
        GraphSerialization.saveSerializedGraph(filename + ".network", g);

    }

    public void loadExp(String filename, FunctionComputingInterface F, AgentProgram ap, Language languaje, String reportsFileOutput) {
        //Definiendo acciones y percepciones
        Vector agentes = new Vector();

        //load Dataset
        GenerateIntegerDataSet dataset = GenerateIntegerDataSet.loadFile(filename + ".dataset");

        //Expected result
        ArrayList expectedRes = dataset.Sort();
        
        //Create Agents
        for (int i = 0; i < dataset.getAgentsNumber(); i++) {
            String p = "p" + i;
            Process p1 = new Process(ap);
            p1.setAttribute("ID", p);
            p1.setAttribute("val", (Integer) i);
            p1.setAttribute("input", dataset.getNext());
            agentes.add(p1);
        }

        //load Network
        Graph g = GraphSerialization.loadDeserializeGraph(filename + ".network");

        //load Statistics engine
        StatisticEngineInterface stat = new StatisticEngineInterfaceIntegerSortingImpl();
        
        //setExpectedRes
        stat.setExpectedRes(expectedRes);

        //Run broadcast protocol In this case Agents
        NetworkEnvironmentMAS n = new NetworkEnvironmentMAS(agentes, languaje, g, F, reportsFileOutput, stat);

        // The Layout<V, E> is parameterized by the vertex and edge types
        Layout<String, String> layout = new ISOMLayout<String, String>(g);
        layout.setSize(new Dimension(600, 600)); // sets the initial size of the layout space

        // The BasicVisualizationServer<V,E> is parameterized by the vertex and edge types
        BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<String, String>(layout);
        vv.setPreferredSize(new Dimension(600, 600)); //Sets the viewing area size

        vv.getRenderContext().setVertexFillPaintTransformer(n.vertexColor);
        vv.getRenderContext().setEdgeDrawPaintTransformer(n.edgeColor);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());
        n.setVV(vv);

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);

        n.run();

    }

}
