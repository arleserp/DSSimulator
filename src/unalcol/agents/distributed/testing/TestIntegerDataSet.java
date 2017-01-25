package unalcol.agents.distributed.testing;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Arles Rodriguez
 */
public class TestIntegerDataSet {

    public static void main(String[] argv) {
        GenerateIntegerDataSet g = new GenerateIntegerDataSet(50, 100, 5);
        //g.saveFile("input.txt");
        g = g.loadFile("input.txt");
        System.out.println(g.getNext());
        System.out.println(g.getNext());
        System.out.println(g.getNext());
        System.out.println(g.getNext());
        System.out.println(g.getNext());
        System.out.println(g.getNext());
        System.out.println("d" + g.toString());
    }

}
