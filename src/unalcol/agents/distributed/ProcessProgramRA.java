package unalcol.agents.distributed;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;

public class ProcessProgramRA implements AgentProgram {

    private Language language;

    public ProcessProgramRA(Language l) {
        this.language = l;
    }

    public Action compute(Percept p) {
        //compute
        ActionParameters act = null;

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
