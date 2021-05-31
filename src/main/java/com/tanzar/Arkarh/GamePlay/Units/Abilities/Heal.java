/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilityGroup;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author Tanzar
 */
public class Heal extends UnitAbility{
    private double spellPowerBonus;
    private int baseHealing;
    private int range;
    
    public Heal(){
        super(Trigger.onAction, UnitAbilityGroup.heal);
        this.spellPowerBonus = 0.05;
        this.baseHealing = 1;
        this.range = 1;
    }

    public Heal(UnitAbilityEntity entity) {
        super(entity, Trigger.onAction);
        String tmp = entity.getEffect();
        Json json = new Json(tmp);
        this.spellPowerBonus = json.getDouble("spellPowerBonus");
        this.baseHealing = json.getInt("baseHealing");
        this.range = json.getInt("range");
    }
    
    public Heal(Json json){
        super(json);
        this.spellPowerBonus = json.getDouble("spellPowerBonus");
        this.baseHealing = json.getInt("baseHealing");
        this.range = json.getInt("range");
    }

    @Override
    protected boolean additionalConditions(Unit source) {
        int tickNumber = this.report.getTickCount();
        if(tickNumber > 150){
            return false;
        }
        return true;
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        Side side = battlefield.getSide(source);
        int position = source.getPosition();
        Units targets = TargetsSelection.getTargets(TargetsSelection.piercing, position, side, 0, range);
        return targets;
    }

    @Override
    protected void onUse(Unit source, Units targets) {
        int healValue = this.baseHealing;
        double spellPowerMultiplier = 1 + source.getTotalSpellPower() * this.spellPowerBonus;
        healValue = (int) Math.round(healValue * spellPowerMultiplier);
        for(Unit target: targets.toArray()){
            target.heal(healValue);
            String stringFormat = source.toString() + " heals " + target.toString() + " for " + healValue + ".";
            this.report.abilityUse(this, source, target, stringFormat);
        }
    }

    @Override
    protected void formJson(Json json) {
        json.add("spellPowerBonus", this.spellPowerBonus);
        json.add("baseHealing", this.baseHealing);
        json.add("range", this.range);
    }
    
}
