package JGraphLoader;

/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 * 
 */
/*
** GraphMLDemo
** Copyright (C) 2011, Matt Johnson
**
** Main.java (Author(s): Matt Johnson)
** 
** Permission is hereby granted, free of charge, to any person obtaining a
** copy of this software and associated documentation files (the "Software"),
** to deal in the Software without restriction, including without limitation
** the rights to use, copy, modify, merge, publish, distribute, sublicense,
** and/or sell copies of the Software, and to permit persons to whom the
** Software is furnished to do so, subject to the following conditions:
**
** The above copyright notice and this permission notice shall be included
** in all copies or substantial portions of the Software. Changes in the
** copyright notice and/or disclaimer are illegal.
**
** THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
** OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
** FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL
** ANY MEMBER OF UNCC'S GAME LAB BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
** WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
** CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

import GraphMLDemo.edge;
import edu.uci.ics.jung.graph.UndirectedGraph;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import GraphMLDemo.edge;

public class Main
{
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
    	String filename = "holaaa.graphml";
    	if(args.length > 0)
            filename = args[0];
        UndirectedGraph g = (UndirectedGraph) GraphML.load(filename);
        
           // Just as we added the vertices to the graph, we add the edges as well.
        //for (Object e : g.getEdges()) {
           // e.setValue(edge_meta.get("d1").transformer.transform(e)); //Set the edge's value.
        //    ((edge)e).setValue("e" + ((edge)e).getID());
            //System.out.println("Edge ID: " + ((edge)e).getID() + ", Value: " + e.getValue());
        //}

        System.out.println("g" + g);
        
        System.out.println("out edges n1:" + g.getOutEdges("n5"));
        
        
        GraphSaver.saveG("chau.graphml", g);
        
    }
}