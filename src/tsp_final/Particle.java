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
public class Particle implements Comparable<Particle>, TSPConstants {

    private int particleData[];
    private double particleBest;
    private double particleVelocity;

    public Particle() {
        this.particleBest = 0;
        this.particleVelocity = 0.0;
        this.particleData = new int[COUNT_OF_CITY];
    }

    public int compareTo(Particle p) {
        if (this.getParticleBest() < p.getParticleBest()) {
            return -1;
        } else if (this.getParticleBest() > p.getParticleBest()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getData(int index) {
        return this.particleData[index];
    }

    public void setData(int index, int val) {
        this.particleData[index] = val;

    }

    public double getParticleBest() {
        return particleBest;
    }

    public void setParticleBest(double particleBest) {
        this.particleBest = particleBest;
    }

    public double getParticleVelocity() {
        return this.particleVelocity;
    }

    public void setParticleVelocity(double particleVelocity) {
        this.particleVelocity = particleVelocity;

    }
}
