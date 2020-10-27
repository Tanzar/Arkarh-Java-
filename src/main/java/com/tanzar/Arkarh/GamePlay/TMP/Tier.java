/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.TMP;

/**
 *
 * @author spako
 */
public enum Tier {
    base(1),
    baseUpgrade(2),
    advanced(3),
    advancedUpgrade(4),
    elite(5),
    eliteUpgrade(6);
    
    private int rank;
    
    Tier(int rank){
        this.rank = rank;
    }
    
    public int getRank(){
        return this.rank;
    }
    
    public boolean isHigherTierThan(Tier comparable){
        if(this.rank > comparable.getRank()){
            return true;
        }
        else{
            return false;
        }
    }
}
