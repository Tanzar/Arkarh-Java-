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
public class Regeneration extends UnitAbility{
    private int baseHealing;
    
    public Regeneration(){
        super(Trigger.onAction, UnitAbilityGroup.regeneration);
        this.baseHealing = 1;
    }

    public Regeneration(UnitAbilityEntity entity) {
        super(entity, Trigger.onAction);
        String tmp = entity.getEffect();
        Json json = new Json(tmp);
        this.baseHealing = json.getInt("baseHealing");
    }
    
    public Regeneration(Json json){
        super(json);
        this.baseHealing = json.getInt("baseHealing");
    }

    @Override
    protected boolean additionalConditions(Unit source) {
        return true;
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        Units targets = new Units();
        targets.add(source);
        return targets;
    }

    @Override
    protected void onUse(Unit source, Units targets) {
        int health = source.getTotalHealth();
        double percentage = ((double) this.baseHealing) / 100;
        int value = (int) Math.round(percentage * health);
        source.heal(value);
        this.report.abilityUse(this, source, source, source.toString() + " regenerates " + value + " health.");
    }

    @Override
    protected void formJson(Json json) {
        json.add("baseHealing", this.baseHealing);
    }
}
