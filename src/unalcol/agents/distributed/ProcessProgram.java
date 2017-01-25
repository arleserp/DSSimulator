package unalcol.agents.distributed;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;


public class ProcessProgram implements AgentProgram {

    private Language language;


    /*public Process (Language language){
     this.language=language;
     //if(this.decision ==null){
     //	this.decision = (Learning) DiscoverSingleton.find( Learning.class, "unalcol.agents.nn.WeightDilemmaNn");
     //}
     }*/
    public ProcessProgram(Language l) {
        //super(this);
        this.language = l;
    }

    public Action compute(Percept p) {
        //compute
        //Integer val = (Integer) p.getAttribute("val");
        //ArrayList l = new ArrayList(n);
        ActionParameters act = null;


       
        //reads the round number
        int r = (Integer) p.getAttribute("round");
        if (r == -1) { //round 1 means initialize
            act = new ActionParameters("Initialize");
        } else {
        /*    if ((boolean) p.getAttribute("hasMsg") == true) {
               act = new ActionParameters("Receive");
                return act;
            }else{ */
                act = new ActionParameters("AsynchRound");
         //   }
            return act;
        }
        return act;
    }
    
    @Override
    public void init() {
       
        // TODO Auto-generated method stub

    }

}
