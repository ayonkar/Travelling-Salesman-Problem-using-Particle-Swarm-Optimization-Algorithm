/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_final;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ayonk
 */
public class TSP implements TSPConstants {

    ArrayList<Particle> particleList = new ArrayList<Particle>();
    ArrayList<City> cityList = new ArrayList<City>();

    int[] xLocation = new int[]{31, 39, 39, 30, 18, 10, 10, 21};
    int[] yLocation = new int[]{6, 11, 19, 24, 24, 20, 10, 5};
    
    //int[] xLocation = new int[]{30, 40, 40, 29, 19, 9, 9, 20};
    //int[] yLocation = new int[]{5, 10, 20, 25, 25, 19, 9, 5};

    public void initialization() {
        for (int i = 0; i < COUNT_OF_CITY; i++) {
            City c = new City();
            c.setX(xLocation[i]);
            c.setY(yLocation[i]);
            cityList.add(c);
        }

    }

    public void psoProcess() {
        Particle p = null;
        int epoch = 0;
        boolean flag = false;

        initializing();

        while (!flag) {
            //  This loop will be terminated by the below two conditions
            //    if the maximum number of epochs has been reached
            //   or,the Target value is found
            
            if (epoch < MAXIMUM_EPOCHS) {

                for (int i = 0; i < COUNT_OF_PARTICLES; i++) {
                    p = particleList.get(i);
                    System.out.print("Path -> ");
                    for (int j = 0; j < COUNT_OF_CITY; j++) {
                        System.out.print(p.getData(j) + ",");
                    }

                    calculateTotalDistance(i);
                    System.out.print("Path Covered(dist) " + p.getParticleBest());
                    System.out.println("\n");
                    if (p.getParticleBest() <= TARGET) {
                        flag = true;
                    }
                }

                TSPUtility.sort(particleList);

                calculateVelocity();

                calculateParticleUpdation();

                System.out.println("Epoch Count: " + epoch);

                epoch++;

            } else {
                flag = true;
            }
        }

    }

    public void initializing() {
        for (int i = 0; i < COUNT_OF_PARTICLES; i++) {
            Particle newParticle = new Particle();
            for (int j = 0; j < COUNT_OF_CITY; j++) {
                newParticle.setData(j, j);
            }
            particleList.add(newParticle);
            for (int j = 0; j < COUNT_OF_PARTICLES; j++) {
                arrangeInRandomWay(particleList.indexOf(newParticle));
            }
            calculateTotalDistance(particleList.indexOf(newParticle));
        }

    }

    private void arrangeInRandomWay(int index) {
        int city1 = new Random().nextInt(COUNT_OF_CITY);
        int city2 = 0;
        boolean flag = false;
        while (!flag) {
            city2 = new Random().nextInt(COUNT_OF_CITY);
            if (city2 != city1) {
                flag = true;
            }
        }

        int temp_var = particleList.get(index).getData(city1);
        particleList.get(index).setData(city1, particleList.get(index).getData(city2));
        particleList.get(index).setData(city2, temp_var);

    }

    private void calculateVelocity() {
        double worstResult = 0;
        double velocity_val = 0.0;

        worstResult = particleList.get(COUNT_OF_PARTICLES - 1).getParticleBest();

        for (int i = 0; i < COUNT_OF_PARTICLES; i++) {
            velocity_val = (MAX_VELOCITY * particleList.get(i).getParticleBest()) / worstResult;

            if (velocity_val > MAX_VELOCITY) {
                particleList.get(i).setParticleVelocity(MAX_VELOCITY);
            } else if (velocity_val < 0.0) {
                particleList.get(i).setParticleVelocity(0.0);
            } else {
                particleList.get(i).setParticleVelocity(velocity_val);
            }
        }

    }

    private void calculateParticleUpdation() {

        for (int i = 1; i < COUNT_OF_PARTICLES; i++) {
            int changes_in_Particle = (int) Math.floor(Math.abs(particleList.get(i).getParticleVelocity()));
            System.out.println("Change in a particle " + i + "->" + changes_in_Particle);
            for (int j = 0; j < changes_in_Particle; j++) {

                if (new Random().nextBoolean()) {
                    arrangeInRandomWay(i);
                }

                particleCopying(i - 1, i);
            }

            calculateTotalDistance(i);
        }

    }

    public void print() {
        if (particleList.get(0).getParticleBest() <= TARGET) {

            System.out.println("Reached target successfully!");
        } else {
            System.out.println("Not reached!");
        }
        System.out.print("Shortest Path -> ");
        for (int j = 0; j < COUNT_OF_CITY; j++) {
            System.out.print(particleList.get(0).getData(j) + ", ");
        }
        System.out.print("Path Covered(dist) " + particleList.get(0).getParticleBest() + "\n");

    }

    private void particleCopying(int s, int d) {
        Particle best_val = particleList.get(s);
        int target1 = new Random().nextInt(COUNT_OF_CITY);
        int target2 = 0;
        int index1 = 0;
        int index2 = 0;
        int tempIndex = 0;

        for (int i = 0; i < COUNT_OF_CITY; i++) {
            if (best_val.getData(i) == target1) {
                if (i == COUNT_OF_CITY - 1) {
                    target2 = best_val.getData(0);
                } else {
                    target2 = best_val.getData(i + 1);
                }
                break;
            }
        }

        for (int j = 0; j < COUNT_OF_CITY; j++) {
            if (particleList.get(d).getData(j) == target1) {
                index1 = j;
            }
            if (particleList.get(d).getData(j) == target2) {
                index2 = j;
            }
        }

        if (index1 == COUNT_OF_CITY - 1) {
            tempIndex = 0;
        } else {
            tempIndex = index1 + 1;
        }

        int temp = particleList.get(d).getData(tempIndex);
        particleList.get(d).setData(tempIndex, particleList.get(d).getData(index2));
        particleList.get(d).setData(index2, temp);

    }

    private void calculateTotalDistance(int index) {
        Particle p = null;
        p = particleList.get(index);
        p.setParticleBest(0.0);

        for (int i = 0; i < COUNT_OF_CITY; i++) {
            if (i == COUNT_OF_CITY - 1) {
                p.setParticleBest(p.getParticleBest() + TSPUtility.getDistance(p.getData(COUNT_OF_CITY - 1), p.getData(0), cityList));
            } else {
                p.setParticleBest(p.getParticleBest() + TSPUtility.getDistance(p.getData(i), p.getData(i + 1), cityList));
            }
        }

    }

}
