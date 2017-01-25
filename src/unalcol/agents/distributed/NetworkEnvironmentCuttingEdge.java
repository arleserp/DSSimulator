package unalcol.agents.distributed;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;

import unalcol.agents.simulate.util.*;
import unalcol.agents.*;
import unalcol.agents.simulate.*;

import java.util.Vector;

import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.algorithms.generators.random.EppsteinPowerLawGenerator;
import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.collections15.Transformer;
import unalcol.agents.distributed.GraphElements.MyVertex;

public class NetworkEnvironmentCuttingEdge extends Environment {

    public static String msg = null;
    public int[][] structure = null;
    public SimpleLanguage language = null;
    Date date;
    reportPajeFormat r;
    Hashtable<String, ConcurrentLinkedQueue> mbuffer = new Hashtable<String, ConcurrentLinkedQueue>();
    Graph<String, GraphElements.MyVertex> g;
    String currentNode = null;
    String currentEdge = null;
    String lastactionlog;

    BasicVisualizationServer<String, String> vv;

    void setVV(BasicVisualizationServer<String, String> v) {
        vv = v;
    }

    // Transformer maps the vertex number to a vertex property
    Transformer<String, Paint> vertexColor = new Transformer<String, Paint>() {
        @Override
        public Paint transform(String i) {
            //System.out.println("callllll" + currentNode);
            if (currentNode.equals(i)) {
                return Color.RED;
            }
            return Color.BLUE;
        }
    };

    Transformer<String, Paint> edgeColor = new Transformer<String, Paint>() {
        @Override
        public Paint transform(String i) {
            //System.out.println("callllll" + currentNode);
            if (i.equals(currentEdge)) {
                return Color.YELLOW;
            }
            return Color.BLACK;
        }
    };

    public int getRowsNumber() {
        return structure.length;
    }

    public int getColumnsNumber() {
        return structure[0].length;
    }

    public boolean act(Agent agent, Action action) {
        boolean flag = (action != null);
        Process a = (Process) agent;

        currentNode = (String) a.getAttribute("ID");
        //System.out.println("cn" + currentNode);
        vv.repaint();
        String log;
        if (flag) {
            //Agents can be put to Sleep for some ms
            //sleep is good is graph interface is on
            agent.sleep(3);

            String act = action.getCode();
            Percept p = sense(a);
            String msg = null;

            /**
             * 0- "Initialize" 1- "Receive" 2- "AsynchRound"
             *
             */
            /* @TODO: Detect Stop Conditions for the algorithm */
            switch (language.getActionIndex(act)) {
                case 0: // Initialize
                    //Load parameters of input
                    Hashtable<String, String> newi = new Hashtable<>();
                    Hashtable<String, String> outi = new Hashtable<>();
                    Hashtable<String, String> infi = new Hashtable<>();
                    
                    newi.put((String) a.getAttribute("ID"), (String) a.getAttribute("ID"));
                    infi.put((String) a.getAttribute("ID"), (String) a.getAttribute("ID"));

                    a.setAttribute("newi", newi);
                    a.setAttribute("outi", outi);
                    a.setAttribute("infi", infi);

                    Collection nbors = (Collection) p.getAttribute("neighbors");
                    ArrayList nbor = new ArrayList(nbors);
                    a.setAttribute("com_with", nbor);
                    a.setRound(a.getRound() + 1);
                    break;
                case 1: // Receive
                    //System.out.println("ID" + a.getAttribute("ID") + " vert: " + g.getOutEdges((String) a.getAttribute("ID")));
                   // ArrayList edges = new ArrayList(g.getOutEdges((String) a.getAttribute("ID")));
                    //String chme = String.valueOf(g.findEdge((String) a.getAttribute("ID"), (String) a.getAttribute("ID")));
                   // edges.remove(chme);
                    //do {
                        String[] s = (String[]) a.getMessage();
                        String from = s[0];
                        String msgr = s[1];
                        Hashtable<String, String> aux = new Hashtable();
                        Hashtable newh = HashtableOperations.deserializeHashtable(msgr);
                        Hashtable tmpout;
                        /* There is some dilemma here... if i don't receive new information 
                         algorithm must stop but agents are always waiting information */
                        String ch = String.valueOf(g.findEdge(from, (String) a.getAttribute("ID")));
                       // System.out.println("ag " + (String) a.getAttribute("ID") + " from: " + from + " ch:" + ch + " newh size: " + newh.size() + " , " + newh + " aux size " + aux.size() + ", " + aux);

                        if (newh.isEmpty()){
                            //((ArrayList) a.getAttribute("com_with")).remove(s[0]);
                            // a.setAttribute("com_with", a.getAttribute("com_with"));
                             //if(((ArrayList)a.getAttribute("com_with")).isEmpty()){
                             //    System.out.println("die!");
                             //    a.die();
                            // }
                        } else {
                            //ArrayList<Boolean> recv = new ArrayList(Collections.nCopies(edges.size(), true)); 
                            //This is for calculate routing tables, so we get the channel
                            //This is not the channel from the message is sended.
                            //Tendría que obtener la colección de canales e ir tachando los canales?
                            //recv.set(edges.indexOf(ch), true);
                            
                            //aux = newh \infi U newi;
                            aux = HashtableOperations.DifferenceSets(newh,
                                    HashtableOperations.JoinSets((Hashtable) a.getAttribute("newi"), (Hashtable)a.getAttribute("infi")));

                            //Cutting edge calculations
                            for (Iterator<String> iterator = newh.keySet().iterator(); iterator.hasNext();) {
                                String key = iterator.next();
                                //validar que esa informacion recibida por el proceso
                                //canal este en los posibles canales hacia el proceso
                                // Debo tener el canal de quien procede el mensaje?
                                if (!((String) key).equals((String) a.getAttribute("ID"))) {
                                    if (((Hashtable) (a.getAttribute("outi"))).get(key) == null) {
                                        tmpout = new Hashtable();
                                    } else {
                                        tmpout = (Hashtable) ((Hashtable) (a.getAttribute("outi"))).get(key);
                                    }

                                    if (tmpout.get(ch) != null) {
                                        System.out.println("ya estaba: " + ch + " r:" + a.getRound());
                                    } else {
                                        System.out.println("new! " + ch + " r: " + a.getRound());
                                        tmpout.put(ch, a.getRound());
                                        ((Hashtable) (a.getAttribute("outi"))).put(key, tmpout);
                                    }
                                    //This is a new output to the control board. 
                                    returnOutput((String) a.getAttribute("ID"), (Hashtable) a.getAttribute("outi"));
                                }
                            }

                        }
                       // edges.remove(ch);
                        /*if (!edges.isEmpty() && !((ArrayList)a.getAttribute("com_with")).isEmpty()) {
                            do {
                                p = sense(a);
                                a.sleep(100);
                                //System.out.println(a.getAttribute("ID") + " wait." + edges);
                            } while ((boolean) p.getAttribute("hasMsg") == false);
                        }*/
                        // newi = newi U aux
                       // System.out.println("aux"+aux);
                        //System.out.println("a.getAttribute(\"newi\")"+a.getAttribute("newi"));
                        a.setAttribute("newi", HashtableOperations.JoinSets((Hashtable) a.getAttribute("newi"), aux));
                    //}while(!edges.isEmpty() && !((ArrayList)a.getAttribute("com_with")).isEmpty());
                    
                    //inf = infi U newi
                    a.setAttribute("infi", HashtableOperations.JoinSets((Hashtable) a.getAttribute("infi"), (Hashtable) a.getAttribute("newi")));
                    break;
                case 2: //Asynchround shares new information to neighbors
                    //if newi is empty output has been calculated.
                    String[] mail = null;
                    if (((Hashtable) a.getAttribute("newi")).isEmpty()) {
                        //@TODO: I don't now if there are something to do
                        // by now this is empty
                        // System.out.println(a.getAttribute("ID") + "is emptyyy");
                        /*a.removeAttribute("newi");
                        a.setAttribute("newi", new Hashtable());
                        //System.out.println(a.getAttribute("ID") + " - " + a.getAttribute("newi"));
                        ArrayList nb = (ArrayList) a.getAttribute("com_with");
                        //System.out.println(a.getAttribute("ID") + ", nb: " + nb);
                        //TODO: Define if it is possible that a process send information to himself
                        ((ArrayList) a.getAttribute("com_with")).remove((String) a.getAttribute("ID"));

                        for (Object neighbors : nb) {
                            mail = new String[2]; //msg: [from|msg|channel]
                            mail[0] = (String) a.getAttribute("ID");
                            mail[1] = HashtableOperations.serializeHashtable((Hashtable) a.getAttribute("newi"));
                            mbuffer.get(neighbors.toString()).add(mail);
                        }*/
                    }else{
                        //round are counted for each new interchange of information
                        //a.setRound(a.getRound() + 1);
                        System.out.println(a.getAttribute("ID") + " - " + a.getAttribute("newi"));
                        ArrayList nb = (ArrayList) a.getAttribute("com_with");
                        System.out.println(a.getAttribute("ID") + ", nb: " + nb);
                        //TODO: Define if it is possible that a process send information to himself
                        ((ArrayList) a.getAttribute("com_with")).remove((String) a.getAttribute("ID"));

                        for (Object neighbors : nb) {
                            mail = new String[2]; //msg: [from|msg|channel]
                            mail[0] = (String) a.getAttribute("ID");
                            mail[1] = HashtableOperations.serializeHashtable((Hashtable) a.getAttribute("newi"));
                            mbuffer.get(neighbors.toString()).add(mail);
                        }
                        //removes newi because it was already shared
                        a.removeAttribute("newi");
                        a.setAttribute("newi", new Hashtable());
                        a.setRound(a.getRound() + 1);
                    }
                    break;
                default:
                    msg = "[Unknown action " + act
                            + ". Action not executed]";
                    System.out.println(msg);
                    break;
            }
        }
        return flag;
    }

    public Percept sense(Agent agent) {
        Process anAgent = (Process) agent;
        Percept p = new Percept();

        p.setAttribute("hasMsg", false);
        String[] nmsg = (String[]) (mbuffer.get((String) anAgent.getAttribute("ID")).poll());

        if (nmsg != null) {
            //System.out.println("sensa id:"+ anAgent.getAttribute("ID") +", nmsssg" + nmsg);
            anAgent.putMessage(nmsg);
            //p.setAttribute("message", nmsg[0]);
            p.setAttribute("hasMsg", true);
            //System.out.println("process:" + (String)anAgent.getAttribute("ID") + " nmsg:" + nmsg[0] + "from: " + nmsg[1]);        
        }
        if (anAgent.hasMessages()) {
            //aca puede haber un problema
            p.setAttribute("hasMsg", true);
            //p.remove("message");
        }

        p.setAttribute("neighbors", g.getNeighbors((String) anAgent.getAttribute("ID")));
        p.setAttribute("val", (Integer) anAgent.getAttribute("val"));
        p.setAttribute("round", (Integer) anAgent.getRound());
        return p;
    }

    public NetworkEnvironmentCuttingEdge(Vector<Agent> _agents, SimpleLanguage _language, Graph gr) {
        super(_agents);
        this.g = new SparseMultigraph();
        int n = _agents.size();
        for (int i = 0; i < n; i++) {
            Process ag = (Process) _agents.get(i);
            //System.out.println("creating buffer id" + ag.getAttribute("ID"));
            mbuffer.put((String) ag.getAttribute("ID"), new ConcurrentLinkedQueue());
        }
        language = _language;
        g = gr;
        date = new Date();
        r = new reportPajeFormat();
        r.addObserver(this);
    }

    public NetworkEnvironmentCuttingEdge(Agent agent, SimpleLanguage _language, Graph gr) {
        super(agent);
        this.g = gr;
        language = _language;
    }

    public NetworkEnvironmentCuttingEdge copy() {
        return new NetworkEnvironmentCuttingEdge(agents, language, g);
    }

    public Vector<Action> actions() {
        Vector<Action> acts = new Vector<Action>();
        int n = language.getActionsNumber();
        for (int i = 0; i < n; i++) {
            acts.add(new Action(language.getAction(i)));
        }
        return acts;
    }

    @Override
    public void init(Agent agent) {
        Process sim_agent = (Process) agent;
        //@TODO: Any special initialization processs of the environment
    }

    public String getLog() {
        return lastactionlog;
    }

    public void updateLog(String event, String log) {
        Date datenow = new Date();
        long diff = datenow.getTime() - date.getTime();
        //long diffSeconds = diff / 1000 % 60;
        lastactionlog = event + (String.valueOf(diff / 1000)) + " " + log;
        setChanged();
        notifyObservers();
    }

    private void returnOutput(String pid, Hashtable out) {
        controlBoard.getInstance().addOutput(pid, out);
    }

}
