/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.Containers.Gameplay.UnitAbilityEntities;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilityGroup;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import com.tanzar.Arkarh.Services.BeanUtil;
import com.tanzar.Arkarh.Services.UnitsService;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author Tanzar
 */
@Configurable
public class Summon extends UnitAbility{
    
    private int summonedId;
    private int count;
    
    public Summon(){
        super(Trigger.onAction, UnitAbilityGroup.summon);
        this.summonedId = 0;
        this.count = 1;
    }
    
    public Summon(UnitAbilityEntity entity) {
        super(entity, Trigger.onAction);
        Json json = new Json(entity.getEffect());
        String triggerString = json.getString("trigger");
        this.trigger = Trigger.valueOf(triggerString);
        this.summonedId = json.getInt("summonedId");
        this.count = json.getInt("count");
    }
    
    public Summon(Json json){
        super(json);
        String triggerString = json.getString("trigger");
        this.trigger = Trigger.valueOf(triggerString);
        this.summonedId = json.getInt("summonedId");
        this.count = json.getInt("count");
    }

    @Override
    protected boolean additionalConditions(Unit source) {
        return true;
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        Units units = this.getSummonedUnits();
        this.setupSummonedUnits(source, battlefield, units);
        return units;
    }
    
    private Units getSummonedUnits(){
        UnitsService bean = BeanUtil.getBean(UnitsService.class);
        UnitEntity template = bean.getByIdAsEntity(this.summonedId);
        Units units = new Units();
        for(int i = 0; i < this.count; i++){
            UnitAbilityEntities abilities = bean.getUnitAbilitiesAsEntities(this.summonedId);
            Unit unit = new Unit(template, abilities.toArray());
            units.add(unit);
        }
        return units;
    }
    
    private void setupSummonedUnits(Unit source, Battlefield battlefield, Units summonedUnits){
        Side side = battlefield.getSide(source);
        for(Unit unit: summonedUnits.toArray()){
            side.addUnit(unit);
        }
    }

    @Override
    protected void onUse(Unit source, Units targets) {
        for(Unit summonedUnit: targets.toArray()){
            report.abilityUse(this, source, summonedUnit, source.toString() + " uses " + this.name + " to summon " + summonedUnit.toString() + " on battlefield.");
        }
        if(targets.isEmpty()){
            report.abilityUse(this, source, source, "None answered call of " + source.toString());
        }
    }

    @Override
    protected void formJson(Json json) {
        json.add("trigger", this.trigger);
        json.add("summonedId", this.summonedId);
        json.add("count", this.count);
    }
    
}
