/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Modifiers;

/**
 *
 * @author spako
 */
public class Active {
    
    private ActiveEffect effect;
    private int value;
    
    public Active(ActiveEffect effect, int value){
        this.effect = effect;
        this.value = value;
    }
    
    public ActiveEffect getEffect(){
        return this.effect;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public double getValueAsPercentage(){
        return (1 - this.value/100);
    }
    
    public void changeValue(int value){
        this.value += value;
    }
    
    public boolean isSimillar(Active active){
        return this.effect == active.getEffect();
    }
    
    public boolean isEffect(ActiveEffect effect){
        return this.effect == effect;
    }
    
    public Active copy(){
        return new Active(this.effect, this.value);
    }
}
