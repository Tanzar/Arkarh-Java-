/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

/**
 *
 * @author spako
 */
public abstract class UnitAbility {
    
    private Trigger trigger;
    
    public UnitAbility(Trigger trigger){
        this.trigger = trigger;
    }
    
    public void use(Trigger mainTrigger){
        if(this.trigger.equals(mainTrigger) && this.additionalConditions()){
            this.onUse();
        }
    }
    
    protected abstract boolean additionalConditions();
    
    protected abstract void onUse();
}
