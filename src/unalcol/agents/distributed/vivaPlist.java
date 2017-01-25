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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arles Rodriguez
 */
public class vivaPlist {
    
   public void vivaPlist(){
       PrintWriter escribir = null;
       try {
           String filename  = getFileName() + "ds.trace";
           escribir = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
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
           Logger.getLogger(vivaPlist.class.getName()).log(Level.SEVERE, null, ex);
       } finally {
           escribir.close();
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
}
