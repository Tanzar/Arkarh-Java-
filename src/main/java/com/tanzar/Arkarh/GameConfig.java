/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh;

/**
 *
 * @author Tanzar
 */
public class GameConfig {
    private int abilityLockTick;
    
    public GameConfig(){
        this.abilityLockTick = 200;
    }
    
    public int getAbilityLockTick(){
        if(this.abilityLockTick > 1000){
            return 1000;
        }
        if(this.abilityLockTick < 0){
            return 0;
        }
        return this.abilityLockTick;
    }
}
