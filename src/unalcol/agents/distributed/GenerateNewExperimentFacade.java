/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.distributed;

import edu.uci.ics.jung.algorithms.generators.Lattice2DGenerator;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import javax.swing.JFrame;
import unalcol.agents.AgentProgram;
import unalcol.agents.distributed.testing.GenerateIntegerDataSet;

/**
 *
 * @author Arles Rodriguez
 */
public class GenerateNewExperimentFacade {
    public static void main(String[] argv) {
        int agentsNumber = 5;
        int channelsNumber = 10;
        int eppsteinEvolutions = 5;

        //Definiendo acciones y percepciones
        String[] _percepts = {"Message", "Neighbors"};
        String[] _actions = {"Initialize", "Receive", "AsynchRound"};
        Language languaje = new Language(_percepts, _actions);
        AgentProgram ap = new ProcessProgramRA(languaje);
        
        //Dataset creation
        GenerateIntegerDataSet dataset = new GenerateIntegerDataSet(agentsNumber, channelsNumber, eppsteinEvolutions);
        
        //Expected result
        System.out.println("dataset" + dataset.Min());
        
        //Network creation
        GraphCreator.VertexFactory v =  new GraphCreator.VertexFactory(languaje, ap, dataset);
        //Graph<String, String>  g = new EppsteinPowerLawGenerator<>(new GraphCreator.GraphFactory(), v, new GraphCreator.EdgeFactory(), agentsNumber, channelsNumber, eppsteinEvolutions).create();
        Graph<String, String>  g = new Lattice2DGenerator(new GraphCreator.GraphFactory(), v, new GraphCreator.EdgeFactory(), agentsNumber, channelsNumber, false).create();

        System.out.println("g" + g.toString());
        
        /*classical max*/
        NetworkEnvironmentOptRoutingPrCh n = new NetworkEnvironmentOptRoutingPrCh(v.getAgents(), languaje, g);
        
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
