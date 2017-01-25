/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JGraphLoader;

import GraphMLDemo.edge;
import GraphMLDemo.node;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphMLWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Arles Rodriguez
 */
public class GraphSaver {

    public static void saveG(String filename, Graph g) {

        final AbstractLayout layout = new StaticLayout(g);

        GraphMLWriter<node, edge> graphWriter
                = new GraphMLWriter<node, edge>();
        try {
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(filename)));

            graphWriter.addEdgeData("id", null, "0",
                new Transformer<edge, String>() {
                @Override
                public String transform(edge i) {
                    return i.getValue();
                }
            });
            /*
             graphWriter.addVertexData("y", null, "0",
             new Transformer<node, String>() {
             public String transform(node v) {
             return Double.toString(layout.getY(v));
             }
             }
             );*/
           
            graphWriter.save(g, out);
        } catch (IOException ex) {
            Logger.getLogger(GraphSaver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
