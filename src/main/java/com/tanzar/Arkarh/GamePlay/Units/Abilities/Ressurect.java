/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilityGroup;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author Tanzar
 */
public class Ressurect extends UnitAbility{
    public int ressurected;
    public int healthPercentage;
    
    public Ressurect(){
        super(Trigger.onAction, UnitAbilityGroup.ressurect);
        this.ressurected = 1;
        this.healthPercentage = 20;
    }
    
    public Ressurect(UnitAbilityEntity entity){
        super(entity, Trigger.onAction);
        String tmp = entity.getEffect();
        Json json = new Json(tmp);
        this.ressurected = json.getInt("ressurected");
        this.healthPercentage = json.getInt("healthPercentage");
    }
    
    public Ressurect(Json json){
        super(json);
        this.ressurected = json.getInt("ressurected");
        this.healthPercentage = json.getInt("healthPercentage");
    }

    @Override
    protected boolean additionalConditions(Unit source) {
        return true;
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        Units deadAllies = battlefield.getSide(source).getDeadReserves();
        Units targets = this.setupTargets(deadAllies);
        if(targets.isEmpty()){
            this.charges++;
        }
        return targets;
    }
    
    private Units setupTargets(Units deadAllies){
        Units targets = new Units();
        for(int i = 0; i < this.ressurected; i++){
            Unit unitToRessurect = null;
            for(Unit unit: deadAllies.toArray()){
                if(unit.canBeRessurected()){
                    if(unitToRessurect == null){
                        unitToRessurect = unit;
                    }
                    else{
                        if(unit.isHigherTierThan(unitToRessurect)){
                            unitToRessurect = unit;
                        }
                    }
                }
            }
            targets.add(unitToRessurect);
            deadAllies.remove(unitToRessurect);
        }
        return targets;
    }
    
    @Override
    protected void onUse(Unit source, Units targets) {
        int bonus = source.getTotalSpellPower() * 2;
        int percentage = this.healthPercentage + bonus;
        for(Unit target: targets.toArray()){
            int restoredHealth = target.ressurect(percentage);
            String stringFormat = source.toString() + " ressurects " + target.toString() + " with " + restoredHealth + " health.";
            this.report.abilityUse(this, source, target, stringFormat);
        }
    }

    @Override
    protected void formJson(Json json) {
        json.add("ressurected", this.ressurected);
        json.add("healthPercentage", this.healthPercentage);
    }
    
}
