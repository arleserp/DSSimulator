Distributed System Simulator

For chapter: Towards a Distributed Systems Model based on Multi-Agent Systems for Reproducing Self-properties

arlese.rodriguezp@konradlorenz.edu.co

This is the First Version of the distributed system simulator. 

In this version two main files are defined for use: ExperimentFacadeGenerate and ExperimentFacadeRunning in package unalcol.agents.distributed


ExperimentFacadeGenerate Generates a new experiment. By now, this class is not parametrized so it is required to uncomment depending of the simulation type you want. For example, to run a similar simulation like in the chapter "Towards a Distributed Systems Model based on Multi-Agent Systems for Reproducing Self-properties". It is only required to uncomment the lines that correspond to calculate minimum of a new generated network of processes.

public static void main(String[] argv) {
        /**
         * This is for create all an experiment uncomment depending what you
         * need.
         */
        FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerMinImpl();
        exp.createNew("min1000agents", 50, 150, 1000);
        // FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceRoutingImpl();
        // exp.createNew("testing_routing", 50, 100, 1);
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerSortingImpl();
        //exp.createNew("testing_sorting", 50, 100, 1);
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerAvgImpl();
        //exp.createNew("testing_average", 9, 100, 1);
    }
	
After editing you can run this file and obtain files necesary to execute the experiment.
	
ExperimentFacadeRunning executes an experiment previously created. It is not parametrized by now to run the experiment generated previously, uncomment the line corresponding to the experiment.

public class ExperimentFacadeRunning {

    public static void main(String[] argv) {
        
        String[] _percepts = {"Message", "Neighbors"};
        String[] _actions = {"Initialize", "Receive", "AsynchRound"};
        Language lang = new Language(_percepts, _actions);

        /**
         * This part is for loading and run an experiment previously created
         * Choose function F, Agent Program and Statistics filename Uncomment
         * according your needs.
         */
        FunctionComputingInterface F = new FunctionComputingInterfaceMinImpl();
        // FunctionComputingInterface F = new FunctionComputingInterfaceRoutingImpl();
        //FunctionComputingInterface F = new FunctionComputingInterfaceSortingImpl();
        //FunctionComputingInterface F = new FunctionComputingInterfaceAvgImpl();
        
        
        //Choose Agent Program
        double pf = 0;
        AgentProgram ap = new ProcessProgramRACrashesFailure(lang, pf);

        //Set filename for statistics 
        //String statsFilename = "sorting000005.csv" ;
        String statsFilename = "1000agentsMin.csv" ;
        //String statsFilename = "1000agentsAvg.csv" ;

        
        //Create Experiment
        FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerMinImpl();
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceRoutingImpl();
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerSortingImpl();
        //FileExperimentDAOInterface exp = new FileExperimentDAOInterfaceIntegerAvgImpl();
        exp.loadExp("min1000agents", F, ap, lang, statsFilename);
        //exp.loadExp("testing_routing", F, ap, lang, statsFilename);
        //exp.loadExp("testing_sorting", F, ap, lang, statsFilename);
       // exp.loadExp("testing_average", F, ap, lang, statsFilename);
    }
}

Once you comment the line you can execute the simulation and obtain an output like this:



filemin1000agents.network
hVertices:p10,p12,p11,p14,p13,p16,p15,p18,p17,p19,p21,p20,p23,p22,p25,p24,p27,p26,p29,p28,p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p30,p32,p31,p34,p33,p36,p35,p38,p37,p39,p41,p40,p43,p42,p45,p44,p47,p46,p49,p48
Edges:e685[p5,p15] e960[p45,p17] e686[p1,p11] e961[p31,p43] e200[p32,p5] e682[p6,p39] e728[p3,p43] e608[p5,p6] e968[p40,p24] e969[p10,p32] e966[p12,p32] e846[p1,p3] e967[p16,p42] e964[p24,p5] e965[p26,p6] e963[p22,p19] e950[p4,p10] e793[p13,p9] e671[p14,p31] e959[p45,p1] e958[p41,p5] e955[p35,p2] e956[p7,p3] e953[p22,p7] e954[p11,p29] e676[p16,p20] e797[p8,p16] e951[p42,p22] e677[p8,p29] e952[p3,p28] e740[p18,p17] e982[p2,p17] e463[p47,p39] e980[p21,p15] e981[p21,p42] e581[p2,p36] e909[p6,p46] e906[p28,p23] e907[p1,p40] e904[p40,p12] e627[p24,p6] e748[p14,p13] e869[p10,p42] e867[p2,p44] e744[p19,p13] e865[p17,p15] e624[p31,p20] e745[p26,p23] e864[p18,p47] e971[p33,p13] e851[p26,p42] e972[p30,p18] e694[p47,p42] e970[p45,p7] e7[p42,p31] e858[p38,p37] e979[p47,p13] e977[p25,p14] e857[p18,p6] e978[p2,p13] e854[p28,p19] e975[p49,p0] e734[p37,p21] e855[p5,p18] e976[p42,p38] e610[p13,p34] e731[p32,p6] e852[p31,p16] e973[p11,p33] e699[p17,p21] e974[p24,p13] e762[p47,p44] e883[p42,p15] e884[p21,p9] e760[p32,p19] e881[p21,p36] e761[p0,p36] e880[p27,p46] e809[p1,p13] e928[p21,p5] e926[p12,p21] e806[p46,p36] e927[p14,p6] e803[p19,p36] e924[p40,p29] e804[p31,p3] e925[p14,p22] e922[p6,p2] e802[p37,p39] e766[p16,p14] e887[p32,p38] e920[p21,p3] e800[p14,p7] e885[p2,p0] e765[p24,p23] e886[p6,p16] e873[p11,p32] e871[p12,p36] e470[p14,p21] e592[p19,p5] e192[p13,p18] e918[p38,p1] e519[p8,p23] e915[p46,p28] e916[p36,p22] e913[p47,p23] e914[p26,p37] e912[p22,p13] e876[p38,p14] e874[p30,p21] e782[p3,p37] e662[p2,p23] e783[p39,p14] e780[p2,p32] e260[p18,p32] e828[p11,p36] e946[p39,p3] e947[p9,p23] e823[p19,p43] e703[p10,p5] e821[p19,p18] e942[p13,p29] e822[p46,p18] e943[p31,p18] e786[p2,p37] e940[p26,p3] e941[p34,p21] e773[p42,p19] e895[p10,p46] e496[p34,p5] e892[p9,p6] e893[p3,p13] e890[p36,p5] e770[p3,p42] e891[p17,p44] e937[p29,p21] e938[p9,p44] e935[p11,p25] e815[p36,p16] e936[p18,p26] e934[p34,p40] e777[p3,p0] e810[p6,p47] e898[p34,p19] e931[p45,p28] e811[p38,p19] e932[p44,p13] e776[p21,p47] e897[p0,p43] e930[p7,p38] 
input: 518, for agent p0
input: 710, for agent p1
input: 219, for agent p20
input: 147, for agent p28
input: 84, for agent p48
input: 555, for agent p21
input: 452, for agent p49
input: 555, for agent p44
input: 943, for agent p40
input: 698, for agent p36
input: 193, for agent p32
input: 864, for agent p45
input: 765, for agent p41
input: 320, for agent p37
input: 425, for agent p33
input: 861, for agent p29
input: 4, for agent p25
input: 328, for agent p24
input: 595, for agent p13
control board:{p48={}}
Die.p48
input: 260, for agent p7
input: 787, for agent p3
input: 838, for agent p8
input: 454, for agent p4
input: 67, for agent p12
input: 522, for agent p16
input: 845, for agent p27
input: 182, for agent p5
input: 389, for agent p9
input: 518, for agent p17
input: 861, for agent p26
input: 144, for agent p2
input: 408, for agent p31
input: 140, for agent p6
input: 343, for agent p35
input: 139, for agent p14
input: 954, for agent p38
input: 454, for agent p11
input: 120, for agent p39
input: 572, for agent p19
input: 462, for agent p15
input: 233, for agent p18
input: 945, for agent p10
input: 521, for agent p34
input: 417, for agent p30
input: 168, for agent p43
input: 121, for agent p42
input: 491, for agent p46
input: 812, for agent p47
input: 52, for agent p23
input: 37, for agent p22

Die.p7
control board:{p29={p29=4}, p28={p28=4}, p27={p27=4}, p26={p26=4}, p25={p25=4}, p24={p24=4}, p23={p23=4}, p22={p22=4}, p21={p21=4}, p20={p20=4}, p19={p19=4}, p18={p18=4}, p17={p17=4}, p49={p49=4}, p16={p16=4}, p48={}, p15={p15=4}, p47={p47=4}, p14={p14=4}, p46={p46=4}, p13={p13=4}, p45={p45=4}, p12={p12=4}, p44={p44=4}, p11={p11=4}, p43={p43=4}, p10={p10=4}, p42={p42=4}, p41={p41=4}, p40={p40=4}, p39={p39=4}, p38={p38=4}, p37={p37=4}, p36={p36=4}, p35={p35=4}, p34={p34=4}, p9={p9=4}, p33={p33=4}, p8={p8=4}, p32={p32=4}, p31={p31=4}, p7={p7=4}, p6={p6=4}, p30={p30=4}, p5={p5=4}, p4={p4=4}, p3={p3=4}, p2={p2=4}, p1={p1=4}, p0={p0=4}}
Die.p35
Die.p46
the end
stats: {wrong=0, right=49}
Statistics were exported to:1000agentsMin.csv format right_agents|wrong_agents|agents_without_response

In the main folder of simulation you can obtain the output of the experiment like in the last line of the output in csv format.