package unalcol.agents.distributed;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import unalcol.agents.distributed.NetworkEnvironment;

/**-
 *
 * @author Arles
 */
public class reportPajeFormat implements Observer {
    String filename;
    
    public reportPajeFormat() {
        try {
            filename  = getFileName() + "ds.trace";
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            String  reportheader = "%EventDef SendMessage 21\n" +
                "% Time date\n" +
                "% ProcessId String\n" +
                "% Receiver String\n" +
                //"% Size int\n" +
                "%EndEventDef";              
            escribir.println(reportheader);
            System.out.println("report" + reportheader);
            escribir.close();
        } catch (IOException ex) {
            Logger.getLogger(NetworkEnvironment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getFileName() {
        Calendar c = new GregorianCalendar();
        String dia, mes, annio, hora, minutos, segundos;
        dia = Integer.toString(c.get(Calendar.DATE));
        mes = Integer.toString(c.get(Calendar.MONTH)+1);
        annio = Integer.toString(c.get(Calendar.YEAR));
        hora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        minutos = Integer.toString(c.get(Calendar.MINUTE));
        segundos = Integer.toString((c.get(Calendar.SECOND)));
        return annio + mes + dia + hora + minutos + segundos;
    }

    /*public void printReport(int failuresTermite, int vt) {
        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
//            escribir.println("Initial Termites with Failures" + arrInitialFailureInduced.size());
//            escribir.println("Initial Termites damaged by bad diagnosis:" + ((int)(arrEvoMutations.size() - arrInitialFailureInduced.size())));
//            escribir.println("Termites healed" + arrHealedTermites.size());
            //escribir.println(pop + ";" + arrInitialFailureInduced.size() + ";" + ((int) (arrEvoMutations.size() - arrInitialFailureInduced.size())) + ";" + arrHealedTermites.size() + ";" + iteration + ";" + food);

            String  report = pop + "," + arrInitialFailureInduced.size() + "," + sickEnd + "," + iteration + "," + food + "," + failuresTermite + "," + vt;
            escribir.println(report);
            System.out.println("report" + report);
            escribir.close();
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(getFileName() + ".healediteration-popsize-" + pop + "-fprob-" + fProb + ".csv", true)));
//            escribir.println("Initial Termites with Failures" + arrInitialFailureInduced.size());
//            escribir.println("Initial Termites damaged by bad diagnosis:" + ((int)(arrEvoMutations.size() - arrInitialFailureInduced.size())));
//            escribir.println("Termites healed" + arrHealedTermites.size());

            for (int i = 0; i < arrIteHealing.size(); i++) {
                escribir.println(arrIteHealing.get(i) + ";");
            }
            escribir.close();
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }


//        try {
//            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(getFileName() + "healedids.csv", true)));
//            escribir.println(pop + ";" + arrInitialFailureInduced.size() + ";" + ((int) (arrEvoMutations.size() - arrInitialFailureInduced.size())) + ";" + arrHealedTermites.size());
//            escribir.close();
//        } catch (IOException ex) {
//            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
//        }


    }*/

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof NetworkEnvironment) {
            NetworkEnvironment t = (NetworkEnvironment) obs;
            //System.out.println("this.id" + t.getLastIdInduceFailure());
           // System.out.println("log:" + t.getLog());
            try {
                //filename = getFileName() + "ds.trace";
                PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
                escribir.println(t.getLog());
                escribir.close();
            } catch (IOException ex) {
                Logger.getLogger(NetworkEnvironment.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public void addObserver(NetworkEnvironment network) {
        network.addObserver(this);
    }
    
    public void addObserver(NetworkEnvironmentMax network) {
        network.addObserver(this);
    }

    public void addObserver(NetworkEnvironmentMerge network) {
        network.addObserver(this);
    }
    
    public void addObserver(NetworkEnvironmentCuttingEdge network) {
        network.addObserver(this);
    }
    
    public void addObserver(NetworkEnvironmentCE2 network) {
        network.addObserver(this);
    }
    
    public void addObserver(NetworkEnvironmentCEBook network) {
        network.addObserver(this);
    }
    
    public void addObserver(NetworkEnvironmentCEBookOpt network) {
        network.addObserver(this);
    }
}

