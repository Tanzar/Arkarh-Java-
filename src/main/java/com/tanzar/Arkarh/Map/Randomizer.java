/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map;

import java.util.Random;

/**
 *
 * @author Tanzar
 */
public class Randomizer {
    private int state;
    private int seed;
    
    public Randomizer(int seed){
        this.state = seed;
        this.seed = seed;
    }

    public Randomizer() {
        Random rand = new Random();
        this.seed = rand.nextInt();
        this.state = this.seed;
    }
    
    public int random(int min, int max){
        if(min != max){
            int a = 1103515245;
            int b = 12345;
            int m = (int) Math.pow(2, 32);
            state = (a * state + b) % m;
            int number = min + (Math.abs(state) % (max - min));
            if(number < 0){
                number = Math.abs(number);
            }
            return number;
        }
        else{
            return min;
        }
    }
    
    public static int generateSeed(){
        Random rand = new Random();
        return rand.nextInt();
    }
    
    public int getSeed(){
        return this.seed;
    }
    
    public void setSeed(int seed){
        this.seed = seed;
        this.state = seed;
    }
}
