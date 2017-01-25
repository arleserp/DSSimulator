/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.distributed;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arles Rodriguez
 */
public class StatisticEngineInterfaceIntegerMinImpl implements StatisticEngineInterface {

    private int expectedRes;
    private String reportFile;

    Hashtable getStatisticsInteger() {
        Hashtable Statistics = new Hashtable();
        Hashtable outtmp = new Hashtable(controlBoard.getInstance().getOut());
        int right = 0;
        int wrong = 0;

        for (Iterator<Hashtable> iterator = outtmp.keySet().iterator(); iterator.hasNext();) {
            Hashtable n = (Hashtable) outtmp.get(iterator.next());
            //System.out.println("n" + n);
            Object[] tmp = (n.values()).toArray();
            Integer[] tmpvalues = Arrays.copyOf(tmp, tmp.length, Integer[].class);
            for (Integer tmpvalue : tmpvalues) {
                if (tmpvalue == expectedRes) {
                    right++;
                } else {
                    wrong++;
                }
            }
        }

        Statistics.put("right", right);
        Statistics.put("wrong", wrong);

        System.out.println("stats: " + Statistics);
        return Statistics;
    }

    public void printStatistics(int n) {
        Hashtable st = getStatisticsInteger();
        try {
            int nr = n - ((Integer) st.get("right") + (Integer) st.get("wrong"));
            //filename = getFileName() + "ds.trace";
            PrintWriter escribir;
            escribir = new PrintWriter(new BufferedWriter(new FileWriter(getReportFile(), true)));
            escribir.println(st.get("right") + "," + st.get("wrong") + "," + nr);
            escribir.close();
        } catch (IOException ex) {
            Logger.getLogger(NetworkEnvironment.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Statistics were exported to:" + getReportFile() + " format right_agents|wrong_agents|agents_without_response");
    }

    /**
     * @return the reportFile
     */
    public String getReportFile() {
        return reportFile;
    }

    /**
     * @param reportFile the reportFile to set
     */
    @Override
    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }

    @Override
    public void setExpectedRes(Object res) {
        this.expectedRes = (Integer) res;
    }

}
