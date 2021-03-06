package JGraphLoader;

/*
** GraphMLDemo
** Copyright (C) 2011, Matt Johnson
**
** vertexPainter.java (Author(s): Matt Johnson)
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

import GraphMLDemo.node;
import org.apache.commons.collections15.Transformer;

import java.awt.*;

//Here we want to dictate how our nodes are painted. The Metadata which we are reading in from our GraphML file is a color
// value. So we write a vertex painter which we will pass to our VisualizationViewer. This way when Jung goes to draw our
// nodes, it will check this function and paint the nodes accordingly.

class vertexPainter implements Transformer<node, Paint>
{
    public Paint transform(node v) //So for each node that we draw...
    {
        //We check the member variable, mColor, of the node.
        if (v.getColor().equalsIgnoreCase("yellow")) //If the node's mColor value is "yellow" we...
            return (Color.yellow); // Return our color, Color.yellow.
        else if (v.getColor().equalsIgnoreCase("red"))
            return (Color.red);
        else if (v.getColor().equalsIgnoreCase("blue"))
            return (Color.blue);
        else if (v.getColor().equalsIgnoreCase("green"))
            return (Color.green);
        else
            return (Color.MAGENTA);
    }
}
