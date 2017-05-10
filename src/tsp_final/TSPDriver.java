/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_final;

/**
 *
 * @author ayonk
 */
public class TSPDriver {

    public static void main(String[] args) {

        TSP tspObject = new TSP();
        
        tspObject.initialization();
        tspObject.psoProcess();
        tspObject.print();
    }
}
