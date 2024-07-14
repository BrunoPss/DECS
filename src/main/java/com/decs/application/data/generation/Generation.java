package com.decs.application.data.generation;

/**
 * <b>Generation Class</b>
 * <p>
 *      This class represents one evolutionary generation, containing its respective information.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class Generation {
    //Internal Data
    private int generation;
    private double fitness;

    /**
     * Generation Class Constructor
     * @param generation Generation number
     * @param fitness Fitness value for the respective generation
     */
    public Generation(int generation, double fitness) {
        this.generation = generation;
        this.fitness = fitness;
    }

    //Get Methods
    public int getGeneration() { return this.generation; }
    public double getFitness() { return this.fitness; }

    //Set Methods


    //Methods


    //Overrides


    //Internal Functions


}