/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author spako
 */
public abstract class UnitAbility {
    protected Unit source;
    protected CombatReport report;
    private int charges;
    private Trigger trigger;
    
    public UnitAbility(Unit source, Trigger trigger, int charges){
        this.source = source;
        this.trigger = trigger;
        this.charges = charges;
    }
    
    public void use(Trigger mainTrigger, Battlefield battlefield, CombatReport report){
        if(this.charges != 0){
            if(this.trigger.equals(mainTrigger) && this.additionalConditions()){
                this.report = report;
                Units targets = this.setupTargets(battlefield);
                this.onUse(targets);
                this.reduceCharges();
            }
        }
    }
    
    protected abstract boolean additionalConditions();
    
    protected abstract Units setupTargets(Battlefield battlefield);
    
    protected abstract void onUse(Units targets);
    
    private void reduceCharges(){
        if(this.charges > 0){
            this.charges--;
        }
    }
    
    public int getCharges(){
        return this.charges;
    }
}
