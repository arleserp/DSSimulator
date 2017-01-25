package unalcol.agents.distributed;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;

public class ProcessProgramRACrashesFailure implements AgentProgram {
    private Language language;
    private double probFailure;

    public ProcessProgramRACrashesFailure(Language l) {
        this.language = l;
        this.probFailure = 0;
    }

    public ProcessProgramRACrashesFailure(Language l, double pf) {
        this.language = l;
        this.probFailure = pf;
    }
    
    @Override
    public Action compute(Percept p) {
        //compute
        ActionParameters act;

        //Crash failure
        if (Math.random() < probFailure) {
            return new ActionParameters("Die");
        }

        //reads the round number
        int r = (Integer) p.getAttribute("round");
        if (r == -1) { //round -1 means initialize
            act = new ActionParameters("Initialize");
        } else if ((boolean) p.getAttribute("hasMsg") == true) {
            act = new ActionParameters("Receive");
        } else {
            act = new ActionParameters("AsynchRound");
        }
        return act;
    }

    @Override
    public void init() {

        // TODO Auto-generated method stub
    }

}
