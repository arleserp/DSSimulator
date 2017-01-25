package unalcol.agents.distributed;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;

import unalcol.agents.simulate.util.*;
import unalcol.agents.*;
import unalcol.agents.simulate.*;

import java.util.Vector;

import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections15.Transformer;

public class NetworkEnvironmentCEBookOpt extends Environment {
    public static String msg = null;
    public int[][] structure = null;
    public SimpleLanguage language = null;
    Date date;
    reportPajeFormat r;
    Graph<String, String> g;
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
            if (currentNode != null && currentNode.equals(i)) {
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
        // vv.repaint();
        String log;
        if (flag) {
            //Agents can be put to Sleep for some ms
            //sleep is good is graph interface is on
            //agent.sleep(500);

            String act = action.getCode();
            // Percept p = sense(a);
            Percept p;
            String msg = null;

            /**
             * 0- "Initialize" 1- "Receive" 2- "AsynchRound"
             *
             */
            /* @TODO: Detect Stop Conditions for the algorithm */
            switch (language.getActionIndex(act)) {
                case 0: // Initialize
                    //Load parameters of input
                    //Data 
                    Hashtable<String, Integer> newi = new Hashtable<>();
                    Hashtable<String, Integer> outi = new Hashtable<>();
                    Hashtable<String, Integer> infi = new Hashtable<>();
                    Hashtable<String, Integer> rchan = new Hashtable<>();

                    Hashtable outtmpi = new Hashtable();
                    Hashtable<String, Integer> recv = new Hashtable<>();

                    newi.put((String) a.getAttribute("ID"), (int) a.getRound());
                    infi.put((String) a.getAttribute("ID"), (int) a.getRound());

                    a.setAttribute("newi", newi);
                    a.setAttribute("outi", outi);
                    a.setAttribute("infi", infi);
                    a.setAttribute("outtmpi", outtmpi);
                    a.setAttribute("rchan", rchan);

                    //pendiente: volver esto percepción.
                    ArrayList<String> ch_in = new ArrayList(g.getOutEdges((String) a.getAttribute("ID")));
                    ArrayList<String> ch_out = new ArrayList(g.getOutEdges((String) a.getAttribute("ID")));

                    //Processes remove themselves
                    ch_in.remove((String) a.getAttribute("ID"));
                    ch_out.remove((String) a.getAttribute("ID"));
                    Hashtable recv_ch = new Hashtable();

                    //Collection nbors = (Collection) p.getAttribute("neighbors");
                    //ArrayList nbor = new ArrayList(nbors);
                    a.setAttribute("ch_in", ch_in);
                    a.setAttribute("ch_out", ch_out);
                    a.setAttribute("recv_ch", recv_ch);

                    a.setRound(a.getRound() + 1);
                    break;
                case 2: //Asynchround shares new information to neighbors
                    //if newi is empty output has been calculated.
                    if (!(((ArrayList) (a.getAttribute("ch_in"))).isEmpty()) || !(((ArrayList) (a.getAttribute("ch_out"))).isEmpty())) {
                        a.setRound(a.getRound() + 1); //TODO: we could have problems with this way of counting rounds.
                        String[] mail = null;
                        //((ArrayList) a.getAttribute("com_with")).remove((String) a.getAttribute("ID"));
                        ArrayList<String> chanlsout = new ArrayList((ArrayList) a.getAttribute("ch_out"));
                        ArrayList<String> chanlsin = (ArrayList) a.getAttribute("ch_in");
                        Hashtable send = new Hashtable();

                        for (Object chan : (ArrayList) a.getAttribute("ch_out")) {
                            if (chanlsin.contains((String) chan)) {
                                Hashtable diff = HashtableOperations.DifferenceSets((Hashtable) a.getAttribute("newi"), (Hashtable) ((Hashtable) a.getAttribute("recv_ch")).get(chan));
                                send.put(chan, diff);
                                System.out.println(a.getAttribute("ID") + ": send" + (Hashtable) diff + ", r" + a.getRound() + ", ch:" + chan);
                            } else {
                                send.put(chan, (Hashtable) a.getAttribute("newi"));
                                System.out.println(a.getAttribute("ID") + ": send" + (Hashtable) a.getAttribute("newi") + ", r" + a.getRound() + ", ch:" + chan);
                            }

                            mail = new String[3]; //msg: [from|msg|channel]
                            mail[0] = (String) a.getAttribute("ID");
                            mail[1] = HashtableOperations.serializeHashtable((Hashtable) send.get(chan));
                            mail[2] = chan.toString();
                            String neigh = g.getOpposite((String) a.getAttribute("ID"), chan.toString());
                            // System.out.println(a.getAttribute("ID") + "neeeeig" + neigh);
                            // System.out.println("cola" + mbuffer.get(neigh));
                            //synchronized (counterLock) {
                            //    mbuffer.get(neigh).add(mail);
                            // }
                            NetworkMessageBuffer.getInstance().putMessage(neigh, mail);
                            //System.out.println("exception" + ex.getMessage());
                            //Logger.getLogger(NetworkEnvironmentCEBookOpt.class.getName()).log(Level.SEVERE, null, ex);

                            if (((Hashtable) send.get(chan)).isEmpty()) {
                                chanlsout.remove((String) chan);
                                // a.setAttribute("ch_out", chanlsout);
                            }
                        }
                        a.setAttribute("ch_out", chanlsout);

                        a.setAttribute("send", send);
                        System.out.println("send" + send + "id" + a.getAttribute("ID"));
                        //removes newi because it was already shared
                        a.removeAttribute("newi");
                        a.setAttribute("newi", new Hashtable());
                    } else {
                        System.out.println("Die." + a.getAttribute("ID"));
                        a.die();
                        // break;
                    }
                //break;
                case 1: // Receive
                    ArrayList<String> chanin = new ArrayList(((ArrayList) a.getAttribute("ch_in")));
                    ArrayList<String> chanout = new ArrayList(((ArrayList) a.getAttribute("ch_out")));
                    ArrayList<String> chanintmp = new ArrayList(((ArrayList) a.getAttribute("ch_in")));

                    while (!chanintmp.isEmpty()) {
                        if (!chanintmp.isEmpty()) {
                            while (!a.hasMessages()) {
                                p = sense2(a);
                                a.sleep(10);
                                System.out.println(a.getAttribute("ID") + " wait." + "cha_intmp" + chanintmp);
                            }
                        }

                        String[] mailn = new String[3];
                        String[] s = (String[]) a.getMessage();
                        //System.out.println("s" + s[1]);
                        String from = s[0];
                        String msgr = s[1];
                        String ch = s[2];
                        Hashtable<String, Integer> aux = null;
                        //System.out.println("msgr"  + msgr);
                        Hashtable newh = HashtableOperations.deserializeHashtable(msgr);
                        System.out.println(a.getAttribute("ID") + ", newh: " + newh);
                        Hashtable tmpout;
                        //System.out.println(a.getAttribute("ID") + ", chanin antes: " + chanintmp);
                        //System.out.println("ch" + ch);
                        chanintmp.remove(ch);
                        //System.out.println(a.getAttribute("ID") + ", chanin despues: " + chanintmp);

                        if (chanout.contains(ch)) {
                            Hashtable send = ((Hashtable) ((Hashtable) a.getAttribute("send")).get(ch));
                            System.out.println(a.getAttribute("ID") + ", send: " + ((Hashtable) a.getAttribute("send")) + "vs newh" + newh + " ch: " + ch);
                            /*if (send == null) {
                             send = new Hashtable();
                             }*/
                            if (HashtableOperations.isContained(send, newh)) {
                                System.out.println("sendi is contained in recvi");
                                chanout.remove(ch);

                            }
                            if (HashtableOperations.isContained(newh, send)) {
                                System.out.println("sendi is contained in recvi");
                                chanin.remove(ch);
                            }
                        } else {
                            System.out.println(a.getAttribute("ID") + ": ch " + ch + " is not in chanout......");

                        }
                        if (newh.isEmpty()) {
                            chanin.remove(ch);
                        }

                        //Modify hashtables on agent
                        a.setAttribute("ch_in", chanin);
                        a.setAttribute("ch_out", chanout);

                        ((Hashtable) a.getAttribute("recv_ch")).put(ch, newh);

                        //This is for calculate routing tables, so we get the channel
                        //aux = newh \infi U newi;
                        aux = HashtableOperations.DifferenceSets(newh,
                                HashtableOperations.JoinSets((Hashtable) a.getAttribute("newi"), (Hashtable) a.getAttribute("infi")));

                        for (Iterator<String> iterator = newh.keySet().iterator(); iterator.hasNext();) {
                            String key = iterator.next();
                            //System.out.println("Id: " + a.getAttribute("ID") + ", round: " + a.getRound() + "vs Id from:" + newh + ", round: " + (int)newh.get(key));
                            if (!((String) key).equals((String) a.getAttribute("ID"))) {
                                if (((Hashtable) (a.getAttribute("outi"))).get(key) == null) {
                                    tmpout = new Hashtable();
                                } else {
                                    tmpout = (Hashtable) ((Hashtable) (a.getAttribute("outi"))).get(key);
                                }
                                //System.out.println(a.getAttribute("ID") + "r:" + a.getRound());
                                // if (tmpout.containsKey(ch)) {
                                //     System.out.println("ya estaba");
                                // } else {
                                tmpout.put(ch, a.getRound());
                                //}

                                Hashtable tmprel = ((Hashtable) (a.getAttribute("rchan")));
                                System.out.println("1.temprel" + tmprel);
                                System.out.println("1.tmpout" + tmpout);
                                tmprel = HashtableOperations.calculateR(key, tmprel, tmpout);
                                a.log("2.temprel" + tmprel);
                                a.setAttribute("rchan", tmprel);
                                
                                a.log("is rel:" + HashtableOperations.isCutP(tmprel));
                                
                                ((Hashtable) (a.getAttribute("outtmpi"))).put((String) a.getAttribute("ID"), HashtableOperations.isCutP(tmprel));

                                ((Hashtable) (a.getAttribute("outi"))).put(key, tmpout);
                                System.out.println(a.getAttribute("ID") + "out:" + ((Hashtable) (a.getAttribute("outi"))));
                                //returnOutput((String) a.getAttribute("ID"), (Hashtable) a.getAttribute("outi"));   
                                returnOutput((String) a.getAttribute("ID"), (Hashtable) a.getAttribute("outtmpi"));
                                //This is a new output to the control board. 
                                // System.out.println(a.getAttribute("ID") + " is biconnected: " + HashtableOperations.isBiconnected((Hashtable) (a.getAttribute("outi"))));
                                //}
                            }
                        }

                        // newi = newi U aux
                        //a.setAttribute("newi", HashtableOperations.JoinSets((Hashtable)a.getAttribute("newi"), aux));
                        Hashtable temp = HashtableOperations.DifferenceSets(newh, (Hashtable) a.getAttribute("infi"));
                        /*System.out.println(a.getAttribute("ID") + "infiiiii" + a.getAttribute("infi"));
                         System.out.println(a.getAttribute("ID") + "newhhhh" + newh);
                         System.out.println(a.getAttribute("ID") + "temp" + temp);
                         System.out.println(a.getAttribute("ID") + "newi" + a.getAttribute("newi"));
                         */
                        a.setAttribute("newi", HashtableOperations.JoinSets((Hashtable) a.getAttribute("newi"), temp));
                        //System.out.println(a.getAttribute("ID") + " end. " + (Hashtable) a.getAttribute("newi"));
                    }
                    //inf = infi U newi
                    a.setAttribute("infi", HashtableOperations.JoinSets((Hashtable) a.getAttribute("infi"), (Hashtable) a.getAttribute("newi")));
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

    public Percept sense2(Agent agent) {
        Process anAgent = (Process) agent;
        Percept p = new Percept();

        p.setAttribute("hasMsg", false);
        //String[] nmsg = (String[]) (mbuffer.get((String) anAgent.getAttribute("ID")).poll());
        String[] nmsg = null;
        nmsg = NetworkMessageBuffer.getInstance().getMessage((String) anAgent.getAttribute("ID"));

        if (nmsg != null) {
            //System.out.println("sensa id:" + anAgent.getAttribute("ID") + ", nmsssg" + nmsg);
            anAgent.putMessage(nmsg);
            p.setAttribute("message", nmsg[0]);
            p.setAttribute("hasMsg", true);
            //System.out.println("process:" + (String) anAgent.getAttribute("ID") + " nmsg:" + nmsg[0] + "from: " + nmsg[1]);
        }
        if (anAgent.hasMessages()) {
            p.setAttribute("hasMsg", true);
        }

        p.setAttribute("neighbors", g.getNeighbors((String) anAgent.getAttribute("ID")));
        p.setAttribute("val", (Integer) anAgent.getAttribute("val"));
        p.setAttribute("round", (Integer) anAgent.getRound());
        return p;
    }

    public Percept sense(Agent agent) {
        Process anAgent = (Process) agent;
        Percept p = new Percept();
        p.setAttribute("round", (Integer) anAgent.getRound());
        return p;
    }

    public NetworkEnvironmentCEBookOpt(Vector<Agent> _agents, SimpleLanguage _language, Graph gr) {
        super(_agents);
        this.g = new SparseMultigraph();
        int n = _agents.size();
        for (int i = 0; i < n; i++) {
            Process ag = (Process) _agents.get(i);
            //System.out.println("creating buffer id" + ag.getAttribute("ID"));
            NetworkMessageBuffer.getInstance().createBuffer((String) ag.getAttribute("ID"));
        }
        language = _language;
        g = gr;
        date = new Date();
        r = new reportPajeFormat();
        r.addObserver(this);
        //System.out.println("creaaaaaaaaaaa!!");
    }

    public NetworkEnvironmentCEBookOpt(Agent agent, SimpleLanguage _language, Graph gr) {
        super(agent);
        this.g = gr;
        language = _language;
    }

    public NetworkEnvironmentCEBookOpt copy() {
        return new NetworkEnvironmentCEBookOpt(agents, language, g);
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

    /*public void updateLog(String event, String log) {
     Date datenow = new Date();
     long diff = datenow.getTime() - date.getTime();
     //long diffSeconds = diff / 1000 % 60;
     lastactionlog = event + (String.valueOf(diff / 1000)) + " " + log;
     setChanged();
     notifyObservers();
     }*/
    private void returnOutput(String pid, Hashtable out) {
        controlBoard.getInstance().addOutput(pid, out);
    }
}
