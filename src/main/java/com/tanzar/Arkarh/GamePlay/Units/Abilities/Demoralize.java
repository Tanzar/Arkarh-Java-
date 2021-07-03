/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Database.Entities.Units.UnitAbilityEntity;
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
public class Demoralize extends UnitAbility{
    private int range;
    private int multiplier;
    
    public Demoralize(){
        super(Trigger.onAction, UnitAbilityGroup.demoralize);
        this.range = 0;
        this.multiplier = 1;
    }
    
    public Demoralize(UnitAbilityEntity entity){
        super(entity, Trigger.onAction);
        String tmp = entity.getEffect();
        Json json = new Json(tmp);
        this.range = json.getInt("range");
        this.multiplier = json.getInt("multiplier");
    }
    
    public Demoralize(Json json){
        super(json);
        this.range = json.getInt("range");
        this.multiplier = json.getInt("multiplier");
    }

    @Override
    protected boolean additionalConditions(Unit source) {
        return true;
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        int position = source.getPosition();
        Side side = battlefield.getOppositeSide(source);
        TargetsSelection selection = TargetsSelection.piercing;
        Units targets = selection.getTargets(position, side, 0, this.range);
        return targets;
    }

    @Override
    protected void onUse(Unit source, Units targets) {
        for(Unit target: targets.toArray()){
            for(int i = 0; i < this.multiplier; i++){
                target.takeMoraleDamage();
            }
            String stringFormat = source.toString() + " " + this.name + " demoralizes " + target.toString() + ".";
            this.report.abilityUse(this, source, target, stringFormat);
        }
    }

    @Override
    protected void formJson(Json json) {
        json.add("range", this.range);
        json.add("multiplier", this.multiplier);
    }
    
}
