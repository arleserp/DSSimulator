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
import java.util.Hashtable;
import javax.swing.JFrame;
import unalcol.agents.AgentProgram;
import unalcol.agents.distributed.testing.GenerateIntegerDataSet;
import java.util.Vector;
import unalcol.agents.distributed.testing.GenerateHashtableDataSet;

/**
 *
 * @author Arles Rodriguez
 */
public class FileExperimentDAOInterfaceRoutingImpl implements FileExperimentDAOInterface {

    /**
     *
     * @param filename Filename for new experiment
     * @param agentsNumber Number of agents
     * @param channelsNumber Number of Channels
     * @param eppsteinEvolutions Number of steady state iterations for Eppstein
     * Algorithm
     */
    public void createNew(String filename, int agentsNumber, int channelsNumber, int eppsteinEvolutions) {
        //Defines Perceptions and actions
        String[] _percepts = {"Message", "Neighbors"};
        String[] _actions = {"Initialize", "Receive", "AsynchRound"};
        Language languaje = new Language(_percepts, _actions);
        AgentProgram ap = new ProcessProgramRA(languaje);
        Vector agentes = new Vector();

        //Dataset creation
        GenerateIntegerDataSet dataset = new GenerateIntegerDataSet(agentsNumber, channelsNumber, eppsteinEvolutions, 0);
        dataset.saveFile(filename + ".dataset");
        //Network creation
        GraphCreator.VertexFactory v = new GraphCreator.VertexFactory(languaje, ap, dataset);
        Graph<String, String> g = new EppsteinPowerLawGenerator<>(new GraphCreator.GraphFactory(), v, new GraphCreator.EdgeFactory(), agentsNumber, channelsNumber, eppsteinEvolutions).create();
        GraphSerialization.saveSerializedGraph(filename + ".network", g);
        //create testing environment
        AgentProgram apt = new ProcessProgramRA(languaje);

        //Create Agents test
        //Create Agents
        String outputOk = filename;
        
        FunctionComputingInterface F = new FunctionComputingInterfaceRoutingImpl();
        NetworkEnvironmentMAS nt = new NetworkEnvironmentMAS(agentes, languaje, g, F, outputOk, null);
        nt.run();
        System.out.println("Simulation created and stored as: " + filename);
    }

    public void loadExp(String filename, FunctionComputingInterface F, AgentProgram ap, Language languaje, String reportsFileOutput) {
        //Definiendo acciones y percepciones
        Vector agentes = new Vector();
        //load Network
        Graph g = GraphSerialization.loadDeserializeGraph(filename + ".network");

        StatisticEngineInterface st = new StatisticEngineInterfaceRoutingImpl();
        //load expected output
        GenerateHashtableDataSet gds = new GenerateHashtableDataSet();
        
        Hashtable expectedOutput = gds.loadOutput(filename + ".outok");
        st.setExpectedRes(expectedOutput);
        
        //loads agent number
        GenerateIntegerDataSet dataset = GenerateIntegerDataSet.loadFile(filename + ".dataset");

        //create testing environment
        AgentProgram apt = new ProcessProgramRA(languaje);

        //Create Agents test
        for (int i = 0; i < dataset.getAgentsNumber(); i++) {
            String p = "p" + i;
            Process pt = new Process(apt);
            pt.setAttribute("ID", p);
            pt.setAttribute("val", (Integer) i);
            pt.setAttribute("input", dataset.getNext());
            agentes.add(pt);
        }

        //Run broadcast protocol In this case Agents
        NetworkEnvironmentMAS n = new NetworkEnvironmentMAS(agentes, languaje, g, F, reportsFileOutput, st);

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
