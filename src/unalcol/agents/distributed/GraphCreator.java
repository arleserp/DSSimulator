/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.distributed;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import org.apache.commons.collections15.Factory;
import unalcol.agents.AgentProgram;
import unalcol.agents.distributed.testing.GenerateIntegerDataSet;
import java.util.Vector;


/**
 *
 * @author Arles Rodriguez
 */
public class GraphCreator {

    public static class GraphFactory implements Factory<Graph<String, String>> {

        @Override
        public Graph<String, String> create() {
            return new SparseMultigraph<>();
        }
    }

    static class VertexFactory implements Factory<String> {

        int a = 0;
        Vector agentes = new Vector();
        //would not be duplicated
        Language languaje;
        AgentProgram program;
        GenerateIntegerDataSet dataset;

        VertexFactory(Language lang, AgentProgram ap) {
            languaje = lang;
            program = ap;
        }

        VertexFactory(Language lang, AgentProgram ap, GenerateIntegerDataSet ds) {
            languaje = lang;
            program = ap;
            dataset = ds;
        }

        @Override
        public String create() {
            String p = "p" + a++;
            Process p1 = new Process(program);
            p1.setAttribute("ID", p);
            p1.setAttribute("val", (Integer) a);
            p1.setAttribute("input", Math.random());//dataset.getNext());
            agentes.add(p1);
            return p;
        }

        public Vector getAgents() {
            return agentes;
        }
    }

    static class EdgeFactory implements Factory<String> {

        int chn = 0;
        int input;

        public String create() {
            return (String) "e" + chn++;
        }
    }

}
