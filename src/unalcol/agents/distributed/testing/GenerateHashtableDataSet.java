/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.distributed.testing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import unalcol.agents.distributed.GraphSerialization;
import unalcol.agents.distributed.HashtableOperations;
import unalcol.agents.distributed.controlBoard;

/**
 *
 * @author Arles Rodriguez
 */
public class GenerateHashtableDataSet {
    
    private String filename;
    
    public void saveOutput() {
        try {
            OutputStream file = new FileOutputStream(filename + ".outok");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(buffer);
                oos.writeObject(controlBoard.getInstance().getOut());
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(HashtableOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Hashtable loadOutput(String filename) {
        InputStream file;
        Hashtable h = null;
        try {
            file = new FileInputStream(filename);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            h = (Hashtable) input.readObject();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GraphSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GraphSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return h;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
