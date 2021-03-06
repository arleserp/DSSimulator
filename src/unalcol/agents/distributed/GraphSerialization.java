/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.distributed;

import edu.uci.ics.jung.graph.Graph;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arles Rodriguez
 */
public class GraphSerialization {

    public static String serialize(Graph h) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        String s = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(h);
            oos.close();
            //baos.close();
            s = baos.toString("ISO-8859-1");
            //System.out.println("sb:" + s.getBytes("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(HashtableOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public static Graph deserialize(String s) {
        Graph h = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes("ISO-8859-1"));
            ObjectInputStream ois = new ObjectInputStream(bais);
            h = (Graph) ois.readObject();
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(HashtableOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HashtableOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return h;
    }

    public static void saveSerializedGraph(String filename, Graph g) {
        try {
            OutputStream file = new FileOutputStream(filename);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(buffer);
                oos.writeObject(g);
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(HashtableOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Graph loadDeserializeGraph(String filename) {
        InputStream file;
        Graph h = null;
        System.out.println("file" + filename);
        try {
            file = new FileInputStream(filename);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            h  = (Graph) input.readObject();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GraphSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GraphSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("h" + h.toString());
        return h;
    }

}
