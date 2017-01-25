package unalcol.agents.distributed;

import unalcol.agents.distributed.GraphCreator.*;

public class OldMain {

    /*public static void main(String[] argv) {
        int numberPlayer = 2;
        //Definiendo acciones y percepciones
        String[] _percepts = {"Message", "Neighbors"};
        String[] _actions = {"Initialize", "Receive", "AsynchRound"};
        Language languaje = new Language(_percepts, _actions);
        AgentProgram ap = new ProcessProgramRA(languaje);
        
        VertexFactory v =  new VertexFactory(languaje, ap);
        
        /* Manual */
       // Graph<String, String> g = new SparseMultigraph<String, String>();
        
        /* Random */
       /* Graph<String, String>  g = new EppsteinPowerLawGenerator<>(new GraphFactory(), v, new EdgeFactory(), 5, 5, 5).create();
        
        //GraphSerialization.saveSerializedGraph("hello.graph", g);
        Graph copy_g = GraphSerialization.loadDeserializeGraph("hello.graph");

        Broker<String> brokerObserver = new WaitNotifyObserver<String>(numberPlayer);
        Broker<String> brokerPlayer = new WaitNotifyPlayer<String>(numberPlayer);
       
        System.out.println("copy:" + copy_g);
        //Vector agentes = new Vector(); //manual mode

        /*
         * Processes manual configuration
         * */
       /* for (int i = 0; i <= 5; i++) {
            Process p1 = new Process(new ProcessProgram(languaje));
            p1.setAttribute("ID", ("p" + i));
            p1.setAttribute("val", (Integer)(i));
            g.addVertex((String) ("p" + i));
            agentes.add(p1);
        }

        g.addEdge((String) ("e" + 0), (String) ("p" + 0), (String) ("p" + (1)));
        g.addEdge((String) ("e" + 1), (String) ("p" + 1), (String) ("p" + (2)));
        g.addEdge((String) ("e" + 2), (String) ("p" + 2), (String) ("p" + (3)));
        g.addEdge((String) ("e" + 3), (String) ("p" + 3), (String) ("p" + (4)));
        g.addEdge((String) ("e" + 4), (String) ("p" + 4), (String) ("p" + (5)));
*/
		//p1.setAttribute( "HISTORY",     new java.util.Vector()     );
        //p1.setAttribute( "TOTAL",  new Integer(0)  );
        //p1.setAttribute( "OPPONENT",     new String("isabel")     );
                //p1.setAttribute( "CX",  new Integer(10)  );
        //p1.setAttribute( "CY",  new Integer(20)  );
        //p1.setAttribute( "COLOR",   Color.blue);
        //p1.setAttribute( "REWARD",  new Integer(0)  );
/*
         p1.setBroker(brokerObserver);
         p1.setBrokerPlayer(brokerPlayer);
         */		//agentes.add(p1);
    /*    System.out.println("g" + g.toString());
        /* Manual way */
       // NetworkEnvironmentCEBookOpt n = new NetworkEnvironmentCEBookOpt(agentes, languaje, g);
        
        
        /*classical max*/
    /*    NetworkEnvironmentOptMinPrCh n = new NetworkEnvironmentOptMinPrCh(v.getAgents(), languaje, copy_g);
        
        /* Automatic way */
        //NetworkEnvironmentCEBookOpt n = new NetworkEnvironmentCEBookOpt(v.getAgents(), languaje, g);
        //NetworkEnvironment n = new NetworkEnvironment(v.getAgents(), languaje, g);

       
        /*GraphPersistence.saveG("hoooola", g);
        Graph y = null ;
        try {
            y = GraphPersistence.load("hoooola");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("y" + y.toString());
        */
        
         // The Layout<V, E> is parameterized by the vertex and edge types
    /*    Layout<String, String> layout = new ISOMLayout<String, String>(copy_g);
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
        /*Observer observer = new Observer( new ObserverAgentProgram(languaje) );
         observer.setBroker(brokerObserver);

         observer.setBrokerPlayer(brokerPlayer);
         agentes.add(observer);
                
         dilemaMainFrame frame = new dilemaMainFrame ( agentes, languaje );
         frame.setVisible(true);*/
    /*    System.out.println("termina.");
    }*/
}
