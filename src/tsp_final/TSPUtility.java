/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_final;

import java.util.ArrayList;

/**
 *
 * @author ayonk
 */
public class TSPUtility {

    public static void sort(ArrayList<Particle> particleList) {
        boolean flag = false;
        while (!flag) {
            int no_of_changes = 0;
            int size_of_list = particleList.size();
            for (int i = 0; i < size_of_list - 1; i++) {
                if (particleList.get(i).compareTo(particleList.get(i + 1)) == 1) {
                    Particle temp_var = particleList.get(i);
                    particleList.set(i, particleList.get(i + 1));
                    particleList.set(i + 1, temp_var);
                    no_of_changes++;
                }
            }
            if (no_of_changes == 0) {
                flag = true;
            }
        }

    }

    public static double getDistance(int city1, int city2, ArrayList<City> map) {
        City c1 = null;
        City c2 = null;
        double a1 = 0;
        double b1 = 0;

        c1 = map.get(city1);
        c2 = map.get(city2);

        a1 = Math.pow(Math.abs(c1.getX() - c2.getX()), 2);
        b1 = Math.pow(Math.abs(c1.getY() - c2.getY()), 2);

        return Math.sqrt(a1 + b1);
    }
}
