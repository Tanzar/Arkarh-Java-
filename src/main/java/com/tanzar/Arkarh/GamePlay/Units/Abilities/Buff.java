/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.TargetsGroup;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveSource;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilityGroup;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author Tanzar
 */
public class Buff extends UnitAbility{
    private TargetsGroup targetsGroup;
    private Passives passives;
    
    public Buff(){
        super(Trigger.onEntry, UnitAbilityGroup.buff);
        this.targetsGroup = TargetsGroup.self;
        this.passives = new Passives();
    }
    
    public Buff(UnitAbilityEntity entity){
        super(entity, Trigger.onEntry);
        Json json = new Json(entity.getEffect());
        String triggerString = json.getString("trigger");
        this.trigger = Trigger.valueOf(triggerString);
        String targetsGroupString = json.getString("targetsGroup");
        this.targetsGroup = TargetsGroup.valueOf(targetsGroupString);
        Json passives = json.getJson("passives");
        this.passives = new Passives(passives);
    }
    
    public Buff(Json json){
        super(json);
        String triggerString = json.getString("trigger");
        this.trigger = Trigger.valueOf(triggerString);
        String targetsGroupString = json.getString("targetsGroup");
        this.targetsGroup = TargetsGroup.valueOf(targetsGroupString);
        Json passivesString = json.getJson("passives");
        this.passives = new Passives(passivesString);
    }

    @Override
    protected boolean additionalConditions(Unit source) {
        return true;
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        Units targets = new Units();
        switch(this.targetsGroup){
            case self:
                targets.add(source);
                break;
            case allies:
                targets = battlefield.getSide(source).getFieldedUnits();
                break;
            case enemies:
                targets = battlefield.getOppositeSide(source).getFieldedUnits();
                break;
        }
        return targets;
    }

    @Override
    protected void onUse(Unit source, Units targets) {
        this.report(source);
        for(Unit target: targets.toArray()){
            for(Passive passive: this.passives.toArray()){
                Passive copy = new Passive(passive);
                copy.setIndex(this.id);
                copy.setSource(PassiveSource.unit);
                target.addPassive(copy);
            }
        }
    }
    
    private void report(Unit source){
        String text = source.toString() + " uses " + this.getName() + " on " + this.targetsGroup + ".";
        this.report.abilityUse(this, source, source, text);
    }

    @Override
    protected void formJson(Json json) {
        json.add("trigger", this.trigger);
        json.add("targetsGroup", this.targetsGroup.toString());
        json.add("passives", this.passives);
    }
    
}
